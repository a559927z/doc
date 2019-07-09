package net.chinahrd.zte.laborEfficiency.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyGridDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyImportDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Repository("laborEfficiencyDao")
public interface LaborEfficiencyDao {

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
	 * 加班预警明细-人员加班线图
	 */
	public List<LaborEfficiencyDto> queryOvertimeWarningPersonHours(Map<String, Object> map);

	/**
	 * 加班时长趋势-人均时长
	 */
	public List<LaborEfficiencyDto> queryOvertimeAvgHours(Map<String, Object> map);

	/**
	 * 加班时长趋势-总时长
	 */
	public List<LaborEfficiencyDto> queryOvertimeTotalHours(Map<String, Object> map);

	/**
	 * 加班情况
	 */
	public List<LaborEfficiencyDto> queryOvertimeCondition(Map<String, Object> map);

	/**
	 * 加班情况-人员加班线图
	 */
	public List<LaborEfficiencyDto> queryOvertimeConditionPersonHours(Map<String, Object> map);

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
	 * 个人出勤明细-总数
	 */
	public int queryOnePersonDetailCount(Map<String, Object> map);

	/**
	 * 个人出勤明细
	 */
	public List<LaborEfficiencyGridDto> queryOnePersonDetail(Map<String, Object> map);
	
	/**
	 * 检查是否有导入数据的权限
	 */
	int checkPermissImport(@Param("customerId") String customerId, @Param("userId") String userId);
	
	/**
	 * 检查是否存在账户 
	 */
	int checkUser(@Param("customerId") String customerId, @Param("userKey") String userKey);
	
	/**
	 * 检查是否已经存在导入数据
	 */
	int checkIsExistEmpData(@Param("customerId") String customerId, @Param("empKey") String empKey, @Param("date") String date);
	
	/**
	 * 添加数据导入信息
	 */
	void addImportInfo(@Param("dto") LaborEfficiencyImportDto dto);
	
	/**
	 * 添加人员考勤信息
	 */
	void addAttendanceInfo(@Param("list") List<LaborEfficiencyDto> list, @Param("customerId") String customerId, @Param("attId") String attId);
	
	/**
	 * 查询员工信息
	 */
	List<LaborEfficiencyDto> queryEmpInfo(@Param("customerId") String customerId, @Param("userId") String userId, @Param("empId") String empId);
	List<LaborEfficiencyDto> checkEmpInfo(@Param("customerId") String customerId, @Param("keyName") String keyName);
	
	/**
	 * 删除导入的人员考勤信息
	 */
	void updateImportData(@Param("dto") LaborEfficiencyDto dto);
	
	/**
	 * 查询待审核数据
	 */
	List<LaborEfficiencyImportDto> queryAuditingInfo(@Param("customerId") String customerId, @Param("examineId") String examineId, @Param("status") int status);
	
	/**
	 * 查询待审核的人员考勤数据
	 */
	List<LaborEfficiencyDto> queryItemInfo(@Param("customerId") String customerId, @Param("attId") String attId);
	
	List<LaborEfficiencyDto> queryOtherItemInfo(@Param("customerId") String customerId, @Param("attId") String attId);
	
	List<LaborEfficiencyDto> queryOtherEmpInfo(@Param("customerId") String customerId, @Param("attId") String attId);
	
	/**
	 * 查询待审核的个人考勤数据
	 */
	List<LaborEfficiencyDto> queryPersonDetail(@Param("customerId") String customerId, @Param("empKey") String empKey, @Param("yearMonth") String yearMonth);
	
	/**
	 * 更新审核状态
	 */
	void updateImportInfo(Map<String, Object> map);
	
	/**
	 * 查询待入库的考勤数据
	 */
	List<LaborEfficiencyDto> queryBeinStorageData(@Param("customerId") String customerId, @Param("examineId") String examineId, @Param("attId") String attId);
	
	/**
	 * 正常考勤入库
	 */
	void insertEmpAttendance(@Param("list") List<LaborEfficiencyDto> list);
	
	/**
	 * 加班考勤入库
	 */
	void insertEmpOverTimeAttendance(@Param("list") List<LaborEfficiencyDto> list);
	
	/**
	 * 其它考勤入库
	 */
	void insertEmpOtherAttendance(@Param("list") List<LaborEfficiencyDto> list);
}
