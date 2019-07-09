package net.chinahrd.dao;

import net.chinahrd.dto.DimUser;
import net.chinahrd.dto.DimUserExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dimUserMapper")
public interface DimUserMapper {
    @SelectProvider(type=DimUserSqlProvider.class, method="countByExample")
    long countByExample(DimUserExample example);

    @DeleteProvider(type=DimUserSqlProvider.class, method="deleteByExample")
    int deleteByExample(DimUserExample example);

    @Delete({
        "delete from dim_user",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String userId);

    @Insert({
        "insert into dim_user (user_id, customer_id, ",
        "emp_id, user_key, ",
        "user_name, user_name_ch, ",
        "password, email, ",
        "note, sys_deploy, ",
        "show_index, create_user_id, ",
        "modify_user_id, create_time, ",
        "modify_time, is_lock)",
        "values (#{userId,jdbcType=VARCHAR}, #{customerId,jdbcType=VARCHAR}, ",
        "#{empId,jdbcType=VARCHAR}, #{userKey,jdbcType=VARCHAR}, ",
        "#{userName,jdbcType=VARCHAR}, #{userNameCh,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{note,jdbcType=VARCHAR}, #{sysDeploy,jdbcType=INTEGER}, ",
        "#{showIndex,jdbcType=INTEGER}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{modifyUserId,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{isLock,jdbcType=BIT})"
    })
    int insert(DimUser record);

    @InsertProvider(type=DimUserSqlProvider.class, method="insertSelective")
    int insertSelective(DimUser record);

    @SelectProvider(type=DimUserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="emp_id", property="empId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_key", property="userKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name_ch", property="userNameCh", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="note", property="note", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_deploy", property="sysDeploy", jdbcType=JdbcType.INTEGER),
        @Result(column="show_index", property="showIndex", jdbcType=JdbcType.INTEGER),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="modify_user_id", property="modifyUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_lock", property="isLock", jdbcType=JdbcType.BIT)
    })
    List<DimUser> selectByExample(DimUserExample example);

    @Select({
        "select",
        "user_id, customer_id, emp_id, user_key, user_name, user_name_ch, password, email, ",
        "note, sys_deploy, show_index, create_user_id, modify_user_id, create_time, modify_time, ",
        "is_lock",
        "from dim_user",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="emp_id", property="empId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_key", property="userKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name_ch", property="userNameCh", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="note", property="note", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_deploy", property="sysDeploy", jdbcType=JdbcType.INTEGER),
        @Result(column="show_index", property="showIndex", jdbcType=JdbcType.INTEGER),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="modify_user_id", property="modifyUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_lock", property="isLock", jdbcType=JdbcType.BIT)
    })
    DimUser selectByPrimaryKey(String userId);

    @UpdateProvider(type=DimUserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") DimUser record, @Param("example") DimUserExample example);

    @UpdateProvider(type=DimUserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") DimUser record, @Param("example") DimUserExample example);

    @UpdateProvider(type=DimUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DimUser record);

    @Update({
        "update dim_user",
        "set customer_id = #{customerId,jdbcType=VARCHAR},",
          "emp_id = #{empId,jdbcType=VARCHAR},",
          "user_key = #{userKey,jdbcType=VARCHAR},",
          "user_name = #{userName,jdbcType=VARCHAR},",
          "user_name_ch = #{userNameCh,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "note = #{note,jdbcType=VARCHAR},",
          "sys_deploy = #{sysDeploy,jdbcType=INTEGER},",
          "show_index = #{showIndex,jdbcType=INTEGER},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "modify_user_id = #{modifyUserId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "is_lock = #{isLock,jdbcType=BIT}",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DimUser record);
}