package net.chinahrd.laborEfficiency.mvc.app.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyGridDto;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
public interface MobileLaborEfficiencyService {

	/**
	 * 查询劳动力效能对比
	 * 
	 * @param customerId
	 * @param organId
	 * @return
	 */
	Map<String, Object> queryLaborEfficiencyRatio(String customerId, String organId, String beginTime,String endTime);

	Map<String, Integer> findMinMaxTime(String customerId);

	String queryParentOrganId(String customerId, String organId);

	/**
	 * 劳动力效能同比环比
	 * 
	 * @param customerId
	 * @param organId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> queryLaborEfficiencyTrend(String customerId, String organId, String beginTime, String endTime);

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
	 * 加班时长趋势-人均时长
	 */
	public Map<String, Object> queryOvertimeTrend(String customerId, String organId, String time);


	/**
	 * 加班情况
	 */
	public List<LaborEfficiencyDto> queryOvertimeCondition(String customerId, String organId, String date,
			String crowds);

	
	/**
	 * 考勤类型分布
	 */
	public Map<String, Object> queryCheckWorkTypeLayout(String customerId, String organId, String time);

	/**
	 * 组织机构加班时长
	 */
	Map<String, Object> queryOvertimeByOrgan(String customerId, String organId, String time);

	/**
	 * 出勤明细
	 */
	PaginationDto<LaborEfficiencyGridDto> queryAttendanceDetail(String customerId, String organId, String time,
			List<Integer> checkList, int page, int rows);

	/**
	 * @param customerId
	 * @param checkList
	 * @return
	 */
	Map<String, Object> queryCheckWorkType(String customerId,
			List<Integer> checkList);




}
