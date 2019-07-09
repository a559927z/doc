package net.chinahrd.api;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardDto;

public interface SalesBoardApi {

	/**
	 * 根据id，获取管理者及管理者下属销售人员销售总额、销售利润、回款总额
	 * @param customerId 客户id
	 * @param empId 管理者或销售者id
	 * @return
	 * */
	List<SalesBoardDto> getSalesMoneyAndProfitAndReturnAmount(String customerId, String empId);
	
	/**
	 * 根据id，获取客户购买总额、销售利润、回款总额
	 * @param customerId 客户id
	 * @param empId 管理者或销售者id
	 * @param clientId 客户Id
	 * @return
	 * */
	List<SalesBoardDto> getClientSalesMoneyAndProfitAndReturnAmount(String customerId, String empId, String clientId);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者或销售者id
	 * @param row 获取最近几个月数据
	 * @return
	 * */
	Map<String, Object> getSalesMoneyAndRing(String customerId, String empId, Integer row);
	
	/**
	 * 根据id，获取销售人员/客户销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者或销售者id
	 * @param clientId 客户id
	 * @param row 获取最近几个月数据
	 * @return
	 * */
	Map<String, Object> getClientSalesMoneyAndRing(String customerId, String empId, String clientId, Integer row);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员回款额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者或销售者id
	 * @param row 获取最近几个月数据
	 * @return
	 * */
	Map<String, Object> getSalesReturnAmountAndRing(String customerId, String empId, Integer row);
	
	/**
	 * 根据id，获取销售人员/客户回款额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者或销售者id
	 * @param clientId 客户id
	 * @param row 获取最近几个月数据
	 * @return
	 * */
	Map<String, Object> getClientReturnAmountAndRing(String customerId, String empId, String clientId, Integer row);
	
}
