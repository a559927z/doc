package net.chinahrd.eis.permission.service;

import java.util.Map;

import net.chinahrd.eis.permission.model.AuthenticationResult;

/**
 * Shiro提供自定服务
 *
 * @author jxzhang on 2016年12月9日
 * @Verdion 1.0 版本
 */
public interface RbacAuthorizerService {

    /**
     * 用户认证. 用户是否可以登录系统
     *
     * @param username 用户名
     * @param password 密码
     * @return 不允许返回null.
     */
    AuthenticationResult authenticate(String customerId, String username, String password);

    /**
     * 获取用户，所有角色、功能、数据、对象集
     *
     * @param customerId
     * @param userId
     * @param isSysDeploy
     * @return
     */
    Map<String, Object> getUserInfo(String customerId, String userId, String empId, boolean isSysDeploy);

    /**
     * 检查用户是否可以重设置密码
     *
     * @param userKey
     * @param email
     * @return
     */
    boolean checkUserExist(String userKey, String email);

    /**
     * 初化始密码
     *
     * @param userKey
     */
    boolean initPassword(String userKey);

}
