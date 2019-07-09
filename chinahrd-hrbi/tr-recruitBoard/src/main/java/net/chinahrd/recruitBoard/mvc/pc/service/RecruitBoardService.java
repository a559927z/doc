package net.chinahrd.recruitBoard.mvc.pc.service;


import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.recruitBoard.*;

import java.util.List;
import java.util.Map;

/**
 * 招聘看板Service接口类
 * Created by wqcai on 16/5/5.
 */
public interface RecruitBoardService {

    String findEmpPQMaxDate(String customerId);

    /**
     * 获取当前年待招聘岗位
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<String, Object> queryWaitRecruitPost(String organId, String customerId);

    /**
     * 获取当前年待招聘人数
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<String, Object> queryWaitRecruitNum(String organId, String customerId);

    /**
     * 获取当前年的招聘费用及预算
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<String, Object> queryRecruitCostAndBudget(String organId, String customerId);

    /**
     * 获取岗位满足率相关信息（为个性化排序）
     *
     * @param organId
     * @param empId
     * @param customerId
     * @return
     */
    List<RecruitPositionMeetRateDto> queryPositionMeetRate(String organId, String empId, String customerId);

    /**
     * 获取岗位满足率相关信息
     *
     * @param organId
     * @param empId
     * @param customerId
     * @return
     */
    List<RecruitPositionMeetRateDto> queryPositionMeetRate(String organId, String quotaId, String empId, String customerId);

    /***
     * 获取招聘结果详情
     *
     * @param publicId   招聘发布ID
     * @param type       查看类型
     * @param customerId
     * @return
     */
    PaginationDto<RecruitPositionResultDto> queryPositionResult(String publicId, Integer type, PaginationDto<RecruitPositionResultDto> dto, String customerId);

    /**
     * 更新员工的岗位满足率排序信息
     *
     * @param sequenceStr
     * @param quotaId
     * @param empId
     * @param customerId
     */
    void updatePositionMeetRateSequence(String sequenceStr, String quotaId, String empId, String customerId);

    /**
     * 获取招聘渠道统计信息
     *
     * @param keyName
     * @param organId
     * @param customerId
     * @return
     */
    List<RecruitChannelResultDto> queryRecruitChannel(String keyName, String organId, String customerId);

    /**
     * 查询招聘岗位薪资信息
     *
     * @param keyName
     * @param organId
     * @param customerId
     * @return
     */
    List<RecruitPositionPayDto> queryPositionPay(String keyName, String organId, String customerId);

    /**
     * 查询招聘周期
     *
     * @param customerId
     * @return
     */
    int queryDismisstionWeekCycleDays(String customerId);

    /**
     * 查询招聘周期占比
     *
     * @param employNum
     * @param channelId
     * @param parent
     * @param customerId
     * @return
     */
    List<RecruitDismissionWeekDto> queryDismissionRate(Integer employNum, String channelId, String parent, String organId, String customerId);

    /**
     * 获取异动提醒统计
     *
     * @param changeType
     * @param organId
     * @param customerId
     * @return
     */
    List<RecruitJobChangeTotalDto> queryRecruitChange(List<Integer> changeType, String organId, String customerId);

    /**
     * 获取画像绩效标签
     *
     * @param customerId
     * @return
     */
    List<RecruitTagDto> queryImagesPerformanceTags(String customerId);

    /**
     * 获取画像查询标签
     *
     * @param customerId
     * @return
     */
    List<RecruitTagDto> queryImagesQueryTags(String position, String customerId);

    /**
     * 获取某岗位绩效人群统计
     * @param position
     * @param yearNum
     * @param continueNum
     * @param star
     * @param customerId
     * @return
     */
    int queryPositionPerfEmpCount(String position, Integer yearNum, Integer continueNum, Integer star, String customerId);

    /**
     * 获取高绩效画像标签
     *
     * @param position
     * @param yearNum
     * @param continueNum
     * @param star
     * @param customerId
     * @return
     */
    List<RecruitTagDto> queryPositionImages(String position, Integer yearNum, Integer continueNum, Integer star, String customerId);

    /**
     * 获取异动提醒-员工列表
     *
     * @param changeType
     * @param organId
     * @param customerId
     * @param dto
     * @return
     */
    PaginationDto<RecruitJobChangeDto> queryRecruitChangeList(Integer changeType, String organId, String customerId, PaginationDto<RecruitJobChangeDto> dto);


    /**
     * 获取岗位推荐人群统计
     * @param sex
     * @param degreeId
     * @param schoolType
     * @param contents
     * @param qualitys
     * @param organIds
     * @param customerId
     * @return
     */
    int queryPositionRecommendCount(String sex, String degreeId, String schoolType, List<String> contents, List<KVItemDto> qualitys, List<String> organIds, String customerId);

    /**
     * 获取岗位推荐人群
     *
     * @param sex
     * @param degreeId
     * @param schoolType
     * @param contents
     * @param qualitys
     * @param customerId
     * @param dto
     * @return
     */
    PaginationDto<RecruitRecommendEmpDto> queryPositionRecommendEmp(String sex, String degreeId, String schoolType, List<String> contents, List<KVItemDto> qualitys, List<String> organIds, String customerId, PaginationDto<RecruitRecommendEmpDto> dto);
}