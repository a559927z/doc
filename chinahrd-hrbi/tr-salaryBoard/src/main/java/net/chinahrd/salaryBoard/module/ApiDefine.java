/**
*net.chinahrd.cache
*/
package net.chinahrd.salaryBoard.module;

import net.chinahrd.api.SalaryBoardApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;
import net.chinahrd.salaryBoard.mvc.pc.service.SalaryBoardService;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements SalaryBoardApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	@Injection
	private SalaryBoardService salaryBoardService;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}

	public List<SalaryBoardDto> getSalaryOrganRateOfReturn(String customerId, String organId, String yearMonth) {
        return salaryBoardService.getSalaryOrganRateOfReturn(customerId, organId, yearMonth);
    }
	
	public List<SalaryBoardDto> getSalaryMonthRateOfReturn(String customerId, String organId) {
        return salaryBoardService.getSalaryMonthRateOfReturn(customerId, organId);
    }
	
	public Map<String,Object> getSalaryWageStructure(String customerId, String organId) {
        return salaryBoardService.getSalaryWageStructure(customerId, organId);
    }
}
