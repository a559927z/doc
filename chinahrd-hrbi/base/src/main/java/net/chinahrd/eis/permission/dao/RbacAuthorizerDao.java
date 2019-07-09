package net.chinahrd.eis.permission.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.eis.permission.model.RbacFunction;
import net.chinahrd.eis.permission.model.RbacRole;
import net.chinahrd.eis.permission.model.RbacUser;

/**
 * Created by wqcai on 15/5/28.
 */
@Repository("authorizerDao")
public interface RbacAuthorizerDao {

	/**
	 * 检查用户是否存在
	 * 
	 * @param paramMap
	 * 
	 * @return
	 */
	List<RbacUser> checkUserExist(Map<String, String> paramMap);

	/**
	 * 根据条件查询用户角色信息
	 * 
	 * @param userId
	 * @return
	 */
	List<RbacRole> findRoleList(@Param("customerId") String customerId, @Param("userId") String userId);

	/**
	 * 根据权限查询功能和操作信息
	 * 
	 * @param roleIds
	 * @return
	 */
	List<RbacFunction> findRbacPermissionByRole(@Param("customerId") String customerId,
			@Param("roleIds") List<String> roleIds);

	/**
	 * 初化始密码
	 * 
	 * @param customerId
	 * @param userKey
	 * @param password
	 */
	int initPassword(@Param("customerId") String customerId, @Param("userKey") String userKey,
			@Param("password") String password);

}
