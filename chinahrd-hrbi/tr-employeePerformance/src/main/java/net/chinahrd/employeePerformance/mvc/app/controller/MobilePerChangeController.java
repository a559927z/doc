package net.chinahrd.employeePerformance.mvc.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.employeePerformance.mvc.app.service.MobilePerChangeService;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCardDto;
import net.chinahrd.entity.dto.app.performance.PreChangeStatusDto;
import net.chinahrd.entity.dto.app.performance.PreDetailDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 员工绩效 app
 * @author htpeng
 *2016年6月6日上午10:10:05
 */
@Controller
@RequestMapping("mobile/perChange")
public class MobilePerChangeController extends BaseController {

    @Autowired
    private MobilePerChangeService perChangeService;


    @RequestMapping(value = "/toPerChangeView")
    public String toPerBenefitView2(HttpServletRequest request) {
      
    	Object obj=request.getParameter("organId");
        List<OrganDto> organPermit = getOrganPermit();
        if (CollectionKit.isNotEmpty(organPermit)) {
            if(null==obj){
          	  //当前机构id
          	  OrganDto topOneOrgan = organPermit.get(0);
	        	request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
            }else{
          	  String orgId=obj.toString();
          	  request.setAttribute("reqOrganId", orgId);
            }
        }
        // 获取绩效周期	by jxzhang
        int perWeek = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GRJXJBH_PERFMANWEEK).intValue();
        //查询绩效日期
        List<Integer> list = perChangeService.queryPreYearMonthByCustomerId(getCustomerId(), perWeek);
        if(list==null||list.size()==0){
        	request.setAttribute("currTime", "没有绩效数据");
        	request.setAttribute("yearMonth", "");
        	request.setAttribute("lastYearMonth", "");
        }else{
        	Integer yearMonth = list.get(0);
            request.setAttribute("yearMonth", yearMonth);
            String lastYearMonth="";
            if(list.size()>1){
            	lastYearMonth=list.get(1)+"";
            }
            request.setAttribute("lastYearMonth", lastYearMonth);
            String beforeLastYearMonth="";
            if(list.size()>2){
            	beforeLastYearMonth=list.get(2)+"";
            }
            request.setAttribute("beforeLastYearMonth", beforeLastYearMonth);
            int month=yearMonth%100;
            String currTime=(yearMonth/100)+(month<=6?"年上半年":"年下半年");
            request.setAttribute("currTime", currTime);
        }
        
        return "mobile/perChange/perChange";
    }

   

    /**
     * 获取界面顶部四个方块内部的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPageTopData", method = RequestMethod.GET)
    public PerfChangeCardDto getPageTopData(String organizationId, String yearMonth) {
        if (organizationId == null) {
            return null;
        }
        int yearMonthInt=0;
        try {
			yearMonthInt=Integer.parseInt(yearMonth);
		} catch (NumberFormatException e) {
			  return null;
		}
        
        //组装数据到前台
        PerfChangeCardDto result = perChangeService.queryHighLowPreByMonth(organizationId, yearMonthInt, getCustomerId());
        return result;
    }

  
    
    /**
     * 返回绩效结果变化趋势和绩效异常（大起大落）的统计数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPreChangeCountData", method = RequestMethod.GET)
    public Map<String, PreChangeStatusDto> getPreChangeCountData(String organizationId, Integer yearMonth,Integer lastYearMonth) {
        if (organizationId == null) {
            return null;
        }

        //查询升降1级以上和没变化的
        PreChangeStatusDto dto1 = perChangeService.queryPreChangeCountByMonth(organizationId, lastYearMonth,
        		yearMonth, 1, getCustomerId());

        //查询升降2级以上
        PreChangeStatusDto dto2 = perChangeService.queryPreChangeCountByMonth(organizationId, lastYearMonth,
        		yearMonth, 2, getCustomerId());
        int empCount = 0;
        //组装数据到前台
        Map<String, PreChangeStatusDto> map = CollectionKit.newMap();
        if (dto1 != null) {
            //查询部门总人数 这里Equal仅仅是用于数据的传输,实际上是人员总数
            empCount = dto1.getDown() + dto1.getEqual() + dto1.getRise();
            map.put("change", dto1);
        }
        if (dto2 != null) {
            dto2.setEqual(empCount);
            map.put("bigChange", dto2);
        }

        return map;
    }



    /**
     * 查询绩效的星级分布
     *
     * @param organizationId
     * @param yearMonth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPreStarCountByMonth", method = RequestMethod.GET)
    public Map<String,Object> queryPreStarCountByMonth(String organizationId, String yearMonth) {
        if (null == organizationId) {
            return CollectionKit.newMap();
        }

        if (null == yearMonth) {
        	 return CollectionKit.newMap();
        }

        return perChangeService.queryPreStarCountByMonth(organizationId, yearMonth, getCustomerId());
    }



    /*
     * 绩效详情
     */
    @ResponseBody
    @RequestMapping(value = "/queryPerformanceDetail", method = RequestMethod.POST)
    public PaginationDto<PreDetailDto> queryPerformanceDetail(String organId,
                                                               String yearMonth, Integer page, Integer rows) {
        if (organId == null) {
//			organizationId="15e24f67bddd47ee94a9a09e7ff1d2b8";
            return null;
        }

        PaginationDto<PreDetailDto> dto = new PaginationDto<PreDetailDto>(page, rows);
        PaginationDto<PreDetailDto> list = perChangeService.queryPerformanceDetail( getCustomerId(), organId,
                dto, yearMonth.trim());

        return list;
    }



}