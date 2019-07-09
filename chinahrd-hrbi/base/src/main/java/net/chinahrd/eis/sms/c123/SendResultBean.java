package net.chinahrd.eis.sms.c123;

public class SendResultBean {
	private long corpId;
	private int staffId;
	private long msgId;
	private int total;
	private double unitPrice;
	private int remain;
	private int result;
	private String errMsg;

	public long getCorpId() {
		return this.corpId;
	}

	public void setCorpId(long corpId) {
		this.corpId = corpId;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getRemain() {
		return this.remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public int getResult() {
		return this.result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
