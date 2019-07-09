package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrganTypeDto implements Serializable {

	private static final long serialVersionUID = -9142049141349983587L;
	private String organizationTypeId;
	private String customerId;
	private String organizationTypeKey;
	private String organizationTypeLevel;
	private String organizationTypeName;
	private String createUserId;
	private String modifyUserId;
	private Timestamp createTime;
	private Timestamp modifyTime;

	public OrganTypeDto(){}
	
	public OrganTypeDto(String organizationTypeLevel){
		this.organizationTypeLevel = organizationTypeLevel;
	}
	public String getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(String organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrganizationTypeKey() {
		return organizationTypeKey;
	}

	public void setOrganizationTypeKey(String organizationTypeKey) {
		this.organizationTypeKey = organizationTypeKey;
	}

	public String getOrganizationTypeLevel() {
		return organizationTypeLevel;
	}

	public void setOrganizationTypeLevel(String organizationTypeLevel) {
		this.organizationTypeLevel = organizationTypeLevel;
	}

	public String getOrganizationTypeName() {
		return organizationTypeName;
	}

	public void setOrganizationTypeName(String organizationTypeName) {
		this.organizationTypeName = organizationTypeName;
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
