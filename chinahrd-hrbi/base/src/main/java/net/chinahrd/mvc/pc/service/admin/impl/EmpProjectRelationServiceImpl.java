package net.chinahrd.mvc.pc.service.admin.impl;

import java.util.Iterator;
import java.util.List;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.enums.PermissionCode;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.admin.ProjectRelationDto;
import net.chinahrd.entity.dto.pc.admin.ProjectTypeDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.mvc.pc.dao.admin.EmpProjectRelationDao;
import net.chinahrd.mvc.pc.service.admin.EmpProjectRelationService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("empProjectRelationService")
public class EmpProjectRelationServiceImpl implements EmpProjectRelationService {

    @Autowired
    private EmpProjectRelationDao empProjectRelationDao;

    @Override
    public List<ProjectRelationDto> queryProjectPermit(String empId) {
        List<ProjectRelationDto> list = CollectionKit.newList();
        String superAdminId = PermissionCode.SUPER_ADMIN_ID.getValue();
        if(superAdminId.equals("")){
            superAdminId = empProjectRelationDao.findSuperAdminIdByUserKey(PermissionCode.SUPER_ADMIN_KEY.getValue());
        }
        if (empId.equals(superAdminId)) {
        
            list = empProjectRelationDao.queryProjectPermitBySuperAdmin(
                    EisWebContext.getCustomerId());
        } else {
            list = empProjectRelationDao.queryProjectPermit(empId,
                    EisWebContext.getCustomerId());

            Iterator<ProjectRelationDto> iterator = list.iterator();
            String id = "";
            while (iterator.hasNext()) {
                ProjectRelationDto ProjectDto = (ProjectRelationDto) iterator.next();
                if (id.equals(ProjectDto.getProjectId())) {
                    iterator.remove();
                }
                id = ProjectDto.getProjectId();
            }

        }
        return list;
    }

    @Override
    public List<ProjectRelationDto> queryEmpProject(String empId, String customerId,
                                          boolean isOrganPermit) {
        return empProjectRelationDao.queryEmpProject(empId, customerId, isOrganPermit);
    }

    @Override
    public boolean deleteEmpProject(String empId, String customerId) {
        try {
            empProjectRelationDao.deleteEmpProject(empId, customerId);
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addEmpProject(String empId, String createUserId,
                                       String customerId, List<ProjectRelationDto> ProjectDtos) {
        if (CollectionKit.isEmpty(ProjectDtos)) {
            return false;
        }
        empProjectRelationDao.deleteEmpProject(empId, customerId);
        List<ProjectRelationDto> dtos = CollectionKit.newList();

        for (ProjectRelationDto projectDto : ProjectDtos) {
        	ProjectRelationDto dto = new ProjectRelationDto();
            dto.setProjectRelationId(Identities.uuid2());
            dto.setCustomerId(customerId);
            dto.setEmpId(empId);
            dto.setHalfCheck(projectDto.getHalfCheck());
            dto.setType(projectDto.getType());
            dto.setProjectId(projectDto.getProjectId());
            dto.setCreateUserId(createUserId);
            dto.setCreateDate(DateUtil.getTimestamp());
            dtos.add(dto);
        }
        empProjectRelationDao.addEmpProject(dtos);
        return true;
    }


    @Override
    public List<TreeDto> dbToZtree(List<ProjectRelationDto> existProject) {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (!(principal instanceof RbacUser)) {
            return CollectionKit.newList();
        }
        RbacUser rbacUser = (RbacUser) principal;
        List<ProjectRelationDto> organPermit = rbacUser.getProjectAllStatus();
        if (null == organPermit || organPermit.isEmpty()) {
            return CollectionKit.newList();
        }
        // 获取第1个是因为数据按好序，level = 1并且只有一个level=1也就是集团公司只有一个
        List<TreeDto> notTopOrgans = packageTree(organPermit, organPermit
                .get(0).getProjectId(), existProject);
        return notTopOrgans;

    }

    private List<TreeDto> packageTree(List<ProjectRelationDto> targetList, String orgId,
                                      List<ProjectRelationDto> existOrgans) {
        List<TreeDto> rs = CollectionKit.newList();
        for (ProjectRelationDto orgDto : targetList) {
                TreeDto treeDto = new TreeDto();
                treeDto.setId(orgDto.getProjectId());
                treeDto.setName(orgDto.getProjectName());
                treeDto.setpId(orgDto.getProjectParentId());

                treeDto.setHalfCheck(orgDto.getHalfCheck());
                for (int i = 0; i < existOrgans.size(); i++) {
                	ProjectRelationDto proExist=existOrgans.get(i);
                    if (proExist.getProjectId()
                            .equals(orgDto.getProjectId())) {
                        existOrgans.remove(i);
                        treeDto.setChecked(true);
                        treeDto.setOpen(true);
                        treeDto.setType(proExist.getType());
                    }
                }

                if (orgDto.getHasChildren() == 1) {
                    treeDto.setIsParent(true);
                } else {
                    treeDto.setIsParent(false);
                }
                rs.add(treeDto);
        }
        return rs;
    }


	@Override
	public List<ProjectTypeDto> queryProType(){
		return empProjectRelationDao.queryProType();
	}

	/**
	 * 通过员工查出项目下的所有员工
	 * @param empId
	 * @return
	 */
	public List<String> queryEmpInfosByEmpid(String empId){
		return empProjectRelationDao.queryEmpInfosByEmpid(empId);
	}
}
