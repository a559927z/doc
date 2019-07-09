package net.chinahrd.talentContrast.mvc.app.service;

import java.util.List;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.EvalReportDto;
import net.chinahrd.entity.dto.app.JobChangeDto;
import net.chinahrd.entity.dto.app.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.app.employeePerformance.PerfChangeDto;
import net.chinahrd.entity.dto.app.talentContrast.ContrastEmpDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;



/**
 * 
 * @author htpeng
 *2016年3月30日下午5:31:48
 */
public interface MobileTalentContrastService {
	
	 /**
     * 根据员工ID查询员工信息
	 * @param customerId
	 * @param empList
	 * @return
	 */
	ContrastEmpDto queryContrastAll(String customerId,String empId,List<OrganDto> list);

	/**获取员工绩效信息（多个员工id用“,”号隔开）
	 * @param empId
	 * @param customerId
	 * @return
	 */
	List<PerfChangeDto> queryPerfChange(String empId, String customerId);

	/**获取员工工作异动信息（多个员工id用“,”号隔开
	 * @param empId
	 * @param customerId
	 * @param changeType
	 * @return
	 */
	List<JobChangeDto> queryJobChange(String empId, String customerId,
			Integer changeType);

	/**获取员工最新一次的测评信息
	 * @param customerId
	 * @param empId
	 * @return
	 */
	List<EvalReportDto> queryNewEvalReport(String customerId, String empId);

	/**获取员工过往履历信息 （多个员工id用“,”号隔开）
	 * @param empId
	 * @param customerId
	 * @return
	 */
	List<EmpPastResumeDto> getPastResume(String empId, String customerId);

	/**
	 * @param customerId
	 * @param keyName
	 * @param dto
	 * @return
	 */
	PaginationDto<ContrastEmpDto> queryContrastAll(String customerId,
			String keyName, PaginationDto<ContrastEmpDto> dto,List<OrganDto> organPermit);


}
