package net.chinahrd.accordDismiss.mvc.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.chinahrd.accordDismiss.mvc.app.service.MobileccordDismissService;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.dismiss.AccordDismissDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTrendDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTypeDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端
 * 主动流失率Controller
 * @author htpeng
 *2016年3月30日下午5:32:23
 */
@Controller
@RequestMapping("mobile/accordDismiss")
public class MobileAccordDismissController extends BaseController {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private MobileccordDismissService mobileAccordDismissService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/toAccordDismissView")
    public String toAccordDismissView() {
    	 String customerId = getCustomerId();
    	  Object obj=request.getParameter("organId");
    	  Object pobj=request.getParameter("porganId");
          List<OrganDto> organPermit = getOrganPermit();
          if (CollectionKit.isNotEmpty(organPermit)) {
              String quarterLastDay = mobileAccordDismissService.queryQuarterLastDay(customerId);
              request.setAttribute("quarterLastDay", quarterLastDay.substring(0, 10));
              DateTime dt=new DateTime(quarterLastDay.substring(0, 10));
              Map<String,Object> map=getYearMonthDayArr(dt.toString("yyyy-MM-dd"));
              //数据时间范围
              request.setAttribute("timeRange",map.get("startYearMonth")+"-"+map.get("endYearMonth") ); 
              
              if(null==obj){
            	  //当前机构id
            	  OrganDto topOneOrgan = organPermit.get(0);
  	        	request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
  	        	request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
  	        	//上级机构id
  	        	request.setAttribute("parentOrganId", "");
  	        	request.setAttribute("parentOrganName", "");
              }else{
            	  String orgId=obj.toString();
            	  request.setAttribute("reqOrganId", orgId);
            	  boolean bool=false;
            	   for(OrganDto topOneOrgan:organPermit){
            		   if(topOneOrgan.getOrganizationId().equals(orgId)){
            			   request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
            			   bool=true;
            			   break;
            		   }
            	   }
            	   if(!bool){
            		   request.setAttribute("reqOrganName", "没有找到");
            	   }
            	 //上级机构id
    	          if(null==pobj){
    	        	  request.setAttribute("parentOrganId", "");
      	        	request.setAttribute("parentOrganName", "");
    	          }else{
    	        	  String porgid=pobj.toString();
    	        	 // request.setAttribute("parentOrganId", porgid);
    	        	  bool=false;
	               	   for(OrganDto topOneOrgan:organPermit){
	               		   if(topOneOrgan.getOrganizationId().equals(porgid)){
	               			 request.setAttribute("parentOrganId", porgid);
	               			   request.setAttribute("parentOrganName", topOneOrgan.getOrganizationName());
	               			   bool=true;
	               			   break;
	               		   }
	               	   }
	               	   if(!bool){
	               		 //  request.setAttribute("parentOrganName", "没有找到上级");
	               	   }
    	          }
              }
             
          }
        return "mobile/drivingforce/accordDismiss";
    }

   





    /**
     * 查询流失人员明细
     */
    @ResponseBody
    @RequestMapping(value = "/queryRunOffDetail", method = RequestMethod.POST)
    public PaginationDto<AccordDismissDto> queryRunOffDetail(String organId, String prevQuarter,Integer page, Integer rows) {
        PaginationDto<AccordDismissDto> dto = new PaginationDto<AccordDismissDto>(page, rows);
        return mobileAccordDismissService.queryRunOffDetail( getCustomerId(), organId,prevQuarter, dto );
    }

    /**
     * 本部与下属部门流失率对比数据的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubDismissData")
    public List<DismissTrendDto> getSubDismissData(String organizationId, String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return mobileAccordDismissService.getSubDismissData(getCustomerId(), organizationId, date);
    }


    /**
     * 查询主动流失率趋势相关信息
     *
     * @param organId
     * @param prevQuarter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDismissTrend")
    public Map<String,List<DismissTrendDto>> queryDismissTrend(@RequestParam("organId") String organId, @RequestParam("prevQuarter") String prevQuarter) {
         List<OrganDto> organPermit = getOrganPermit();
      String firstOrganId="";
      if (CollectionKit.isNotEmpty(organPermit)) {
          OrganDto topOneOrgan = organPermit.get(0);
          firstOrganId=topOneOrgan.getOrganizationId();
      }
    	return mobileAccordDismissService.queryDismissTrend(organId,firstOrganId, prevQuarter, getCustomerId());
    }

    /**
     * 根据季度查询主动流失率
     *
     * @param organId
     * @param prevQuarter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDisminss4Quarter")
    public Map<String, Object> queryDisminss4Quarter(@RequestParam("organId") String organId, @RequestParam("prevQuarter") String prevQuarter) {
        Map<String, Object> map = CollectionKit.newMap();
        //主动流失率-正常值和风险值
        double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_NORMAL);
        double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_RISK);
//        double max = ArithUtil.sum(risk, 0.1);      //主动流失率的最大值取风险值+10%
        double max = ArithUtil.sum(risk, ArithUtil.div(risk, 2d, 4));      //主动流失率的最大值取风险值+10%
        map.put("normal", normal);
        map.put("risk", risk);
        map.put("max", max);
        map.put("date","("+getYearAndQ(prevQuarter)+")");
        DismissTrendDto dto = mobileAccordDismissService.queryDisminss4Quarter(organId, prevQuarter, getCustomerId());
        map.put("dismissTrend", dto);
        return map;
    }

	/**
	 * 获取当前季度和年
	 * 
	 * @param date
	 * @param dbDate
	 * @return
	 */
	private String getYearAndQ(String date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	int year=cal.get(Calendar.YEAR);
    	int month=cal.get(Calendar.MONTH)+1;
    	String q="Q1";
    	switch (month) {
		case 1:
		case 2:
		case 3:
			q="Q1";
			break;
		case 4:
		case 5:
		case 6:
			q="Q2";
			break;
		case 7:
		case 8:
		case 9:
			q="Q3";
			break;
		case 10:
		case 11:
		case 12:
			q="Q4";
			break;
		default:
			break;
		}
		
		return year+q;

	}

    /**
     * 查询流失人员统计信息
     *
     * @param organId
     * @param prevQuarter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDismissInfo")
    public Map<String, List<DismissTypeDto>> queryDismissInfo(@RequestParam("organId") String organId, @RequestParam("prevQuarter") String prevQuarter) {
        return mobileAccordDismissService.queryDismissInfo(organId, prevQuarter, getCustomerId());
    }
    
    
	private  Map<String,Object> getYearMonthDayArr(String time){
		Calendar cal=Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	String year1_begin=formatDate(getCurrYearFirst(cal.get(Calendar.YEAR)));
    	DateTime dt=new DateTime(cal.getTime());
    	String year1_end=dt.toString("yyyy.MM.dd");
		Map<String,Object> map=CollectionKit.newMap();
		map.put("startYearMonth", year1_begin);
		map.put("endYearMonth", year1_end);
		return map;
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
     * 格式化日期 
     * @param date 日期对象 
     * @return String 日期字符串 
     */  
	private static String formatDate(Date date){  
        return formatDate(date,"yyyy.MM.dd");  
    }  
	/** 
     * 格式化日期 
     * @param date 日期对象 
     * @return String 日期字符串 
     */  
	private static String formatDate(Date date,String pattern){  
        SimpleDateFormat f = new SimpleDateFormat(pattern);  
        String sDate = f.format(date);  
        return sDate;  
    }  
}
