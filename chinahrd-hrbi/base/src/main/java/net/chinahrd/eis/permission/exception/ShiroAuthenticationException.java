package net.chinahrd.eis.permission.exception;

import net.chinahrd.eis.permission.enums.AuthenticationCode;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Authentication exception with {@link AuthenticationCode}, thrown due to an
 * error during the Authentication process.
 */
public class ShiroAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 4179940800994025894L;

	private AuthenticationCode code;

	public ShiroAuthenticationException(AuthenticationCode code) {
		this.code = code;
	}

	public ShiroAuthenticationException(AuthenticationCode code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public AuthenticationCode getCode() {
		return code;
	}

	public void setCode(AuthenticationCode code) {
		this.code = code;
	}

}
