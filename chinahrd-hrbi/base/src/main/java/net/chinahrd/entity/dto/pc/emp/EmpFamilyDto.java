package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 家属信息dto
 */
public class EmpFamilyDto  implements Serializable {
	
	private static final long serialVersionUID = -7599922791130173722L;
	
	private String id;
    /**家属名称 */
    private String name;
    /**称呼(父、母、子、女) */
    private String call;
	/** 工作单位 */
	private String workUnit;
	/** 部门 */
	private String departmentName;
	/** 职务*/
	private String positionName;
	/** 手机号码 */
	private String telPhone;
	/** 出生年月 */
	private Timestamp bornDate;
	/** 备注*/
	private String note;
	/** 备注*/
	private int isChild;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	/**
	 * 移除格式化时间注解   因为格式化后的时间可能和数据时间有差异  
	 * 如  1987-04-21 00:00:00 格式化后变为1987-04-20
	 * 改由前台json进行数据格式化
	 * @return
	 */
	//@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Timestamp getBornDate() {
		return bornDate;
	}
	public void setBornDate(Timestamp bornDate) {
		this.bornDate = bornDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getIsChild() {
		return isChild;
	}
	public void setIsChild(int isChild) {
		this.isChild = isChild;
	}
	
}
