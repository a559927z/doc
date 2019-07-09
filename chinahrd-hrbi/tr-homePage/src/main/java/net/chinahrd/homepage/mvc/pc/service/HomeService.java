package net.chinahrd.homepage.mvc.pc.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.emp.WorkOvertimeDto;
import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;
import net.chinahrd.entity.dto.pc.manage.GainOfLossDto;
import net.chinahrd.entity.dto.pc.manage.RemindEmpDto;
import net.chinahrd.entity.dto.pc.manage.TalentDevelEmpDto;
import net.chinahrd.entity.dto.pc.manage.WarnInfoDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.manage.*;

/**
 * 管理者首页Service接口
 * Created by wqcai on 15/11/11 0011.
 */
public interface HomeService {

    /**
     * 查询更新数据的最新时间
     *
     * @param customerId
     * @return
     */
    Date findUpdateDate(String customerId);

    /***
     * 获取团队成员信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    PaginationDto<EmpBaseInfoDto> queryTeamEmp(String organId, String customerId, PaginationDto pageDto);

    /***
     * 获取团队提醒-生日榜成员信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    PaginationDto<RemindEmpDto> queryRemindEmp(String organId, String customerId, PaginationDto pageDto);

    /**
     * 根据条件查询团队提醒-生日提醒相关信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<RemindEmpDto> queryBirthdayRemind(String organId, Timestamp time, String customerId);

    /**
     * 根据条件查询团队提醒-入司周年提醒相关信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<String, List<RemindEmpDto>> queryAnnualRemind(String organId, Timestamp time, String customerId);

    /**
     * 查询人才损益-编制信息和招聘信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    GainOfLossDto findGainOfLossInfo(String organId, String customerId);

    /**
     * 管理者首页-团队画像
     *
     * @param organId
     * @return
     */
    Map<Integer, Object> getTeamImgAb(String organId, String customerId);

    /**
     * 管理者首页-绩效目标
     *
     * @param organId
     * @return
     */
    Map<Integer, Object> getPerformance(String organId, String customerId);

    /**
     * 管理者首页-员工近两周加班信息
     *
     * @param organId
     * @return
     */
    List<WorkOvertimeDto> getWorkOvertimeInfo(String empid, String customerId);

//    /**
//     * 获取预警信息
//     *
//     * @param organId
//     * @return
//     */
//    public  List<WarnSynopsisDto> getWarnInfo(String organId , String customerId) ;
//    

    /**
     * 获取低绩效信息
     *
     * @param organId
     * @return
     */
    List<WarnInfoDto> queryLowPerformanceEmp(String organId, String customerId);

    /**
     * 获取高绩效未晋升人员列表
     *
     * @param organId
     * @return
     */
    List<WarnInfoDto> queryHighPerformanceEmp(String organId, String customerId);


    /**
     * 离职风险人员统计
     *
     * @param organId
     * @param customerId
     * @return
     */
    Integer queryRunOffWarnCount(String customerId, String organId);

    /**
     * 离职风险
     *
     * @param empKeys
     * @param customerId
     * @return
     */
    List<WarnInfoDto> queryRunOffWarnEmp(String customerId, String organId);

    /**
     * 加班超过预警
     *
     * @param empKeys
     * @param customerId
     * @return
     */
    List<WarnInfoDto> queryOvertimeEmp(String customerId, String organId);

    /**
     * 管理者首页-人才发展
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<Integer, Object> getTalentDevel(String organId, String customerId);

    /**
     * 管理者首页-人才发展-测试项目
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<TalentDevelEmpDto> getTalentDevelExamItem(Integer yearInt, String empId, String customerId);


}
