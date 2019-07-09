package net.chinahrd.talentContrast.mvc.app.dao;

import java.util.List;

import net.chinahrd.entity.dto.app.EvalReportDto;
import net.chinahrd.entity.dto.app.JobChangeDto;
import net.chinahrd.entity.dto.app.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.app.employeePerformance.PerfChangeDto;
import net.chinahrd.entity.dto.app.talentContrast.ContrastEmpDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * 人员对比dao
 *
 * @author htpeng
 *2016年3月30日下午5:29:41
 */
@Repository("mobileTalentContrastDao")
public interface MobileTalentContrastDao {

	/**
	 * @param customerId
	 * @param object
	 * @param empList
	 * @param organPermitIds
	 * @param object2
	 * @param object3
	 * @return
	 */
    List<ContrastEmpDto> queryContrastAll(@Param("customerId") String customerId, @Param("keyName") String keyName, @Param("empId") String empId, 
    		@Param("organPermitIds") List<String> organPermitIds, 
    		@Param("offset")Integer offset, @Param("limit")Integer limit);

    int queryContrastCount(@Param("customerId") String customerId, @Param("keyName") String keyName, @Param("empIds") List<String> empIds,
    		@Param("organPermitIds") List<String> organPermitIds);

    /**
     * 根据员工ID集合查询工作异动信息
     *
     * @param empIds
     * @param customerId
     * @param changeType 异动类型  1：晋升，2：调入，3：入职，4：调出，5：离职
     * @return
     */
    List<JobChangeDto> queryJobChange(@Param("empId") String empId, @Param("customerId") String customerId,@Param("changeType")Integer changeType);

    /**
     * 查询最新一次的测评信息
     *
     * @param customerId
     * @param empIds
     * @return
     */
    List<EvalReportDto> queryNewEvalReport(@Param("customerId") String customerId, @Param("empId") String empId);

    /**
     * 获取员工过往履历信息
     */
    List<EmpPastResumeDto> queryPastResume(@Param("empId") String empId, @Param("customerId") String customerId);

    
    /**
     * 根据员工ID集合查询绩效信息
     *
     * @param empIds
     * @param customerId
     * @return
     */
    List<PerfChangeDto> queryPerfChange(@Param("empId") String empId, @Param("customerId") String customerId, @Param("empNumber") Integer empNumber);


  
}
