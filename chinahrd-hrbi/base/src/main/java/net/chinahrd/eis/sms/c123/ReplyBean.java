package net.chinahrd.eis.sms.c123;

import java.util.Date;

public class ReplyBean {
	private int id;
	private long msgId;
	private Date replyTime;
	private String mobile;
	private String content;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
}
