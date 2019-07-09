package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.pc.admin.OrganDto;

public class PojoDto implements Serializable {

	private static final long serialVersionUID = -1263390655478896349L;

	private String roleId;
	private String empId;
	private String userId;
	private List<OrganDto> organDto;
	private List<ProjectRelationDto> projectDto;
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<OrganDto> getOrganDto() {
		return organDto;
	}

	public void setOrganDto(List<OrganDto> organDto) {
		this.organDto = organDto;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ProjectRelationDto> getProjectDto() {
		return projectDto;
	}

	public void setProjectDto(List<ProjectRelationDto> projectDto) {
		this.projectDto = projectDto;
	}

}
