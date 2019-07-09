package net.chinahrd.service;

import java.util.List;

import net.chinahrd.dto.AopInformation;
import net.chinahrd.dto.EmpAttendanceDto;
import net.chinahrd.dto.EmpOtherDayDto;
import net.chinahrd.dto.LogDto;

public interface LogMgrService {

    /**
     * 操作日志-插入
     *
     * @param aopInformation
     */
    void information(AopInformation aopInformation);

    /**
     * 操作日志-查询
     *
     * @param startIndex
     * @param pageSize
     * @param search
     * @return
     */
    List<LogDto> queryAllLog(int startIndex, int pageSize, String search);

    Long countAllLog(String search);
    // ----------------------

    /**
     * 员工剩下假期-查询
     *
     * @param start
     * @param length
     * @param search
     * @return
     */
    List<EmpAttendanceDto> queryEmpVacation(int start, int length, String search);

    int countEmpVacation();

    // ----------------------

    /**
     * 休假明细-查询
     *
     * @param start
     * @param length
     * @param startYm
     * @param endYm
     * @param search
     * @return
     */
    List<EmpOtherDayDto> queryEmpOtherDay(int start, int length, int startYm, int endYm, String search);

    int countEmpOtherDay();

    List<String> queryEmpOtherDayYear();

}
