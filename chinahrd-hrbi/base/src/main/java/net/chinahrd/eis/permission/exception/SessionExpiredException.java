package net.chinahrd.eis.permission.exception;

/**
 * Exception thrown when an action is taken by a user who's session has expired.
 */
public class SessionExpiredException extends RuntimeException {

	private static final long serialVersionUID = 1342326252967623524L;

	/**
	 * Creates a new SessionExpiredException object
	 */
	public SessionExpiredException() {
		super();
	}

	/**
	 * Creates a new SessionExpiredException object with the given message.
	 * 
	 * @param message
	 *            the reason for this SessionExpiredException.
	 */
	public SessionExpiredException(String message) {
		super(message);
	}

}
