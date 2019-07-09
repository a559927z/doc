package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.admin.EmpDto;
import net.chinahrd.entity.dto.pc.admin.EmpOrganizationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.emp.DimEmp;

/**
 * Created by htpeng on 15/12/23.
 */
@Repository("empOrganDao")
public interface EmpOrganDao {

    /**
     * 根据员工ID查询员工信息
     *
     * @param customerId 客户ID
     * @param empId     员工ID
     * @return
     */
    EmpDto findEmpById(@Param("customerId") String customerId, @Param("empId") String roleId);

    
    /**
     * 根据员工ID查询员组织架构权限
     *
	 * 	isOrganPermit==true 角色下所有数据权限 </br>
	 *  isOrganPermit==false 角色下所有带状态数据,用作机构树配置半勾情况
	 * 
	 * @param empId
	 * @param customerId
	 *            TODO
	 * @param isOrganPermit
	 *            TODO
	 * @return
	 */
	List<OrganDto> queryEmpOrgans(@Param("empId")String empId, @Param("customerId")String customerId,
			@Param("isOrganPermit")boolean isOrganPermit);

	



	/**
	 * 删除角色功能权限信息
	 * 
	 * @param roleId
	 */
	void deleteEmpOrganization(@Param("empId")String empId, @Param("customerId")String customerId);

	/**
	 * 添加角色数据权限信息
	 * 
	 * @param dtos
	 */
	void addEmpOrganization(@Param("dtos")List<EmpOrganizationDto> dtos);



    /**
     * 查找一个员工
     * @param emp
     * @return
     */
    DimEmp queryDimEmp(@Param("empId") String empId);
    /**
     * 添加一个员工
     * @param emp
     */
	void addvDimEmp(DimEmp emp);


	/**
	 * 修改一个员工
	 * @param emp
	 */
	void updatevDimEmp(DimEmp emp);
}
