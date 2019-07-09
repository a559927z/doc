package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 员工关联数据Dto Created by htpeng on 15/12/29.
 */
public class EmpOrganizationDto implements Serializable {

	private static final long serialVersionUID = -331284020771861985L;
	private String empOrganizationId;
	private String customerId;
	private String empId;
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

	public String getEmpOrganizationId() {
		return empOrganizationId;
	}

	public void setEmpOrganizationId(String empOrganizationId) {
		this.empOrganizationId = empOrganizationId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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
