package net.chinahrd.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.chinahrd.dto.EmpAttendanceDto;
import net.chinahrd.dto.EmpOtherDayDto;

@Repository("empAttendanceDao")
public interface EmpAttendanceDao {

    /**
     * 员工剩下假期-查询
     *
     * @param paramMap
     * @return
     */
    List<EmpAttendanceDto> queryEmpVacation(Map<String, Object> paramMap);

    int countEmpVacation();

    List<EmpOtherDayDto> queryEmpOtherDay(Map<String, Object> paramMap);

    int countEmpOtherDay();

    List<String> queryEmpOtherDayYear();
}
