package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 员工关联数据Dto Created by htpeng on 15/12/29.
 */
public class UserOrganizationDto implements Serializable {

	private static final long serialVersionUID = -331284020771861985L;
	private String userOrganizationId;
	private String customerId;
	private String userId;
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

	public String getUserOrganizationId() {
		return userOrganizationId;
	}

	public void setUserOrganizationId(String userOrganizationId) {
		this.userOrganizationId = userOrganizationId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
