package net.chinahrd.perBenefit.mvc.pc.dao;

import java.util.List;

import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitTrendDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitResultDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 人均效益dao
 */
@Repository("perBenefitDao")
public interface PerBenefitDao {

    /**
     * 获取现有数据-万元薪资、人力资金执行率最新年份
     *
     * @param organId
     * @param customerId
     * @return
     */
    Integer findBenefitResultYear(@Param("organId") String organId, @Param("customerId") String customerId);

    /**
     * 获取万元薪资、人力资金执行率
     *
     * @param organId
     * @param year
     * @param customerId
     * @return
     */
    PerBenefitResultDto findPerBenefitResult(@Param("organId") String organId, @Param("year") int year, @Param("customerId") String customerId);

    /**
     * 获取人均效益(收入、毛利)
     *
     * @param organId
     * @param yearMonth
     * @param prevYearMonth
     * @param customerId
     * @return
     */
    AvgBenefitDto findAvgBenefit(@Param("organId") String organId, @Param("yearMonth") int yearMonth, @Param("prevYearMonth") int prevYearMonth, @Param("customerId") String customerId);


    /**
     * 获取人均效益集合
     *
     * @param organId
     * @param minYearMonth
     * @param maxYearMonth
     * @param type
     * @param customerId
     * @return
     */
    List<AvgBenefitTrendDto> queryAvgBenefitTrendList(@Param("organId") String organId, @Param("type") int type, @Param("minYearMonth") String minYearMonth, @Param("maxYearMonth") String maxYearMonth, @Param("customerId") String customerId);

    /**
     * 查询下级组织人均效益
     * @param organizationId
     * @param type
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<PerBenefitDto> queryOrgBenefit(@Param("organId") String organizationId, @Param("type") int type, @Param("minYearMonth") String minYearMonth, @Param("maxYearMonth") String maxYearMonth, @Param("customerId") String customerId);

    /**
     * 获取万元薪资集合
     *
     * @param organId
     * @param type
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<AvgBenefitTrendDto> queryPayTrendList(@Param("organId") String organId, @Param("type") int type, @Param("minYearMonth") String minYearMonth, @Param("maxYearMonth") String maxYearMonth, @Param("customerId") String customerId);

    /**
     * 获取下级组织万元薪资
     *
     * @param organId
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<PerBenefitDto> querySubOrganPay(@Param("organId") String organId, @Param("type") int type,  @Param("minYearMonth") String minYearMonth, @Param("maxYearMonth") String maxYearMonth, @Param("customerId") String customerId);

    /**
     * 获取人力资金执行率集合
     *
     * @param organId
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<AvgBenefitTrendDto> queryExecuteRateTrendList(@Param("organId") String organId, @Param("minYearMonth") String minYearMonth, @Param("maxYearMonth") String maxYearMonth, @Param("customerId") String customerId);

    /**
     * 获取下级组织人力资金执行率
     *
     * @param organId
     * @param minYearMonth
     * @param maxYearMonth
     * @param customerId
     * @return
     */
    List<PerBenefitDto> querySubOrganExecuteRate(@Param("organId") String organId, @Param("minYearMonth") String minYearMonth, @Param("maxYearMonth") String maxYearMonth, @Param("customerId") String customerId);

    /**
     * 查询等效全职员工数的最大日期
     *
     * @param organizationId
     * @return
     */
    Integer findFteMaxDate(@Param("organizationId") String organizationId, @Param("customerId") String customerId);

}
