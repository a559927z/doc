package net.chinahrd.eis.sms.c123;

public class SendStateBean {
	private int id;//序号
	private long msgId;//发送编号
	private String mobile;//发送号码
	private int status;//发送状态 0 未知状态 1 发送失败 2 发送成功
	private String detail;//运营商返回 DELIVRD,EXPIRED,DELETED,UNDELIV,ACCEPTD,UNKNOWN,REJECTD, DTBLACK,DTWORDS,DTFAILD,ERRBUSY

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}