package net.chinahrd.humanInventory.mvc.pc.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryChartDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryInputTypeDto;

/**
 * 项目人力盘点
 * @author malong and lixingwen
 */
@Repository("humanInventoryDao")
public interface HumanInventoryDao {

	/**
	 * 获取项目进度参数
	 * 
	 * @param customerId
	 * @return
	 */
	List<HumanInventoryDto> queryProjectProgress(@Param("customerId") String customerId);

	/**
	 * 获取本年度项目总数和人均项目数
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> getProjectConAndAvgNum(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("subOrganIdList") List<String> subOrganIdList);

	/**
	 * 获取本年度项目投入产出
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> getProjectInputOutputNum(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year);

	/**
	 * 获取本年度项目负荷
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> getProjectLoadNum(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year);

	/**
	 * 投入产出分析
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public List<HumanInventoryChartDto> queryInputOutputAmount(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("quarter") String quarter);

	/**
	 * 投入产出地图
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> queryInputOutputMap(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year);

	/**
	 * 盈亏项目数分析，盈亏金额分析
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public LinkedList<HumanInventoryChartDto> queryProfitLossProject(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("quarter") String quarter);

	/**
	 * 盈亏项目数分析，盈亏金额分析 获取盈利总金额，亏损总金额
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> getProfitAndLossCountAmount(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("month") String month);

	/**
	 * 盈亏项目数分析，盈亏金额分析 获取盈亏项目明细
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> getProfitAndLossProjectDetail(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("month") String month);

	/**
	 * 项目投入统计-人力统计
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public List<HumanInventoryChartDto> queryProjectManpower(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("quarter") String quarter);

	/**
	 * 项目投入统计-费用统计
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public List<HumanInventoryChartDto> queryProjectInputCost(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("quarter") String quarter);

	/**
	 * 项目类型分析
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public List<HumanInventoryChartDto> queryProjectType(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year);

	public int findLeadingProjectCount(Map<String, Object> map);

	/**
	 * 主导项目
	 */
	public List<HumanInventoryDto> findLeadingProject(Map<String, Object> map);

	public int findParticipateProjectCount(Map<String, Object> map);

	/**
	 * 参与项目
	 */
	public List<HumanInventoryDto> findParticipateProject(Map<String, Object> map);

	public int findEmployeeStatisticsCount(Map<String, Object> map);

	/**
	 * 人员统计
	 */
	public List<HumanInventoryDto> findEmployeeStatistics(Map<String, Object> map);

	public int findCurrentEmployeeCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("projectId") String projectId, @Param("time") String time);

	/**
	 * 当前人力投入
	 */
	public List<HumanInventoryDto> findCurrentEmployee(@Param("customerId") String customerId,
			@Param("time") String time, @Param("organId") String organId, @Param("projectId") String projectId,
			RowBounds rowBounds);

	/**
	 * 人力投入环比趋势
	 */
	public List<HumanInventoryDto> queryManpowerInputByMonth(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("projectId") String projectId, @Param("now") String now);

	/**
	 * 子项目明细
	 * 
	 * @param customerId
	 * @param organId
	 * @param projectId
	 * @param now
	 * @return
	 */
	public List<HumanInventoryDto> querySubprojectById(Map<String, Object> param);

	/**
	 * 费用明细
	 * 
	 * @param param
	 * @return
	 */
	public List<HumanInventoryInputTypeDto> queryFeeDetailById(Map<String, Object> param);

	/**
	 * 查询费用类型
	 * 
	 * @return
	 */
	public List<HumanInventoryDto> queryProjectInputTypeInfo(@Param("customerId") String customerId);

	/**
	 * 投入产出比
	 * 
	 * @param customerId
	 * @param organId
	 * @param projectId
	 * @param now
	 * @return
	 */
	public List<HumanInventoryDto> queryInputOutputByMonth(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("projectId") String projectId, @Param("now") String now);

	/**
	 * 年投入产出比
	 * 
	 * @param customerId
	 * @param organId
	 * @param projectId
	 * @param year
	 * @return
	 */
	public HumanInventoryDto queryInputOutputByYear(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("projectId") String projectId, @Param("year") int year);

	/*public int findParticipateProjectDetailCount(@Param("customerId") String customerId, @Param("empId") String empId,
			@Param("year") int year);*/

	/**
	 * 参与项目明细
	 * 
	 * @param customerId
	 * @param empId
	 * @param year
	 * @param rowBounds
	 * @return
	 */
	public List<HumanInventoryDto> findParticipateProjectDetail(@Param("customerId") String customerId,
			@Param("empId") String empId, @Param("year") int year);

	/**
	 * 部门人力投入
	 * 
	 * @param customerId
	 * @param projectId
	 * @param year
	 */
	public List<HumanInventoryDto> getDepartmentInput(@Param("customerId") String customerId,
			@Param("projectId") String projectId, @Param("year") int year);

	/**
	 * 职位序列人力投入
	 * 
	 * @param customerId
	 * @param projectId
	 * @param year
	 */
	public List<HumanInventoryDto> getJobSeqInput(@Param("customerId") String customerId,
			@Param("projectId") String projectId, @Param("year") int year);

	/**
	 * 职级人力投入
	 * 
	 * @param customerId
	 * @param projectId
	 * @param year
	 */
	public List<HumanInventoryDto> getRankInput(@Param("customerId") String customerId,
			@Param("projectId") String projectId, @Param("year") int year);

	/**
	 * 工作地人力投入
	 * 
	 * @param customerId
	 * @param organId
	 * @param projectId
	 * @param year
	 */
	public List<HumanInventoryDto> getWorkplaceInput(@Param("customerId") String customerId,
			@Param("projectId") String projectId, @Param("year") int year);

	/**
	 * 获取客户对应的费用名称
	 */
	public List<HumanInventoryDto> queryProjectCostTypeNames(@Param("customerId") String customerId);

	/**
	 * 根据导入的excel文件，查询符合条件的负责人数据
	 */
	public List<String> queryProjectUserByImport(@Param("customerId") String customerId, @Param("list") List<String> list);

	/**
	 * 根据导入的excel文件，查询符合条件的组织数据
	 */
	public List<HumanInventoryDto> queryProjectOrganIdByImport(@Param("customerId") String customerId,
			@Param("list") List<String> list);

	/**
	 * 查询项目信息
	 */
	public List<HumanInventoryDto> queryProjectMessage(@Param("customerId") String customerId);

	/**
	 * 根据id删除项目信息
	 */
	public void deleteProjectDataByProjectId(@Param("customerId") String customerId,
			@Param("projectId") String projectId);

	/**
	 * 根据项目名称，查询项目信息
	 */
	public List<HumanInventoryDto> queryProjectMessageByName(@Param("customerId") String customerId,
			@Param("projectName") String projectName);

	/**
	 * 根据项目人力明细，查询项目信息
	 */
	public List<HumanInventoryDto> queryProjectMessageByManpower(@Param("customerId") String customerId,
			@Param("date") String date);

	/**
	 * 查询项目进度
	 */
	public List<HumanInventoryDto> queryProjectProgressBySourceCodeItem(@Param("customerId") String customerId);

	/**
	 * 查询项目类型
	 */
	public List<HumanInventoryDto> queryProjectTypeByDimProjectType(@Param("customerId") String customerId);

	/**
	 * 根据当前用户及类型名称查询项目类型信息
	 */
	public List<HumanInventoryDto> queryProjectTypeByCustomerIdAndTypeName(@Param("customerId") String customerId,
			@Param("projectTypeName") String typeName);

	/**
	 * 查询组织信息
	 */
	public List<HumanInventoryDto> queryProjectOrganMessage(@Param("customerId") String customerId);

	/**
	 * 根据组织名称查询组织信息
	 */
	public List<HumanInventoryDto> queryProjectOrganMessageByName(@Param("customerId") String customerId,
			@Param("organName") String organName);

	/**
	 * 根据父组织名称查询组织信息
	 */
	public List<HumanInventoryDto> queryProjectOrganMessageByParentName(@Param("customerId") String customerId,
			@Param("organName") String organName, @Param("organParentName") String organParentName);

	/**
	 * 查询项目负责人信息
	 */
	public List<HumanInventoryDto> queryProjectPrincipalIdByName(@Param("customerId") String customerId);

	/**
	 * 项目表添加操作
	 */
	public void addProject(@Param("customerId") String customerId, @Param("dto") HumanInventoryDto dto);

	/**
	 * 项目类型维度表添加操作
	 */
	public void addDimProjectType(@Param("customerId") String customerId, @Param("dto") HumanInventoryDto dto);

	/**
	 * 项目投入费用明细表添加操作
	 */
	public void addProjectInputDetail(@Param("customerId") String customerId, @Param("dto") HumanInventoryDto dto);

	/**
	 * 根据盘点日期删除项目投入费用明细表操作
	 */
	public void deleteProjectInputDetailByDate(@Param("customerId") String customerId,
			@Param("projectId") String projectId, @Param("date") String date, @Param("type") int type);

	/**
	 * 项目费用明细表添加操作
	 */
	public void addProjectCost(@Param("customerId") String customerId, @Param("dto") HumanInventoryDto dto);

	/**
	 * 项目费用明细表删除操作
	 */
	public void deleteProjectCostByDate(@Param("customerId") String customerId, @Param("projectId") String projectId,
			@Param("date") String date, @Param("type") int type);
	
	/**
	 * 根据项目id删除主导项目与参与项目关系表操作
	 */
	public void deleteProjectPartakeRelationByProjectId(@Param("customerId") String customerId, @Param("projectId") String projectId);

	/**
	 * 主导项目参与项目关系表添加操作
	 */
	public void addLeadAndPartakeProjectRelation(@Param("customerId") String customerId,
			@Param("dto") HumanInventoryDto dto);

	/**
	 * 根据项目id更新项目信息
	 */
	public void updateProjectById(@Param("customerId") String customerId, @Param("dto") HumanInventoryDto dto);

	/**
	 * 统计组织机构是否存在
	 */
	public int queryOrganizationCount(@Param("customerId") String customerId, @Param("organId") String organId);

	/**
	 * 统计项目是否存在
	 */
	public int queryProjectCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("projectName") String projectName);

	/**
	 * 统计账户是否存在
	 */
	public int queryEmpCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("userName") String userName);

	/**
	 * 查询项目人员信息
	 */
	public List<HumanInventoryDto> queryProjectManpowerInfo(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("yearMonth") String yearMonth);

	/**
	 * 查询人员信息是否重复
	 */
	public int queryManpowerCount(Map<String, Object> map);
	
	/**
	 * 查询是否存在项目人员信息
	 */
	public int queryProjectManpowerCount(Map<String, Object> map);

	/**
	 * 新增人力投入数据
	 * 
	 * @param list
	 */
	public void addProjectManpowerInfo(@Param("list") List<HumanInventoryDto> list);

	public void deleteProjectManpower(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("yearMonth") String yearMonth);

	String queryEmpId(@Param("customerId") String customerId, @Param("userName") String userName);

	String queryProjectId(@Param("customerId") String customerId, @Param("projectName") String projectName);

}
