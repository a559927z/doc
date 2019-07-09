package net.chinahrd.mvc.pc.service.admin;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.ProjectRelationDto;
import net.chinahrd.entity.dto.pc.admin.ProjectTypeDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;


public interface EmpProjectRelationService {

	List<TreeDto> dbToZtree(List<ProjectRelationDto> existProject);

	List<ProjectRelationDto> queryProjectPermit(String empId);

	List<ProjectRelationDto> queryEmpProject(String empId, String customerId,
			boolean b);

	boolean deleteEmpProject(String empId, String customerId);

	boolean addEmpProject(String empId, String createUserId,
			String customerId, List<ProjectRelationDto> ProjectDtos);

	public List<ProjectTypeDto> queryProType();
	/**
	 * 通过员工查出项目下的所有员工
	 */
	List<String> queryEmpInfosByEmpid(String empId);
}


