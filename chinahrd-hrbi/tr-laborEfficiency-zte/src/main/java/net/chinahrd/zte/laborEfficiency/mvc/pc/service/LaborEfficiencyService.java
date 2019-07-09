package net.chinahrd.zte.laborEfficiency.mvc.pc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyGridDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyImportDto;

import org.springframework.web.multipart.MultipartFile;


/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
public interface LaborEfficiencyService {

	/**
	 * 查询劳动力效能对比
	 * 
	 * @param customerId
	 * @param organId
	 * @return
	 */
	Map<String, Object> queryLaborEfficiencyRatio(String customerId, String organId, String beginTime, String endTime,
			String populationIds);

	Map<String, Integer> findMinMaxTime(String customerId);

	String queryParentOrganId(String customerId, String organId);

	Map<String, Object> getConditionValue(String customerId, String organId);

	/**
	 * 查询劳动力效能明细
	 * 
	 * @param customerId
	 * @param organId
	 * @return
	 */
	PaginationDto<LaborEfficiencyDto> queryLaborEfficiencyDetail(String customerId, String organId, String beginTime,
			String endTime, String populationIds, PaginationDto<LaborEfficiencyDto> dto);

	/**
	 * 劳动力效能同比环比
	 * 
	 * @param customerId
	 * @param organId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> queryLaborEfficiencyTrend(String customerId, String organId, String beginTime, String endTime,
			String popudationIds, int type);

	/**
	 * 获取劳动力效能值
	 */
	public Map<String, Object> getLaborEfficiencyValue(String customerId, String organId, String date);

	/**
	 * 加班时长
	 */
	public Map<String, Object> queryOvertimeHours(String customerId, String organId, String date);

	/**
	 * 加班预警统计
	 */
	public Map<String, Object> queryOvertimeWarningCount(String customerId, String organId, Integer otTime,
			Integer otWeek);

	/**
	 * 加班预警明细
	 */
	public List<LaborEfficiencyDto> queryOvertimeWarningDetail(String customerId, String organId, Integer otTime,
			Integer otWeek);

	/**
	 * 加班预警明细-人员加班线图
	 */
	public Map<String, Object> queryOvertimeWarningPersonHours(String customerId, String empId);

	/**
	 * 加班时长趋势-人均时长
	 */
	public Map<String, Object> queryOvertimeAvgHours(String customerId, String organId, String times, String crowds);

	/**
	 * 加班时长趋势-总时长
	 */
	public Map<String, Object> queryOvertimeTotalHours(String customerId, String organId, String times, String crowds);

	/**
	 * 加班情况
	 */
	public List<LaborEfficiencyDto> queryOvertimeCondition(String customerId, String organId, String date,
			String crowds);

	/**
	 * 加班情况-人员加班线图
	 */
	public Map<String, Object> queryOvertimeConditionPersonHours(String customerId, String empId, String date);

	/**
	 * 考勤类型分布-日期
	 */
	public Map<String, String> queryCheckWorkTypeForDate(String year, String month, String minYearMonth,
			String maxYearMonth);

	/**
	 * 考勤类型分布
	 */
	public Map<String, Object> queryCheckWorkTypeLayout(String customerId, String organId, String date);

	/**
	 * 组织机构加班时长
	 */
	Map<String, Object> queryOvertimeByOrgan(String customerId, String organId, String beginTime, String endTime,
			String populationIds);

	/**
	 * 出勤明细
	 */
	PaginationDto<LaborEfficiencyGridDto> queryAttendanceDetail(String customerId, String organId, String userName,
			String date, List<Integer> checkList, int page, int rows);

	/**
	 * 出勤明细-考勤类型
	 */
	Map<String, Object> queryCheckWorkType(String customerId, List<Integer> checkList);

	/**
	 * 个人出勤明细-考勤类型
	 */
	Map<String, Object> queryOnePersonDetailCheckWorkType(String customerId, List<Integer> checkList);

	/**
	 * 出勤明细
	 */
	PaginationDto<LaborEfficiencyGridDto> queryOnePersonDetail(String customerId, String organId, String empId,
			String date, List<Integer> checkList, int page, int rows);

	/**
	 * 下载excel模版
	 */
	void downloadTempletExcel(String customerId, HttpServletResponse response, String title, String dateTitle, String date, String[] headers, String[] content);
	
	/**
	 * 检查是否有导入数据的权限
	 */
	int checkPermissImport(String customerId, String userId);
	
	/**
	 * 导入数据
	 */
	Map<String, Object> importFile(String customerId, String userId, MultipartFile file, String empId, String attendanceTime, String type);
	
	/**
	 * 查询审核数据
	 */
	List<LaborEfficiencyImportDto> queryAuditingInfo(String customerId, String userId, int status);
	
	/**
	 * 查询待审核的人员考勤数据
	 */
	List<LaborEfficiencyDto> queryItemInfo(String customerId, String attId);
	
	/**
	 * 查询待审核的人员考勤数据
	 */
	List<LaborEfficiencyDto> queryPersonDetail(String customerId, String empKey, String yearMonth);
	
	/**
	 * 更新审核状态
	 */
	void updateImportInfo(String customerId, String attId, String date, String userId);
	
	/**
	 * 查询员工信息
	 */
	List<LaborEfficiencyDto> checkEmpInfo(String customerId, String keyName);
	
	/**
	 * 查询机构
	 */
	Map<String, Object> queryOrgan(String customerId, String organId);
}
