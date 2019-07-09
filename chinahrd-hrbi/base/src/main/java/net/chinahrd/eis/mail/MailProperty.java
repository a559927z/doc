package net.chinahrd.eis.mail;

/**
 * 邮件服务类
 * 
 * @author bright
 *
 */
public class MailProperty {
	//邮件主机
	private String host;
	//邮件主机端口
	private int port;
	//发送人账号
	private String userName;
	//发送人密码
	private String password;
	//发送邮箱
	private String from;
	//回复到
	private String replyTo;

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
}
