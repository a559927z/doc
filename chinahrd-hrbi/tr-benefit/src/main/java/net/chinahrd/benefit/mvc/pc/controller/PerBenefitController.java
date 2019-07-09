package net.chinahrd.benefit.mvc.pc.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.benefit.mvc.pc.service.PerBenefitService;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitAmountDto;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CacheHelper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人均效益Controller
 */
@Controller
@RequestMapping("/perBenefit")
public class PerBenefitController extends BaseController {

    @Autowired
    private PerBenefitService perBenefitService;

    private static final String YEAR = "year";

    @RequestMapping(value = "/toPerBenefitView")
    public String toPerBenefitView(HttpServletRequest request) {
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("perBenefit/toPerBenefitView");
        if(StringUtils.isNotEmpty(functionId)){request.setAttribute("quotaId", functionId); }
        
        return "biz/productivity/perBenefits";
    }

    /**
     * 获取人均效益数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerBenefitData", method = RequestMethod.GET)
    public List<PerBenefitDto> getPerBenefitData(String organizationId) {
        List<PerBenefitDto> perBenefitDate = perBenefitService.getPerBenefitData(getCustomerId(), organizationId);
        return perBenefitDate;
    }

    /**
     * 获取行业均值信息
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAvgValueData", method = RequestMethod.GET)
    public Double getAvgValueData(String organizationId) {
        return perBenefitService.getAvgValueData(getCustomerId(), organizationId);
    }

    /**
     * 获取人均效益趋势数据
     *
     * @param organizationId 机构id
     * @param type           month：月； year:年
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrendData", method = RequestMethod.GET)
    public List<PerBenefitDto> getTrendData(String organizationId, String type) {
        List<PerBenefitDto> trendData = null;
        if (YEAR.equals(type)) {
            trendData = perBenefitService.getTrendByYear(getCustomerId(), organizationId);
        } else {
            trendData = perBenefitService.getTrendByMonth(getCustomerId(), organizationId);
        }
        return trendData;
    }

    /**
     * 获取当前组织人均效益数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrgBenefitData", method = RequestMethod.GET)
    public List<PerBenefitDto> getOrgBenefitData(String organizationId) {
        return perBenefitService.getOrgBenefitData(getCustomerId(), organizationId);
    }

    /**
     * 获取当前组织最近12个月人均效益、利润总额、销售总额数据(按时间倒序)
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrgRecentData", method = RequestMethod.GET)
    public PerBenefitAmountDto getOrgRecentData(String organizationId) {
        return perBenefitService.getOrgRecentData(getCustomerId(), organizationId);
    }

    /**
     * 人均效益变化幅度（最近一个月和最近上一个月）
     *
     * @param organizationId
     * @param upgrade        true: 升幅,false: 降幅
     * @return Map<机构Id, List<PerBenefitDto>>
     */
    @ResponseBody
    @RequestMapping(value = "/getVariationRange", method = RequestMethod.GET)
    public Map<Integer, List<PerBenefitDto>> getVariationRange(String organizationId, boolean upgrade) {
        return perBenefitService.queryVariationRange(getCustomerId(), organizationId, upgrade);
    }

}
