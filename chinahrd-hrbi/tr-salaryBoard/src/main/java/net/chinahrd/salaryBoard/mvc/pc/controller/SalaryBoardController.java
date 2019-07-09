package net.chinahrd.salaryBoard.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpSharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalarySharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWageDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWelfareDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.salaryBoard.mvc.pc.service.SalaryBoardService;
import net.chinahrd.utils.CacheHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 薪酬看板
 * Created by wqcai on 16/3/29.
 */
@Controller
@RequestMapping("/salaryBoard")
public class SalaryBoardController extends BaseController {

    @Autowired   
    private SalaryBoardService salaryBoardService;

    @RequestMapping(value = "/toSalaryBoardView")
    public String toSalaryBoardView(HttpServletRequest request) {

        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("salaryBoard/toSalaryBoardView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
        
        return "biz/drivingforce/salaryBoard";
    }
     
    /**
     * 薪酬年度预算、年度累计与去年的比较 
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBudgetYear", method = RequestMethod.GET)
    public SalaryBoardDto getSalaryBudgetYear(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryBudgetYear(getCustomerId(), organId);
    }
    
    /**
     * 薪酬占人力成本比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryProportion", method = RequestMethod.GET)
    public Map<String,Object> getSalaryProportion(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryProportion(getCustomerId(), organId);
    }
    
    /**
     * 投资回报率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryRateOfReturn", method = RequestMethod.GET)
    public Map<String,Object> getSalaryRateOfReturn(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryRateOfReturn(getCustomerId(), organId);
    }
    
    /**
     * 薪酬总额统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryPayTotal", method = RequestMethod.GET)
    public Map<String,Object> getSalaryPayTotal(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryPayTotal(getCustomerId(), organId);
    }
    
    /**
     * 下级组织薪酬统计以及平均统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrganization", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalarySubOrganization(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrganization(getCustomerId(), organId);
    }
    
    
    
    /**
     * 组织KPI达标率、人力成本、薪酬总额的年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryCostKpi", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryCostKpi(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryCostKpi(getCustomerId(), organId);
    }
    
    /**
     * 营业额、利润、人力成本及薪酬总额的月度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryCostSalesProfit", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryCostSalesProfit(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryCostSalesProfit(getCustomerId(), organId);
    }
    
    /**
     * 下级机构人力资本投资回报率
     * @param organId
     * @param yearMonth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryOrganRateOfReturn", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryOrganRateOfReturn(String organId, String yearMonth) {
        if (StringUtils.isEmpty(organId) || StringUtils.isEmpty(yearMonth)) {
            return null;
        }
        return salaryBoardService.getSalaryOrganRateOfReturn(getCustomerId(), organId, yearMonth);
    }
    
    /**
     * 人力资本投资回报率月度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryMonthRateOfReturn", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryMonthRateOfReturn(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryMonthRateOfReturn(getCustomerId(), organId);
    }
    
    /**
     * 行业分位值年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBitValueYear", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryBitValueYear (String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryBitValueYear(getCustomerId(), organId);
    }
    
    /**
     * 薪酬差异度岗位表
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryDifferencePost", method = RequestMethod.GET)
    public Map<String,Object> getSalaryDifferencePost (String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryDifferencePost(getCustomerId(), organId);
    }
    
    /**
     * 员工CR值
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryEmpCR", method = RequestMethod.GET)
    public Map<String,Object> getSalaryEmpCR (String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryEmpCR(getCustomerId(), organId);
    }
    
    /**
     * 员工CR值分页
     *
     * @param keyName
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalaryEmpCR", method = RequestMethod.POST)
    public PaginationDto<SalaryEmpCRDto> findSalaryEmpCR(String organId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<SalaryEmpCRDto> dto = new PaginationDto<SalaryEmpCRDto>(page, rows);
        dto = salaryBoardService.findSalaryEmpCR(organId, dto, sidx, sord, getCustomerId());
        return dto;
    }
    
    /**
     * 工资统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWageStatistics", method = RequestMethod.GET)
    public Map<String,Object> getSalaryWageStatistics(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWageStatistics(getCustomerId(), organId);
    }
    
    /**
     * 工资总额月度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWagesMonth", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalaryWagesMonth(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWagesMonth(getCustomerId(), organId);
    }
    
    /**
     * 工资总额及占薪酬比年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWagesYear", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalaryWagesYear(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWagesYear(getCustomerId(), organId);
    }
    
    /**
     * 下级组织工资对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgWages", method = RequestMethod.GET)
    public Map<String,Object> getSalarySubOrgWages(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgWages(getCustomerId(), organId);
    }
    
    /**
     * 工资结构
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWageStructure", method = RequestMethod.GET)
    public Map<String,Object> getSalaryWageStructure(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWageStructure(getCustomerId(), organId);
    }
    
    /**
     * 固定与浮动薪酬比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryFixedProportion", method = RequestMethod.GET)
    public Map<String,Object> getSalaryFixedProportion(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryFixedProportion(getCustomerId(), organId);
    }
    
    /**
     * 职位序列固浮比统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySequenceFixed", method = RequestMethod.GET)
    public Map<String,Object> getSalarySequenceFixed(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySequenceFixed(getCustomerId(), organId);
    }
    
    /**
     * 职位层级固浮比统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryAbilityFixed", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalaryAbilityFixed(String positionId, String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryAbilityFixed(getCustomerId(),positionId, organId);
    }
    
    /**
     * 下级组织固浮比统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgFixed", method = RequestMethod.GET)
    public Map<String,Object> getSalarySubOrgFixed(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgFixed(getCustomerId(), organId);
    }
    
    /**
     * 下级组织固浮比统计列表
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgFixedList", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalarySubOrgFixedList(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgFixedList(getCustomerId(), organId);
    }
    
    /**
     * 年终奖金总额与利润的年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBonusProfit", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalaryBonusProfit(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryBonusProfit(getCustomerId(), organId);
    }
    
    /**
     * 年终奖金总额与利润的年度列表
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBonusProfitList", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalaryBonusProfitList(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryBonusProfitList(getCustomerId(), organId);
    }
    
    /**
     * 福利费用统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWelfare", method = RequestMethod.GET)
    public Map<String,Object> getSalaryWelfare(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWelfare(getCustomerId(), organId);
    }
    
    /**
     * 福利费用月度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWelfareMonth", method = RequestMethod.GET)
    public List<SalaryWelfareDto> getSalaryWelfareMonth(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWelfareMonth(getCustomerId(), organId);
    }
    
    /**
     * 福利总额及占薪酬比年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWelfareYear", method = RequestMethod.GET)
    public List<SalaryWelfareDto> getSalaryWelfareYear(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryWelfareYear(getCustomerId(), organId);
    }
    
    /**
     * 下级组织福利对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgWelfare", method = RequestMethod.GET)
    public Map<String,Object> getSalarySubOrgWelfare(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgWelfare(getCustomerId(), organId);
    }
    
    /**
     * 下级组织平均福利对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgAvgWelfare", method = RequestMethod.GET)
    public Map<String,Object> getSalarySubOrgAvgWelfare(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgAvgWelfare(getCustomerId(), organId);
    }
    
    
    /**
     * 根据类型获取(nfb,cpm,uncpm)福利key和name
     *
     * @param welfareType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryWelfareCategory", method = RequestMethod.GET)
    public List<SalaryWelfareDto> getSalaryWelfareCategory(String welfareType) {
        if (StringUtils.isEmpty(welfareType)) {
            return null;
        }
        return salaryBoardService.getSalaryWelfareCategory(getCustomerId(), welfareType);
    }
    
    /**
     * 根据时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryTime", method = RequestMethod.GET)
    public List<Integer> getSalaryTime() {
        return salaryBoardService.getSalaryTime(getCustomerId());
    }
    
    /**
     * 国家固定福利
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryFixedBenefits", method = RequestMethod.GET)
    public List<SalaryWelfareDto> getSalaryFixedBenefits(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryFixedBenefits(getCustomerId(), organId);
    }
    
    /**
     * 国家固定福利明细
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalaryBenefitsDetailed", method = RequestMethod.POST)
    public PaginationDto<SalaryWelfareDto> findSalaryBenefitsDetailed(String keyName,String welfareKey,String organId,String yearMonth,Integer page, Integer rows) {
        PaginationDto<SalaryWelfareDto> dto = new PaginationDto<SalaryWelfareDto>(page, rows);
        if (StringUtils.isEmpty(keyName)){
            keyName = null;
        }
        if (StringUtils.isEmpty(welfareKey)){
            welfareKey = null;
        }
        return salaryBoardService.findSalaryBenefitsDetailed(getCustomerId(), organId,keyName,welfareKey,yearMonth,dto);
    }
    
    /**
     * 企业福利货币
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBenefitsCurrency", method = RequestMethod.GET)
    public List<SalaryWelfareDto> getSalaryBenefitsCurrency(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryBenefitsCurrency(getCustomerId(), organId);
    }
    
    /**
     * 企业福利货币明细
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalaryCurrencyDetailed", method = RequestMethod.POST)
    public PaginationDto<SalaryWelfareDto> findSalaryCurrencyDetailed(String keyName,String welfareKey,String organId,String yearMonth,Integer page, Integer rows) {
    	PaginationDto<SalaryWelfareDto> dto = new PaginationDto<SalaryWelfareDto>(page, rows);
        if (StringUtils.isEmpty(keyName)){
            keyName = null;
        }
        if (StringUtils.isEmpty(welfareKey)){
            welfareKey = null;
        }
        return salaryBoardService.findSalaryCurrencyDetailed(getCustomerId(), organId,keyName,welfareKey,yearMonth,dto);
    }
    
    /**
     * 企业福利非货币
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBenefitsNoCurrency", method = RequestMethod.GET)
    public List<SalaryWelfareDto> getSalaryBenefitsNoCurrency(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryBenefitsNoCurrency(getCustomerId(), organId);
    }
    
    /**
     * 企业福利非货币明细
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalaryNoCurrencyDetailed", method = RequestMethod.POST)
    public PaginationDto<SalaryWelfareDto> findSalaryNoCurrencyDetailed(String keyName,String welfareKey,String organId,String yearMonth,Integer page, Integer rows) {
    	PaginationDto<SalaryWelfareDto> dto = new PaginationDto<SalaryWelfareDto>(page, rows);
        if (StringUtils.isEmpty(keyName)){
            keyName = null;
        }
        if (StringUtils.isEmpty(welfareKey)){
            welfareKey = null;
        }
        return salaryBoardService.findSalaryNoCurrencyDetailed(getCustomerId(), organId,keyName,welfareKey,yearMonth,dto);
    }
    
    /**
     * 持股统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryShares", method = RequestMethod.GET)
    public SalarySharesDto getSalaryShares(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryShares(getCustomerId(), organId);
    }
    
    /**
     * 持股员工总数年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryEmpShares", method = RequestMethod.GET)
    public List<SalarySharesDto> getSalaryEmpShares(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalaryEmpShares(getCustomerId(), organId);
    }
    
    /**
     * 持股数量年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySumShares", method = RequestMethod.GET)
    public List<SalarySharesDto> getSalarySumShares(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySumShares(getCustomerId(), organId);
    }
    
    /**
     * 下级组织持股员工数
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgShares", method = RequestMethod.GET)
    public List<SalarySharesDto> getSalarySubOrgShares(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgShares(getCustomerId(), organId);
    }
    
    /**
     * 下级组织持股数量
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrgSumShares", method = RequestMethod.GET)
    public List<SalarySharesDto> getSalarySubOrgSumShares(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return salaryBoardService.getSalarySubOrgSumShares(getCustomerId(), organId);
    }
    
    /**
     * 员工股票期权
     *
     * @param keyName
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalaryEmpShares", method = RequestMethod.POST)
    public PaginationDto<SalaryEmpSharesDto> findSalaryEmpShares(String keyName,String organId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<SalaryEmpSharesDto> dto = new PaginationDto<SalaryEmpSharesDto>(page, rows);
        dto = salaryBoardService.findSalaryEmpShares(keyName,organId, dto, sidx, sord, getCustomerId());
        return dto;
    }
}
