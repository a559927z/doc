package net.chinahrd.eis.sms.c123;

public class UpdateResultBean {
	private long corpId;
	private int staffId;
	private String authKey;
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

	public String getAuthKey() {
		return this.authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
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
