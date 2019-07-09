package net.chinahrd.mvc.app;

import java.io.Serializable;

/**
 * app 接口数据包
 * @author guanjian
 *
 */
public class DataPacket implements Serializable{
	public final static int CODE_SUCCESS = 1;//成功
	public final static int CODE_LOGIN_FAIL = 100;//登录失败
	public final static int CODE_INVALID_TOKEN=200;//token无效
	public final static int CODE_FAIL=0;//请求失败
	public final static int CODE_999=999;//接口临时测试用的
	
	public final static String MSG_SUCCESS = "success";
	public final static String MSG_INVALID_TOKEN = "Invalid token(验证失效,请重新登入！)";//token无效
	public final static String MSG_PARAMETER_ERROR = "Parameter error";//参数错误
	/**  */
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	

}
