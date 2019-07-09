package net.chinahrd.manpowerCost.mvc.pc.service;

import java.util.List;
import java.util.Map;


import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerOrganDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerTrendDto;


public interface ManpowerCostService {
	
	List<ManpowerCompareDto> getCompareYear(String customerId, String organId,String time);

	List<ManpowerCompareDto> getCompareMonth(String customerId, String organId,String time);

	/**
	 * 成本月度趋势
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	ManpowerTrendDto queryTrendByMonth(String customerId, String organId,String time);
	
	
	/**
	 * 人均成本月度趋势
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerDto> queryAvgTrendByMonth(String customerId, String organId,String time);
	
	
	/**
	 * 人力成本结构
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerItemDto> queryItemDetail(String customerId, String organId,String time);

	/**
	 * 各架构人力成本
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerOrganDto> queryOrganCost(String customerId, String organId,String time);


	
	List<ManpowerCompareDto> queryProportionYear(String customerId, String organId,String time);

	List<ManpowerCompareDto> queryProportionMonth(String customerId, String organId,String time);

	
	/**
	 * 行业均值
	 * @param customerId
	 * @param organId
	 * @return
	 */
	Double queryAvgValue(String customerId, String organId);

	/**
	 * 销售 成本  利润 明细
	 * @param customerId
	 * @param organId
	 * @param time
	 * @return
	 */
	List<ManpowerDto> queryAllDetailData(String customerId, String organId, String time);

	Map<String, Double> getCostAvgWarn(String customerId, String organId, String time);
	
	
}
