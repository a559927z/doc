package net.chinahrd.mvc.pc.service.admin;


import java.util.List;

import net.chinahrd.entity.dto.pc.admin.EmpDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.emp.DimEmp;

/**
 * 员工关联组织机构Service接口类
 * Created by htpeng on 15/6/10.
 */
public interface EmpOrganService {
    /**
  
  

    /**
     * 根据员工ID查询员工信息
     * @return
     */
    EmpDto findEmpById(String customerId,String empId);

	List<OrganDto> queryEmpOrgans(String empId, String customerId, boolean b);

	List<TreeDto> dbToZtree(List<OrganDto> existOrgans);

	boolean deleteEmpOrganization(String empId, String customerId);

	boolean addEmpOrganization(String empId, String userId, String customerId,
			List<OrganDto> organDtos);

    /**
     * 查找一个员工
     * @param empId
     * @return
     */
    DimEmp getDimEmp(String empId);
    
    /**
     * 新加一个员工
     * @param emp
     * @param customerId
     * @param empId
     */
    DimEmp saveEnmInfo(DimEmp emp, String customerId);

    /**
     * 更新一个员工
     * @param emp
     * @param customerId
     * @return
     */
	DimEmp updateEmpInfo(DimEmp emp, String customerId);

   
}
