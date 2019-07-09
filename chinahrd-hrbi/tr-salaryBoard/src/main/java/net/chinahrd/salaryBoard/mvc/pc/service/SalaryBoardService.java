package net.chinahrd.salaryBoard.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpSharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalarySharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWageDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWelfareDto;
import net.chinahrd.utils.DateUtil;

/**
 * 薪酬看板Service
 * @author qpzhu by 2016-04-06
 */
public interface SalaryBoardService {

	SalaryBoardDto getSalaryBudgetYear(String customerId, String organId);

	Map<String, Object> getSalaryProportion(String customerId, String organId);

	Map<String, Object> getSalaryRateOfReturn(String customerId, String organId);

	Map<String, Object> getSalaryPayTotal(String customerId, String organId);

	List<SalaryBoardDto> getSalarySubOrganization(String customerId, String organId);

	List<SalaryBoardDto> getSalaryCostKpi(String customerId, String organId);

	List<SalaryBoardDto> getSalaryCostSalesProfit(String customerId, String organId);

	List<SalaryBoardDto> getSalaryOrganRateOfReturn(String customerId, String organId, String yearMonth);
	
	List<SalaryBoardDto> getSalaryMonthRateOfReturn(String customerId, String organId);

	List<SalaryBoardDto> getSalaryBitValueYear(String customerId, String organId);

	Map<String,Object> getSalaryDifferencePost(String customerId, String organId);

	Map<String,Object> getSalaryEmpCR(String customerId, String organId);

	PaginationDto<SalaryEmpCRDto> findSalaryEmpCR(String organId, PaginationDto<SalaryEmpCRDto> dto, String sidx,
			String sord, String customerId);

	Map<String, Object> getSalaryWageStatistics(String customerId, String organId);

	List<SalaryWageDto> getSalaryWagesMonth(String customerId, String organId);

	List<SalaryWageDto> getSalaryWagesYear(String customerId, String organId);

	Map<String,Object> getSalarySubOrgWages(String customerId, String organId);

	Map<String,Object> getSalaryWageStructure(String customerId, String organId);

	Map<String, Object> getSalaryFixedProportion(String customerId, String organId);

	Map<String,Object> getSalarySequenceFixed(String customerId, String organId);

	List<SalaryWageDto> getSalaryAbilityFixed(String customerId,String positionId, String organId);

	Map<String,Object> getSalarySubOrgFixed(String customerId, String organId);

	List<SalaryWageDto> getSalarySubOrgFixedList(String customerId, String organId);

	List<SalaryWageDto> getSalaryBonusProfit(String customerId, String organId);

	List<SalaryWageDto> getSalaryBonusProfitList(String customerId, String organId);

	Map<String,Object> getSalaryWelfare(String customerId, String organId);

	List<SalaryWelfareDto> getSalaryWelfareMonth(String customerId, String organId);

	List<SalaryWelfareDto> getSalaryWelfareYear(String customerId, String organId);

	Map<String,Object> getSalarySubOrgWelfare(String customerId, String organId);

	Map<String,Object> getSalarySubOrgAvgWelfare(String customerId, String organId);

	List<SalaryWelfareDto> getSalaryFixedBenefits(String customerId, String organId);

	PaginationDto<SalaryWelfareDto> findSalaryBenefitsDetailed(String customerId, String organId,String keyName, String welfareKey,String yearMonth,PaginationDto<SalaryWelfareDto> dto);

	List<SalaryWelfareDto> getSalaryBenefitsCurrency(String customerId, String organId);

	PaginationDto<SalaryWelfareDto> findSalaryCurrencyDetailed(String customerId, String organId,String keyName, String welfareKey,String yearMonth,PaginationDto<SalaryWelfareDto> dto);

	List<SalaryWelfareDto> getSalaryBenefitsNoCurrency(String customerId, String organId);

	PaginationDto<SalaryWelfareDto> findSalaryNoCurrencyDetailed(String customerId, String organId,String keyName, String welfareKey,String yearMonth,PaginationDto<SalaryWelfareDto> dto);

	SalarySharesDto getSalaryShares(String customerId, String organId);

	List<SalarySharesDto> getSalaryEmpShares(String customerId, String organId);

	List<SalarySharesDto> getSalarySumShares(String customerId, String organId);

	List<SalarySharesDto> getSalarySubOrgShares(String customerId, String organId);

	List<SalarySharesDto> getSalarySubOrgSumShares(String customerId, String organId);

	PaginationDto<SalaryEmpSharesDto> findSalaryEmpShares(String keyName, String organId,
			PaginationDto<SalaryEmpSharesDto> dto, String sidx, String sord, String customerId);

	List<SalaryWelfareDto> getSalaryWelfareCategory(String customerId, String welfareType);
	
	List<Integer> getSalaryTime(String customerId);
	/**
	 * OLAP 固定福利月统计
	 */
	void setUpWelfareNfbTotal();
	/**
	 * OLAP 薪酬看板
	 * @throws Exception 
	 */
	void proFetchPayCollectYear();
	
}
