package net.chinahrd.salaryBoard.mvc.app.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryWageDto;


/**
 * 薪酬看板Service
 * @author hjli
 */
public interface MobileSalaryBoardService {

	Map<String, Object> getSalaryPayTotal(String customerId, String organId);
	Map<String, Object> getSalaryProportion(String customerId, String organId);
	Map<String, Object> getSalaryRateOfReturn(String customerId, String organId);
	List<SalaryBoardDto> getSalarySubOrganization(String customerId, String organId);
	List<SalaryBoardDto> getSalaryMonthRateOfReturn(String customerId, String organId);
	List<SalaryBoardDto> getSalaryCostKpi(String customerId, String organId);
	List<SalaryBoardDto> getSalaryCostSalesProfit(String customerId, String organId);
	List<SalaryBoardDto> getSalaryBitValueYear(String customerId, String organId);
	Map<String,Object> getSalaryEmpCR(String customerId, String organId);
	PaginationDto<SalaryEmpCRDto> findSalaryEmpCR(String organId, PaginationDto<SalaryEmpCRDto> dto, String sidx,
			String sord, String customerId);
    PaginationDto<SalaryBoardDto> getSalaryDifferencePost(String customerId, String organId, int page, int rows);
	List<SalaryWageDto> getSalaryBonusProfit(String customerId, String organId);

}
