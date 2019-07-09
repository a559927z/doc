package net.chinahrd.api;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;

/**
 * 薪酬看板Api 接口
 * @author xwli
 * 2017年5月8日
 */
public interface SalaryBoardApi {
	List<SalaryBoardDto> getSalaryOrganRateOfReturn(String customerId, String organId, String yearMonth);
	
	List<SalaryBoardDto> getSalaryMonthRateOfReturn(String customerId, String organId);
	
	Map<String,Object> getSalaryWageStructure(String customerId, String organId);
}
