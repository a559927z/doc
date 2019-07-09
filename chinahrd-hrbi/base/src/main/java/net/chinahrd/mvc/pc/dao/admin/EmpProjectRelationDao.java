package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.ProjectRelationDto;
import net.chinahrd.entity.dto.pc.admin.ProjectTypeDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("empProjectRelationDao")
public interface EmpProjectRelationDao {

	/**
	 * 查出superAdmin的ID
	 * @param userKey
	 * @return
	 */
	String findSuperAdminIdByUserKey(String userKey);
	/**
	 * 查询当前登录人所有项目权限
	 * 
	 * @param userId
	 * @param customerId
	 *            TODO
	 * @return （带出全勾和半勾选的机构）
	 */
	List<ProjectRelationDto> queryProjectPermit(@Param("empId") String empId,
			@Param("customerId") String customerId);

	
	/**
	 * 查询superAdmin所有项目权 
	 * 
	 * @param userId
	 * @param customerId
	 *            TODO
	 * @return （带出全勾和半勾选的机构）
	 */
	List<ProjectRelationDto> queryProjectPermitBySuperAdmin(@Param("customerId") String customerId);


	
	
	/**
	 * 根据用户ID查询用户项目权限
	 *
	 * 	isOrganPermit==true 用户下所有项目权限 </br>
	 *  isOrganPermit==false 用户下所有带状态数据,用作项目树配置半勾情况
	 *
	 * @param userId
	 * @param customerId
	 *            TODO
	 * @param isOrganPermit
	 *            TODO
	 * @return
	 */
	List<ProjectRelationDto> queryEmpProject(@Param("empId")String empId, @Param("customerId")String customerId,
								   @Param("isOrganPermit")boolean isOrganPermit);
	/**
	 * 删除用户项目权限信息
	 *
	 * @param userId
	 * @param customerId
	 */
	void deleteEmpProject(@Param("empId")String empId, @Param("customerId")String customerId);
	/**
	 * 添加用户项目权限信息
	 *
	 * @param dtos
	 */
	void addEmpProject(@Param("dtos")List<ProjectRelationDto> dtos);


	List<ProjectTypeDto> queryProType();
	
	/**
	 * 通过员工查出项目下的所有员工
	 */
	List<String> queryEmpInfosByEmpid(@Param("empId") String empId);
}
