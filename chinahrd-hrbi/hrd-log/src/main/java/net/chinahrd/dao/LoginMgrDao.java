package net.chinahrd.dao;

import net.chinahrd.dto.EmpExtendDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
@Repository("loginMgrDao")
public interface LoginMgrDao {

    @Select(value = "SELECT * FROM dim_user WHERE user_key = #{userKey} AND password = #{password} AND is_lock = 0")
    EmpExtendDto getUser(@Param("userKey") String userKey, @Param("password") String password);

    @Select(value = "SELECT t.role_key FROM dim_role t inner join user_role_relation t1 on t.role_id = t1.role_id WHERE t1.user_id = #{userId} ")
    List<String> queryRoleKeyByUserId(@Param("userId") String userId);


    @Select(value = "SELECT * FROM dim_user WHERE user_id = #{userId} AND is_lock = 0")
    EmpExtendDto getUserByUserId(@Param("userId") String userId);
}
