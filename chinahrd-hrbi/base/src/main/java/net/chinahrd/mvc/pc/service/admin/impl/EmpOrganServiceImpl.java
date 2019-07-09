package net.chinahrd.mvc.pc.service.admin.impl;

import java.util.Date;
import java.util.List;

import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.admin.EmpDto;
import net.chinahrd.entity.dto.pc.admin.EmpOrganizationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.emp.DimEmp;
import net.chinahrd.mvc.pc.dao.admin.EmpOrganDao;
import net.chinahrd.mvc.pc.dao.admin.OrganDao;
import net.chinahrd.mvc.pc.service.admin.EmpOrganService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.Str;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("empOrganService")
public class EmpOrganServiceImpl implements EmpOrganService {
	@Autowired
    private EmpOrganDao empOrganDao;
	
	@Autowired
	private OrganDao organDao;
	
	@Override
	public EmpDto findEmpById(String customerId, String empId) {
		 if (StringUtils.isEmpty(empId)) {
	            return null;
	       }
	       return empOrganDao.findEmpById(customerId, empId);
	}

	@Override
	public List<OrganDto> queryEmpOrgans(String empId, String customerId,
			boolean isOrganPermit) {
		 return empOrganDao.queryEmpOrgans(empId, customerId, isOrganPermit);
	}

	@Override
	public List<TreeDto> dbToZtree(List<OrganDto> existOrgans) {
		Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (!(principal instanceof RbacUser)) {
            return CollectionKit.newList();
        }
        RbacUser rbacUser = (RbacUser) principal;
        List<OrganDto> organPermit = rbacUser.getOrganAllStatus();
        if (null == organPermit || organPermit.isEmpty()){
            return CollectionKit.newList();
        }
        // 获取第1个是因为数据按好序，level = 1并且只有一个level=1也就是集团公司只有一个
        List<TreeDto> notTopOrgans = packageTree(organPermit, organPermit
                .get(0).getOrganizationId(), existOrgans);

        if (organPermit.get(0).getOrganizationParentId().equals("-1")) {
            TreeDto topTreeDto = new TreeDto();
            topTreeDto.setId(organPermit.get(0).getOrganizationId());
            topTreeDto.setName(organPermit.get(0).getOrganizationName());
            topTreeDto.setParentId(organPermit.get(0).getOrganizationParentId());
            // topTreeDto.setChecked(true);
            for (OrganDto existOrgan : existOrgans) {
                String existFP = existOrgan.getFullPath();
                String orgFP = organPermit.get(0).getFullPath();
                if (existFP.contains(orgFP)) {
                    topTreeDto.setChecked(true);
                    // topTreeDto.setHalfCheck(true);
                }
            }
            topTreeDto.setIsParent(true);
            topTreeDto.setOpen(true);
            topTreeDto.setChildren(notTopOrgans);
            List<TreeDto> topOrgans = CollectionKit.newList();
            topOrgans.add(topTreeDto);
            return topOrgans;
        } else {
            return notTopOrgans;
        }
	}
	
	
	 /**
     * 递归子孙部门List
     *
     * @param targetList
     * @param orgId
     * @param existOrgans
     * @return
     */
    private List<TreeDto> packageTree(List<OrganDto> targetList, String orgId,
                                      List<OrganDto> existOrgans) {
        List<TreeDto> rs = CollectionKit.newList();
        for (OrganDto orgDto : targetList) {
            String targetParendId = orgDto.getOrganizationParentId();
            if (targetParendId.equals(orgId)) {
                TreeDto treeDto = new TreeDto();
                treeDto.setId(orgDto.getOrganizationId());
                treeDto.setName(orgDto.getOrganizationName());
                treeDto.setParentId(orgDto.getOrganizationParentId());

                treeDto.setHalfCheck(orgDto.getHalfCheck());
                for (int i = 0; i < existOrgans.size(); i++) {
                    if (existOrgans.get(i).getOrganizationId()
                            .equals(orgDto.getOrganizationId())) {
                        existOrgans.remove(i);
                        treeDto.setChecked(true);
                        treeDto.setOpen(true);
                    }
                }

                if (orgDto.getHasChildren() == 1) {
                    treeDto.setIsParent(true);
                    List<TreeDto> childrenList = packageTree(targetList,
                            orgDto.getOrganizationId(), existOrgans);
                    treeDto.setChildren(childrenList);
                } else {
                    treeDto.setIsParent(false);
                }
                rs.add(treeDto);
            }
        }
        return rs;
    }

	@Override
	public boolean deleteEmpOrganization(String empId, String customerId) {
		 try {
			 empOrganDao.deleteEmpOrganization(empId, customerId);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            return false;
	        }
	        return true;
	}

	@Override
	public boolean addEmpOrganization(String empId, String userId,
			String customerId, List<OrganDto> organDtos) {
		  if (CollectionKit.isEmpty(organDtos)) {
	            return false;
	        }

		  empOrganDao.deleteEmpOrganization(empId, customerId);
	        List<EmpOrganizationDto> dtos = CollectionKit.newList();

	        for (OrganDto organDto : organDtos) {
	        	EmpOrganizationDto dto = new EmpOrganizationDto();
	            dto.setEmpOrganizationId(Identities.uuid2());
	            dto.setCustomerId(customerId);
	            dto.setEmpId(empId);
	            dto.setHalfCheck(organDto.getHalfCheck());
	            dto.setOrganizationId(organDto.getOrganizationId());
	            dto.setCreateUserId(userId);
	            dto.setCreateTime(DateUtil.getTimestamp());
	            dtos.add(dto);
	        }

	        empOrganDao.addEmpOrganization(dtos);
	        return true;
	}


	
	
	@Override
	public DimEmp getDimEmp(String empId) {
		return empOrganDao.queryDimEmp(empId);
	}

	@Override
	public DimEmp saveEnmInfo(DimEmp emp, String customerId) {
		emp.setcId(emp.getEmpKey());
		emp.setEmpId(Identities.uuid2());
		emp.setvDimEmpId(emp.getEmpId());
		emp.setCustomerId(customerId);
		if(Str.IsEmpty(emp.getMarryStatus())){
			emp.setMarryStatus("0");
		}
		if(!Str.IsEmpty(emp.getBirthDate())){
			int age = DateUtil.getBetweenDiff(DateUtil.strToDate(emp.getBirthDate()), new Date(), 2);
			double ages = 0.0d;
			ages = age/12;
			emp.setAge(ages+"");
		}else{emp.setAge("0");}
		//司年龄
		if(!Str.IsEmpty(emp.getEntryDate()))
		{
			int compage = DateUtil.getBetweenDiff(DateUtil.strToDate(emp.getEntryDate()), new Date(), 2);
			double compages = 0.0d;
			compages = compage/12;
			emp.setCompanyAge(compages+"");
		}else{emp.setCompanyAge("0");}
		//非关键人才
		emp.setIsKeyTalent("0");
		//默认新增
		emp.setRemark("1");
		emp.setRefreshDate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//获取工作单位
		OrganDto organ = organDao.findOrganById(emp.getOrganId(), customerId);
		//获取部门的单位
		if(!Str.IsEmpty(organ.getOrganizationParentId()) && !organ.getOrganizationParentId().equals("-1")){
			OrganDto organParent = organDao.findOrganById(organ.getOrganizationParentId(), customerId);
			emp.setOrganizationParentId(organParent.getOrganizationId());
			emp.setOrganizationParentName(organParent.getOrganizationName());
		}
		else if(organ.getOrganizationParentId().equals("-1")){
			emp.setOrganizationParentId("-1");
			emp.setOrganizationParentName("-1");
		}
		empOrganDao.addvDimEmp(emp);
		return emp;
		
	}

	@Override
	public DimEmp updateEmpInfo(DimEmp emp, String customerId) {
		if(!Str.IsEmpty(emp.getBirthDate())){
			int age = DateUtil.getBetweenDiff(DateUtil.strToDate(emp.getBirthDate()), new Date(), 2);
			double ages = 0.0d;
			ages = age/12;
			emp.setAge(ages+"");
		}else{emp.setAge("0");}
		//司年龄
		if(!Str.IsEmpty(emp.getEntryDate()))
		{
			int compage = DateUtil.getBetweenDiff(DateUtil.strToDate(emp.getEntryDate()), new Date(), 2);
			double compages = 0.0d;
			compages = compage/12;
			emp.setCompanyAge(compages+"");
		}else{emp.setCompanyAge("0");}
		//默认新增
		emp.setRemark("2");
		emp.setRefreshDate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//获取工作单位
		OrganDto organ = organDao.findOrganById(emp.getOrganId(), customerId);
		//获取部门的单位
		if(!Str.IsEmpty(organ.getOrganizationParentId()) && !organ.getOrganizationParentId().equals("-1")){
			OrganDto organParent = organDao.findOrganById(organ.getOrganizationParentId(), customerId);
			emp.setOrganizationParentId(organParent.getOrganizationId());
			emp.setOrganizationParentName(organParent.getOrganizationName());
		}
		else if(organ.getOrganizationParentId().equals("-1")){
			emp.setOrganizationParentId("-1");
			emp.setOrganizationParentName("-1");
		}
		try{
			empOrganDao.updatevDimEmp(emp);
		return emp;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}



}
