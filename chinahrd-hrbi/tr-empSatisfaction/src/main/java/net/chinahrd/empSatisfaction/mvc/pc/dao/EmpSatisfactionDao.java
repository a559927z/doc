package net.chinahrd.empSatisfaction.mvc.pc.dao;


import java.util.List;

import net.chinahrd.entity.dto.pc.competency.SatisfactionChartDto;
import net.chinahrd.entity.dto.pc.competency.SatisfactionDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 满意度敬业度dao接口类
 * Created by qpzhu on 16/03/14.
 */
@Repository("empSatisfactionDao")
public interface EmpSatisfactionDao {

	/**
	 * 获取根节点ID
	 * @param customerId
	 * @return
	 */
	String queryRootNode(@Param("customerId") String customerId);
	
	/**
	 * 获取敬业度最大日期
	 * @param customerId
	 * @return
	 */
	String queryEngagementMaxDay(@Param("customerId") String customerId);
	
	/**
	 * 获取满意度最大日期
	 * @param customerId
	 * @return
	 */
	
	String querySatisfactionMaxDay(@Param("customerId") String customerId);
	

	/**
	 * 获取敬业度年度分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionChartDto> queryEngagementYearSoure(@Param("customerId") String customerId, @Param("organizationId")String organizationId,@Param("flag")boolean flag);
	
	/**
	 * 获取敬业度子节点分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionChartDto> queryEngagementSoure(@Param("customerId") String customerId, @Param("organizationId")String organizationId,@Param("maxDay") String maxDay);
	
	/**
	 * 获取敬业度题目以及分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionDto> queryEngagementSubject(@Param("customerId")String customerId, @Param("organizationId")String organizationId, @Param("maxDay")String maxDay);
	
	/**
	 * 获取满意度敬业度年度分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionChartDto> querySatisfactionYearSoure(@Param("customerId") String customerId, @Param("organizationId")String organizationId,@Param("flag")boolean flag);
	
	/**
	 * 获取满意度子节点分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionChartDto> querySatisfactionSoure(@Param("customerId") String customerId, @Param("organizationId")String organizationId,@Param("maxDay") String maxDay);
	
	/**
	 * 获取满意度度题目以及分数以及公司分数
	 * @param customerId
	 * @return
	 */
	List<SatisfactionDto> querySatisfactionSubject(@Param("customerId")String customerId, @Param("organizationId")String organizationId, @Param("maxDay")String maxDay);
	
}
