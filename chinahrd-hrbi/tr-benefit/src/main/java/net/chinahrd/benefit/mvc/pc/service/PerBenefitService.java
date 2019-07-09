package net.chinahrd.benefit.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.benefit.PerBenefitAmountDto;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;

import org.apache.ibatis.annotations.Param;

/**
 *	人均效益Controller
 */
public interface PerBenefitService {
	
	/**
	 * 获取前13个月的人均效益趋势（按月统计）
	 * @param organizationId 架构id
	 * @return
	 */
	List<PerBenefitDto> getTrendByMonth(String customerId,String organizationId);
	/**
	 * 获取人均效益趋势（按年统计）
	 * @param organizationId 架构id
	 * @return
	 */
	List<PerBenefitDto> getTrendByYear(String customerId,String organizationId);
	
	/**
	 * 获取上个月和上上个月的人均效益
	 * @param organizationId 架构id
	 * @return
	 */
	List<PerBenefitDto> getPerBenefitData(String customerId,String organizationId);
	
	/**
	 * 获取"当前组织人均效益"（包含子节点为独立核算的部门）
	 * @param organizationId 
	 * @return
	 */
	List<PerBenefitDto> getOrgBenefitData(String customerId,String organizationId);
	
	/**
	 * 获取当前组织最近12个月人均效益、利润总额、销售总额数据(按时间倒序)
	 * @param organizationId
	 * @return
	 */
	PerBenefitAmountDto getOrgRecentData(String customerId, String organizationId);
	
	/**
	 * 获取行业均值
	 * @param organizationId 架构id
	 * @return
	 */
	Double getAvgValueData(String customerId,String organizationId);
	
	/**
	 * 人均效益变化幅度
	 * @param organizationId
	 * @param upgrade	真：升幅
	 * @return
	 */
	Map<Integer, List<PerBenefitDto>> queryVariationRange(String customerId,
			@Param("organizationId")String organizationId,
			@Param("upgrade")boolean upgrade
			);
}
