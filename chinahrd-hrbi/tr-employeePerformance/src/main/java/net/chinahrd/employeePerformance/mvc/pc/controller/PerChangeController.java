package net.chinahrd.employeePerformance.mvc.pc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.employeePerformance.mvc.pc.service.PerChangeService;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeEmpDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreDetailDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreStarCountDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.PropertiesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人绩效及变化  过往绩效
 *
 * @author guanjian
 */
@Controller
@RequestMapping("/perChange")
public class PerChangeController extends BaseController {
    @Autowired
    private FunctionService functionService;
    @Autowired
    private PerChangeService perChangeService;
    @Autowired
    private OrganService organService;
    @Autowired
    private ConfigService configService;

//    @ControllerAop(description="跳转到员工绩效")
    @RequestMapping(value = "/toPerChangeView")
    public String toPerBenefitView2(HttpServletRequest request, String organId) {
        String functionId = CacheHelper.getFunctionId("perChange/toPerChangeView");
        if(null != functionId){request.setAttribute("quotaId", functionId); }
        
        // 获取绩效周期	by jxzhang
        int perWeek = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GRJXJBH_PERFMANWEEK).intValue();
        request.setAttribute("perWeek", perWeek);

        //查询绩效日期
        List<Integer> listYM = perChangeService.queryPreYearMonthByCustomerId(getCustomerId(), perWeek);
        if (listYM != null && !listYM.isEmpty()) {
            String yearMonths = CollectionKit.convertToString(listYM, ",");
            request.setAttribute("yearMonths", yearMonths);
        }
        return "biz/productivity/perChange2";
    }

    /**
     * 获取绩效考核期
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerfChangeDate", method = RequestMethod.GET)
    public List<Integer> getPerfChangeDate() {
        int perWeek = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GRJXJBH_PERFMANWEEK).intValue();
        request.setAttribute("perWeek", perWeek);

        //查询绩效日期
        List<Integer> listYM = perChangeService.queryPreYearMonthByCustomerId(getCustomerId(), perWeek);
        return listYM;
    }


    /**
     * 获取界面顶部四个方块内部的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPageTop4BlockData", method = RequestMethod.GET)
    public KVItemDto getPageTop4BlockData(String organId, String yearMonths, int idx) {
        if (organId == null) {
            return null;
        }
        String[] arr = StringUtils.isEmpty(yearMonths) ? null : yearMonths.split(",");

        if (arr == null || arr.length == 0) {
            return null;
        }
        Integer currYearMonth = Integer.parseInt(arr[0].trim()); // 当前的绩效年月
        if (idx % 2 != 0) {          //索引非2的倍数的为连续绩效
            List<Integer> twoMonths = CollectionKit.newList();
            twoMonths.add(currYearMonth);
            if (arr.length > 1) {
                twoMonths.add(Integer.parseInt(arr[1].trim()));
            }
            if (idx == 1) {    //低绩效
                return perChangeService.queryHighLow2MonthCount(organId, twoMonths, Boolean.TRUE, getCustomerId());
            }
            return perChangeService.queryHighLow2MonthCount(organId, twoMonths, Boolean.FALSE, getCustomerId());
        }
        if (idx == 2) {    //低绩效
            return perChangeService.queryHighLowMonthCount(organId, currYearMonth, Boolean.TRUE, getCustomerId());
        }
        return perChangeService.queryHighLowMonthCount(organId, currYearMonth, Boolean.FALSE, getCustomerId());
    }

    /**
     * 获取界面顶部四个方块内部的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPageTop4BlockDataDetail", method = RequestMethod.POST)
    public PaginationDto<PerfChangeEmpDto> getPageTop4BlockDataDetail(String organizationId, String yearMonths, Integer index, Integer total, Integer page, Integer rows) {
        if (organizationId == null || index == null) {
            return null;
        }

        String[] arr = StringUtils.isEmpty(yearMonths) ? null : yearMonths.split(",");

        if (arr == null || arr.length == 0) {
            return null;
        }

        Integer currentYearMonth = Integer.parseInt(arr[0].trim()); // 当前的绩效年月
        Integer prevYearMonth = null;
        if (arr.length > 1) {
            prevYearMonth = Integer.parseInt(arr[1].trim());
        }
        PaginationDto<PerfChangeEmpDto> dto = new PaginationDto<PerfChangeEmpDto>(page, rows);
        dto.setRecords(total);
        switch (index) {
            case 1:
                return perChangeService.queryLowPre(organizationId, currentYearMonth, prevYearMonth, Boolean.TRUE, getCustomerId(), dto);
            case 2:
                return perChangeService.queryLowPre(organizationId, currentYearMonth, prevYearMonth, Boolean.FALSE, getCustomerId(), dto);
            case 3:
                return perChangeService.queryHighPre(organizationId, currentYearMonth, prevYearMonth, Boolean.TRUE, getCustomerId(), dto);
            case 4:
                return perChangeService.queryHighPre(organizationId, currentYearMonth, prevYearMonth, Boolean.FALSE, getCustomerId(), dto);
        }
        return dto;
    }

    /**
     * 获取绩效考核期的总人数
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpCount", method = RequestMethod.GET)
    public Map<String, Integer> queryEmpCount(String organizationId, String yearMonth, String empType, String crowds) {
        if (organizationId == null) {
            return null;
        }
        if (null == crowds) {
            return null;
        }
        List<String> cs = null;
        if (!StringUtils.isEmpty(crowds)) {
            cs = Arrays.asList(crowds.split(","));
        }

        Map<String, Integer> map = CollectionKit.newMap();

        //查询升降1级以上和没变化的
        Integer count = perChangeService.queryEmpCount(getCustomerId(), organizationId, yearMonth, empType, cs);
        map.put("count", count);
        return map;
    }


    /**
     * 获取绩效分布的高低发布
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPreDistributionData", method = RequestMethod.GET)
    public List<List<PerfChangeEmpDto>> getPreDistributionData(String organizationId, Integer currYearMonth, Integer prevYearMonth, Integer empType, String crowds) {
        if (StringUtils.isEmpty(organizationId)) {
            return null;
        }
        if (StringUtils.isEmpty(currYearMonth)) {
            return null;
        }
        if (StringUtils.isEmpty(empType)) {
            return null;
        }

        List<String> cs = null;
        if (!StringUtils.isEmpty(crowds)) {
            cs = Arrays.asList(crowds.split(","));
        }

        return perChangeService.queryPreDistributionListByMonth(organizationId, currYearMonth, prevYearMonth, empType, getCustomerId(), cs);
    }

    /**
     * 获取绩效分布的高低绩效统计
     *
     * @param organizationId
     * @param yearMonth
     * @param empType
     * @param crowds
     * @return {type:1-低绩效 2-高绩效,total:统计人数}
     */
    @ResponseBody
    @RequestMapping(value = "/getPerfDistributeCount", method = RequestMethod.GET)
    private List<PreStarCountDto> getPerfDistributeCount(String organizationId, Integer yearMonth, Integer empType, String crowds) {
        if (StringUtils.isEmpty(organizationId)) {
            return null;
        }
        if (StringUtils.isEmpty(yearMonth)) {
            return null;
        }
        if (StringUtils.isEmpty(empType)) {
            return null;
        }

        List<String> cs = null;
        if (!StringUtils.isEmpty(crowds)) {
            cs = Arrays.asList(crowds.split(","));
        }
        return perChangeService.queryPerfDistributeCount(organizationId, yearMonth, empType, cs, getCustomerId());
    }

    /**
     * 获取绩效分布的高低绩效人员信息
     *
     * @param organizationId
     * @param yearMonth
     * @param empType
     * @param crowds
     * @return {type:1-低绩效 2-高绩效,total:统计人数}
     */
    @ResponseBody
    @RequestMapping(value = "/getPerfDistributeEmp", method = RequestMethod.POST)
    private PaginationDto<PerfChangeEmpDto> getPerfDistributeEmp(String organizationId, Integer yearMonth, Integer empType, String crowds, Integer idx, Integer total, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organizationId)) {
            return null;
        }
        if (StringUtils.isEmpty(yearMonth)) {
            return null;
        }
        if (StringUtils.isEmpty(empType)) {
            return null;
        }

        List<String> cs = null;
        if (!StringUtils.isEmpty(crowds)) {
            cs = Arrays.asList(crowds.split(","));
        }

        PaginationDto<PerfChangeEmpDto> dto = new PaginationDto<PerfChangeEmpDto>(page, rows);
        dto.setRecords(total);
        dto = perChangeService.queryPerfDistributeEmp(organizationId, yearMonth, empType, cs, idx, getCustomerId(), dto);
        return dto;

    }

    /**
     * 查询绩效分布的星级分布
     *
     * @param organizationId
     * @param yearMonth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPreStarCountByMonth", method = RequestMethod.GET)
    public List<PreStarCountDto> queryPreStarCountByMonth(String organizationId, Integer yearMonth, String crowds) {
        if (StringUtils.isEmpty(organizationId)) {
            return null;
        }
        if (StringUtils.isEmpty(yearMonth)) {
            return null;
        }
        List<String> cs = null;
        if (!StringUtils.isEmpty(crowds)) {
            cs = Arrays.asList(crowds.split(","));
        }

        List<PreStarCountDto> list = perChangeService.queryPreStarCountByMonth(organizationId, yearMonth, getCustomerId(), cs);
        return list;
    }

    /**
     * 返回绩效结果变化趋势和绩效异常（大起大落）的统计数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPreChangeCountData", method = RequestMethod.GET)
    public Map<String, PreChangeStatusDto> getPreChangeCountData(String organizationId, String yearMonths, String crowds) {
        if (organizationId == null) {
            return null;
        }

        if (null == crowds) {
        	crowds = "";
        }
        List<String> cs = null;
        if (!StringUtils.isEmpty(crowds)) {
            cs = Arrays.asList(crowds.split(","));
        }

        Integer beginYearMonth = null;
        Integer endYearMonth = null;
        String[] arr = StringUtils.isEmpty(yearMonths) ? null : yearMonths.split(",");

        if (arr != null && arr.length > 1) {
            endYearMonth = Integer.parseInt(arr[0].trim());
            beginYearMonth = Integer.parseInt(arr[1].trim());
        } else {
            return null;
        }

        //查询升降1级以上和没变化的
        PreChangeStatusDto dto1 = perChangeService.queryPreChangeCountByMonth(organizationId, beginYearMonth,
                endYearMonth, 1, getCustomerId(), cs);

        //查询升降2级以上
        PreChangeStatusDto dto2 = perChangeService.queryPreChangeCountByMonth(organizationId, beginYearMonth,
                endYearMonth, 2, getCustomerId(), cs);
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


    @ResponseBody
    @RequestMapping(value = "/getEmpCountByOrganId", method = RequestMethod.GET)
    public int getEmpCountByOrganId(String organizationId) {
        if (organizationId == null) {
            return 0;
        }
        Integer empCount = organService.getEmpCountByOrganId(getCustomerId(), organizationId);
        return empCount == null ? 0 : empCount;
    }

    /*
     * 绩效详情
     */
    @ResponseBody
    @RequestMapping(value = "/queryPerformanceDetail", method = RequestMethod.POST)
    public PaginationDto<PreDetailDto> queryPerformanceDetail(String queryType, String keyName, String organizationId,
                                                              String sequenceId, String abilityId, String yearMonth,
                                                              String performanceKey, Integer page, Integer rows) {
        if (organizationId == null) {
            return null;
        }

        PaginationDto<PreDetailDto> dto = new PaginationDto<PreDetailDto>(page, rows);
        PaginationDto<PreDetailDto> list = perChangeService.queryPerformanceDetail(queryType, keyName, getCustomerId(), organizationId,
                dto, yearMonth.trim(), sequenceId, abilityId, performanceKey);

        return list;
    }

    /***
     * 更新员工绩效基础配置信息
     *
     * @param heightPer   高绩效
     * @param lowPer      低绩效
     * @param perfmanWeek 绩效周期
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBaseConfig", method = RequestMethod.POST)
    public ResultDto<String> updateBaseConfig(String heightPer, String lowPer, String perfmanWeek) {
        ResultDto<String> rsDto = new ResultDto<String>();
        List<ConfigDto> list = CollectionKit.newList();
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN), heightPer));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.GRJXJBH_LOWPERFMAN), lowPer));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.GRJXJBH_PERFMANWEEK), perfmanWeek));
        configService.updateSysConfig(getCustomerId(), list);
        rsDto.setType(true);
        return rsDto;
    }

    /**
     * 获取员工绩效基础配置信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBaseConfig", method = RequestMethod.POST)
    public Map<String, Object> getBaseConfig() {
        Map<String, Object> maps = CollectionKit.newMap();
        List<Integer> lowPerfman = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
        List<Integer> heightPerfman = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        Integer perfmanWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        maps.put("lowPerfman", lowPerfman);
        maps.put("heightPerfman", heightPerfman);
        maps.put("perfmanWeek", perfmanWeek);
        return maps;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getPerchangeByOrgan", method = RequestMethod.POST)
    public List<PerfChangeCountDto> queryPerchangeByOrgan(String organId, String yearMonth) {
    	return perChangeService.queryPerchangeByOrgan(getCustomerId(), organId, Integer.parseInt(yearMonth));
    }
}