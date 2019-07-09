package net.chinahrd.empSatisfaction.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.empSatisfaction.mvc.pc.service.EmpSatisfactionService;
import net.chinahrd.entity.dto.pc.competency.SatisfactionChartDto;
import net.chinahrd.entity.dto.pc.competency.SatisfactionDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CacheHelper;

/**
 * 员工敬业度满意度
 * Created by qpzhu on 16/03/14.
 */
@Controller
@RequestMapping("/empSatisfaction")
public class EmpSatisfactionController extends BaseController {
	
	@Autowired
    private EmpSatisfactionService empSatisfactionService;
	
	@RequestMapping(value = "/toEmpSatisfactionView")
    public String toEmpSatisfactionView(HttpServletRequest request){
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("empSatisfaction/toEmpSatisfactionView");
        if(StringUtils.isNotEmpty(functionId)){request.setAttribute("quotaId", functionId); }
        
        return "biz/drivingforce/empSatisfaction";
    }
	
	/**
     * 获取敬业度年度得分
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEngagementYearSoure", method = RequestMethod.GET)
    public Map<String, Object>  getEngagementYearSoure(String organizationId) {
        return empSatisfactionService.getEngagementYearSoure(getCustomerId(),organizationId);
    }
	
	/**
     * 获取敬业度分数
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEngagementSoure", method = RequestMethod.GET)
    public List<SatisfactionChartDto> getEngagementSoure(String organizationId) {
        List<SatisfactionChartDto> engagementData = empSatisfactionService.getEngagementSoure(getCustomerId(),organizationId);
        return engagementData;
    }
	
	/**
     * 获取敬业度题目数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEngagementSubject", method = RequestMethod.GET)
    public List<SatisfactionDto> getEngagementSubject(String organizationId) {
        List<SatisfactionDto> engagementData = empSatisfactionService.getEngagementSubject(getCustomerId(),organizationId);
        return engagementData;
    }
    
    
    /**
     * 获取满意度年度得分
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSatisfactionYearSoure", method = RequestMethod.GET)
    public Map<String, Object>  getSatisfactionYearSoure(String organizationId) {
        return  empSatisfactionService.getSatisfactionYearSoure(getCustomerId(),organizationId);
    }
	
	/**
     * 获取满意度分数
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSatisfactionSoure", method = RequestMethod.GET)
    public List<SatisfactionChartDto> getSatisfactionSoure(String organizationId) {
        List<SatisfactionChartDto> satisfactionData = empSatisfactionService.getSatisfactionSoure(getCustomerId(),organizationId);
        return satisfactionData;
    }
	
	/**
     * 获取满意度题目数据
     *
     * @param organizationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSatisfactiontSubject", method = RequestMethod.GET)
    public List<SatisfactionDto> getSatisfactiontSubject(String organizationId) {
        List<SatisfactionDto> satisfactionData = empSatisfactionService.getSatisfactionSubject(getCustomerId(),organizationId);
        return satisfactionData;
    }
}
