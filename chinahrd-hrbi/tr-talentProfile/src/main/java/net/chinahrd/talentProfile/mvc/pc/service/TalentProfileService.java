package net.chinahrd.talentProfile.mvc.pc.service;

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


/**
 * 人才剖像Service接口类
 * Created by wqcai on 15/10/14 0014.
 */
public interface TalentProfileService {

    /**
     * 根据员工ID查询工作异动信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @param customerId
     * @param changeType 异动类型  1：晋升，2：调入，3：入职，4：调出，5：离职
     * @return
     */
    List<JobChangeDto> queryJobChange(String empId, String customerId,Integer changeType);

    /**
     * 根据员工ID查询绩效信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<PerfChangeDto> queryPerfChange(String empId, String customerId);

    /**
     * 根据员工ID查询360测评报告信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<EvalReportDto> query360Evaluation(String empId, String customerId);
    
    /**
     * 根据员工ID查询360测评报告信息 For Mobile
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<EvalReport4MobileDto> query360Evaluation4Mobile(String empId, String customerId);

    /**
     * 根据员工ID查询其它测评报告信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<OtherReportDto> queryOtherEvaluation(String empId, String customerId);

    /**
     * 根据员工ID查询员工详细信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    EmpDetailDto findEmpDetail(String empId, String customerId);

    /**
     * 获取员工奖惩信息
     */
    List<BonusPenaltyDto> getBonusData(String empId, String customerId);

    /**
     * 获取员工培训经历信息
     */
    List<TrainExperienceDto> getTrainExp(String empId, String customerId);

    /**
     * 获取员工过往履历信息
     */
    List<EmpPastResumeDto> getPastResume(String empId, String customerId);

    /**
     * 获取员工教育背景信息
     */
    List<EmpEduDto> getEduBg(String empId, String customerId);

    /**
     * 获取员工所获职称信息
     *
     * @param empId
     * @return
     */
    List<ProfTitleDto> getProfTitle(String empId, String customerId);

    /**
     * 获取员工家庭关系信息
     *
     * @param empId
     * @return
     */
    List<EmpFamilyDto> getFamilyData(String empId, String customerId);


}
