package net.chinahrd.manpowerCost.mvc.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerOrganDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerTrendDto;
import net.chinahrd.manpowerCost.mvc.app.dao.MobileManpowerCostDao;
import net.chinahrd.manpowerCost.mvc.app.service.MobileManpowerCostService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Sort;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author htpeng
 *2016年5月31日上午11:35:42
 */
@Service("mobileManpowerCostService")
public class MobileManpowerCostServiceImpl implements MobileManpowerCostService{
	@Autowired
	MobileManpowerCostDao mobileManpowerCostDao;
	
	
	

	@Override
	public ManpowerTrendDto queryTrendByMonth(String customerId,
			String organId, String time) {
		ManpowerTrendDto mtd=new ManpowerTrendDto();
		Double yearBudget=mobileManpowerCostDao.queryYearBudget(customerId,organId,getYear(time));
		if(null==yearBudget){
			yearBudget=0.0;
		}
		List<ManpowerDto> list=mobileManpowerCostDao.queryTrendByMonth(customerId,organId,getYear(time));
		double total=0;
		if(null!=list&&list.size()>0){
			for(ManpowerDto o:list){
				total+=o.getCost();
			}
		}
		mtd.setBudget(yearBudget);
		mtd.setTotal(total);
		mtd.setDetail(list);
		return mtd;
	}




	@Override
	public List<ManpowerItemDto> queryItemDetail(String customerId,
			String organId, String time) {
		List<ManpowerItemDto> list=new Sort<ManpowerItemDto>().desc(mobileManpowerCostDao.queryItemDetail(customerId,organId,getYear(time)));
		return list;
	}
	
	@Override
	public List<ManpowerOrganDto> queryOrganCost(String customerId,
			String organId, String time) {
		List<ManpowerOrganDto> list=mobileManpowerCostDao.queryOrganCost(customerId,organId,getYear(time));
		if(null!=list&&list.size()>0){
			for(ManpowerOrganDto o:list){
				if(o.getOrganId().equals(organId)){
					list.remove(o);
					list.add(o);
					break;
				}
			}
		}
		return list;
	}


	@Override
	public List<ManpowerCompareDto> queryProportionYear(String customerId,
			String organId, String time) {
		Map<String,String> map=getYearArr(time);
		List<ManpowerCompareDto> list=mobileManpowerCostDao.queryProportionYear(customerId,organId,map.get("year1"),map.get("year2"));
		return list;
	}




	@Override
	public List<ManpowerDto> queryAllDetailData(String customerId,
			String organId, String time) {
		return mobileManpowerCostDao.queryAllDetailData(customerId,organId,getYear(time));
	}
	
	private int getYear(String time){
		Calendar cal=Calendar.getInstance();
    	cal.setTime(DateUtil.getDate());
    	return cal.get(Calendar.YEAR);
	}
	

	private  Map<String,String> getYearArr(String time){
		Calendar cal=Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	String year1=cal.get(Calendar.YEAR)+"";
    	
    	String year1_begin=formatDate(getCurrYearFirst(cal.get(Calendar.YEAR)));
    	DateTime dt=new DateTime(cal.getTime());
    	String year1_end=dt.toString("yyyy-MM-dd");
    	
    	cal.add(Calendar.YEAR, -1);
		String year2=cal.get(Calendar.YEAR)+"";
		String year2_begin=formatDate(getCurrYearFirst(cal.get(Calendar.YEAR)));
		
    	String year2_end=formatDate(getCurrYearLast(cal.get(Calendar.YEAR)));
		Map<String,String> map=CollectionKit.newMap();
		map.put("year1", year1);
		map.put("year1_begin", year1_begin);
		map.put("year1_end", year1_end);
		map.put("year2", year2);
		map.put("year2_begin", year2_begin);
		map.put("year2_end", year2_end);
		return map;
	}

	/** 
     * 格式化日期 
     * @param date 日期对象 
     * @return String 日期字符串 
     */  
	private static String formatDate(Date date){  
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");  
        String sDate = f.format(date);  
        return sDate;  
    }  
      
    /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    private static Date getCurrYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    private static Date getCurrYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }  
}
