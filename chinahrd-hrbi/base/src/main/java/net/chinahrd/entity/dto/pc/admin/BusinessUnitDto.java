package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

public class BusinessUnitDto implements Serializable {

	private static final long serialVersionUID = 34220025684963911L;
	private String professionId;
	private String customerId;
	private String businessUnitKey;
	private String businessUnitName;
	private String createUserId;
	private String modifyUserId;
	private Timestamp createTime;
	private Timestamp modifyTime;


	public String getProfessionId() {
		return professionId;
	}

	public void setProfessionId(String professionId) {
		this.professionId = professionId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBusinessUnitKey() {
		return businessUnitKey;
	}

	public void setBusinessUnitKey(String businessUnitKey) {
		this.businessUnitKey = businessUnitKey;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}
