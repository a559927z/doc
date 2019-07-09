package net.chinahrd.salaryBoard.mvc.app.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryWageDto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * 薪酬看板Dao
 *
 * @author hjli
 */
@Repository("mobileSalaryBoardDao")
public interface MobileSalaryBoardDao {
	SalaryBoardDto queryCostTotal(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	SalaryBoardDto queryIncomeExpenditureYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	SalaryBoardDto querysalaryWelfareYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	SalaryBoardDto querySalaryBudgetYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	SalaryBoardDto querySalarySumThisYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	List<SalaryBoardDto> querySalaryPayTotal(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	List<SalaryBoardDto> querySalarySubOrganization(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	List<SalaryBoardDto> querySalaryMonthRateOfReturn(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	List<SalaryBoardDto> querySalaryCostKpi(@Param("customerId") String customerId, @Param("organId")String organId);
	List<SalaryBoardDto> querySalaryCostSalesProfit(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	List<SalaryBoardDto> querySalaryAvgPayList(@Param("customerId") String customerId, @Param("organId")String organId);
	List<SalaryBoardDto> querySalaryBitValueYear(@Param("customerId") String customerId, @Param("organId")String organId, @Param("year")String year);
	List<SalaryBoardDto> querySalaryEmpCR(@Param("customerId") String customerId, @Param("organId")String organId,@Param("cryptKey")String cryptKey);
	List<SalaryEmpCRDto> findSalaryEmpCR(Map<String, Object> map, RowBounds rowBounds);
	int findSalaryEmpCRCount(Map<String, Object> map);
	int querySalaryDifferencePostCount(Map<String, Object> map);
	List<SalaryBoardDto> querySalaryDifferencePost(Map<String, Object> map);
	List<SalaryWageDto> querySalaryBonusProfit(@Param("customerId") String customerId, @Param("organId")String organId);

}
