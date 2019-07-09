package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;

/**
 * 员工加班信息
 * Created by htpeng on 15/12/07 0011.
 */
public class WorkOvertimeDto implements Serializable  {
    private static final long serialVersionUID = -5442821281238704284L;
    private String id;
    private String customerId;                
    private String organizationId;                
    private String empId;                
    private String empKey;      
    private double hourCount;    
    private String date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpKey() {
		return empKey;
	}
	public void setEmpKey(String empKey) {
		this.empKey = empKey;
	}
	public double getHourCount() {
		return hourCount;
	}
	public void setHourCount(double hourCount) {
		this.hourCount = hourCount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

    
}
