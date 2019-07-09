package net.chinahrd.talentProfile.mvc.pc.dao;

import java.util.List;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.EvalReport4MobileDto;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;
import net.chinahrd.entity.dto.pc.OtherReportDto;
import net.chinahrd.entity.dto.pc.emp.BonusPenaltyDto;
import net.chinahrd.entity.dto.pc.emp.EmpEduDto;
import net.chinahrd.entity.dto.pc.emp.EmpFamilyDto;
import net.chinahrd.entity.dto.pc.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.pc.emp.ProfTitleDto;
import net.chinahrd.entity.dto.pc.emp.TrainExperienceDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 人才剖像dao接口类
 * Created by wqcai on 15/10/14 0014.
 */
@Repository("talentProfileDao")
public interface TalentProfileDao {


    /**
     * 根据员工ID集合查询工作异动信息
     *
     * @param empIds
     * @param customerId
     * @param changeType 异动类型简称
     * @return
     */
    List<JobChangeDto> queryJobChange(@Param("empIds") List<String> empIds, @Param("customerId") String customerId,@Param("changeType")Integer changeType);

    /**
     * 根据员工ID集合查询绩效信息
     *
     * @param empIds
     * @param customerId
     * @return
     */
    List<PerfChangeDto> queryPerfChange(@Param("empIds") List<String> empIds, @Param("customerId") String customerId, @Param("empNumber") Integer empNumber);

    /**
     * 根据员工ID查询360测评报告信息
     * @param empId
     * @param customerId
     * @return
     */
    List<EvalReportDto> query360Evaluation(@Param("empId") String empId, @Param("customerId") String customerId);
    
    /**
     * 根据员工ID查询360测评报告信息 For Mobile
     * @param empId
     * @param customerId
     * @return
     */
    List<EvalReport4MobileDto> query360Evaluation4Mobile(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 根据员工ID查询其它测评报告信息
     * @param empId
     * @param customerId
     * @return
     */
    List<OtherReportDto> queryOtherEvaluation(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 根据员工ID查询员工详细信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    EmpDetailDto findEmpDetail(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 获取员工奖惩信息
     */
    List<BonusPenaltyDto> queryBonusData(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 获取员工培训经历信息
     */
    List<TrainExperienceDto> queryTrainExp(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 获取员工过往履历信息
     */
    List<EmpPastResumeDto> queryPastResume(@Param("empIds") List<String> empIds, @Param("customerId") String customerId);

    /**
     * 获取员工教育背景信息
     */
    List<EmpEduDto> queryEduBg(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 获取员工所获职称信息
     */
    List<ProfTitleDto> queryProfTitle(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 获取员工家庭关系信息
     */
    List<EmpFamilyDto> queryFamilyData(@Param("empId") String empId, @Param("customerId") String customerId);


}
