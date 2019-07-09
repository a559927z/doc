package net.chinahrd.eis.permission.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.enums.AuthenticationCode;
import net.chinahrd.eis.permission.exception.ShiroAuthenticationException;
import net.chinahrd.eis.permission.model.AuthenticationResult;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.eis.permission.service.RbacAuthorizerService;

/**
 * 在 Shiro 认证与授权处理过程中,需要通过 Realm 来获取用户,密码信息及用户权限信息. Realm 可以理解为读取用户信息、角色及权限的
 * 
 * @author jxzhang on 2016年12月9日
 * @Verdion 1.1 版本
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(ShiroAuthorizingRealm.class);
	
	@Autowired
	private RbacAuthorizerService authorizerService;

	@Override
	public Class<?> getAuthenticationTokenClass() {
		return UsernamePasswordToken.class;
	}

	/**
	 * 认证, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// check invalid input
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		if (StringUtils.isBlank(token.getUsername())) {
			throw new ShiroAuthenticationException(AuthenticationCode.USER_NOT_FOUND);
		}
		if (null == token.getPassword()) {
			throw new ShiroAuthenticationException(AuthenticationCode.PASS_ERROR);
		}
		// authenticate
		String inputPwd = String.valueOf(token.getPassword());

		AuthenticationResult info = 
				authorizerService.authenticate(
			 			EisWebContext.getCustomerId(),
						token.getUsername(),inputPwd);

		if (info.getCode() == AuthenticationCode.SUCCESS) {
			
			return new SimpleAuthenticationInfo(info.getUser(), inputPwd, getName());
		}
		throw new ShiroAuthenticationException(info.getCode());
	}

	/**
	 * 授权, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		RbacUser user = (RbacUser) principals.fromRealm(getName()).iterator().next();
		if (null == user) return null;
		if (null == user.getShiroRolesKey() && null == user.getShiroPermissions()) return null;

		// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(user.getShiroRolesKey());
		info.addStringPermissions(user.getShiroPermissions());

		return info;
	}
	

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	

}
