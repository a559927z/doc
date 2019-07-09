package net.chinahrd.empAttendance.mvc.pc.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpAttendanceDto;

public interface EmpAttendanceService {

    /**
     * 查询员工信息
     *
     * @param customerId
     * @param empId
     * @return
     */
    EmpAttendanceDto queryEmpInfo(String customerId, String empId);

    /**
     * 查询员工假期
     *
     * @param customerId
     * @param empId
     * @return
     */
    EmpAttendanceDto queryEmpVacationInfo(String customerId, String empId);

    /**
     * 统计员工考勤信息
     *
     * @param customerId
     * @param empId
     * @return
     */
    Map<String, Object> queryAttendanceRecord(String customerId, String empId);

    /**
     * 查询考勤情况
     *
     * @param customerId
     * @param empId
     * @return id=0 正常出勤；id=1 加班；id=2 异常
     */
    List<EmpAttendanceDto> queryAttendance(String customerId, String empId, Integer yearMonth);

    /**
     * 查询员工考勤信息
     *
     * @param customerId
     * @param empId
     * @param day
     * @return
     */
    EmpAttendanceDto queryAttendanceByEmpId(String customerId, String empId, String day);

    /**
     * 查询打卡记录
     *
     * @param customerId
     * @param empId
     * @return
     */
    Map<String, Object> queryAttendanceFromCard(String customerId, String empId, int yearMonth);

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

    @Transactional(rollbackFor = Exception.class)
    boolean updateEmpAttendance(String customerId, String empId, String in, String out, String reason, String typeId, String note,
                                String typeName, String time, String empKey, String userName, String day, String yearMonth, String hour, String organId, int type);

    /**
     * 检查用户工作地是否在某个指定地点
     *
     * @param workPlaceName
     * @param organId
     * @param empId
     * @param customerId
     * @return
     */
    int checkEmpInOrgan(String workPlaceName, String organId, String empId, String customerId);

    List<EmpAttendanceDto> queryEmpOtherDay(String customerId, String userEmpId, int startYm, int endYm);

    List<String> queryEmpOtherDayYear(String customerId, String userEmpId);
}
