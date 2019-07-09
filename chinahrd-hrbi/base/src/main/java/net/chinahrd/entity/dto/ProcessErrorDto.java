/**
 * 
 */
package net.chinahrd.entity.dto;

/**
 * 存储过程调用返回的异常信息Dto
 * @author htpeng
 *2017年4月20日下午5:07:00
 */
public class ProcessErrorDto {
	private String level;
	private String code;
	private String message;
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
