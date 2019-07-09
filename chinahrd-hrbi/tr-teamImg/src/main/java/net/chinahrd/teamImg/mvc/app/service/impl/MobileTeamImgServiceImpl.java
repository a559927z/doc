package net.chinahrd.teamImg.mvc.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.teamImg.TeamImgDto;
import net.chinahrd.teamImg.mvc.app.dao.MobileTeamImgDao;
import net.chinahrd.teamImg.mvc.app.service.MobileTeamImgService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Sort;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 团队画像 app service
 * @author htpeng
 *2016年6月14日下午5:28:26
 */
@Service("mobileTeamImgService")
public class MobileTeamImgServiceImpl implements MobileTeamImgService {

	@Autowired
	private MobileTeamImgDao teamImgDao;

	@Override
	public Map<String, Object> getTeamImgData(String organId, String customeId) {
		
		List<TeamImgDto> rs = teamImgDao.queryTeamImg(organId, customeId);
		Map<String, Object> rsMap = CollectionKit.newMap();
		if(CollectionKit.isEmpty(rs)){rsMap.put("total", 0);return rsMap; }
		// 分母不会为null
		double total = rs.size();
		// 分母不能为零
		rsMap.put("total", total);
		if(total == 0) {return rsMap;}

		List<KVItemDto> sexDtos = packagSex(rs);
		List<KVItemDto> ageDto = packagAge(rs);
		List<KVItemDto> marryStatusDto = new Sort<KVItemDto>().desc(packagMarryStatus(rs));
		List<KVItemDto> bloodDtos = new Sort<KVItemDto>().desc(packagBlood(rs));
		List<KVItemDto> starDto = new Sort<KVItemDto>().desc(packagStar(rs));
		List<KVItemDto> personalityDtos = new Sort<KVItemDto>().desc(packagPersonality(organId, customeId));

		rsMap.put("total", total);
		Map<String,KVItemDto> perMap=CollectionKit.newMap();
		perMap.put("sex", packagTop(sexDtos, total,"士"));//性别占比
		perMap.put("age", packagTop(ageDto, total)); //出生年度占比
		perMap.put("marry", packagTop(marryStatusDto, total)); //婚姻状况占比
        rsMap.put("top", perMap);    

		rsMap.put("sex", sexDtos);
		rsMap.put("marry", marryStatusDto);
		rsMap.put("age", ageDto);
		rsMap.put("blood", bloodDtos);
		rsMap.put("constellatory", starDto);
		rsMap.put("personality", personalityDtos);
		
		return rsMap;
	}

	
    /**
     * 占比
     *
     * @param rs
     * @param total
     * @return
     */
    private KVItemDto packagTop(List<KVItemDto> list, double total) {
      
        return packagTop(list,total,"");
    }
    private KVItemDto packagTop(List<KVItemDto> list, double total,String append) {
        if (total == 0) {
            return null;
        }
        int max = 0;
        String label = "";
        for(KVItemDto k:list){
        	int v=Integer.parseInt(k.getV());
        	if(v>max){
        		label=k.getK();
        		max=v;
        	}
        }
        double maxPer = ArithUtil.div(max, total, 2);
        KVItemDto dto = new KVItemDto();
        dto.setK(label+append);
        dto.setV(String.valueOf((int) ArithUtil.mul(maxPer, 100.0)));
        return dto;
    }
    
	/**
	 * 男女比例
	 * @param rs
	 * @return
	 */
	private List<KVItemDto> packagSex(List<TeamImgDto> rs){
		MultiValueMap<String, TeamImgDto> m = new LinkedMultiValueMap<String, TeamImgDto>();
		for (TeamImgDto dto : rs) {
			m.add(dto.getSex(), dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();
		boolean bool=false;
		for (Entry<String, List<TeamImgDto>> empEntry : m.entrySet()) {
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
	private List<KVItemDto> packagMarryStatus(List<TeamImgDto> rs){
		MultiValueMap<Integer, TeamImgDto> m = new LinkedMultiValueMap<Integer, TeamImgDto>();
		for (TeamImgDto dto : rs) {
			m.add(dto.getMarryStatus(), dto);
		}
		List<KVItemDto> result=CollectionKit.newList();
		for (Entry<Integer, List<TeamImgDto>> empEntry : m.entrySet()) {
			if(empEntry.getKey() instanceof Integer){
				Integer key = empEntry.getKey();
				if (key == 0) {
					KVItemDto k=new KVItemDto();
					k.setK("未婚");
					k.setV(empEntry.getValue().size()+"");
					result.add(k);
				}else{
					KVItemDto k=new KVItemDto();
					k.setK("已婚");
					k.setV(empEntry.getValue().size()+"");
					result.add(k);
				}
			}
		}

		return result;
	}
	
	/**
	 * 年龄
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagAge(List<TeamImgDto> rs){
		MultiValueMap<Integer, TeamImgDto> m = new LinkedMultiValueMap<Integer, TeamImgDto>();
		for (TeamImgDto dto : rs) {
			DateTime dt = new DateTime(dto.getBirthDate());
			m.add(dt.getYear(), dto);
		}
		String[] xAxisData = {"90后","80后","70后","60后","其他"};
		int[] seriesData = {0,0,0,0,0};
		for (Entry<Integer, List<TeamImgDto>> empEntry : m.entrySet()) {
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
		List<KVItemDto> result = CollectionKit.newList();
		for(int i=0;i<xAxisData.length;i++){
			if(seriesData[i]>0){
				KVItemDto kvItemDto=new KVItemDto();
				kvItemDto.setK(xAxisData[i]);
				kvItemDto.setV(seriesData[i]+"");
				result.add(kvItemDto);
			}
		}
		return result;
	}
	
	
	/**
	 * 血型
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagBlood(List<TeamImgDto> rs){
		MultiValueMap<String, TeamImgDto> m = new LinkedMultiValueMap<String, TeamImgDto>();
		for (TeamImgDto dto : rs) {
			m.add(dto.getBlood(), dto);
		}
		List<KVItemDto> dtos = CollectionKit.newList();
		
		for (Entry<String, List<TeamImgDto>> empEntry : m.entrySet()) {
			String name = empEntry.getKey();
			KVItemDto dto = new KVItemDto();
			if("".equals(name) || null==name){
				dto.setK("其他");
			}else{
				dto.setK(name);
			}
			dto.setV(empEntry.getValue().size() + "");
			dtos.add(dto);
		}
		return dtos;
	}
	
	/**
	 * 星座
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagStar(List<TeamImgDto> rs){
		MultiValueMap<Integer, TeamImgDto> m = new LinkedMultiValueMap<Integer, TeamImgDto>();
		for (TeamImgDto dto : rs) {
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
		for (Entry<Integer, List<TeamImgDto>> empEntry : m.entrySet()) {
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
		
		List<KVItemDto> result = CollectionKit.newList();
		for(int i=0;i<xAxisData.length;i++){
			if(seriesData[i]>0){
				KVItemDto kvItemDto=new KVItemDto();
				kvItemDto.setK(xAxisData[i]);
				kvItemDto.setV(seriesData[i]+"");
				result.add(kvItemDto);
			}
		}
		return result;
	}
	
	
	/**
	 * 性格
	 * @param rs
	 * @param total
	 * @return
	 */
	private List<KVItemDto> packagPersonality(String organId, String customerId){
//		MultiValueMap<Integer, TeamImgDto> m = new LinkedMultiValueMap<Integer, TeamImgDto>();
//		for (TeamImgDto dto : rs) {
//			if("".equals(dto.getPersonalityType()) || null==dto.getPersonalityType()){
//				m.add(0, dto);
//			}
//			m.add(dto.getPersonalityType(), dto);
//		}
//
//		List<KVItemDto> result = CollectionKit.newList();
//		for (Entry<Integer, List<TeamImgDto>> empEntry : m.entrySet()) {
//			if(empEntry.getKey() instanceof Integer){
//				Integer key = empEntry.getKey();
//				List<TeamImgDto> value = empEntry.getValue();
//				KVItemDto kvItemDto=new KVItemDto();
//				if(key == 1) {
//					kvItemDto.setK("支配型");
//				}
//				if(key == 2) {
//					kvItemDto.setK("影响型");
//				}
//				if(key == 3) {
//					kvItemDto.setK("谨慎型");
//				}
//				if(key == 4) {
//					kvItemDto.setK("稳健型");
//				}
//				if(key == 0) {
//					kvItemDto.setK("其他");
//				}
//				kvItemDto.setV(value.size()+"");
//				result.add(kvItemDto);
//			}
//		}
//		return result;

		// 性格单独查询	 by jxzhang 2016-07-25
		List<KVItemDto> queryEmpPersonality = teamImgDao.queryEmpPersonality(organId, customerId);
		List<KVItemDto> result = CollectionKit.newList();
		 
		 for (KVItemDto kv : queryEmpPersonality) {
		
			 KVItemDto kvItemDto=new KVItemDto();
			 kvItemDto.setK(kv.getK());
			 kvItemDto.setV(kv.getV());
			 result.add(kvItemDto);
		}
		return result;
	}
	
}
