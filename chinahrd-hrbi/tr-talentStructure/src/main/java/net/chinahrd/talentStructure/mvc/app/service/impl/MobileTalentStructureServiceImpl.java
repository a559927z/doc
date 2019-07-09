package net.chinahrd.talentStructure.mvc.app.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.talent.structure.TalentstructureDto;
import net.chinahrd.module.SysCache;
import net.chinahrd.talentStructure.mvc.app.dao.MobileTalentStructureDao;
import net.chinahrd.talentStructure.mvc.app.service.MobileTalentStructureService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.TableKeyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 
 * @author htpeng
 *2016年4月7日下午2:22:49
 */
@Service("mobileTalentStructureService")
public class MobileTalentStructureServiceImpl implements MobileTalentStructureService {
	
	@Autowired
	MobileTalentStructureDao talentStructureDao;
	
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
    
    
    @Override
	public Map<String, Integer> queryAbEmpCountBarBySeqId(String organId, String customerId, String seqId) {
		Map<String, Integer> rsMap = new LinkedHashMap<>();
		List<TalentstructureDto> rsList = talentStructureDao.queryAbEmpCountBarBySeqId(organId, customerId, seqId);
		
		MultiValueMap<String, TalentstructureDto> m = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : rsList) {
			if (null != dto.getAbCurtName()) {
				m.add(dto.getAbCurtName(), dto);
			}
		}
		for (Entry<String, List<TalentstructureDto>> entry : m.entrySet()) {
			String key = entry.getKey();
			List<TalentstructureDto> groupDtos = entry.getValue();
			
			rsMap.put(key, groupDtos.size());
		}
		return rsMap;
	}
    
    
    @Override
    public Map<String, Object> getTalentStuctureData(String organId, String customerId){
    	Map<String, Object> rs = new LinkedHashMap<String, Object>();
    	
    	List<TalentstructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId);
    	Map<Integer, List<TalentstructureDto>> empDistinguishByHasAb = getEmpDistinguishByHasAb(rsDb);
    	
    	if(null == rsDb) {return null;}
    	
    	// 补0操作
		Map<String,Integer> mackUpRs = new LinkedHashMap<String,Integer>();
		Integer maxAbLevel = rsDb.get(0).getMaxAbLevel();
		for (int i = maxAbLevel; i >=1; i--) {
			mackUpRs.put(i+"级", 0);
		}
    	
    	Map<String, Integer> abilityEmpCountPie = queryAbilityEmpCountPie(rsDb);
//    	Map<String, Map<String, Integer>> abilityEmpCount = queryAbilityEmpCount(rsDb, organId);
    	Map<String, Integer> abilityCurtEmpCountPie = queryAbilityCurtEmpCountPie(empDistinguishByHasAb.get(1), empDistinguishByHasAb.get(2));
//    	Map<String, Map<String, Integer>> abilityCurtEmpCount = queryAbilityCurtEmpCount(rsDb, organId);
    	Map<String, Integer> seqEmpCountBar = querySeqEmpCountBar(rsDb);
//    	Map<String, Map<String, Integer>> seqEmpCount = querySeqEmpCount(rsDb);
    	
    	Map<String, Integer> organEmpCount = queryOrganEmpCount(rsDb, organId);
    	Map<String, Integer> workplaceEmpCount = queryWorkplaceEmpCount(rsDb);
    	Map<String, Integer> degreeEmpCountPie = queryDegreeEmpCountPie(rsDb);
//    	Map<String, Map<String, Integer>> degreeEmpCount = queryDegreeEmpCount(rsDb, organId);
    	Map<String, Integer> companyAgeEmpCountPie = queryCompanyAgeEmpCountPie(rsDb);
//    	Map<String, Map<String, Integer>> companyAgeEmpCount = queryCompanyAgeEmpCount(rsDb, organId);
    	
    	rs.put("abilityEmpCountPie", abilityEmpCountPie);			// 管理者员工分布-饼图
//    	rs.put("abilityEmpCount", abilityEmpCount);					// 管理者员工分布-表
    	rs.put("abilityCurtEmpCountPie", abilityCurtEmpCountPie);	// 职级分布-饼图
//    	rs.put("abilityCurtEmpCount", abilityCurtEmpCount);			// 职级分布-表
    	rs.put("seqEmpCountBar", seqEmpCountBar);					// 职位序列分布-柱状图
//    	rs.put("seqEmpCount", seqEmpCount);							// 职位序列分布-表
    	
    	rs.put("organEmpCount", organEmpCount);						// 组织分布
    	rs.put("workplaceEmpCount", workplaceEmpCount);				// 工作地点分布
    	rs.put("degreeEmpCountPie", degreeEmpCountPie);				// 学历分布-饼图
//    	rs.put("degreeEmpCount", degreeEmpCount);					// 学历分布-表
    	rs.put("companyAgeEmpCountPie", companyAgeEmpCountPie);		// 司龄分布-饼图
//    	rs.put("companyAgeEmpCount", companyAgeEmpCount);			// 司龄分布-表
    	return rs;
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
		for (Entry<String, List<TalentstructureDto>> entry : m.entrySet()) {
			String key = entry.getKey();
			List<TalentstructureDto> groupDtos = entry.getValue();
			
			rsMap.put(key, groupDtos.size());
		}
		Map<String, Integer> sortMap = CollectionKit.sortMapByValue(rsMap);
		return sortMap;
	}
    
    
	
	/**
	 * 司龄分布-饼图 by jxzhang on 2016-02-25
	 * @param rs
	 * @param total
	 * @return
	 */
	private Map<String, Integer> queryCompanyAgeEmpCountPie(List<TalentstructureDto> rs){
		MultiValueMap<Integer, TalentstructureDto> m = new LinkedMultiValueMap<Integer, TalentstructureDto>();
		for (TalentstructureDto dto : rs) {
			m.add(dto.getCompanyAge(), dto);
		}
		Map<String, Integer> mapDto = new LinkedHashMap<String, Integer>();
		int[] seriesData = {0,0,0,0,0};
		for (Entry<Integer, List<TalentstructureDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof Integer){
				Integer key = empEntry.getKey();
				if(key >= 0 && key < 3){
					int temp = seriesData[0];
					seriesData[0] = temp + empEntry.getValue().size();
				}else if(key >= 3 && key < 12){
					int temp = seriesData[1];
					seriesData[1] = temp + empEntry.getValue().size();
				}else if(key >= 12 && key < 36){
					int temp = seriesData[2];
					seriesData[2] = temp + empEntry.getValue().size();
				}else if(key >= 36 && key < 60){
					int temp = seriesData[3];
					seriesData[3] = temp + empEntry.getValue().size();
				}else{
					int temp = seriesData[4];
					seriesData[4] = temp + empEntry.getValue().size();
				}
			}
		}
		mapDto.put("3月以下", seriesData[0]);
		mapDto.put("3月-1年", seriesData[1]);
		mapDto.put("1-3年", seriesData[2]);
		mapDto.put("3-5年", seriesData[3]);
		mapDto.put("5年以上", seriesData[4]);
		return mapDto;
	}
    
	
    /**
     * 学历分布饼图 by jxzhang on 2016-02-24
     * @param hasList
     *      * @return
     */
	private Map<String, Integer> queryDegreeEmpCountPie(
			List<TalentstructureDto> rsDb) {

		// 能力层级分组
		MultiValueMap<String, TalentstructureDto> abMap = new LinkedMultiValueMap<String, TalentstructureDto>();
		for (TalentstructureDto dto : rsDb) {
			if (null != dto.getDegree()) {
				abMap.add(dto.getDegree(), dto);
			}
		}
		Map<String, Integer> rs = new LinkedHashMap<String, Integer>();

		for (Entry<String, List<TalentstructureDto>> entry : abMap.entrySet()) {
			rs.put(entry.getKey(), entry.getValue().size());
		}

		// 补0操作
		Map<String, Integer> markUpRs = new LinkedHashMap<String, Integer>();
		markUpRs.put("大专以下", 0);
		markUpRs.put("大专", 0);
		markUpRs.put("本科", 0);
		markUpRs.put("硕士", 0);
		markUpRs.put("博士", 0);

		Set<Entry<String, Integer>> entrySet = rs.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			String key = entry.getKey();
			if (null != key && 
					(!(key.equals("大专")) && !(key.equals("本科"))
					&& !(key.equals("硕士")) && !(key.equals("博士")))) {
				
				markUpRs.put("大专以下", entry.getValue());
			}
			markUpRs.put(key, entry.getValue());
		}
		return markUpRs;
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
		for (Entry<String, List<TalentstructureDto>> entry : rsMap.entrySet()) {
			rs.put("".equals(entry.getKey())?"其他":entry.getKey(), entry.getValue().size());
		}
		return rs;
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
		
		for (Entry<String, List<TalentstructureDto>> entry : rsMap.entrySet()) {
			rs.put(entry.getKey(), entry.getValue().size());
		}
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

		for (Entry<String, List<TalentstructureDto>> entry : abMap.entrySet()) {
			rs.put(entry.getKey(), entry.getValue().size());
		}

		// 补0操作
		Map<String, Integer> orderRs = new LinkedHashMap<String, Integer>();
		Integer maxAbLevel = hasList.get(0).getMaxAbLevel();
		for (int i = maxAbLevel; i >= 1; i--) {
			orderRs.put(i + "级", 0);
		}

		Set<Entry<String, Integer>> entrySet = rs.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			String key = entry.getKey();
			orderRs.put(key, entry.getValue());
		}
		if (notHasList.size() > 0) {
			orderRs.put("其他", notHasList.size());
		}
		return orderRs;
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
		
		for (Entry<String, List<TalentstructureDto>> abNameEntry : abMap.entrySet()) {
			// BUG:517 管理者员工分布：环形图右边的标识信息显示错误；不是显示5/4/3/2/1，与列表一致；
			String abId = abNameEntry.getKey();
			 mackUpRs.put(CacheHelper.getAbilityName(abId), abNameEntry.getValue().size());
		}
		mackUpRs.put("员工", empList.size());
		return mackUpRs;
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

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentStructureService#quertAllSeq(java.lang.String)
	 */
	@Override
	public List<KVItemDto> quertAllSeq(String customerId) {
		// TODO Auto-generated method stub
		List<KVItemDto> list=talentStructureDao.quertAllSeq( customerId);
		KVItemDto dto=new KVItemDto("","全部");
		list.add(0,dto);
		return list;
	}
    
    
}
