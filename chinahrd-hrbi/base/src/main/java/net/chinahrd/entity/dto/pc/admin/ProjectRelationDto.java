package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProjectRelationDto implements Serializable {

	private static final long serialVersionUID = -7715350923278809559L;
	private String projectRelationId;
	private String projectId;
	private String customerId;
	private String empId;
	private String projectParentId;
	private String projectName;
	private String fullPath;
	private int hasChildren;
	private int halfCheck;
	private int type;
	private String createUserId;
	private Timestamp createDate;

	private boolean queryChildren=true;  //有子节点时  默认点击查询子节点

	public String getProjectRelationId() {
		return projectRelationId;
	}

	public void setProjectRelationId(String projectRelationId) {
		this.projectRelationId = projectRelationId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public String getProjectParentId() {
		return projectParentId;
	}

	public void setProjectParentId(String projectParentId) {
		this.projectParentId = projectParentId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public int getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	public int getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(int halfCheck) {
		this.halfCheck = halfCheck;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public boolean isQueryChildren() {
		return queryChildren;
	}

	public void setQueryChildren(boolean queryChildren) {
		this.queryChildren = queryChildren;
	}

	

}
