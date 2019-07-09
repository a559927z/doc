package net.chinahrd.empAttendance.mvc.pc.dao;

import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpAttendanceDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 考勤
 * 
 * @author xwli 2016-11-4
 */
@Repository("empAttendanceDao")
public interface EmpAttendanceDao {

	void insertDebug(@Param("content") String content);

	/**
	 * 查询员工信息
	 * 
	 * @param customerId
	 * @param empId
	 * @return
	 */
	EmpAttendanceDto queryEmpInfo(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 查询员工假期
	 * 
	 * @param customerId
	 * @param empId
	 * @return
	 */
	EmpAttendanceDto queryEmpVacationInfo(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 查询月应出勤天数
	 * 
	 * @param yearMonth
	 * @return
	 */
	List<String> queryAttendanceTheory(@Param("yearMonth") Integer yearMonth, @Param("begin") String begin,
			@Param("end") String end);

	/**
	 * 统计员工考勤信息
	 * 
	 * @param customerId
	 * @param empId
	 * @param yearMonth
	 * @return
	 */
	List<EmpAttendanceDto> queryAttendanceRecord(@Param("customerId") String customerId, @Param("empId") String empId,
			@Param("yearMonth") Integer yearMonth);

	/**
	 * 查询员工考勤信息
	 * 
	 * @param customerId
	 * @param empId
	 * @param day
	 * @return
	 */
	List<EmpAttendanceDto> queryAttendanceByEmpId(@Param("customerId") String customerId, @Param("empId") String empId,
			@Param("day") String day);

	/**
	 * 查询打卡记录
	 * 
	 * @param customerId
	 * @param empId
	 * @param day
	 * @param yearMonth
	 * @return
	 */
	List<EmpAttendanceDto> queryAttendanceFromCard(@Param("customerId") String customerId, @Param("empId") String empId,
			@Param("day") String day, @Param("yearMonth") Integer yearMonth);

	/**
	 * 查询异常类型
	 * 
	 * @return
	 */
	List<EmpAttendanceDto> querySoureItem();

	/**
	 * 考勤类型
	 * 
	 * @return
	 */
	List<EmpAttendanceDto> queryCheckType();

	/**
	 * 更新考勤信息
	 * 
	 * @param map
	 */
	void updateEmpAttendance(Map<String, Object> map);

	/**
	 * 添加考勤信息
	 * 
	 * @param map
	 */
	void addEmpAttendance(Map<String, Object> map);

	/**
	 * 生成劳动力效能数据
	 * 
	 * @param map
	 */
	// void callProLaoDongLiXiaoNeng(Map<String, Object> map);

	// ===========================================================================================================

	/**
	 * 删除日考勤信息
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param p_day
	 */
	void deleteEmpAttendanceDay(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("p_day") String p_day);

	/**
	 * 新增日考勤信息
	 * 
	 * @param map
	 */
	void insertEmpAttendanceDay(Map<String, Object> map);

	/**
	 * 删除日加班信息
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param p_day
	 */
	void deleteEmpOvertimeDay(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("p_day") String p_day);

	/**
	 * 新增日加班信息
	 * 
	 * @param map
	 */
	void insertEmpOvertimeDay(Map<String, Object> map);

	/**
	 * 删除其它考勤类型信息
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param p_day
	 */
	void deleteEmpOtherDay(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("p_day") String p_day);

	/**
	 * 新增其它考勤类型信息
	 * 
	 * @param map
	 */
	void insertEmpOtherDay(Map<String, Object> map);

	/**
	 * 查找假期表 by jxzhang on 2016-11-21
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @return
	 */
	Map<String, Object> findEmpVacation(@Param("p_customer_id") String p_customer_id,
			@Param("p_emp_id") String p_emp_id);

	/**
	 * 更新假期 by jxzhang on 2016-11-21
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param can_leave
	 * @param history_hour
	 */
	void updateEmpVacation(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("can_leave") String can_leave, @Param("history_hour") String history_hour);

	/**
	 * 更新年假
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param annul
	 */
	void updateEmpAnnulVacation(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("annul") String annul);

	/**
	 * 更新可调休
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param can_leave
	 */
	void updateEmpLeaveVacation(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("can_leave") String can_leave);

	/**
	 * 
	 * @param p_customer_id
	 * @param p_emp_id
	 * @param p_days
	 */
	void deleteHistoryEmpVacation(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("ym") int ym);

	/**
	 * 
	 * @param p_customer_id
	 * @param hourVal
	 * @param checkworkTypeId
	 * @param ym
	 * @param p_emp_id
	 * @return
	 */
	String insertHistoryEmpVacation(@Param("p_customer_id") String p_customer_id, @Param("p_emp_id") String p_emp_id,
			@Param("ym") int ym);

	/**
	 * 检查用户是否在指定工作地点
	 * 
	 * @param workPlaceName
	 * @param organId
	 * @param empId
	 * @param customerId
	 * @return
	 */
	int checkEmpInOrgan(@Param("workPlaceName") String workPlaceName, @Param("organIds") List<String> organIds,
			@Param("empId") String empId, @Param("customerId") String customerId);

	List<EmpAttendanceDto> queryEmpOtherDay(@Param("customerId") String customerId, @Param("empId") String empId,
			@Param("startYm") int startYm, @Param("endYm") int endYm);

	
	List<String> queryEmpOtherDayYear(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 查询是否存在日考勤信息
	 * 
	 * @param empId
	 * @param day
	 * @return
	 */
	// int findEmpAttendanceDay(@Param("empId") String empId, @Param("day") String
	// day);

	/**
	 * 更新日考勤信息
	 * 
	 * @param map
	 */
	// void updateEmpAttendanceDay(Map<String, Object> map);

	/**
	 * 查询是否存在日加班信息
	 * 
	 * @param empId
	 * @param day
	 * @return
	 */
	// int findEmpOvertimeDay(@Param("empId") String empId, @Param("day") String
	// day);

	/**
	 * 更新日加班信息
	 * 
	 * @param map
	 */
	// void updateEmpOvertimeDay(Map<String, Object> map);

	/**
	 * 查询是否存在日加班信息
	 * 
	 * @param empId
	 * @param day
	 * @return
	 */
	// int findEmpOtherDay(@Param("empId") String empId, @Param("day") String day);

	/**
	 * 更新日加班信息
	 * 
	 * @param map
	 */
	// void updateEmpOtherDay(Map<String, Object> map);

}
