/**
*net.chinahrd.cache
*/
package net.chinahrd.salesBoard.module;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.chinahrd.api.SalesBoardApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardDto;
import net.chinahrd.salesBoard.mvc.pc.service.SalesBoardService;

/**
 * @author htpeng 2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements SalesBoardApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);

	@Injection
	private SalesBoardService salesBoardService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.INTERFACE;
	}
	@Override
	public List<SalesBoardDto> getSalesMoneyAndProfitAndReturnAmount(String customerId, String empId){
		if(StringUtils.isEmpty(empId)){
			return null;
		}
		return salesBoardService.querySalesMoneyAndProfitAndReturnAmount(customerId, empId);
	}
	@Override
	public Map<String, Object> getSalesMoneyAndRing(String customerId, String empId, Integer row) {
		return salesBoardService.querySalesMoneyAndRing(customerId, empId, row);
	}
	@Override
	public Map<String, Object> getSalesReturnAmountAndRing(String customerId, String empId, Integer row) {
		return salesBoardService.querySalesReturnAmountAndRing(customerId, empId, row);
	}
	@Override
	public List<SalesBoardDto> getClientSalesMoneyAndProfitAndReturnAmount(String customerId, String empId,
			String clientId) {
		return salesBoardService.queryClientSalesMoneyAndProfitAndReturnAmount(customerId, empId, clientId);
	}
	@Override
	public Map<String, Object> getClientSalesMoneyAndRing(String customerId, String empId, String clientId,
			Integer row) {
		return salesBoardService.queryClientSalesMoneyAndRing(customerId, empId, clientId, row);
	}
	@Override
	public Map<String, Object> getClientReturnAmountAndRing(String customerId, String empId, String clientId,
			Integer row) {
		return salesBoardService.queryClientReturnAmountAndRing(customerId, empId, clientId, row);
	}
}
