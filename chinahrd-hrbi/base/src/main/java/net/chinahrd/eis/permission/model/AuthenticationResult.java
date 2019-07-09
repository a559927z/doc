package net.chinahrd.eis.permission.model;

import net.chinahrd.eis.permission.enums.AuthenticationCode;

/**
 * 认证结果
 */
public class AuthenticationResult {

	/** 认证成功后的用户 */
	private RbacUser user;

	/** 认证结果类型 */
	private AuthenticationCode code;

	public AuthenticationResult(RbacUser user) {
		super();
		this.user = user;
		this.code = AuthenticationCode.SUCCESS;
	}

	public AuthenticationResult(AuthenticationCode code) {
		super();
		this.code = code;
	}

	public RbacUser getUser() {
		return user;
	}

	public void setUser(RbacUser user) {
		this.user = user;
	}

	public AuthenticationCode getCode() {
		return code;
	}

	public void setCode(AuthenticationCode type) {
		this.code = type;
	}

}
