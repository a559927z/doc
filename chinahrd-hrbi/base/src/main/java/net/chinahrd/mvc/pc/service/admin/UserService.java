package net.chinahrd.mvc.pc.service.admin;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.admin.UserDto;
import net.chinahrd.entity.dto.PaginationDto;

/**
 * 用户Service接口类 Created by wqcai on 15/6/8.
 */
public interface UserService {

	/**
	 * 查询用户信息
	 *
	 * @param customerId
	 *            客户ID
	 * @param userName
	 *            用户名
	 * @param pageDto
	 *            分页dto
	 * @return
	 */
	PaginationDto<UserDto> findAll(String customerId, String userName, PaginationDto<UserDto> pageDto);

	/**
	 * 添加用户
	 *
	 * @param userDto
	 * @return
	 */
	boolean addUser(UserDto userDto);

	/**
	 * 修改用户
	 *
	 * @param userDto
	 * @return
	 */
	boolean editUser(UserDto userDto);

	/**
	 * 修改用户密码信息
	 * 
	 * @param passwd
	 * @param userId
	 * @return
	 */
	boolean updateUserPasswd(String passwd, String userId, String customerId);

	/**
	 * 删除用户
	 *
	 * @param customerId
	 *            客户ID
	 * @param userIds
	 *            用户ID集合
	 * @return
	 */
	boolean deleteUser(String customerId, List<String> userIds);

	/**
	 * 用户绑定员工
	 * 
	 * @param customerId
	 * @param userId
	 * @param empId
	 * @return
	 */
	boolean userBindEmp(String customerId, String userId, String empId);

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @return
	 */
	UserDto findUserById(String customerId, String userId);

	List<TreeDto> dbToZtree(List<OrganDto> existOrgans);

	boolean updateUserPWDbyUserName(String enPwd, String userName,String customerId);

	Integer existUser(String empId, String userName);

	boolean updateUserPasswdByUserKey(String enPwd, String userKey, String customerId);

}
