package net.chinahrd.talentMaps.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsConfigDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsEmpCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsPointDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsSimpleCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamInfoDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamQueryDto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;


/**
 * 人才地图
 *
 * @author xwLi 2016-07-18
 */
@Repository("talentMapsDao")
public interface TalentMapsDao {

    /**
     * 检查是否审核人
     *
     * @param empId
     * @param customerId
     * @return
     */
    boolean checkIsAuditor(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 将已发布的人员数据，组装为人才地图结果表map_talent_result<br />
     *
     * @param customerId
     * @param topId      本次提交的顶级机构ID
     * @param yearMonth  本次年月
     * @return 0 没有错误返回，1有错误返回
     * @author jxzhang on 2016-10-1
     */
//    Integer callMapTalentResult(Map<String, Object> map);
    /*Integer callMapTalentResult(@Param("p_customer_id") String customerId,
                                @Param("p_top_id") String topId,
                                @Param("p_ym") int yearMonth);*/

    /**
     * 查询时间区间选择
     */
    List<String> queryTimesCycle(@Param("customerId") String customerId);

    /**
     * 团队能力/绩效趋势图 地图显示-查看能力
     */
    List<TalentMapsDto> queryTeamCPMapForAbility(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 地图显示-查看绩效
     */
    List<TalentMapsDto> queryTeamCPMapForPerformance(Map<String, Object> map);

    /**
     * 查询原始人才地图
     *//*
    List<TalentMapsDto> queryMapsAdjustment(@Param("customerId") String customerId, @Param("topOrgan") List<OrganDto> topOrgan);*/

    /**
     * 生成原始人才地图
     */
//    void addMapsAdjustment(@Param("customerId") String customerId, @Param("topOrgan") List<OrganDto> topOrgan, @Param("updateTime") String updateTime);

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细-查看能力总数
     */
    int queryTeamCPMapPersonDetailForAbilityCount(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细-查看能力
     */
    List<TalentMapsDto> queryTeamCPMapPersonDetailForAbility(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细-查看绩效总数
     */
    int queryTeamCPMapPersonDetailForPerformanceCount(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细-查看绩效
     */
    List<TalentMapsDto> queryTeamCPMapPersonDetailForPerformance(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 列表显示-列表标题全部
     */
    int queryAbilityForListCount(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 列表显示-列表标题其他
     */
    List<TalentMapsDto> queryAbilityForList(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 列表显示
     */
    List<TalentMapsDto> queryTeamCPGrid(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-查看能力
     */
    List<TalentMapsSimpleCountDto> queryTeamCPDetailForAbility(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-查看绩效
     */
    List<TalentMapsSimpleCountDto> queryTeamCPDetailForPerformance(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-查看能力-全屏显示
     */
    List<TalentMapsSimpleCountDto> queryTeamCPDetailForFullAbility(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-查看绩效-全屏显示
     */
    List<TalentMapsSimpleCountDto> queryTeamCPDetailForFullPerformance(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-能力/时间员工数
     */
    int queryTeamCPAbilityEmpCount(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-能力/时间员工
     */
    List<TalentMapsPointDto> queryTeamCPAbilityEmpPoint(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-绩效/时间员工数
     */
    int queryTeamCPPerformanceEmpCount(Map<String, Object> map);

    /**
     * 团队能力/绩效趋势图 明细显示-绩效/时间员工
     */
    List<TalentMapsPointDto> queryTeamCPPerformanceEmpPoint(Map<String, Object> map);

    /**
     * 盘点报告-人员信息
     */
    List<TalentMapsDto> queryInventoryReportPersonInfo(@Param("customerId") String customerId, @Param("empId") String empId);

    /**
     * 查询z轴信息
     * return
     * curtName	zName
     */
    List<TalentMapsDto> queryPositionInfo(@Param("customerId") String customerId);

    /**
     * 查询能力信息
     * return
     * curtName	abilityNumberName
     */
    List<TalentMapsDto> queryAbilityInfo(@Param("customerId") String customerId);

    /**
     * 查询绩效信息
     * return
     * curtName	performanceName
     */
    List<TalentMapsDto> queryPerformanceInfo(@Param("customerId") String customerId);

    /**
     * 查询z轴纬度
     * return
     * curtName	performanceName
     */
    List<TalentMapsDto> queryZInfo(@Param("customerId") String customerId);

    /**
     * 查询职级信息
     * return
     * curtName	abilityLvName
     */
    List<TalentMapsDto> queryRankInfo(@Param("customerId") String customerId);

    /**
     * 盘点报告-人才地图轨迹
     */
    List<TalentMapsDto> queryTalentMapTrajectory(@Param("customerId") String customerId, @Param("empId") String empId,
                                                 @Param("maxDate") String maxDate, @Param("rows") Integer rows);

    /**
     * 盘点报告-绩效轨迹
     */
    List<TalentMapsDto> queryPerformanceTrajectory(@Param("customerId") String customerId, @Param("empId") String empId,
                                                   @Param("rows") Integer rows);

    /**
     * 盘点报告-能力轨迹
     */
    List<TalentMapsDto> queryAbilityTrajectory(@Param("customerId") String customerId, @Param("empId") String empId,
                                               @Param("rows") Integer rows);

    /**
     * 盘点报告-异动轨迹
     */
    List<TalentMapsDto> queryChangeTrajectory(@Param("customerId") String customerId, @Param("empId") String empId,
                                              @Param("rows") Integer rows);

    /**
     * 生成原始人才地图
     */
    void addOriginalTalentMaps(Map<String, Object> map);

    /**
     * 查询待审核的人员信息
     */
    List<TalentMapsDto> queryTalentPerformanceAbility(Map<String, Object> map);

    int queryTalentPerformanceAbilityCount(Map<String, Object> map);

    /**
     * 查询地图坐标
     */
    List<TalentMapsDto> queryMapsPreview(Map<String, Object> map);

    /**
     * 查询待发布的人员信息
     */
    List<TalentMapsDto> queryTalentPublish(Map<String, Object> map);

    int queryTalentPublishCount(Map<String, Object> map);

    List<TalentMapsDto> queryTalentAdjustmentInfo(Map<String, Object> map);

    /**
     * 查询已发布的人才信息
     *
     * @param map
     * @return
     */
    int queryTalentPublishInfoCount(Map<String, Object> map);

    /**
     * 查询已调整的人员信息-全部
     *
     * @param map
     * @return
     */
    List<TalentMapsDto> queryTalentPublishInfo(Map<String, Object> map);

    /**
     * 查询人才地图
     */
    List<TalentMapsDto> queryAllTalentMaps(Map<String, Object> map);

    /**
     * 查询待审核的人才地图
     */
    List<TalentMapsDto> queryTalentMapsAuditing(Map<String, Object> map);

    /**
     * 查询待发布的人才地图
     */
    List<TalentMapsDto> queryTalentMapsPublish(Map<String, Object> map);

    /**
     * 查询历史审核列表
     */
    List<TalentMapsDto> queryHistoricalAuditing(@Param("customerId") String customerId, @Param("topOrgan") List<OrganDto> topOrgan, @Param("empId") String empId);

    /**
     * 查询历史
     */
    TalentMapsDto queryMapManage(@Param("customerId") String customerId, @Param("id") String id, @Param("topId") String topId, @Param("yearMonth") Integer yearMonth);

    /**
     * 查询机构所对应地图人员统计
     *
     * @param organs
     * @param yearMonths
     * @param customerId
     * @return
     */
    List<TalentMapsEmpCountDto> queryOrganMapsEmpCount(@Param("organs") List<TalentMapsEmpCountDto> organs, @Param("yearMonths") String yearMonths, @Param("customerId") String customerId);

    /**
     * 获取员工信息
     */
    List<TalentMapsDto> checkEmpInfo(@Param("customerId") String customerId, @Param("keyName") String keyName);

    /**
     * 更新人才地图坐标-审核
     */
    void updateAuditingInfo(@Param("list") List<TalentMapsDto> list);

    /**
     * 更新人才地图坐标-发布
     */
    void updatePublishInfo(@Param("list") List<TalentMapsDto> list);

    /**
     * 更新人才地图坐标
     */
    void updateMapsManagement(Map<String, Object> map);

    /**
     * 查询是否存在人才地图发布
     */
    int queryPublishMaps(@Param("customerId") String customerId, @Param("organId") String organId, @Param("time") String time);

    /**
     * 添加人才地图发布
     */
    void addPublishMaps(Map<String, Object> map);

    /**
     * 人才地图发布
     */
    void pulishMapsManagement(Map<String, Object> map);

    /**
     * 获取地图简易模式数据
     *
     * @param dto
     * @return
     */
    List<TalentMapsSimpleCountDto> queryMapsSimpleEmpCount(TalentMapsTeamQueryDto dto);

    /**
     * 获取地图相关员工点信息
     *
     * @param dto
     * @return
     */
    List<TalentMapsPointDto> queryMapsEmpPoint(TalentMapsTeamQueryDto dto, RowBounds rowBounds);

    /**
     * 获取人才地图时间周期
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryTalentMapsDateCycle(@Param("organIds") List<String> organIds, @Param("customerId") String customerId);

    /**
     * 获取团队PK里面团队设置的人员统计
     *
     * @param queryDto
     * @return
     */
    int queryMapsSettingEmpCount(TalentMapsTeamQueryDto queryDto);

    /**
     * 获取地图相关员工点信息
     *
     * @param queryDto
     * @return
     */
    List<TalentMapsPointDto> queryTeamEmpPoint(TalentMapsTeamQueryDto queryDto);

    /**
     * 删除用户团队信息
     *
     * @param empId
     * @param teamId
     * @param customerId
     */
    void deleteUserTeamInfo(@Param("empId") String empId, @Param("teamId") String teamId, @Param("customerId") String customerId);

    /**
     * 添加团队相关信息
     *
     * @param teamInfoDtos
     */
    void insertMapsTeams(@Param("teamInfoDtos") List<TalentMapsTeamInfoDto> teamInfoDtos);

    /**
     * 获取用户保存的团队信息条数
     *
     * @param teamId
     * @param empId
     * @param customerId
     * @return
     */
    int findUserTeamNum(@Param("teamId") String teamId, @Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 修改用户团队信息
     *
     * @param dto
     */
    void updateUserTeam(TalentMapsTeamInfoDto dto);

    /**
     * 查询用户团队信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<TalentMapsTeamInfoDto> queryUserTeamInfo(@Param("empId") String empId, @Param("customerId") String customerId);

    List<TalentMapsConfigDto> queryMapsConfigInfo(@Param("customerId") String customerId);

    /**
     * 查询原始地图配置
     *
     * @return
     */
    List<OrganDto> queryConfigTalentMaps(@Param("customerId") String customerId);

    /**
     * 检查是否有发布地图权限
     *
     * @param customerId
     * @param topId
     * @param empId
     * @param time
     * @return
     */
    int checkPublishPermission(@Param("customerId") String customerId, @Param("topId") String topId, @Param("empId") String empId, @Param("time") String time);

    List<Integer> queryTalentMapsTime(@Param("customerId") String customerId);
    
    /**
     * 取出地图结果数据
     * @param customerId
     * @param organId
     * @param yearMonth
     * @return
     */
    List<TalentMapsDto> queryTalentmapsResult(@Param("customerId") String customerId, @Param("organId") String organId, @Param("yearMonth") int yearMonth);
    
    /**
     * 添加人才地图结果数据
     * @param talentMapsResult
     */
    void insertMapTalentResult(@Param("talentMapsResult") List<TalentMapsDto> talentMapsResult);
}
