package net.chinahrd.organizationalStructure.mvc.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.entity.dto.pc.OrgChartDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.organizationalStructure.mvc.pc.service.OrgChartService;

/**
 * 组织机构(编制和空缺)
 *
 * @author guanjian
 */
@Controller
@RequestMapping("/orgChart")
public class OrgChartController extends BaseController {
    @Autowired
    private OrgChartService orgChartService;


    @RequestMapping(value = "/toOrgView", method = RequestMethod.GET)
    public String toOrgChartView(HttpServletRequest request) {
        return "biz/other/orgChart2";
    }


    /**
     * 获取组织机构图root节点数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRootOrgData", method = RequestMethod.GET)
    public OrgChartDto getRootOrgData(String organizationId) {
        return orgChartService.queryOrgChartInitDataById(getCustomerId(), organizationId);
    }

    /**
     * 由获取组织机构图子节点数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getChildOrgData", method = RequestMethod.GET)
    public List<OrgChartDto> getChildOrgData(String organizationId) {
        return orgChartService.queryChildOrgById(getCustomerId(), organizationId);
    }
}
