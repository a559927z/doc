package net.chinahrd.eis.permission.enums;

/**
 * 认证结果类型
 */
public enum AuthenticationCode {

	/**
	 * 认证成功
	 */
	SUCCESS(1),

	/**
	 * 帐户已锁定
	 */
	LOCKED(2),

	/**
	 * 密码错误
	 */
	PASS_ERROR(3),

	/**
	 * 用户不存在
	 */
	USER_NOT_FOUND(4),

	/**
	 * 用户已在其它地方登录
	 */
	HAS_LOGON(5),

	/**
	 * 密码过期
	 */
	PASS_EXPIRED(6);

	private final int code;

	private AuthenticationCode(int code) {
		this.code = code;
	}

	public static AuthenticationCode fromCode(int code) {
		switch (code) {
		case 1:
			return SUCCESS;
		case 2:
			return LOCKED;
		case 3:
			return PASS_ERROR;
		case 4:
			return USER_NOT_FOUND;
		case 5:
			return HAS_LOGON;
		case 6:
			return PASS_EXPIRED;
		default:
			throw new IllegalArgumentException("无效的LoginResult code : " + code);
		}
	}

	public int code() {
		return code;
	}
}
