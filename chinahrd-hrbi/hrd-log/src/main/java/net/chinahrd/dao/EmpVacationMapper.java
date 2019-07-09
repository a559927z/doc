package net.chinahrd.dao;

import java.util.List;
import net.chinahrd.dto.EmpVacation;
import net.chinahrd.dto.EmpVacationExample;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository("empVacationMapper")
public interface EmpVacationMapper {
    @SelectProvider(type=EmpVacationSqlProvider.class, method="countByExample")
    long countByExample(EmpVacationExample example);

    @DeleteProvider(type=EmpVacationSqlProvider.class, method="deleteByExample")
    int deleteByExample(EmpVacationExample example);

    @Insert({
        "insert into emp_vacation (emp_id, customer_id, ",
        "emp_key, user_name_ch, ",
        "annual_total, annual, ",
        "can_leave, history_hour, ",
        "refresh)",
        "values (#{empId,jdbcType=VARCHAR}, #{customerId,jdbcType=VARCHAR}, ",
        "#{empKey,jdbcType=VARCHAR}, #{userNameCh,jdbcType=VARCHAR}, ",
        "#{annualTotal,jdbcType=VARCHAR}, #{annual,jdbcType=VARCHAR}, ",
        "#{canLeave,jdbcType=VARCHAR}, #{historyHour,jdbcType=VARCHAR}, ",
        "#{refresh,jdbcType=TIMESTAMP})"
    })
    int insert(EmpVacation record);

    @InsertProvider(type=EmpVacationSqlProvider.class, method="insertSelective")
    int insertSelective(EmpVacation record);

    @SelectProvider(type=EmpVacationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="emp_id", property="empId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="emp_key", property="empKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name_ch", property="userNameCh", jdbcType=JdbcType.VARCHAR),
        @Result(column="annual_total", property="annualTotal", jdbcType=JdbcType.VARCHAR),
        @Result(column="annual", property="annual", jdbcType=JdbcType.VARCHAR),
        @Result(column="can_leave", property="canLeave", jdbcType=JdbcType.VARCHAR),
        @Result(column="history_hour", property="historyHour", jdbcType=JdbcType.VARCHAR),
        @Result(column="refresh", property="refresh", jdbcType=JdbcType.TIMESTAMP)
    })
    List<EmpVacation> selectByExample(EmpVacationExample example);

    @UpdateProvider(type=EmpVacationSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EmpVacation record, @Param("example") EmpVacationExample example);

    @UpdateProvider(type=EmpVacationSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EmpVacation record, @Param("example") EmpVacationExample example);
}