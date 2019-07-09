package net.chinahrd.talentContrast.mvc.app.service.impl;

import java.util.List;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.EvalReportDto;
import net.chinahrd.entity.dto.app.JobChangeDto;
import net.chinahrd.entity.dto.app.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.app.employeePerformance.PerfChangeDto;
import net.chinahrd.entity.dto.app.talentContrast.ContrastEmpDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.talentContrast.mvc.app.dao.MobileTalentContrastDao;
import net.chinahrd.talentContrast.mvc.app.service.MobileTalentContrastService;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人员对比service
 * @author htpeng
 *2016年3月30日下午5:31:18
 */
@Service("mobileTalentContrastService")
public class MobileTalentContrastServiceImpl implements MobileTalentContrastService {

	@Autowired
	private MobileTalentContrastDao mobileTalentContrastDao;

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentContrastService#queryContrastAll(java.lang.String, java.util.List)
	 */
	@Override
	public ContrastEmpDto queryContrastAll(String customerId,
			String empId,List<OrganDto> organPermit) {
		
		// 通过keyName查询员工时，登录人必须要具备查看所在员工下的数据权限	by jxzhang 
    	if(null == organPermit){
        	return null;
        }
    	List<String> organPermitIds = CollectionKit.newList();
    	for (OrganDto organDto : organPermit) {
    		organPermitIds.add(organDto.getOrganizationId());
		}
    	if(organPermitIds.isEmpty()){ return null; }
    	
//        List<ContrastEmpDto> resultDto = CollectionKit.newList();
    	List<ContrastEmpDto> dtos = mobileTalentContrastDao.queryContrastAll(customerId, null, empId, organPermitIds, null, null);
    	if(dtos!=null&&dtos.size()==1){
    		return dtos.get(0);
    	}
    	return null;
//        for (int i = 0; i < empList.size(); i++) {
//            for (ContrastEmpDto dto : dtos){
//                if (empList.get(i).equals(dto.getEmpId())){
//                    resultDto.add(dto);
//                }
//            }
//        }
//        return resultDto;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentContrastService#queryPerfChange(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PerfChangeDto> queryPerfChange(String empId, String customerId) {
//		  List<String> empIds = CollectionKit.strToList(empId);
	        return mobileTalentContrastDao.queryPerfChange(empId, customerId, 6);
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentContrastService#queryJobChange(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public List<JobChangeDto> queryJobChange(String empId, String customerId,
			Integer changeType) {
//		  List<String> empIds = CollectionKit.strToList(empId);
	        return mobileTalentContrastDao.queryJobChange(empId, customerId,changeType);
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentContrastService#queryNewEvalReport(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EvalReportDto> queryNewEvalReport(String customerId,
			String empId) {
//		List<String> empIds = CollectionKit.strToList(empId);
        return mobileTalentContrastDao.queryNewEvalReport(customerId, empId);
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentContrastService#getPastResume(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EmpPastResumeDto> getPastResume(String empId, String customerId) {
//		 List<String> empIds = CollectionKit.strToList(empId);
	     return mobileTalentContrastDao.queryPastResume(empId, customerId);
	}

	
	private List<String> getOrganPermitId(List<OrganDto> organPermit){
		//List<OrganDto> organPermit = EisWebContext.getCurrentUser().getOrganPermit();
    	if(null == organPermit){
        	return null;
        }
    	List<String> rs = CollectionKit.newList();
    	for (OrganDto organDto : organPermit) {
    		rs.add(organDto.getOrganizationId());
		}
		return  rs;
	}
    
	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentContrastService#queryContrastAll(java.lang.String, java.lang.String, net.chinahrd.biz.paper.dto.common.PaginationDto)
	 */
	@Override
	public PaginationDto<ContrastEmpDto> queryContrastAll(
			String customerId, String keyName,
			PaginationDto<ContrastEmpDto> pageDto,List<OrganDto> organPermit) {
		
    	// 通过keyName查询员工时，登录人必须要具备查看所在员工下的数据权限	by jxzhang 
    	List<String> organPermitIds = getOrganPermitId(organPermit);
    	if(organPermitIds.isEmpty()){ return null; }
    	
        int count = mobileTalentContrastDao.queryContrastCount(customerId, keyName, null, organPermitIds);
        
        List<ContrastEmpDto> dtos = mobileTalentContrastDao.queryContrastAll(customerId, keyName, null, organPermitIds, pageDto.getOffset(), pageDto.getLimit());
        
        pageDto.setRecords(count);
        pageDto.setRows(dtos);
        return pageDto;
	}

	
}
