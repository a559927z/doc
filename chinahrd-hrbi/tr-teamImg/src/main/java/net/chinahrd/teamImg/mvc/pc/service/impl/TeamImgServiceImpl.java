package net.chinahrd.teamImg.mvc.pc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.teamImg.TeamImgEmpDto;
import net.chinahrd.teamImg.mvc.pc.dao.TeamImgDao;
import net.chinahrd.teamImg.mvc.pc.service.TeamImgService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

/**
 * by jxzhang
 */
@Service("teamImgService")
public class TeamImgServiceImpl implements TeamImgService {

	@Autowired
	private TeamImgDao teamImgDao;

	@Override
	public Map<Integer, Object> getTeamImgData(String organId, String customeId) {
		
		List<TeamImgEmpDto> rs = teamImgDao.queryTeamImg(organId, customeId);
		
		if(CollectionKit.isEmpty(rs)){ return null; }
		// 分母不会为null
		double total = rs.size();
		// 分母不能为零
		if(total == 0) {return null;}
		
		List<KVItemDto> abilityDtos = packagAbility(rs);
		List<KVItemDto> workPlaceDtos = packagWorkPlace(rs);
		List<KVItemDto> sexDtos = packagSex(rs);
		Map<String, Integer> marryStatusDto = packagMarryStatus(rs);
		Map<String, Object> ageDto = packagAge(rs);
		Map<String, Object> companyAgeDto = packagCompanyAge(rs);
		List<KVItemDto> bloodDtos = packagBlood(rs);
		Map<String, Object> starDto = packagStar(rs);
//		List<Map<String, Object>> personalityDtos = packagPersonality(rs);
		List<Map<String, Object>> personalityDtos = packagPersonality(organId, customeId);

		Map<Integer, Object> rsMap = CollectionKit.newMap();
		rsMap.put(0, abilityDtos);
		rsMap.put(1, workPlaceDtos);
		rsMap.put(2, sexDtos);
		rsMap.put(3, marryStatusDto);
		rsMap.put(4, ageDto);
		rsMap.put(5, companyAgeDto);
		rsMap.put(6, bloodDtos);
		rsMap.put(7, starDto);
		rsMap.put(8, personalityDtos);
		
		return rsMap;
	}

	/**
	 * 职位层级
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagAbility(List<TeamImgEmpDto> rs){
		Map<String,List<TeamImgEmpDto>> map = CollectionKit.newMap();
		for (TeamImgEmpDto dto : rs) {
			if ("".equals(dto.getCurtName())|| null==dto.getCurtName() ){
				dto.setCurtName("0");
			}
			List<TeamImgEmpDto>list=map.get(dto.getCurtName());
			if(list==null){
				list=new ArrayList<TeamImgEmpDto>();
				map.put(dto.getCurtName(), list);
			}
			list.add( dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();
		
		for(String key:map.keySet()){
			KVItemDto dto = new KVItemDto();
			if("0".equals(key)){
				dto.setK("0级");
			}else{
				dto.setK(key+"级");
			}
				
			dto.setV(map.get(key).size() + "");
			
			//对结果排序
			int index=dtos.size();
			for(;index>0;index--){
				if(dtos.get(index-1).getK().substring(0,1).compareTo(key)>0){
					break;
				}
			}
			dtos.add(index,dto);
		}
		for(int i=0;i<dtos.size();i++){
			if("0级".equals(dtos.get(i).getK())){
				dtos.get(i).setK("其他");
			}
		}
		return dtos;
	}

	/**
	 * 工作地点
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagWorkPlace(List<TeamImgEmpDto> rs){
		MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			if("".equals(dto.getWorkPlace()) || null==dto.getWorkPlace()){
				m.add("其他", dto);
				continue;
			}
			m.add(dto.getWorkPlace(), dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();
		
		for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof String){
				String name = empEntry.getKey();
				KVItemDto dto = new KVItemDto();
				dto.setK(name);
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
		}
		return dtos;
	}
	
	/**
	 * 男女比例
	 * @param rs
	 * @return
	 */
	private List<KVItemDto> packagSex(List<TeamImgEmpDto> rs){
		MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			m.add(dto.getSex(), dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();
		boolean bool=false;
		for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof String){
				String key = empEntry.getKey();
				if (key.equals("m")) {
					bool=true;
					KVItemDto dto = new KVItemDto();
					dto.setK("男");
					dto.setV(empEntry.getValue().size() + "");
					dtos.add(dto);
				}
				if (key.equals("w")) {
					KVItemDto dto = new KVItemDto();
					dto.setK("女");
					dto.setV(empEntry.getValue().size() + "");
					dtos.add(dto);
				}
			}
		}
		if(!bool){
			KVItemDto dto = new KVItemDto();
			dto.setK("男");
			dto.setV(0 + "");
			dtos.add(0,dto);
		}
		return dtos;
	}

	/**
	 * 婚姻状况
	 * @param rs
	 * @param total
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
		for (Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
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
	
	/**
	 * 年龄
	 * @param rs
	 * @param total
	 * @return
	 */
	private Map<String, Object> packagAge(List<TeamImgEmpDto> rs){
		MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			DateTime dt = new DateTime(dto.getBirthDate());
			m.add(dt.getYear(), dto);
		}
		Map<String, Object> mapDto = CollectionKit.newMap();
		String[] xAxisData = {"90后","80后","70后","60后","其他"};
		int[] seriesData = {0,0,0,0,0};
		
		mapDto.put("xAxisData", xAxisData);
		for (Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof Integer){
				Integer key = empEntry.getKey();
				if(key >= 1960 && key < 1970){
					seriesData[3] += empEntry.getValue().size();
				}else if(key >= 1970 && key < 1980){
					seriesData[2] += empEntry.getValue().size();
				}else if(key >= 1980 && key < 1990){
					seriesData[1] += empEntry.getValue().size();
				}else if(key >= 1990 && key < 2000){
					seriesData[0] += empEntry.getValue().size();
				}else{
					seriesData[4] += empEntry.getValue().size();
				}
			}
		}
		mapDto.put("seriesData", seriesData);
		return mapDto;
	}
	
	/**
	 *  司龄
	 * @param rs
	 * @param total
	 * @return
	 */
	private Map<String, Object> packagCompanyAge(List<TeamImgEmpDto> rs){
		MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			m.add(dto.getCompanyAge(), dto);
		}
		Map<String, Object> mapDto = CollectionKit.newMap();
		String[] xAxisData = {"3月以下","3月-1年","1-3年","3-5年","5年以上"};
		int[] seriesData = {0,0,0,0,0};
		
		mapDto.put("xAxisData", xAxisData);
		for (Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
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
		mapDto.put("seriesData", seriesData);
		return mapDto;
	}
	
	
	/**
	 * 血型
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagBlood(List<TeamImgEmpDto> rs){
		MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			m.add(dto.getBlood(), dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();
		
		for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
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
	
	/**
	 * 星座
	 * @param rs
	 * @param total
	 * @return
	 */
	private Map<String, Object> packagStar(List<TeamImgEmpDto> rs){
		MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
		for (TeamImgEmpDto dto : rs) {
			int monthDayInt = DateUtil.convertToIntMonthDay(dto.getBirthDate());
			m.add(monthDayInt, dto);
		}
		Map<String, Object> mapDto = CollectionKit.newMap();
		String[] xAxisData = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
		int[] seriesData = {0,0,0,0,0,
							0,0,0,0,0,
							0,0
							};
		mapDto.put("xAxisData", xAxisData);
		for (Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
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
	
	
	/**
	 * 性格
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<Map<String, Object>> packagPersonality(String organId, String customerId){
		
		 List<KVItemDto> queryEmpPersonality = teamImgDao.queryEmpPersonality(organId, customerId);
		 
		 List<Map<String, Object>> dtos = CollectionKit.newList();
		 
		 for (KVItemDto kvItemDto : queryEmpPersonality) {
			 Map<String, Object> packagMap = CollectionKit.newMap();
			 packagMap.put("name", kvItemDto.getK());
			 packagMap.put("value", kvItemDto.getV());
			 // @SEE teamImg2.js,823行，对接，因去掉明细所以不要了js还没有删除。
//			 packagMap.put("empNames", 0); 
			 dtos.add(packagMap);
		}
		return dtos;
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
	@Override
	public void getDwq(int a) {
//		int b = 1/a;
		Integer a1 = teamImgDao.abc();
		teamImgDao.abc3();
		teamImgDao.abc2();
	}
}
