package net.chinahrd.eis.permission;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import net.chinahrd.eis.permission.exception.SessionExpiredException;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.utils.PropertiesUtil;

/**
 * web上下文
 */
public class EisWebContext {

	private EisWebContext() {
	}

	/**
	 * Get current logged in user
	 * 
	 * @return 永不为null
	 * @throws SessionExpiredException
	 */
	public static RbacUser getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		if (principal instanceof RbacUser) {
			return (RbacUser) principal;
		}
		throw new SessionExpiredException(
				"There is no user available, maybe the session has expired");
	}

	/**
	 * 判断用户是否登录
	 */
	public static boolean isLogon() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	/**
	 * 获取客户Id
	 * 
	 * @return
	 */
	public static String getCustomerId() {
		return PropertiesUtil.getProperty("customer.id").trim();
	}
	
	/**
	 * 获取加密和解密钥匙
	 * @return
	 */
	public static String getCryptKey(){
		return "hrbi";
	}


	public static boolean checkUserPasswd(String passwd) {
		if(passwd == null){
			passwd = getCurrentUser().getPassword();
		}
		String password = PropertiesUtil.getProperty("user.password");
		if (passwd.equals(password)) {
			return true;
		}
		return false;
	}
}
