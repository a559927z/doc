package net.chinahrd.perBenefit.mvc.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitTrendDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.perBenefit.mvc.pc.service.PerBenefitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 人均效益Controller
 */
@Controller
@RequestMapping("/perBenefit")
public class PerBenefitController extends BaseController {

    @Autowired
    private PerBenefitService perBenefitService;
    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = "/toPerBenefitView")
    public String toPerBenefitView(HttpServletRequest request) {
        FunctionTreeDto dto = functionService.findFunctionObj(getCustomerId(), null, "perBenefit/toPerBenefitView");
        if (dto != null) {
            request.setAttribute("quotaId", dto.getId());
        }
        return "biz/productivity/perBenefits";
    }

    /**
     * 获取人均效益(收入、毛利)
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAvgBenefit", method = RequestMethod.GET)
    public AvgBenefitDto getAvgBenefit(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return perBenefitService.findAvgBenefit(organId, getCustomerId());
    }

    /**
     * 获取万元薪资、人力资金执行率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerBenefitResult", method = RequestMethod.GET)
    public PerBenefitResultDto getPerBenefitResult(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return perBenefitService.findPerBenefitResult(organId, getCustomerId());
    }

    /**
     * 获取人均效益趋势数据
     *
     * @param organId 机构id
     * @param type    0:毛利  1:收入
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAvgBenefitTrend", method = RequestMethod.GET)
    public List<AvgBenefitTrendDto> getAvgBenefitTrend(String organId, String yearMonthStr, Integer type) {
        if (type == null) {
            type = 0;
        }
        String[] yearMonths = stringToArr(yearMonthStr);
        String minYearMonth = yearMonths.length > 1 ? yearMonths[0] : null;
        String maxYearMonth = yearMonths.length > 1 ? yearMonths[1] : null;
        List<AvgBenefitTrendDto> trendData = perBenefitService.queryAvgBenefitTrend(organId, type, minYearMonth, maxYearMonth, getCustomerId());
        return trendData;
    }

    /**
     * 获取下级组织人均效益
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrgBenefitData", method = RequestMethod.GET)
    public List<PerBenefitDto> getOrgBenefitData(String organizationId, String yearMonthStr, Integer type) {
        if (type == null) {
            type = 0;
        }
        String[] yearMonths = stringToArr(yearMonthStr);
        String minYearMonth = yearMonths.length > 1 ? yearMonths[0] : null;
        String maxYearMonth = yearMonths.length > 1 ? yearMonths[1] : null;
        return perBenefitService.getOrgBenefitData(organizationId, type, minYearMonth, maxYearMonth, getCustomerId());
    }

    /**
     * 获取万元薪资集合
     *
     * @param organId 机构id
     * @param type    0:毛利  1:收入
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBenefitPayTrend", method = RequestMethod.GET)
    public List<AvgBenefitTrendDto> getBenefitPayTrend(String organId, String yearMonthStr, Integer type) {
        if (type == null) {
            type = 0;
        }
        String[] yearMonths = stringToArr(yearMonthStr);
        String minYearMonth = yearMonths.length > 1 ? yearMonths[0] : null;
        String maxYearMonth = yearMonths.length > 1 ? yearMonths[1] : null;
        List<AvgBenefitTrendDto> trendData = perBenefitService.queryPayTrend(organId, type, minYearMonth, maxYearMonth, getCustomerId());
        return trendData;
    }

    /**
     * 获取下级组织万元薪资
     *
     * @param organId 机构id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganPay", method = RequestMethod.GET)
    public List<PerBenefitDto> getSubOrganPay(String organId, String yearMonthStr, Integer type) {
        if (type == null) {
            type = 0;
        }

        String[] yearMonths = stringToArr(yearMonthStr);
        String minYearMonth = yearMonths.length > 1 ? yearMonths[0] : null;
        String maxYearMonth = yearMonths.length > 1 ? yearMonths[1] : null;
        List<PerBenefitDto> resultDtos = perBenefitService.querySubOrganPay(organId, type, minYearMonth, maxYearMonth, getCustomerId());
        return resultDtos;
    }


    /**
     * 获取人力资金执行率集合
     *
     * @param organId 机构id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getExecuteRateTrend", method = RequestMethod.GET)
    public List<AvgBenefitTrendDto> getExecuteRateTrend(String organId, String yearMonthStr) {
        String[] yearMonths = stringToArr(yearMonthStr);
        String minYearMonth = yearMonths.length > 1 ? yearMonths[0] : null;
        String maxYearMonth = yearMonths.length > 1 ? yearMonths[1] : null;
        List<AvgBenefitTrendDto> trendData = perBenefitService.queryExecuteRateTrend(organId, minYearMonth, maxYearMonth, getCustomerId());
        return trendData;
    }

    /**
     * 获取下级组织人力资金执行率
     *
     * @param organId
     * @param yearMonthStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganExecuteRate", method = RequestMethod.GET)
    public List<PerBenefitDto> getSubOrganExecuteRate(String organId, String yearMonthStr) {
        String[] yearMonths = stringToArr(yearMonthStr);
        String minYearMonth = yearMonths.length > 1 ? yearMonths[0] : null;
        String maxYearMonth = yearMonths.length > 1 ? yearMonths[1] : null;

        List<PerBenefitDto> resultDtos = perBenefitService.querySubOrganExecuteRate(organId, minYearMonth, maxYearMonth, getCustomerId());
        return resultDtos;
    }

    /**
     * string转数组
     * @param yearMonthStr
     * @return
     */
    private String[] stringToArr(String yearMonthStr){
        String[] yearMonths = new String[2];
        if (!StringUtils.isEmpty(yearMonthStr)) {
            yearMonths = yearMonthStr.split(",");
        }
        return yearMonths;
    }

}
