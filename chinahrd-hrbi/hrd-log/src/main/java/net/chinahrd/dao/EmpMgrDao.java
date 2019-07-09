package net.chinahrd.dao;

import net.chinahrd.dto.EmpExtendDto;
import net.chinahrd.dto.EmpVacation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("empMgrDao")
public interface EmpMgrDao {

    //    @Select(value = "SELECT count(1) FROM dim_user where user_key != 'superAdmin' and is_lock = 0 ")
    Long countEmpSQL(@Param("isLock") Integer isLock, @Param("search") String search);


    @Select(value = "select emp_id empId, customer_id customerId, emp_key empKey, user_name_ch, annual_total annualTotal, annual, can_leave canLeave, history_hour historyHour FROM emp_vacation where emp_id = #{empId} ")
    EmpVacation getVacation(@Param("empId") String empId);

    @Select(value = "delete FROM emp_vacation where emp_id = #{empId} ")
    void deleteAnnual(@Param("empId") String empId);

    void saveAnnual(EmpVacation empVacationDto);

    /**
     * 员工列表
     *
     * @param startIndex
     * @param pageSize
     * @param search
     * @param orderColumn
     * @param orderDir
     * @return
     */
    List<EmpExtendDto> queryEmpList(@Param("isLock") Integer isLock,
                                    @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize, @Param("search") String search,
                                    @Param("orderColumn") String orderColumn, @Param("orderDir") String orderDir);


    /**
     * 逻辑删除员工状态
     *
     * @param isLock
     * @param userIds
     */
    void updateLockStatus(@Param("isLock") Integer isLock, @Param("userIds") List<String> userIds);

    /**
     * 更新员工年假
     *
     * @param annualTotal
     * @param annual
     * @param empId
     */
    void updateAnnualTotal(@Param("annualTotal") String annualTotal, @Param("annual") String annual, @Param("empId") String empId);
}
