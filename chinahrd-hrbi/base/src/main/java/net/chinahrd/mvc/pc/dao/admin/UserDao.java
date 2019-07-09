package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.admin.UserDto;

/**
 * Created by wqcai on 15/6/8.
 */
@Repository("userDao")
public interface UserDao {

    List<UserDto> findAll(Map<String, Object> mapParam);
    
    int findAllCount(@Param("customerId") String customerId, @Param("userName") String userName,@Param("roleIds") List<String> roleIds);

    void insertUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(@Param("customerId") String customerId, @Param("userId") List<String> userId);

    void updateUserEmp(@Param("customerId") String customerId, @Param("userId") String userId, @Param("empId") String empId);

    /**
     * 根据用户ID查询用户信息
     *
     * @param customerId 客户ID
     * @param userId     员工ID
     * @return
     */
    UserDto findUserById(@Param("customerId") String customerId, @Param("userId") String userId);


    /**
     * 修改用户密码信息
     *
     * @param passwd
     * @param userId
     * @param customerId
     * @return
     */
    boolean updateUserPasswd(@Param("passwd") String passwd, @Param("userId") String userId, @Param("customerId") String customerId);

	void updateUserPWDbyUserName(@Param("passwd")String enPwd, @Param("userName") String userName, @Param("customerId") String customerId);

	Integer existUser(@Param("empId") String empId, @Param("userName") String userName);

	void updateUserPasswdByUserKey(@Param("passwd") String passwd, @Param("userKey") String userKey, @Param("customerId") String customerId);


}
