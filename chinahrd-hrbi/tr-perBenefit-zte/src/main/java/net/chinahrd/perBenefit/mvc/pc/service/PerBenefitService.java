package net.chinahrd.perBenefit.mvc.pc.service;

import java.util.List;

import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitTrendDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitResultDto;

/**
 * 人均效益Controller
 */
public interface PerBenefitService {

    /**
     * 获取万元薪资、人力资金执行率
     *
     * @param organizationId
     * @param customerId
     * @return
     */
    PerBenefitResultDto findPerBenefitResult(String organizationId, String customerId);


    /**
     * 获取人均效益(收入、毛利)
     *
     * @param organizationId
     * @param customerId
     * @return
     */
    AvgBenefitDto findAvgBenefit(String organizationId, String customerId);

    /**
     * 获取人均效益趋势集合
     *
     * @param organizationId
     * @param type
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<AvgBenefitTrendDto> queryAvgBenefitTrend(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId);


    /**
     * 获取万元薪资集合
     *
     * @param organizationId
     * @param type
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<AvgBenefitTrendDto> queryPayTrend(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId);

    /**
     * 获取下级组织万元薪资
     *
     * @param organizationId
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<PerBenefitDto> querySubOrganPay(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId);

    /**
     * 获取人力资金执行率集合
     *
     * @param organizationId
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<AvgBenefitTrendDto> queryExecuteRateTrend(String organizationId, String minYearMonth, String maxYearMonth, String customerId);

    /**
     * 获取下级组织人力资金执行率
     *
     * @param organizationId
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<PerBenefitDto> querySubOrganExecuteRate(String organizationId, String minYearMonth, String maxYearMonth, String customerId);

    /**
     * 获取下级组织人均效益
     * @param organizationId
     * @param type
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<PerBenefitDto> getOrgBenefitData(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId);

}
