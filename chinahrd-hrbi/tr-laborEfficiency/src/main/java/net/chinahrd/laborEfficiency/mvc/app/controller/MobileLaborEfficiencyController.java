package net.chinahrd.laborEfficiency.mvc.app.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyGridDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.laborEfficiency.mvc.app.service.MobileLaborEfficiencyService;
import net.chinahrd.mvc.app.controller.BaseController;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端
 * 劳动力效能Controller
 * @author htpeng
 *2016年3月30日下午5:32:23
 */
@Controller
@RequestMapping("mobile/laborEfficiency")
public class MobileLaborEfficiencyController extends BaseController {
   
    @Autowired
	private MobileLaborEfficiencyService laborEfficiencyService;

    @RequestMapping(value = "/toLaborEfficiencyView")
    public String toAccordDismissView() {
    	  Object obj=request.getParameter("organId");
          List<OrganDto> organPermit = getOrganPermit();
          if (CollectionKit.isNotEmpty(organPermit)) {
              if(null==obj){
            	  //当前机构id
            	  OrganDto topOneOrgan = organPermit.get(0);
  	        	request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
  	        	request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
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
              }
             
          }
  		Map<String, Integer> map = laborEfficiencyService.findMinMaxTime(getCustomerId());
  		if(map==null||map.size()==0){
  			DateTime dt=new DateTime(DateUtil.getDate());
  	  		String time=dt.toString("yyyy-MM-dd");
  	  	    setYearMonth(time);
  	  		request.setAttribute("maxTime", dt.toString("yyyyMM"));
  	  		request.setAttribute("minTime", dt.toString("yyyyMM"));
  		}else{
  	  		String maxTime=String.valueOf(map.get("maxTime"));
  	  	    setYearMonth(maxTime.substring(0,4)+"-"+maxTime.substring(4)+"-01");
  	  		Integer minTime=map.get("minTime");
  	  		request.setAttribute("maxTime", maxTime);
  	  		request.setAttribute("minTime", minTime);
  		}

        return "mobile/laborEfficiency/laborEfficiency";
    }

    /**
   	 * 获取劳动力效能值 ,加班时长,加班预警统计
   	 */
   	@ResponseBody
   	@RequestMapping(value = "getTopData", method = RequestMethod.POST)
   	public Map<String, Object> getTopData(String organId) {
   		if (StringUtils.isEmpty(organId)) {
   			return null;
   		}
   		Map<String, Object> map=CollectionKit.newMap();
   		Integer otTime = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_OTTIME);
		Integer otWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);
   		String curDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
   		map.put( "laborEfficiency",laborEfficiencyService.getLaborEfficiencyValue(getCustomerId(), organId, curDate));
   		map.put( "overtimeHours",laborEfficiencyService.queryOvertimeHours(getCustomerId(), organId, curDate));
   		map.put( "overtimeWarningCount",laborEfficiencyService.queryOvertimeWarningCount(getCustomerId(), organId, otTime, otWeek));
   		return map;
   	}
   	/**
	 * 劳动力效能对比
	 * 
	 * @param organId
	 * @param type
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLaborEfficiencyRatio")
	public Map<String, Object> getLaborEfficiencyRatio(String organId, String time) {

		return laborEfficiencyService.queryLaborEfficiencyRatio(getCustomerId(), organId, time, time);
	}

	/**
	 * 劳动力效能趋势
	 * 
	 * @param organId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLaborEfficiencyTrend")
	public Map<String, Object> getLaborEfficiencyTrend(String organId, String time) {
		return laborEfficiencyService.queryLaborEfficiencyTrend(getCustomerId(), organId, time,time);
	}
	
	/**
	 * 组织机构加班时长
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOvertimeByOrgan")
	public Map<String, Object> queryOvertimeByOrgan(String organId, String time) {
		return laborEfficiencyService.queryOvertimeByOrgan(getCustomerId(), organId, time);
	}
	
	/**
	 * 加班时长趋势-人均时长
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeTrend", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeHours(String organId, String time) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return laborEfficiencyService.queryOvertimeTrend(getCustomerId(), organId, time);
	}

	
	
	/**
	 * 考勤类型分布
	 */
	@ResponseBody
	@RequestMapping(value = "queryCheckWorkTypeLayout", method = RequestMethod.POST)
	public Map<String, Object> queryCheckWorkTypeLayout(String organId, String time) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return laborEfficiencyService.queryCheckWorkTypeLayout(getCustomerId(), organId, time);
	}


	/**
	 * 出勤明细
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAttendanceDetail")
	public PaginationDto<LaborEfficiencyGridDto> queryAttendanceDetail(String organId,  String time,
			int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> checkList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.LDLXN_CHECK);
		return laborEfficiencyService.queryAttendanceDetail(getCustomerId(), organId, time, checkList, page,
				rows);
	}

	private  void setYearMonth(String time){
    	DateTime dt=new DateTime(DateUtil.getDate());
    	DateTime max=new DateTime(time);
    	Calendar calendar = Calendar.getInstance();  
    	String minTime="";
    	String maxTime="";
    	calendar.set(Calendar.YEAR, max.getYear());
		calendar.set(Calendar.MONTH, max.getMonthOfYear()-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar     
                .getActualMinimum(Calendar.DAY_OF_MONTH));
		minTime=new DateTime(calendar.getTime()).toString("yyyy.MM.dd");
    	if(dt.toString("yyyyMM").compareTo(time)==1){
    		calendar.set(Calendar.DAY_OF_MONTH, calendar     
                    .getActualMaximum(Calendar.DAY_OF_MONTH));
    		maxTime=new DateTime(calendar.getTime()).toString("yyyy.MM.dd");
    		request.setAttribute("time", new DateTime(calendar.getTime()).toString("yyyyMM"));
    		request.setAttribute("timeVal", new DateTime(calendar.getTime()).toString("yyyy年MM月"));
  		}else{
  			request.setAttribute("time", dt.toString("yyyyMM"));
  	  		request.setAttribute("timeVal", dt.toString("yyyy年MM月"));
    		maxTime=dt.toString("yyyy.MM.dd");
  		}
		request.setAttribute("timeRange", minTime+"-"+maxTime);

	}
	/**
	 * 出勤明细-考勤类型
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCheckWorkType")
	public Map<String, Object> queryCheckWorkType() {
		List<Integer> checkList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.LDLXN_CHECK);
		return laborEfficiencyService.queryCheckWorkType(getCustomerId(), checkList);
	}
	public static void main(String[]d){
		String maxTime="201512";
		String time=maxTime.substring(0,4)+"-"+maxTime.substring(4,6)+"-01";
		DateTime max=new DateTime(time);
	  	  Calendar calendar = Calendar.getInstance();  
	    	String minTime="";
	    	calendar.set(Calendar.YEAR, max.getYear());
			calendar.set(Calendar.MONTH, max.getMonthOfYear()-1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar     
	                .getActualMinimum(Calendar.DAY_OF_MONTH));
			minTime=new DateTime(calendar.getTime()).toString("yyyy.MM.dd");
			System.out.println(minTime);
	}
}
