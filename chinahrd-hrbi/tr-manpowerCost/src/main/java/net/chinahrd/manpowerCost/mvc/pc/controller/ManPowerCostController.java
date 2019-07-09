package net.chinahrd.manpowerCost.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerOrganDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerTrendDto;
import net.chinahrd.manpowerCost.mvc.pc.service.ManpowerCostService;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人才成本
 * Created by htpeng on 15/12/30.
 */
@Controller
@RequestMapping("/manpowerCost")
public class ManPowerCostController extends BaseController {

    @Autowired
    private ManpowerCostService manpowerCostService;

    /**
     * 跳转人力成本分析
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toManpowerCostView", method = RequestMethod.GET)
    public String toManpowerCostView(HttpServletRequest request) {
    	// 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("manpowerCost/toManpowerCostView");
        if(null != functionId){request.setAttribute("quotaId", functionId); }
        
        request.setAttribute("curdate", DateUtil.getDBCurdate());
        return "biz/productivity/manpowerCost";
    }

    /**
     * 获取上月的人力成本 和人均成本
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompareMonth", method = RequestMethod.GET)
    public List<ManpowerCompareDto> getCompareMonth(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.getCompareMonth(getCustomerId(), organId, time);
    }

    /**
     * 获取本年的人力成本 和人均成本
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompareYear", method = RequestMethod.GET)
    public List<ManpowerCompareDto> getCompareYear(String organId, String time) {

        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.getCompareYear(getCustomerId(), organId, time);
    }


    /**
     * 成本月度趋势
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrendByMonth", method = RequestMethod.GET)
    ManpowerTrendDto getTrendByMonth(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.queryTrendByMonth(getCustomerId(), organId, time);
    }


    /**
     * 人均成本月度趋势
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAvgTrendByMonth", method = RequestMethod.GET)
    List<ManpowerDto> queryAvgTrendByMonth(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.queryAvgTrendByMonth(getCustomerId(), organId, time);
    }


    /**
     * 人力成本结构
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getItemDetail", method = RequestMethod.GET)
    List<ManpowerItemDto> queryItemDetail(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.queryItemDetail(getCustomerId(), organId, time);
    }

    /**
     * 各架构人力成本
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrganCost", method = RequestMethod.GET)
    List<ManpowerOrganDto> queryOrganCost(String organId, String time) {
        return manpowerCostService.queryOrganCost(getCustomerId(), organId, time);
    }

    /**
     * 获取上月的人力成本 和总成本
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProportionMonth", method = RequestMethod.GET)
    public List<ManpowerCompareDto> getProportionMonth(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.queryProportionMonth(getCustomerId(), organId, time);
    }

    /**
     * 获取本年的人力成本 和总成本
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProportionYear", method = RequestMethod.GET)
    public List<ManpowerCompareDto> getProportionYear(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return manpowerCostService.queryProportionYear(getCustomerId(), organId, time);
    }

    /**
     * 行业均值
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAvgValueData", method = RequestMethod.GET)
    public Double getAvgValueData(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.queryAvgValue(getCustomerId(), organId);
    }

    /**
     * 人均成本预警
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCostAvgWarn", method = RequestMethod.GET)
    public Map<String, Double> getCostAvgWarn(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.getCostAvgWarn(getCustomerId(), organId, time);
    }

    /**
     * 销售 成本  利润 明细
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllDetailData", method = RequestMethod.GET)
    public List<ManpowerDto> getAllDetailData(String organId, String time) {

        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manpowerCostService.queryAllDetailData(getCustomerId(), organId, time);
    }

}
