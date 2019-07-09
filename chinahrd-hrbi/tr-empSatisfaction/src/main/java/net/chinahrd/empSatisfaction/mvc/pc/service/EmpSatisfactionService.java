package net.chinahrd.empSatisfaction.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.competency.SatisfactionChartDto;
import net.chinahrd.entity.dto.pc.competency.SatisfactionDto;

/**
 * 满意度敬业度Service接口类
 * Created by qpzhu on 16/03/14.
 */
public interface EmpSatisfactionService {
	
	/**
	 * 获取敬业度年度分数以及返回是否是根节点
	 * @param customerId
	 * @return
	 */
	Map<String, Object>  getEngagementYearSoure(String customerId, String organizationId);
	
	/**
	 * 获取敬业度题目以及分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionDto> getEngagementSubject(String customerId, String organizationId);
	
	/**
	 * 获取敬业度子节点分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionChartDto> getEngagementSoure(String customerId, String organizationId);
	
	/**
	 * 获取满意度敬业度年度分数以及返回是否是根节点
	 * @param customerId
	 * @return
	 */
	Map<String, Object> getSatisfactionYearSoure(String customerId, String organizationId);
	
	/**
	 * 获取满意度子节点分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionChartDto> getSatisfactionSoure(String customerId, String organizationId);

	/**
	 * 获取满意度题目以及分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionDto> getSatisfactionSubject(String customerId, String organizationId);

}
