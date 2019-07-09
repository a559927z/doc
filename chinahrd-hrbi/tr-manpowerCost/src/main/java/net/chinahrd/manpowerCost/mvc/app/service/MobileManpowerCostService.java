package net.chinahrd.manpowerCost.mvc.app.service;

import java.util.List;

import net.chinahrd.entity.dto.app.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerOrganDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerTrendDto;

/**
 *  移动端人力成本Service
 * @author htpeng
 *2016年5月31日上午11:35:28
 */
public interface MobileManpowerCostService {

	/**
	 * 成本月度趋势
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	ManpowerTrendDto queryTrendByMonth(String customerId, String organId,String time);
	
	
	
	
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


	/**
	 * 销售 成本  利润 明细
	 * @param customerId
	 * @param organId
	 * @param time
	 * @return
	 */
	List<ManpowerDto> queryAllDetailData(String customerId, String organId, String time);

	
}
