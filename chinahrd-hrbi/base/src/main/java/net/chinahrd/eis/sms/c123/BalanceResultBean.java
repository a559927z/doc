package net.chinahrd.eis.sms.c123;

public class BalanceResultBean {
	private long corpId;
	private int staffId;
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