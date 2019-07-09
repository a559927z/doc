package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

public class SoureOrganDto implements Serializable {

	private static final long serialVersionUID = 2311211523813328905L;
	private String organizationId;
	private String customerId;
	private String businessUnitKey;
	private String organizationTypeKey;
	private String organizationKey;
	private String organizationParentKey;
	private String organizationName;
	private int isSingle;
	private Timestamp effectData;
	private int enabled;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Timestamp getEffectData() {
		return effectData;
	}

	public void setEffectData(Timestamp effectData) {
		this.effectData = effectData;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
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

	public String getOrganizationTypeKey() {
		return organizationTypeKey;
	}

	public void setOrganizationTypeKey(String organizationTypeKey) {
		this.organizationTypeKey = organizationTypeKey;
	}

	public String getOrganizationKey() {
		return organizationKey;
	}

	public void setOrganizationKey(String organizationKey) {
		this.organizationKey = organizationKey;
	}

	public String getOrganizationParentKey() {
		return organizationParentKey;
	}

	public void setOrganizationParentKey(String organizationParentKey) {
		this.organizationParentKey = organizationParentKey;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(int isSingle) {
		this.isSingle = isSingle;
	}

}
