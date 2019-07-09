package net.chinahrd.empAttendance.module;

import java.util.List;

import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpVacationDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpAttendanceDto;

@Repository("vacationDao")
public interface VacationDao {

    @Select("SELECT tt.emp_id empId, tt.user_name_ch userName, tt.annual FROM emp_vacation tt ")
    List<EmpAttendanceDto> queryAnnual();

    @Update({"update emp_vacation set annual=#{annual}, refresh=#{refresh}", "where emp_id=#{empId}"})
        // @Update("update emp_vacation set annual=#{annual} where emp_id=#{empId}")
    int updateAnnual(EmpAttendanceDto params);

    /**
     * 检查是否有本年的年假
     *
     * @param year
     * @return
     */
    @Select("SELECT count(1) FROM emp_vacation t where  t.refresh BETWEEN '${year}-01-01' and '${year}-12-31'")
    int checkIsExist(@Param("year") String year);


    /**
     * 查每年新的年假
     *
     * @param year
     * @return
     */
    @Select("SELECT tt.emp_id empId, tt.user_name_ch userName, tt.annual_total annualTotal, tt.annual_delayed annualDelayed FROM emp_vacation_history tt WHERE tt.year = #{year}")
    List<EmpVacationDto> queryAnnualHistory(@Param("year") String year);

}
