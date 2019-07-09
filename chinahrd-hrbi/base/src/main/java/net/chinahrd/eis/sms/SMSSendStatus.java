package net.chinahrd.eis.sms;

import java.sql.Date;

public class SMSSendStatus {
	
	private String id;
	private String provider;
	private String msgNo;
	private String mobile;
	private String smsTxt;
	private Date sendTime;
	private int status;
	private String telecomStatus;

	public String getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmsTxt() {
		return smsTxt;
	}

	public void setSmsTxt(String smsTxt) {
		this.smsTxt = smsTxt;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTelecomStatus() {
		return telecomStatus;
	}

	public void setTelecomStatus(String telecomStatus) {
		this.telecomStatus = telecomStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getStatus() {
		return status;
	}

}
