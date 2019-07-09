package net.chinahrd.salaryBoard.mvc.pc.dao;


import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpSharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalarySharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWageDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWelfareDto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;


/**
 * 薪酬看板Dao
 * 
 * @author qpzhu by 2016-04-06
 */
@Repository("salaryBoardDao")
public interface SalaryBoardDao {
	
	int queryEmpCount(@Param("customerId") String customerId, @Param("organId")String organId, @Param("quit")String quit);

	SalaryBoardDto querySalaryBudgetYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	SalaryBoardDto querySalarySumThisYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	SalaryBoardDto querySalarySumLastYear(@Param("customerId") String customerId, @Param("organId")String organId, 
			@Param("beginDate")String beginDate, @Param("endDate")String endDate);

	SalaryBoardDto queryCostTotal(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	SalaryBoardDto queryIncomeExpenditureYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	SalaryBoardDto querysalaryWelfareYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryBoardDto> querySalaryPayTotal(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryBoardDto> querySalarySubOrganization(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryBoardDto> querySalaryCostKpi(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalaryBoardDto> querySalaryCostSalesProfit(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryBoardDto> querySalaryOrganRateOfReturn(@Param("customerId") String customerId, @Param("organId")String organId, @Param("yearMonth")String yearMonth);
	
	List<SalaryBoardDto> querySalaryMonthRateOfReturn(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	
	List<SalaryBoardDto> querySalaryAvgPayList(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalaryBoardDto> querySalaryBitValueYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryBoardDto> querySalaryDifferencePost(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year,
			@Param("cryptKey") String cryptKey);

	List<SalaryBoardDto> querySalaryEmpCR(@Param("customerId") String customerId, @Param("organId")String organId,@Param("cryptKey")String cryptKey);

	int findSalaryEmpCRCount(Map<String, Object> map);

	List<SalaryEmpCRDto> findSalaryEmpCR(Map<String, Object> map, RowBounds rowBounds);

	double querySalaryWages(String customerId, String organId, String year);

	List<SalaryWageDto> querySalaryWagesMonth(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryWageDto> querySalaryWagesYear(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalaryWageDto> querySalarySubOrgWages(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryWageDto> querySalaryWageStructure(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year,
			@Param("subOrganIdList") List<String> subOrganIdList);

	SalaryWageDto querySalaryFixedProportion(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year,
			@Param("subOrganIdList") List<String> subOrganIdList);

	List<SalaryWageDto> querySalarySequenceFixed(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year,
			@Param("subOrganIdList") List<String> subOrganIdList);

	List<SalaryWageDto> querySalaryAbilityFixed(@Param("customerId") String customerId,@Param("positionId") String positionId, @Param("organId")String organId, 
			@Param("year") String year, @Param("subOrganIdList") List<String> subOrganIdList);

	List<SalaryWageDto> querySalarySubOrgFixed(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryWageDto> querySubOrganization(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalaryWageDto> querySalaryBonusProfit(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalaryWageDto> querySalaryBonusProfitList(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	SalaryBoardDto querySalaryWelfare(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	
	List<SalaryWelfareDto> querySalaryWelfareMonth(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryWelfareDto> querySalaryWelfareYear(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalaryWelfareDto> querySalarySubOrgWelfare(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryWelfareDto> querySalarySubOrgAvgWelfare(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	List<SalaryWelfareDto> querySalaryFixedBenefits(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year,
			@Param("empCount")int empCount);
	
	int findSalaryBenefitsDetailedCount(Map<String, Object> map);

	List<SalaryWelfareDto> findSalaryBenefitsDetailed(Map<String, Object> map);

	List<SalaryWelfareDto> querySalaryBenefitsCurrency(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year,
			@Param("empCount")int empCount);

	int findSalaryCurrencyDetailedCount(Map<String, Object> map);
	
	List<SalaryWelfareDto> findSalaryCurrencyDetailed(Map<String, Object> map);

	List<SalaryWelfareDto> querySalaryBenefitsNoCurrency(
			@Param("customerId") String customerId,
			@Param("organId") String organId, 
			@Param("yearHead") Integer yearHead,
			@Param("yearLast") Integer yearLast,
			@Param("empCount") int empCount,
			@Param("subOrganIdList") List<String> subOrganIdList);

	int findSalaryNoCurrencyDetailedCount(Map<String, Object> map);
	
	List<SalaryWelfareDto> findSalaryNoCurrencyDetailed(Map<String, Object> map);
	
	SalarySharesDto querySalaryShares(@Param("customerId") String customerId, @Param("organId")String organId,@Param("empCount")int empCount,@Param("year")String year);

	List<SalarySharesDto> querySalaryEmpShares(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalarySharesDto> querySalarySumShares(@Param("customerId") String customerId, @Param("organId")String organId);

	List<SalarySharesDto> querySalarySubOrgShares(@Param("customerId") String customerId, @Param("organId")String organId,@Param("year")String year);

	List<SalarySharesDto> querySalarySubOrgSumShares(@Param("customerId") String customerId, @Param("organId")String organId,@Param("year")String year);

	int findSalaryEmpSharesCount(Map<String, Object> map);

	List<SalaryEmpSharesDto> findSalaryEmpShares(Map<String, Object> map, RowBounds rowBounds);

	List<SalaryWelfareDto> querySalaryWelfareCategory(@Param("customerId") String customerId, @Param("welfareType")String welfareType);

	List<Integer> querySalaryTime(@Param("customerId") String customerId, @Param("year") String year);
	
	/**
	 * 优化querySalaryFixedBenefits方法 by jxzhang on 2016-08-09
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<SalaryWelfareDto> querySalaryFixedBenefits2(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);

	/**
	 * 优化querySalaryBenefitsCurrency方法 by jxzhang on 2016-08-09
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<SalaryWelfareDto> querySalaryBenefitsCurrency2(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	
	

}
