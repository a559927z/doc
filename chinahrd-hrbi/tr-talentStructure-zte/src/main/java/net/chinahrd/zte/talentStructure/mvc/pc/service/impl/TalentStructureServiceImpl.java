package net.chinahrd.zte.talentStructure.mvc.pc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgListDto;
import net.chinahrd.entity.dto.pc.teamImg.TeamImgEmpDto;
import net.chinahrd.entity.dtozte.pc.talentstructure.TalentstructureDto;
import net.chinahrd.module.SysCache;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.TableKeyUtil;
import net.chinahrd.zte.talentStructure.mvc.pc.dao.TalentStructureDao;
import net.chinahrd.zte.talentStructure.mvc.pc.service.TalentStructureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * 人力结构Service
 * @author jxzhang by 2016-02-21
 */
@Service("talentStructureService")
public class TalentStructureServiceImpl implements TalentStructureService {
	
	@Autowired
	TalentStructureDao talentStructureDao;

	@Override
	public TalentstructureDto findBudgetAnalyse(String organId, String customerId){

		List<Integer> personTypeKey = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RLCB_personType);

		TalentstructureDto dto = talentStructureDao.findBudgetAnalyse(organId, customerId, DateUtil.getDBCurdate(), personTypeKey);
		if(null == dto){
			return null;
		}
		Double empCount = dto.getEmpCount();
		Double number = dto.getNumber();
		if(null == number){
			number = 0.0;
			dto.setHasBudgetPer(false);
		}else{
			dto.setHasBudgetPer(true);
		}
		dto.setUsableEmpCount(number - empCount);

		if(0.0 == empCount && 0.0 == number
				|| 0.0 == empCount
				|| 0.0 == number){
			dto.setBudgetPer(0.0);
		}else{
			dto.setBudgetPer(empCount / number);
		}
		Double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_NORMAL);
		Double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_RISK);
		dto.setNormal(normal);
		dto.setRisk(risk);
		return dto;
	}

	@Override
	public boolean updateConfigWarnVal(String customerId, Double normal, Double risk) {
		try {
			if(null != normal && null != risk){
				talentStructureDao.updateConfigWarnValByNormal(customerId, normal);
				talentStructureDao.updateConfigWarnValByRisk(customerId, risk);
				SysCache.config.update();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	private PaginationDto<TalentstructureDto> getPagination(List<TalentstructureDto> list, Integer page, Integer rows){
		PaginationDto<TalentstructureDto> dto = new PaginationDto<TalentstructureDto>(page, rows);
		dto.setRecords(list.size());
		Integer to=dto.getPage()*dto.getRow()>dto.getRecords()?dto.getRecords():dto.getPage()*dto.getRow();
		dto.setRows(list.subList(dto.getOffset(), to));
		return dto;
	}

	private PaginationDto<TeamImgEmpDto> getPaginationImg(List<TeamImgEmpDto> list, Integer page, Integer rows){
		PaginationDto<TeamImgEmpDto> dto = new PaginationDto<TeamImgEmpDto>(page, rows);
		dto.setRecords(list.size());
		Integer to=dto.getPage()*dto.getRow()>dto.getRecords()?dto.getRecords():dto.getPage()*dto.getRow();
		dto.setRows(list.subList(dto.getOffset(), to));
		return dto;
	}

	/*
    * 管理者员工分布
    * */
	@Override
	public Map<String, Object> getAbilityEmpCount(String organId, String customerId, Date day, List<String> crowds){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		Map<String, Integer> abilityEmpCountPie = queryAbilityEmpCountPie(rsDb);
		Map<String, Map<String, Integer>> abilityEmpCount = queryAbilityEmpCount(rsDb, organId);

		rs.put("abilityEmpCountPie", abilityEmpCountPie);			// 管理者员工分布-饼图
		rs.put("abilityEmpCount", abilityEmpCount);					// 管理者员工分布-表
		return rs;
	}

	@Override
	public PaginationDto<TalentstructureDto> getAbilityEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		List<TalentstructureDto> abilityEmpList = queryAbilityEmpList(rsDb, id);

		return getPagination(abilityEmpList, page, rows);
	}

	/*
    * 职级分布
    * */
	@Override
	public Map<String, Object> getAbilityCurtEmpCount(String organId, String customerId, Date day, List<String> crowds){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		Map<Integer, List<TalentstructureDto>> empDistinguishByHasAb = getEmpDistinguishByHasAb(rsDb);

		Map<String, Integer> abilityCurtEmpCountPie = queryAbilityCurtEmpCountPie(empDistinguishByHasAb.get(1), empDistinguishByHasAb.get(2));
		Map<String, Map<String, Integer>> abilityCurtEmpCount = queryAbilityCurtEmpCount(rsDb, organId);


		rs.put("abilityCurtEmpCountPie", abilityCurtEmpCountPie);	// 职级分布-饼图
		rs.put("abilityCurtEmpCount", abilityCurtEmpCount);			// 职级分布-表

		return rs;
	}

	@Override
	public PaginationDto<TalentstructureDto> getAbilityCurtEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		Map<Integer, List<TalentstructureDto>> empDistinguishByHasAb = getEmpDistinguishByHasAb(rsDb);

		List<TalentstructureDto> abilityCurtEmpList = queryAbilityCurtEmpList(empDistinguishByHasAb.get(1), empDistinguishByHasAb.get(2), id);

		return getPagination(abilityCurtEmpList, page, rows);
	}

	/*
    * 职位序列分布
    * */
	@Override
	public Map<String, Object> getSeqEmpCount(String organId, String customerId, Date day, List<String> crowds){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);
		Map<Integer, List<TalentstructureDto>> empDistinguishByHasAb = getEmpDistinguishByHasAb(rsDb);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		Map<String, Integer> abilityCurtEmpCountPie = queryAbilityCurtEmpCountPie(empDistinguishByHasAb.get(1), empDistinguishByHasAb.get(2));
		Map<String, Integer> seqEmpCountBar = querySeqEmpCountBar(rsDb);
		Map<String, Map<String, Integer>> seqEmpCount = querySeqEmpCount(rsDb);

		rs.put("abilityCurtEmpCountPie", abilityCurtEmpCountPie);	// 职级分布-饼图
		rs.put("seqEmpCountBar", seqEmpCountBar);					// 职位序列分布-柱状图
		rs.put("seqEmpCount", seqEmpCount);							// 职位序列分布-表

		return rs;
	}

	/*
    * 组织分布
    * */
	@Override
	public Map<String, Object> getOrganEmpCount(String organId, String customerId, Date day, List<String> crowds){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		Map<String, Integer> organEmpCount = queryOrganEmpCount(rsDb, organId);

		rs.put("organEmpCount", organEmpCount);						// 组织分布
		return rs;
	}

	@Override
	public PaginationDto<TalentstructureDto> getOrganEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		List<TalentstructureDto> OrganEmpList = queryOrganEmpList(rsDb, organId, id);

		return getPagination(OrganEmpList, page, rows);
	}

	/*
    * 工作地点分布
    * */
	@Override
	public Map<String, Object> getWorkplaceEmpCount(String organId, String customerId, Date day, List<String> crowds){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		Map<String, Integer> workplaceEmpCount = queryWorkplaceEmpCount(rsDb);

		rs.put("workplaceEmpCount", workplaceEmpCount);				// 工作地点分布
		return rs;
	}

	@Override
	public PaginationDto<TalentstructureDto> getWorkplaceEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows){
		Map<String, Object> rs = new LinkedHashMap<String, Object>();

		List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId, day, crowds);

		if(null == rsDb || rsDb.size() == 0) {return null;}

		List<TalentstructureDto> workplaceEmpList = queryWorkplaceEmpList(rsDb, id);

		return getPagination(workplaceEmpList, page, rows);
	}

	@Override
	public Map<String, Integer> queryAbEmpCountBarBySeqId(String organId, String customerId, String seqId, Date day, List<String> crowds) {
		Map<String, Integer> rsMap = new LinkedHashMap<>();
		List<TalentstructureDto> rsList = talentStructureDao.queryAbEmpCountBarBySeqId(organId, customerId, seqId, day, crowds);

		MultiValueMap<String, TalentstructureDto> m = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : rsList) {
			if (null != dto.getAbCurtName()) {
				m.add(dto.getAbCurtName(), dto);
			}
		}
		for (Map.Entry<String, List<TalentstructureDto>> entry : m.entrySet()) {
			String key = entry.getKey();
			List<TalentstructureDto> groupDtos = entry.getValue();

			rsMap.put(key, groupDtos.size());
		}
		return rsMap;
	}

	@Override
	public PaginationDto<TalentstructureDto> getAbEmpCountBarBySeqIdList(String organId, String seqId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows){
		List<TalentstructureDto> rsList = talentStructureDao.queryAbEmpCountBarBySeqId(organId, customerId, seqId, day, crowds);

		List<TalentstructureDto> list = new LinkedList<>();
		for (TalentstructureDto dto : rsList) {
			if (null != dto.getAbCurtName() && dto.getAbCurtName().equals(id)) {
				list.add(dto);
			}
		}

		return getPagination(list, page, rows);
	}


	/**
	 * 职位序列分布 by jxzhang 2016-02-26
	 * @param rsDb
	 * @return
	 */
	private Map<String, Map<String, Integer>> querySeqEmpCount(List<TalentstructureDto> rsDb) {
		// TODO 排序使用
		List<SequenceItemsDto> sequence = CacheHelper.getSequence();


		MultiValueMap<String, TalentstructureDto> m = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : rsDb) {
			if (null != dto.getAbCurtName()) {
				m.add(dto.getAbCurtName(), dto);
			}
		}
		Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<>();

		for (Map.Entry<String, List<TalentstructureDto>> abEntry : m.entrySet()) {
			List<TalentstructureDto> groupDtos = abEntry.getValue();

			MultiValueMap<String, TalentstructureDto> seqMap = new LinkedMultiValueMap<String, TalentstructureDto>();
			for (TalentstructureDto dto : groupDtos) {
				if (null != dto.getSequenceName()) {
					seqMap.add(dto.getSequenceName(), dto);
				}
			}

			Map<String, Integer> rsSeqMap = new LinkedHashMap<>();
			for (Map.Entry<String, List<TalentstructureDto>> seqEntry : seqMap.entrySet()) {

				rsSeqMap.put(seqEntry.getKey(), seqEntry.getValue().size());
			}
			Map<String, Integer> sortMap = CollectionKit.sortMapByValue(rsSeqMap);
			rsMap.put(abEntry.getKey(), sortMap);
		}
		return rsMap;
	}

	/**
	 * 能力层级统计饼图 by jxzhang on 2016-02-23
	 * @param rsDb
	 * @return
	 */
	private Map<String, Integer> queryAbilityEmpCountPie(List<TalentstructureDto> rsDb) {
		Map<Integer, List<TalentstructureDto>> empDistinguish = getEmpDistinguishByMgr(rsDb);
		List<TalentstructureDto> mgrList = empDistinguish.get(1);
		List<TalentstructureDto> empList = empDistinguish.get(2);

		// 能力层级分组
		MultiValueMap<String, TalentstructureDto> abMap = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : mgrList) {
			if (null != dto.getAbilityId()) {
				abMap.add(dto.getAbilityId(), dto);
			}
		}
		Map<String,Integer> mackUpRs = new LinkedHashMap<String,Integer>();
//		Integer maxAbLevel = rsDb.get(0).getMaxAbLevel();
//		for (int i = maxAbLevel; i >=1; i--) {
//			mackUpRs.put(i+"级", 0);
//		}

		for (Map.Entry<String, List<TalentstructureDto>> abNameEntry : abMap.entrySet()) {
			// BUG:517 管理者员工分布：环形图右边的标识信息显示错误；不是显示5/4/3/2/1，与列表一致；
			String abId = abNameEntry.getKey();
			mackUpRs.put(
					CacheHelper.getAbilityName(abId),
					abNameEntry.getValue().size());
		}
		mackUpRs.put("员工", empList.size());
		return mackUpRs;
	}

	private List<TalentstructureDto> queryAbilityEmpList(List<TalentstructureDto> rsDb, String id) {
		Map<Integer, List<TalentstructureDto>> empDistinguish = getEmpDistinguishByMgr(rsDb);
		List<TalentstructureDto> mgrList = empDistinguish.get(1);
		List<TalentstructureDto> empList = empDistinguish.get(2);

		List<TalentstructureDto> list = new LinkedList<TalentstructureDto>();

		if(id.equals("员工")){
			list=empList;
		}else{
			for (TalentstructureDto dto : mgrList) {
				if (null != dto.getAbilityId() && dto.getAbilityName().equals(id)) {
					list.add(dto);
				}
			}
		}
		return list;
	}

	/**
	 * 能力层级统计表 by jxzhang on 2016-02-23
	 * @param rsDb
	 * @return
	 */
	private Map<String, Map<String, Integer>> queryAbilityEmpCount(List<TalentstructureDto> rsDb, String organId){
		Map<String, List<TalentstructureDto>> m = new LinkedHashMap<>();

		//BUG:497
		List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
		List<String> mgrAbNameList = CollectionKit.newList();
		if(null == underSubOrganIdList){
			return myselfSubCount(rsDb, organId, 1);
		}else{
			for (String string : underSubOrganIdList) {
				String pKey = string.split(",")[1];
				String pName = string.split(",")[2];
				List<TalentstructureDto> dtos = CollectionKit.newList();
				for (TalentstructureDto item : rsDb) {
					if(item.getFullPath().contains(pKey)){
						dtos.add(item);
					}
					// 管理序列的名称
					if(null != item.getSeqKey() && item.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)){
						mgrAbNameList.add(item.getAbilityName());
					}
				}
				m.put(pName, dtos);
			}
		}

		Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<>();
		//BUG:498
		Map<String, Map<String, Integer>> myselfMap = myselfSubCount(rsDb, organId, 1);
		if(null != myselfMap){
			rsMap.putAll(myselfMap);
		}


		for (Map.Entry<String, List<TalentstructureDto>> organNameEntry : m.entrySet()) {
			List<TalentstructureDto> groupDtos = organNameEntry.getValue();
			// 能力层级分组
			MultiValueMap<String, TalentstructureDto> abMap = new LinkedMultiValueMap<String, TalentstructureDto>();
			for (TalentstructureDto groupDto : groupDtos) {
				if(null != groupDto.getAbilityName() ){
					abMap.add(groupDto.getAbilityName(), groupDto);
				}
			}
			Integer mgrSize = 0;
			Integer empSize = 0;
//			Map<String, Integer> rsAbMap = CollectionKit.newMap();
			Map<String, Integer> rMap = new LinkedHashMap<String, Integer>();
			for (Map.Entry<String, List<TalentstructureDto>> abNameEntry : abMap.entrySet()) {
				// 在能力层级里面，区分管理者和谱通员工
				for (TalentstructureDto dto : abNameEntry.getValue()) {
					if(null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)){
						mgrSize++;
					}else{
						empSize++;
					}
				}
				// bug:504 管理者员工分布：列表不属于管理序列的职位层级不要显示；
				for (String mgrAbNameStr : mgrAbNameList) {
					if(mgrAbNameStr.equals(abNameEntry.getKey())){
//						rsAbMap.put(abNameEntry.getKey(), mgrSize);
						rMap.put(abNameEntry.getKey(), mgrSize);
						break;
					}
				}
				mgrSize = 0;
			}
//			rsAbMap.put("员工", empSize);
			rMap.put("员工", empSize);
			empSize = 0;
			rsMap.put(organNameEntry.getKey(), rMap);
		}
		return rsMap;
	}

	/**
	 * 计算选择机构
	 * @param rsDb
	 * @param organId
	 * @param groupByType
	 * @return
	 */
	private Map<String, Map<String, Integer>> myselfSubCount(List<TalentstructureDto> rsDb, String organId, int groupByType){
		Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<>();

		List<TalentstructureDto> myList = CollectionKit.newList();
		for (TalentstructureDto dto : rsDb) {
			if(dto.getOrganizationId().equals(organId)){
				myList.add(dto);
			}
		}

		if(myList.size() > 0){
			Map<String, Integer> myRsAbMap = new LinkedHashMap<String, Integer>();
			// 管理者员工分布-本机构
			if(1 == groupByType) {
				MultiValueMap<String, TalentstructureDto> myMap = new LinkedMultiValueMap<String, TalentstructureDto>();
				for (TalentstructureDto myOrgan : myList) {
					if(null != myOrgan.getAbilityName() ){
						myMap.add(myOrgan.getAbilityName(), myOrgan);
					}
				}
				// 管理序列的名称
				List<String> mgrAbNameList = CollectionKit.newList();
				for (TalentstructureDto item : rsDb) {
					if(null != item.getSeqKey() && item.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)){
						mgrAbNameList.add(item.getAbilityName());
					}
				}
				Integer myMgrSize = 0;
				Integer myEmpSize = 0;
				for (Map.Entry<String, List<TalentstructureDto>> abNameEntry : myMap.entrySet()) {
					for (TalentstructureDto dto : abNameEntry.getValue()) {
						if(null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)){
							myMgrSize++;
						}else{
							myEmpSize++;
						}
					}
					if(null != mgrAbNameList){
						// bug:504 管理者员工分布：列表不属于管理序列的职位层级不要显示；
						for (String mgrAbNameStr : mgrAbNameList) {
							if(mgrAbNameStr.equals(abNameEntry.getKey())){
								myRsAbMap.put(abNameEntry.getKey(), myMgrSize);
								break;
							}
						}
					}
					myMgrSize = 0;
				}
				myRsAbMap.put("员工", myEmpSize);
			}
			// 能力层级分布-本机构
			if(2 == groupByType){
				MultiValueMap<String, TalentstructureDto> myMap = new LinkedMultiValueMap<String, TalentstructureDto>();
				for (TalentstructureDto myOrgan : myList) {
					// BUG:514
					if(null != myOrgan.getAbCurtName() ){
						myMap.add(myOrgan.getAbCurtName(), myOrgan);
					}
				}
				Integer myMgrSize = 0;
				Integer myEmpSize = 0;
				for (Map.Entry<String, List<TalentstructureDto>> abNameEntry : myMap.entrySet()) {
					for (TalentstructureDto dto : abNameEntry.getValue()) {
						if(null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)){
							myMgrSize++;
						}else{
							myEmpSize++;
						}
					}
					myRsAbMap.put(abNameEntry.getKey(), myMgrSize);
					myMgrSize = 0;
				}
				myRsAbMap.put("员工", myEmpSize);
			}
			// 学历分布-本机构
			if(3 == groupByType){
				MultiValueMap<String, TalentstructureDto> degreeMap = new LinkedMultiValueMap<String, TalentstructureDto>();
				for (TalentstructureDto groupDto : myList) {
					if(null != groupDto.getDegree()){
						degreeMap.add(groupDto.getDegree(), groupDto);
					}
				}
				for (Map.Entry<String, List<TalentstructureDto>> degreeEntry : degreeMap.entrySet()) {

					String mapkey = degreeEntry.getKey();
					if (null != mapkey &&
							(!(mapkey.equals("大专")) && !(mapkey.equals("本科"))
									&& !(mapkey.equals("硕士")) && !mapkey.equals("博士"))){
						myRsAbMap.put("大专以下", degreeEntry.getValue().size());
					}
					myRsAbMap.put(degreeEntry.getKey(), degreeEntry.getValue().size());
				}
			}
			// 年龄分布-本机构
			if(4 == groupByType){
				Map<String, Integer> mapDto = new LinkedHashMap<String, Integer>();
				int[] seriesData = {0,0,0,0,0};
				List<TalentstructureDto> value = myList;
				for(TalentstructureDto dto : value){
					Integer compAge = dto.getCompanyAge();
					if(compAge >=0 && compAge <3){
						int temp = seriesData[0];
						seriesData[0] = temp+1;
					}else if(compAge >= 3 && compAge < 12){
						int temp = seriesData[1];
						seriesData[1] = temp+1;
					}else if(compAge >= 12 && compAge < 36){
						int temp = seriesData[2];
						seriesData[2] = temp+1;
					}else if(compAge >= 36 && compAge < 60){
						int temp = seriesData[3];
						seriesData[3] = +temp+1;
					}else{
						int temp = seriesData[4];
						seriesData[4] = temp+1;
					}
				}
				mapDto.put("3月以下", seriesData[0]);
				mapDto.put("3月-1年", seriesData[1]);
				mapDto.put("1-3年", seriesData[2]);
				mapDto.put("3-5年", seriesData[3]);
				mapDto.put("5年以上", seriesData[4]);

				myRsAbMap.putAll(mapDto);
			}
			rsMap.put(myList.get(0).getOrganizationName(), myRsAbMap);
			return rsMap;
		} else{
			return null;
		}
	}

	/**
	 * 人员区分-管理者
	 * @param rsDb
	 * @return
	 */
	private Map<Integer, List<TalentstructureDto>> getEmpDistinguishByMgr(List<TalentstructureDto> rsDb){
		List<TalentstructureDto> mgrList = CollectionKit.newList();
		List<TalentstructureDto> empList = CollectionKit.newList();
		for (TalentstructureDto dto : rsDb) {
			if(null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)){
				mgrList.add(dto);
			}else{
				empList.add(dto);
			}
		}
		Map<Integer, List<TalentstructureDto>> rs = CollectionKit.newMap();
		rs.put(1, mgrList);
		rs.put(2, empList);
		return rs;
	}

	/**
	 * 人员区分-有没有职级
	 * @param rsDb
	 * @return
	 */
	private Map<Integer, List<TalentstructureDto>> getEmpDistinguishByHasAb(List<TalentstructureDto> rsDb){
		List<TalentstructureDto> hasList = CollectionKit.newList();
		List<TalentstructureDto> notHasList = CollectionKit.newList();
		for (TalentstructureDto dto : rsDb) {
			if(null != dto.getAbilityId()){
				hasList.add(dto);
			}else{
				notHasList.add(dto);
			}
		}
		Map<Integer, List<TalentstructureDto>> rs = CollectionKit.newMap();
		rs.put(1, hasList);
		rs.put(2, notHasList);
		return rs;
	}

	/**
	 * 职级分布饼图 by jxzhang on 2016-02-24
	 * @param hasList
	 *      * @return
	 */
	private Map<String, Integer> queryAbilityCurtEmpCountPie(
			List<TalentstructureDto> hasList,
			List<TalentstructureDto> notHasList) {

		// 能力层级分组
		MultiValueMap<String, TalentstructureDto> abMap = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : hasList) {
			if (null != dto.getAbCurtName()) {
				abMap.add(dto.getAbCurtName(), dto);
			}
		}
		Map<String, Integer> rs = new LinkedHashMap<String, Integer>();

		for (Map.Entry<String, List<TalentstructureDto>> entry : abMap.entrySet()) {
			rs.put(entry.getKey(), entry.getValue().size());
		}

		// 补0操作
		Map<String, Integer> orderRs = new LinkedHashMap<String, Integer>();
		Integer maxAbLevel = hasList.get(0).getMaxAbLevel();
		for (int i = maxAbLevel; i >= 1; i--) {
			orderRs.put(i + "级", 0);
		}

		Set<Map.Entry<String, Integer>> entrySet = rs.entrySet();
		for (Map.Entry<String, Integer> entry : entrySet) {
			String key = entry.getKey();
			orderRs.put(key, entry.getValue());
		}
		if (notHasList.size() > 0) {
			orderRs.put("其他", notHasList.size());
		}
		return orderRs;
	}

	private List<TalentstructureDto> queryAbilityCurtEmpList(
			List<TalentstructureDto> hasList,
			List<TalentstructureDto> notHasList,
			String id) {

		List<TalentstructureDto> list = new ArrayList<>();

		if("其他".equals(id)){
			list=notHasList;
		}else{
			for (TalentstructureDto dto : hasList) {
				if (null != dto.getAbCurtName() && dto.getAbCurtName().equals(id)) {
					list.add(dto);
				}
			}
		}
		return list;
	}

	/**
	 * 职级分布表 by jxzhang on 2016-02-24
	 * @param rsDb
	 * @return
	 */
	private Map<String, Map<String, Integer>> queryAbilityCurtEmpCount(List<TalentstructureDto> rsDb, String organId) {

		Map<String, List<TalentstructureDto>> m = new LinkedHashMap<String, List<TalentstructureDto>>();
		//BUG:497
		List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
		if(null == underSubOrganIdList){
			return myselfSubCount(rsDb, organId, 1);
		} else {
			// 按子机构分组
			for (String string : underSubOrganIdList) {
				String pKey = string.split(",")[1];
				String pName = string.split(",")[2];
				List<TalentstructureDto> dtos = CollectionKit.newList();

				for (TalentstructureDto item : rsDb) {
					if(item.getFullPath().contains(pKey)){
						dtos.add(item);
					}
				}
				m.put(pName, dtos);
			}
		}

		Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<String, Map<String, Integer>>();
		//BUG:498
		// xxx
		Map<String, Map<String, Integer>> myselfMap = myselfSubCount(rsDb, organId, 2);
		if(null != myselfMap){
			rsMap.putAll(myselfMap);
		}


		for (Map.Entry<String, List<TalentstructureDto>> organNameEntry : m.entrySet()) {
			List<TalentstructureDto> groupDtos = organNameEntry.getValue();
			// 能力层级分组
			MultiValueMap<String, TalentstructureDto> abMap = new LinkedMultiValueMap<String, TalentstructureDto>();
			for (TalentstructureDto groupDto : groupDtos) {
				if(null != groupDto.getAbCurtName()){
					abMap.add(groupDto.getAbCurtName(), groupDto);
				}
			}
			Integer hasSize = 0;
			Integer notHasSize = 0;
			Map<String, Integer> rsAbMap = new LinkedHashMap<String, Integer>();

			for (Map.Entry<String, List<TalentstructureDto>> abNameEntry : abMap.entrySet()) {
				for (TalentstructureDto dto : abNameEntry.getValue()) {
					if(null != dto.getAbilityId()){
						hasSize++;
					}else{
						notHasSize++;
					}
				}
				rsAbMap.put(abNameEntry.getKey(), hasSize);
				hasSize = 0;
			}
			rsAbMap.put("其它", notHasSize);
			notHasSize = 0;
			rsMap.put(organNameEntry.getKey(), rsAbMap);
		}
		return rsMap;
	}

	/**
	 * 职位序列分布-柱状图 by jxzhang 2016-02-26
	 * @param rsDb
	 * @return
	 */
	private Map<String, Integer> querySeqEmpCountBar(List<TalentstructureDto> rsDb) {
		MultiValueMap<String, TalentstructureDto> m = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : rsDb) {
			if (null != dto.getSequenceId()) {
				m.add(dto.getSequenceId() +","+ dto.getSequenceName(), dto);
			}else{
				m.add("其他", dto);
			}
		}
		Map<String, Integer> rsMap = new LinkedHashMap<>();
		for (Map.Entry<String, List<TalentstructureDto>> entry : m.entrySet()) {
			String key = entry.getKey();
			List<TalentstructureDto> groupDtos = entry.getValue();

			rsMap.put(key, groupDtos.size());
		}
		Map<String, Integer> sortMap = CollectionKit.sortMapByValue(rsMap);
		return sortMap;
	}

	/**
	 * 组织分布 by jxzhang on 2016-02-23
	 * @param rsDb
	 * @return
	 */
	private Map<String, Integer> queryOrganEmpCount(List<TalentstructureDto> rsDb, String organId) {
		Map<String,Integer> rs = new LinkedHashMap<String, Integer>();

		List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
		if(null == underSubOrganIdList){
			List<TalentstructureDto> myList = CollectionKit.newList();
			for (TalentstructureDto dto : rsDb) {
				if(dto.getOrganizationId().equals(organId)){
					myList.add(dto);
				}
			}
			rs.put(myList.get(0).getOrganizationName(), myList.size());
			return rs;
		}
		MultiValueMap<String, TalentstructureDto> rsMap = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (String string : underSubOrganIdList) {
			String pKey = string.split(",")[1];
			String pName = string.split(",")[2];
			List<TalentstructureDto> dtos = CollectionKit.newList();

			for (TalentstructureDto item : rsDb) {
				if(item.getFullPath().contains(pKey)){
					dtos.add(item);
				}
			}
			rsMap.put(pName, dtos);
		}

		// BUG:506 组织分布：只显示了一个下级架构；
		List<TalentstructureDto> myOrganList = CollectionKit.newList();
		for (TalentstructureDto dto : rsDb) {
			if (null == dto.getOrganizationId()){
				continue;
			}
			// 选中的机构如果也有人，也显示出来
			if (null != dto.getOrganizationId() && dto.getOrganizationId().equals(organId)) {
				myOrganList.add(dto);
			}
		}
		if(myOrganList.size() > 0){
			rs.put(myOrganList.get(0).getOrganizationName(), myOrganList.size());
		}

		for (Map.Entry<String, List<TalentstructureDto>> entry : rsMap.entrySet()) {
			rs.put(entry.getKey(), entry.getValue().size());
		}
		return rs;
	}

	private List<TalentstructureDto> queryOrganEmpList(List<TalentstructureDto> rsDb, String organId, String id) {
		List<TalentstructureDto>  list = new LinkedList<TalentstructureDto>();

		List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
		if(null == underSubOrganIdList){
			for (TalentstructureDto dto : rsDb) {
				if(dto.getOrganizationId().equals(organId) && dto.getOrganizationName().equals(id)){
					list.add(dto);
				}
			}
			return list;
		}

		for (String string : underSubOrganIdList) {
			String pKey = string.split(",")[1];
			String pName = string.split(",")[2];
			if(pName.equals(id)) {
				for (TalentstructureDto item : rsDb) {
					if (item.getFullPath().contains(pKey)) {
						list.add(item);
					}
				}
			}
		}

		// BUG:506 组织分布：只显示了一个下级架构；
		List<TalentstructureDto> myOrganList = CollectionKit.newList();
		for (TalentstructureDto dto : rsDb) {
			if (null == dto.getOrganizationId()){
				continue;
			}
			// 选中的机构如果也有人，也显示出来
			if (null != dto.getOrganizationId() && dto.getOrganizationId().equals(organId) && dto.getOrganizationName().equals(id)) {
				list.add(dto);
			}
		}
		return list;
	}

	/**
	 * 工作地点分布 by jxzhang on 2016-02-24
	 * @param rsDb
	 * @return
	 */
	private Map<String, Integer> queryWorkplaceEmpCount(List<TalentstructureDto> rsDb) {
		MultiValueMap<String, TalentstructureDto> rsMap = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : rsDb) {
			if (null != dto.getWorkPlace()) {
				rsMap.add(dto.getWorkPlace(), dto);
			}
		}
		Map<String,Integer> rs = CollectionKit.newMap();
		for (Map.Entry<String, List<TalentstructureDto>> entry : rsMap.entrySet()) {
			rs.put("".equals(entry.getKey())?"其他":entry.getKey(), entry.getValue().size());
		}
		return rs;
	}

	private List<TalentstructureDto> queryWorkplaceEmpList(List<TalentstructureDto> rsDb, String id) {
		List<TalentstructureDto> list = new LinkedList<>();

		if("其他".equals(id)){
			id="";
		}
		for (TalentstructureDto dto : rsDb) {
			if (null != dto.getWorkPlace() && dto.getWorkPlace().equals(id)) {
				list.add(dto);
			}
		}
		return list;
	}


	//男女占比
	public List<TalentStructureTeamImgDto> getSexData(String organId, String customeId, Date day, List<String> crowds) {
		List<TalentStructureTeamImgDto> dto = talentStructureDao.getSexData(organId, customeId, day, crowds);

		return dto;
	}

	public PaginationDto<TalentStructureTeamImgListDto> getSexDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows) {
		PaginationDto<TalentStructureTeamImgListDto> dto = new PaginationDto<TalentStructureTeamImgListDto>(page, rows);
		Integer total = talentStructureDao.getSexDataListCount(organId, customeId, day, crowds, id);
		dto.setRecords(total);
		List<TalentStructureTeamImgListDto> list = talentStructureDao.getSexDataList(organId, customeId, day, crowds, id, dto.getOffset(), dto.getLimit());
		dto.setRows(list);
		return dto;
	}

	//婚姻状况
	public List<TalentStructureTeamImgDto> getMarryStatusData(String organId, String customeId, Date day, List<String> crowds) {
		List<TalentStructureTeamImgDto> dto = talentStructureDao.getMarryStatusData(organId, customeId, day, crowds);

		return dto;
	}
	public PaginationDto<TalentStructureTeamImgListDto> getMarryStatusDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows) {
		PaginationDto<TalentStructureTeamImgListDto> dto = new PaginationDto<TalentStructureTeamImgListDto>(page, rows);
		Integer total = talentStructureDao.getMarryStatusDataListCount(organId, customeId, day, crowds, id);
		dto.setRecords(total);
		List<TalentStructureTeamImgListDto> list = talentStructureDao.getMarryStatusDataList(organId, customeId, day, crowds, id, dto.getOffset(), dto.getLimit());
		dto.setRows(list);
		return dto;
	}

	//血型
	public List<TalentStructureTeamImgDto> getBloodData(String organId, String customeId, Date day, List<String> crowds) {
		List<TalentStructureTeamImgDto> dto = talentStructureDao.getBloodData(organId, customeId, day, crowds);
		return dto;
	}
	public PaginationDto<TalentStructureTeamImgListDto> getBloodDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows) {
		PaginationDto<TalentStructureTeamImgListDto> dto = new PaginationDto<TalentStructureTeamImgListDto>(page, rows);
		Integer total = talentStructureDao.getBloodDataListCount(organId, customeId, day, crowds, id);
		dto.setRecords(total);
		List<TalentStructureTeamImgListDto> list = talentStructureDao.getBloodDataList(organId, customeId, day, crowds, id, dto.getOffset(), dto.getLimit());
		dto.setRows(list);
		return dto;
	}

	//星座
	public  Map<String, Object> getStarData(String organId, String customeId, Date day, List<String> crowds) {
		List<TalentStructureTeamImgDto> dto = talentStructureDao.getStarData(organId, customeId, day, crowds);
		String[] xAxisData = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
		int[] seriesData = {0,0,0,0,0, 0,0,0,0,0, 0,0 };
		for (TalentStructureTeamImgDto d:dto){
			seriesData[d.getNumber()]=d.getTotal();
		}
		Map<String, Object> mapDto = CollectionKit.newMap();
		mapDto.put("xAxisData", xAxisData);
		mapDto.put("seriesData", seriesData);

		return mapDto;
	}
	public PaginationDto<TalentStructureTeamImgListDto> getStarDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows) {
		PaginationDto<TalentStructureTeamImgListDto> dto = new PaginationDto<TalentStructureTeamImgListDto>(page, rows);
		Integer total = talentStructureDao.getStarDataListCount(organId, customeId, day, crowds, id);
		dto.setRecords(total);
		List<TalentStructureTeamImgListDto> list = talentStructureDao.getStarDataList(organId, customeId, day, crowds, id, dto.getOffset(), dto.getLimit());
		dto.setRows(list);
		return dto;
	}

	//性格
	public List<TalentStructureTeamImgDto> getPersonalityData(String organId, String customeId, Date day, List<String> crowds) {
		List<TalentStructureTeamImgDto> dto = talentStructureDao.getPersonalityData(organId, customeId, day, crowds);

		return dto;
	}
	public PaginationDto<TalentStructureTeamImgListDto> getPersonalityDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows) {
		PaginationDto<TalentStructureTeamImgListDto> dto = new PaginationDto<TalentStructureTeamImgListDto>(page, rows);
		Integer total = talentStructureDao.getPersonalityDataListCount(organId, customeId, day, crowds, id);
		dto.setRecords(total);
		List<TalentStructureTeamImgListDto> list = talentStructureDao.getPersonalityDataList(organId, customeId, day, crowds, id, dto.getOffset(), dto.getLimit());
		dto.setRows(list);
		return dto;
	}
































	/**
	 * 男女比例
	 * @param rs
	 * @return
	 */
//	private List<KVItemDto> packagSex(List<TeamImgEmpDto> rs){
//		MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
//		for (TeamImgEmpDto dto : rs) {
//			m.add(dto.getSex(), dto);
//		}
//		List<KVItemDto> dtos = CollectionKit.newList();
//		boolean bool=false;
//		for (Map.Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
//			if(empEntry.getKey() instanceof String){
//				String key = empEntry.getKey();
//				if (key.equals("m")) {
//					bool=true;
//					KVItemDto dto = new KVItemDto();
//					dto.setK("男");
//					dto.setV(empEntry.getValue().size() + "");
//					dtos.add(dto);
//				}
//				if (key.equals("w")) {
//					KVItemDto dto = new KVItemDto();
//					dto.setK("女");
//					dto.setV(empEntry.getValue().size() + "");
//					dtos.add(dto);
//				}
//			}
//		}
//		if(!bool){
//			KVItemDto dto = new KVItemDto();
//			dto.setK("男");
//			dto.setV(0 + "");
//			dtos.add(0,dto);
//		}
//		return dtos;
//	}

	private List<TeamImgEmpDto> packagSexList(List<TeamImgEmpDto> rs, String id){
		List<TeamImgEmpDto> list = new LinkedList<>();
		for (TeamImgEmpDto dto : rs) {
			if(dto.getSex().equals(id))
				list.add(dto);
		}
		return list;
	}

	/**
	 * 婚姻状况
	 * @param rs
	 * @return
	 */
	private Map<String, Integer> packagMarryStatus(List<TeamImgEmpDto> rs){
		MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			m.add(dto.getMarryStatus(), dto);
		}
		Map<String, Integer> mapDto = CollectionKit.newMap();
		mapDto.put("unIsMarry", 0);
		mapDto.put("isMarry", 0);
		for (Map.Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof Integer){
				Integer key = empEntry.getKey();
				if (key == 0) {
					mapDto.put("unIsMarry", empEntry.getValue().size());
				}else{
					mapDto.put("isMarry", empEntry.getValue().size());
				}
			}
		}
		mapDto.put("total", rs.size());
		return mapDto;
	}
	private List<TeamImgEmpDto> packagMarryStatusList(List<TeamImgEmpDto> rs, String id){
		Integer key=Integer.parseInt(id);
		List<TeamImgEmpDto> list = new LinkedList<>();
		for (TeamImgEmpDto dto : rs) {
			if(dto.getMarryStatus().equals(key))
				list.add(dto);
		}
		return list;
	}


	/**
	 * 血型
	 * @param rs
	 * @return
	 */
	private List<KVItemDto> packagBlood(List<TeamImgEmpDto> rs){
		MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			m.add(dto.getBlood(), dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();

		for (Map.Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			String name = empEntry.getKey();
			KVItemDto dto = new KVItemDto();
			if("".equals(name) || null==name){
				dto.setK("其他");
			}else{
				dto.setK(name);
			}
			dto.setV(empEntry.getValue().size() + "");
			//按人数从大到小排序
			int index=dtos.size();
			for(;index>0;index--){
				int n=Integer.parseInt(dtos.get(index-1).getV());
				if(n>empEntry.getValue().size()){
					break;
				}
			}
			dtos.add(index,dto);
			//dtos.add(dto);
		}
		return dtos;
	}
	private List<TeamImgEmpDto> packagBloodList(List<TeamImgEmpDto> rs, String id){
		if("其他".equals(id)){
			id="";
		}
		List<TeamImgEmpDto> list = new LinkedList<>();
		for (TeamImgEmpDto dto : rs) {
			if(dto.getBlood().equals(id))
				list.add(dto);
		}
		return list;
	}

	/**
	 * 星座
	 * @param rs
	 * @return
	 */
	private Map<String, Object> packagStar(List<TeamImgEmpDto> rs){
		MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			int monthDayInt = DateUtil.convertToIntYearMonth(dto.getBirthDate());
			m.add(monthDayInt, dto);
		}
		Map<String, Object> mapDto = CollectionKit.newMap();
		String[] xAxisData = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
		int[] seriesData = {0,0,0,0,0,
				0,0,0,0,0,
				0,0
		};
		mapDto.put("xAxisData", xAxisData);
		for (Map.Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof Integer){
				Integer key = empEntry.getKey();

				if(key >= 321 && key < 419){
					int temp = seriesData[0];
					seriesData[0] = temp + empEntry.getValue().size();
				}else if(key >= 420 && key <= 520){
					int temp = seriesData[1];
					seriesData[1] = temp + empEntry.getValue().size();
				}else if(key >= 521 && key <= 621){
					int temp = seriesData[2];
					seriesData[2] = temp + empEntry.getValue().size();
				}else if(key >= 622 && key <= 722){
					int temp = seriesData[3];
					seriesData[3] = temp + empEntry.getValue().size();
				}else if(key >= 723 && key <= 822){
					int temp = seriesData[4];
					seriesData[4] = temp + empEntry.getValue().size();
				}else if(key >= 823 && key <= 922){
					int temp = seriesData[5];
					seriesData[5] = temp + empEntry.getValue().size();
				}else if(key >= 923 && key <= 1023){
					int temp = seriesData[6];
					seriesData[6] = temp + empEntry.getValue().size();
				}else if(key >= 1024 && key <= 1122){
					int temp = seriesData[7];
					seriesData[7] = temp + empEntry.getValue().size();
				}else if(key >= 1123 && key <= 1221){
					int temp = seriesData[8];
					seriesData[8] = temp + empEntry.getValue().size();
				}else if(key <= 1222 && key <= 119){
					int temp = seriesData[9];
					seriesData[9] = temp + empEntry.getValue().size();
				}else if(key >= 120 && key <= 218){
					int temp = seriesData[10];
					seriesData[10] = temp + empEntry.getValue().size();
				}else{
					int temp = seriesData[11];
					seriesData[11] = temp + empEntry.getValue().size();
				}
			}
		}
		mapDto.put("seriesData", seriesData);
		return mapDto;
	}
	private List<TeamImgEmpDto> packagStarList(List<TeamImgEmpDto> rs, String id){
		List<TeamImgEmpDto> list = new LinkedList<>();
		String[] starName = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
		Integer index=-1;
		for (TeamImgEmpDto dto : rs) {
			int key = DateUtil.convertToIntYearMonth(dto.getBirthDate());
			if(key >= 321 && key < 419){
				index=0;
			}else if(key >= 420 && key <= 520){
				index=1;
			}else if(key >= 521 && key <= 621){
				index=2;
			}else if(key >= 622 && key <= 722){
				index=3;
			}else if(key >= 723 && key <= 822){
				index=4;
			}else if(key >= 823 && key <= 922){
				index=5;
			}else if(key >= 923 && key <= 1023){
				index=6;
			}else if(key >= 1024 && key <= 1122){
				index=7;
			}else if(key >= 1123 && key <= 1221){
				index=8;
			}else if(key <= 1222 && key <= 119){
				index=9;
			}else if(key >= 120 && key <= 218){
				index=10;
			}else{
				index=11;
			}
			if(starName[index].equals(id)){
				list.add(dto);
			}
		}
		return list;
	}

	/**
	 * 性格
	 * @param rs
	 * @return
	 */
	private List<Map<String, Object>> packagPersonality(List<TeamImgEmpDto> rs){
		MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			if("".equals(dto.getPersonalityType()) || null==dto.getPersonalityType()){
				m.add(0, dto);
			}
			m.add(dto.getPersonalityType(), dto);
		}
		List<Map<String, Object>> dtos = CollectionKit.newList();

		for (Map.Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof Integer){
				Integer key = empEntry.getKey();
				List<TeamImgEmpDto> value = empEntry.getValue();
				Map<String, Object> packagMap = CollectionKit.newMap();
				if(key == 1) {
					packagMap.put("name", "支配型");
				}
				if(key == 2) {
					packagMap.put("name", "影响型");
				}
				if(key == 3) {
					packagMap.put("name", "谨慎型");
				}
				if(key == 4) {
					packagMap.put("name", "稳健型");
				}
				if(key == 0) {
					packagMap.put("name", "其他");
				}
				packagMap.put("value", value.size());
				List<String> empNames = CollectionKit.newList();
				for (TeamImgEmpDto emp : value) {
					empNames.add(emp.getUserNameCh());
				}
				packagMap.put("empNames", empNames);
				//按人数从大到小排序
				int index=dtos.size();
				for(;index>0;index--){
					int n=Integer.parseInt(dtos.get(index-1).get("value").toString());
					if(n>value.size()){
						break;
					}
				}
				dtos.add(index,packagMap);
			}
		}
		return dtos;
	}
	private List<TeamImgEmpDto> packagPersonalityList(List<TeamImgEmpDto> rs, String id){
		Integer key=0;
		switch (id){
			case "支配型":{key=1;break;}
			case "影响型":{key=2;break;}
			case "谨慎型":{key=3;break;}
			case "稳健型":{key=4;break;}
			case "其他":{key=0;break;}
		}
		List<TeamImgEmpDto> list = new LinkedList<>();
		for (TeamImgEmpDto dto : rs) {
			if("".equals(dto.getPersonalityType()) || null==dto.getPersonalityType()) {
				if(key == 0){
					list.add(dto);
				}
			}else{
				if(dto.getPersonalityType().equals(key)){
					list.add(dto);
				}
			}
		}
		return list;
	}
}
