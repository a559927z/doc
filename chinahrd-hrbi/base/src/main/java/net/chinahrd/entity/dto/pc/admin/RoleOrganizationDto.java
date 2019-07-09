package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 角色数据Dto Created by jxzhang on 15/6/25.
 */
public class RoleOrganizationDto implements Serializable {

	private static final long serialVersionUID = -331284020771861985L;
	private String roleOrganizationId;
	private String customerId;
	private String roleId;
	private int halfCheck;
	private String organizationId;
	private String createUserId;
	private Timestamp createTime;

	public int getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(int halfCheck) {
		this.halfCheck = halfCheck;
	}

	public String getRoleOrganizationId() {
		return roleOrganizationId;
	}

	public void setRoleOrganizationId(String roleOrganizationId) {
		this.roleOrganizationId = roleOrganizationId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
