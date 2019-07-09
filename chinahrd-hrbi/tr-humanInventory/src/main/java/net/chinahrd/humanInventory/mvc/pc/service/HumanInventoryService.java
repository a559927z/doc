package net.chinahrd.humanInventory.mvc.pc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryInputTypeDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryParamDto;

/**
 * 项目人力盘点
 * @author malong and lixingwen
 */
public interface HumanInventoryService {
	
	/**
	 * 获取项目进度参数
	 * @param customerId
	 * @return
	 */
	List<HumanInventoryDto> queryProjectProgress(String customerId);

	/**
	 * 获取本年度项目总数和人均项目数
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> getProjectConAndAvgNum(String customerId, String organId, String year);

	/**
	 * 获取本年度项目投入产出
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> getProjectInputOutputNum(String customerId, String organId, String year);

	/**
	 * 获取本年度项目负荷
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> getProjectLoadNum(String customerId, String organId, String year);

	/**
	 * 投入产出分析
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public Map<String, Object> queryInputOutputAmount(String customerId, String organId, String year, String quarter);

	/**
	 * 投入产出地图
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> queryInputOutputMap(String customerId, String organId, String year);

	/**
	 * 盈亏项目数分析，盈亏金额分析
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public Map<String, Object> queryProfitLossProject(String customerId, String organId, String year, String quarter);

	/**
	 * 盈亏项目数分析，盈亏金额分析 获取盈利总金额，亏损总金额
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> getProfitAndLossCountAmount(String customerId, String organId, String year,
			String month);

	/**
	 * 盈亏项目数分析，盈亏金额分析 获取盈亏项目明细
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> getProfitAndLossProjectDetail(String customerId, String organId, String year,
			String month);

	/**
	 * 项目投入统计-人力统计
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public Map<String, Object> queryProjectManpower(String customerId, String organId, String year, String quarter);

	/**
	 * 项目投入统计-费用统计
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 * @param quarter
	 */
	public Map<String, Object> queryProjectInputCost(String customerId, String organId, String year, String quarter);

	/**
	 * 项目类型分析
	 * 
	 * @param customerId
	 * @param organId
	 * @param year
	 */
	public Map<String, Object> queryProjectType(String customerId, String organId, String year);

	/**
	 * 主导项目
	 */
	public PaginationDto<HumanInventoryDto> findLeadingProject(String time, HumanInventoryParamDto humanDto, 
			PaginationDto<HumanInventoryDto> dto);

	/**
	 * 参与项目
	 */
	public PaginationDto<HumanInventoryDto> findParticipateProject(String time, HumanInventoryParamDto humanDto,
			PaginationDto<HumanInventoryDto> dto);

	/**
	 * 员工统计
	 */
	public PaginationDto<HumanInventoryDto> findEmployeeStatistics(String time, HumanInventoryParamDto humanDto,
			PaginationDto<HumanInventoryDto> dto);

	/**
	 * 当前人力投入
	 */
	public PaginationDto<HumanInventoryDto> getCurrentEmployeeList(String customerId, String organId, String projectId,
			String time, PaginationDto<HumanInventoryDto> dto);

	/**
	 * 参与项目明细
	 * @param customerId
	 * @param empId
	 * @param time
	 * @return
	 */
	public List<HumanInventoryDto> getParticipateProjectDetail(String customerId, String empId, String time);

	/**
	 * 人力投入环比趋势
	 */
	public List<HumanInventoryDto> queryManpowerInputByMonth(String customerId, String organId, String projectId);
	/**
	 * 子项目明细
	 * @param customerId
	 * @param organId
	 * @param projectId
	 * @return
	 */
	public List<HumanInventoryDto> querySubprojectById(String customerId, String organId, String projectId, String type);

	/**
	 * 费用类型
	 * @param customerId
	 * @return
	 */
	List<HumanInventoryDto> queryProjectInputTypeInfo(String customerId);
	/**
	 * 费用明细
	 * @param customerId
	 * @param organId
	 * @param projectId
	 * @return
	 */
	public List<HumanInventoryInputTypeDto> queryFeeDetailById(String customerId, String organId, String projectId, String type);

	/**
	 * 费用投入产出比
	 */
	public Map<String, Object> queryInputOutputByMonth(String customerId, String organId, String projectId,
			String time);

	/**
	 * 下载《项目信息及费用数据》模板
	 */
	public void downLoadProjectInfoAndCost(String customerId, HttpServletResponse response, String title,
			String dateTitle, String date, String[] headers);

	/**
	 * 各部门人力投入
	 */
	public List<Object> getDepartmentInput(String customerId, String organId, String projectId, String time);

	/**
	 * 职位序列人力投入
	 */
	public List<Object> getJobSeqInput(String customerId, String organId, String projectId, String time);

	/**
	 * 职级人力投入
	 */
	public List<Object> getRankInput(String customerId, String organId, String projectId, String time);

	/**
	 * 工作地人力投入
	 */
	public List<Object> getWorkplaceInput(String customerId, String organId, String projectId, String time);

	/**
	 * 比较导入模板与选择导入类型是否一致
	 */
//	public List<Object> compareTemplateIsSame2(String organId, String customerId, String tempType, String total,
//			String detail, MultipartFile file);

	public Map compareTemplateIsSame(String customerId, String organId, String tempType, String total, String detail,
			String type, MultipartFile file);

	/**
	 * 项目人员数据模版下载
	 */
	public void downLoadProjectPersonExcel(String customerId, HttpServletResponse response, String title,
			String dateTitle, String date, String[] headers);

	/**
	 * 数据导入获取时间
	 */
	public List<String> queryDateForImport();
}
