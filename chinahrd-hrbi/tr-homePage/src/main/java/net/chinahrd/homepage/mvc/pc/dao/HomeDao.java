package net.chinahrd.homepage.mvc.pc.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.emp.WorkOvertimeDto;
import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;
import net.chinahrd.entity.dto.pc.manage.GainOfLossDto;
import net.chinahrd.entity.dto.pc.manage.LossesEmpDto;
import net.chinahrd.entity.dto.pc.manage.RemindEmpDto;
import net.chinahrd.entity.dto.pc.manage.TalentDevelEmpDto;
import net.chinahrd.entity.dto.pc.manage.WarnInfoDto;
import net.chinahrd.entity.dto.pc.manage.WarnSynopsisDto;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.manage.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * 管理者首页Dao接口类
 * Created by wqcai on 15/11/11 0011.
 */
@Repository("manageHomeDao")
public interface HomeDao {

    /**
     * 查询更新数据的最新时间
     *
     * @param customerId
     * @return
     */
    Date findUpdateDate(@Param("customerId") String customerId);


    /**
     * 统计团队成员人数
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    Integer queryTeamEmpCount(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId);

    /**
     * 统计团队生日榜成员人数
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    Integer queryRemindEmpCount(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId);

    /**
     * 查询团队成员信息
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<EmpBaseInfoDto> queryTeamEmp(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId, RowBounds rb);

    /**
     * 查询团队提醒-生日榜成员信息
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<RemindEmpDto> queryRemindEmp(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId, @Param("begin") Integer begin,@Param("end") Integer end);

    /**
     * 根据条件查询团队提醒-生日提醒相关信息
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<RemindEmpDto> queryBirthdayRemind(@Param("subOrganIdList") List<String> subOrganIdList, @Param("currTime") Timestamp currTime, @Param("customerId") String customerId);

    /**
     * 根据条件查询团队提醒相关信息
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<RemindEmpDto> queryAnnualRemind(@Param("subOrganIdList") List<String> subOrganIdList, @Param("currTime") Timestamp currTime, @Param("annuals") List<Integer> annuals, @Param("customerId") String customerId);

    /**
     * 查询人才损益-编制信息和招聘信息
     *
     * @param organId
     * @param customerId
     * @param day        TODO
     * @return
     */
    GainOfLossDto findGainOfLossInfo(@Param("organId") String organId, @Param("customerId") String customerId, @Param("day") String day);

    /**
     * 查询人才损益员工信息
     * 员工：入职和离职
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<LossesEmpDto> queryLossesEmp(@Param("organId") String organId, @Param("customerId") String customerId, @Param("now") String now);

    /**
     * 查询人才损益员工信息
     * 员工：调入和调出
     *
     * @param customerId
     * @param now
     * @return
     */
    List<LossesEmpDto> queryInAndOut(@Param("customerId") String customerId, @Param("now") String now);


    /**
     * 查询人才损益员工信息
     * 招聘进程
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<GainOfLossDto> queryRecruitmentProcess(@Param("organId") String organId, @Param("customerId") String customerId);


   


    /**
     * 员工近两周加班信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<WorkOvertimeDto> queryWorkOvertimeInfo(@Param("empId") String empId, @Param("customerId") String customerId, @Param("curdate") String curdate, @Param("cycle") Integer cycle);

    /**
     * 员工近两周加班信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<WarnSynopsisDto> queryWarnInfo(@Param("organId") String organId, @Param("customerId") String customerId);

    /**
     * 低绩效未调整
     *
     * @return
     */
    List<WarnInfoDto> queryLowPerformanceEmp(Map<String, Object> map);

    Integer queryLowPerformanceCount(Map<String, Object> map);

    /**
     * 高绩效未晋升
     *
     * @return
     */
    List<WarnInfoDto> queryHighPerformanceEmp(Map<String, Object> map);

    Integer queryHighPerformanceCount(Map<String, Object> map);


    /**
     * 离职风险统计
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    Integer queryRunOffWarnCount(@Param("customerId") String customerId, @Param("subOrganIdList") List<String> subOrganIdList);

    /**
     * 离职风险
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<WarnInfoDto> queryRunOffWarnEmp(@Param("customerId") String customerId, @Param("subOrganIdList") List<String> subOrganIdList);

    /**
     * 加班预警
     *
     * @param customerId
     * @return
     */
    List<WarnInfoDto> queryOvertimeEmp(@Param("customerId") String customerId, @Param("subOrganIdList") List<String> subOrganIdList, @Param("availabilityDay") int availabilityDayNum, @Param("cycle") Integer cycle, @Param("warnTime") double warnTime, @Param("curdate") String curdate);

    /**
     * 管理者首页-人才发展
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<TalentDevelEmpDto> queryTalentDevel(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId, @Param("year") int year);

    /**
     * 管理者首页-人才发展-晋升数据
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<TalentDevelEmpDto> queryTalentDevelPromotionData(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId);

    /**
     * 管理者首页-人才发展-培训数据
     *
     * @param subOrganIdList
     * @param customerId
     * @return
     */
    List<TalentDevelEmpDto> queryTalentDevelTrainData(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId);

    /**
     * 管理者首页-人才发展-测评数据
     *
     * @param subOrganIdList
     * @param customerId
     * @param year           TODO
     * @return
     */
    List<TalentDevelEmpDto> queryTalentDevelExamData(@Param("subOrganIdList") List<String> subOrganIdList, @Param("customerId") String customerId, @Param("year") int year);

    /**
     * 管理者首页-人才发展-测评数据-项
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<TalentDevelEmpDto> queryTalentDevelExamItemData(@Param("yearInt") Integer yearInt, @Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 管理者首页-测评数据最新时间
     *
     * @param customerId
     * @return
     */
    Integer queryReportMaxYear(@Param("customerId") String customerId);


}
