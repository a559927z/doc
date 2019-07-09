package net.chinahrd.laborEfficiency.mvc.app.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyGridDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Repository("mobileLaborEfficiencyDao")
public interface MobileLaborEfficiencyDao {

	/**
	 * 劳动力效能对比
	 */
	LaborEfficiencyDto queryLaborEfficiencyRatio(Map<String, Object> map);

	String queryParentOrganId(@Param("customerId") String customerId, @Param("organId") String organId);

	List<LaborEfficiencyDto> queryOrgan(@Param("customerId") String customerId, @Param("organId") String organId);

	Map<String, Integer> findMinMaxTime(@Param("customerId") String customerId);

	/**
	 * 获取劳动力效能明细
	 */
	int queryLaborEfficiencyDetailCount(Map<String, Object> map);

	List<LaborEfficiencyDto> queryLaborEfficiencyDetail(Map<String, Object> map);

	/**
	 * 获取劳动力效能趋势
	 */
	List<LaborEfficiencyDto> queryLaborEfficiencyTrend(Map<String, Object> map);

	/**
	 * 获取劳动力效能值
	 */
	public Map<String, Object> getLaborEfficiencyValue(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("date") String date);

	/**
	 * 加班时长
	 */
	public Map<String, Object> queryOvertimeHours(Map<String, Object> map);

	/**
	 * 加班预警统计
	 */
	public Map<String, Object> queryOvertimeWarningCount(Map<String, Object> map);

	/**
	 * 加班预警明细
	 */
	public List<LaborEfficiencyDto> queryOvertimeWarningDetail(Map<String, Object> map);

	/**
	 * 加班时长趋势-人均时长
	 */
	public List<LaborEfficiencyDto> queryOvertimeTrend(Map<String, Object> map);


	/**
	 * 考勤类型分布-日期
	 */
	public Map<String, Integer> queryCheckWorkTypeForDate(@Param("customerId") String customerId,
			@Param("organId") String organId);

	/**
	 * 考勤类型分布
	 */
	public List<LaborEfficiencyDto> queryCheckWorkTypeLayout(Map<String, Object> map);

	/**
	 * 组织机构加班时长
	 */
	List<LaborEfficiencyDto> queryOvertimeByOrgan(Map<String, Object> map);

	/**
	 * 出勤明细-总数
	 */
	public int queryAttendanceDetailCount(Map<String, Object> map);

	/**
	 * 出勤明细
	 */
	public List<LaborEfficiencyGridDto> queryAttendanceDetail(Map<String, Object> map);

	/**
	 * 出勤明细-考勤类型
	 */
	public List<LaborEfficiencyDto> queryCheckWorkType(Map<String, Object> map);


	/**
	 * 加班情况-人员加班线图
	 */
	public List<LaborEfficiencyDto> queryOvertimeCondition(Map<String, Object> map);

}
