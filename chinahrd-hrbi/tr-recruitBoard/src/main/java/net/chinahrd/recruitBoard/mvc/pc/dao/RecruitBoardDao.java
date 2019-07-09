package net.chinahrd.recruitBoard.mvc.pc.dao;

import net.chinahrd.entity.dto.pc.recruitBoard.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 招聘看板Dao接口类
 * Created by wqcai on 16/5/5.
 */
@Repository("recruitBoardDao")
public interface RecruitBoardDao {

    String findEmpPQMaxDate(@Param("customerId") String customerId);

    List<RecruitPositionMeetRateDto> queryWaitRecruitPost(@Param("organId") String organId, @Param("year") int year, @Param("customerId") String customerId);

    Integer queryWaitRecruitNum(@Param("organId") String organId, @Param("year") int year, @Param("customerId") String customerId);

    Map<String, Double> queryRecruitCostAndBudget(@Param("organId") String organId, @Param("year") int year, @Param("customerId") String customerId);

    List<RecruitPositionMeetRateDto> queryPostMeetRate(@Param("organId") String organId, @Param("currDate") Timestamp currDate, @Param("year") int year, @Param("customerId") String customerId);

    int findPositionResultCount(@Param("publicId") String publicId, @Param("year") int year, @Param("type") Integer type, @Param("customerId") String customerId);

    List<RecruitPositionResultDto> queryPositionResult(@Param("publicId") String publicId, @Param("year") int year, @Param("type") Integer type, @Param("customerId") String customerId, RowBounds rb);

    List<RecruitChannelQueryDto> queryRecruitChannel(@Param("keyName") String keyName, @Param("organId") String organId, @Param("cycleDays") int cycleDays, @Param("year") int year, @Param("customerId") String customerId);

    String findPositionPayMaxYearMonth(@Param("customerId") String customerId);

    List<RecruitPositionPayDto> queryPositionPay(@Param("keyName") String keyName, @Param("organId") String organId, @Param("cryptKey") String cryptKey, @Param("year") int year, @Param("yearMonth") String yearMonth, @Param("customerId") String customerId);

    Integer queryDismisstionWeekCycleDays(@Param("type") int type, @Param("customerId") String customerId);

    List<RecruitDismissionWeekDto> queryDismissionRate(Map<String, Object> map);

    RecruitJobChangeTotalDto queryRecruitChange(@Param("changeType") Integer changeType, @Param("year") int year, @Param("organId") String organId, @Param("customerId") String customerId);

    List<RecruitJobChangeDto> queryRecruitChangeList(@Param("changeType") Integer changeType, @Param("year") int year, @Param("organId") String organId, @Param("customerId") String customerId, RowBounds rowBounds);

    int queryPositionPerfEmpCount(Map<String, Object> map);

    List<RecruitHighPerfEmpDto> queryHighPerfImagesEmps(Map<String, Object> map);

    List<RecruitTagDto> queryImagesPerformanceTags(@Param("customerId") String customerId);

    List<RecruitTagDto> queryImagesQueryTags(@Param("position") String position, @Param("customerId") String customerId);

    List<RecruitTagDto> querySuperiorityTags(@Param("emps") List<String> emps, @Param("customerId") String customerId);

    List<RecruitEmpQualityTagDto> queryEmpQualityTags(@Param("position") String position, @Param("emps") List<String> emps, @Param("maxDate") String maxDate, @Param("customerId") String customerId);

    int queryPositionRecommendCount(Map<String, Object> map);

    List<RecruitRecommendEmpDto> queryPositionRecommendEmp(Map<String, Object> map, RowBounds rowBounds);
}
