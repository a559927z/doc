package net.chinahrd.talentMaps.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsEmpCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsPointDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsSimpleCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamInfoDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamQueryDto;


/**
 * 人才地图
 *
 * @author xwLi 2016-07-18
 */
public interface TalentMapsService {

    /**
     * 团队能力/绩效趋势图 地图显示
     */
    Map<String, Object> queryTimesCycle(List<String> organIds, String customerId);

    /**
     * 检查是否审核人
     *
     * @param empId
     * @param customerId
     * @return
     */
    boolean checkIsAuditor(String empId, String customerId);

    /**
     * 团队能力/绩效趋势图 地图显示
     */
    Map<String, Object> queryTeamCPMap(TalentMapsTeamQueryDto dto);

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细
     */
    PaginationDto<TalentMapsDto> queryTeamCPMapPersonDetail(TalentMapsTeamQueryDto dto, PaginationDto<TalentMapsDto> pDto);

    /**
     * 团队能力/绩效趋势图 列表显示-标题列表
     */
    Map<String, Object> queryAbilityForList(TalentMapsTeamQueryDto dto);

    /**
     * 团队能力/绩效趋势图 列表显示
     */
    List<TalentMapsDto> queryTeamCPGrid(TalentMapsTeamQueryDto dto, String titleId,
                                        int pageNum, int pageSize);

    /**
     * 团队能力/绩效趋势图- 明细显示
     */
    Map<String, Object> queryTeamCPDetail(TalentMapsTeamQueryDto dto);

    /**
     * 团队能力/绩效趋势图- 全屏明细显示
     */
    PaginationDto<TalentMapsPointDto> queryTeamCPFullDetail(TalentMapsTeamQueryDto qDto, PaginationDto<TalentMapsPointDto> dto);

    /**
     * 查询待审核的人员信息
     */
    PaginationDto<TalentMapsDto> queryTalentPerformanceAbility(String customerId, String topId, Integer yearMonth, String keyName, String abilityId, int flag, PaginationDto<TalentMapsDto> dto);

    /**
     * 盘点报告-人员信息
     */
    List<TalentMapsDto> queryInventoryReportPersonInfo(String customerId, String empId);

    /**
     * 根据页面显示要求，获取最大最小日期
     */
    String getMinMaxDate(List<String> organIds, String customerId);

    /**
     * 盘点报告-人才地图轨迹
     */
    Map<String, Object> queryTalentMapTrajectory(String customerId, String empId, String date, Integer rows);

    /**
     * 盘点报告-绩效轨迹
     */
    Map<String, Object> queryPerformanceTrajectory(String customerId, String empId, Integer rows);

    /**
     * 盘点报告-能力轨迹
     */
    Map<String, Object> queryAbilityTrajectory(String customerId, String empId, Integer rows);

    /**
     * 盘点报告-异动轨迹
     */
    Map<String, Object> queryChangeTrajectory(String customerId, String empId, Integer rows);

    /**
     * 查询待发布的人员信息
     */
    PaginationDto<TalentMapsDto> queryTalentPublish(String customerId, String topId, String adjustmentId, Integer yearMonth, String keyName, int flag, PaginationDto<TalentMapsDto> d);

    /**
     * 查询已调整的人员信息-全部
     *
     * @param customerId
     * @param topId
     * @param adjustmentId
     * @param flag
     * @return
     */
    List<TalentMapsDto> queryTalentAdjustmentInfo(String customerId, String topId, String adjustmentId, Integer yearMonth, int flag);

    /**
     * 查询已发布的人员信息
     *
     * @param customerId
     * @param topId
     * @param releaseId
     * @param flag
     * @param d
     * @return
     */
    PaginationDto<TalentMapsDto> queryTalentPublishInfo(String customerId, String topId, String releaseId, Integer yearMonth, int flag, PaginationDto<TalentMapsDto> d);

    /**
     * 查询时间机构
     *
     * @param customerId
     * @param topOrgan
     * @return
     */
    Map<String, Object> queryTalentMapsTime(String customerId, List<OrganDto> topOrgan);

    /**
     * 查询待审核的人才地图
     */
    List<TalentMapsDto> queryMapsAuditing(String customerId, List<OrganDto> topOrgan, String empId, String updateTime);

    /***
     * 查询机构相关地图人员统计
     * @param organs
     * @param customerId
     * @return
     */
    List<TalentMapsEmpCountDto> queryOrganMapsEmpCount(List<TalentMapsEmpCountDto> organs, String yearMonths, String customerId);

    /**
     * 查询待审核的人员信息
     */
    List<TalentMapsDto> checkEmpInfo(String customerId, String keyName);

    /**
     * 查询地图坐标
     */
    List<TalentMapsDto> queryMapsPreview(String customerId, String topId, String flag, Integer yearMonth);

    /**
     * 审核
     */
    List<TalentMapsDto> updateAuditingInfo(List<TalentMapsDto> listDto, String customerId, String empId, String userId, String modifyTime,
                                           String topId, String fullPath, String publishTime, List<OrganDto> topOrgan);

    /**
     * 发布
     */
    List<TalentMapsDto> pulishMapsManagement(List<TalentMapsDto> listDto, String customerId, String empId, String releaseTime, String topId, String publishTime);

    /**
     * 获取人才地图时间周期
     *
     * @param organIds
     * @param customerId
     * @return
     */
    List<KVItemDto> queryTalentMapsDateCycle(List<String> organIds, String customerId);

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
    PaginationDto<TalentMapsPointDto> queryMapsEmpPoint(TalentMapsTeamQueryDto queryDto, PaginationDto<TalentMapsPointDto> dto);
    
    /**
     * 获取地图相关员工点信息-趋势图用
     *
     * @param dto
     * @return
     */
    PaginationDto<TalentMapsPointDto> queryMapsEmpPointStr(TalentMapsTeamQueryDto queryDto, PaginationDto<TalentMapsPointDto> dto);

    /**
     * 根据查询条件统计人员信息
     *
     * @param dto
     * @return
     */
    int queryMapsSettingEmpCount(TalentMapsTeamQueryDto dto);

    /**
     * 获取地图相关员工点信息
     *
     * @param dto
     * @return
     */
    List<TalentMapsPointDto> queryTeamEmpPoint(TalentMapsTeamQueryDto dto);

    /**
     * 存储用户团队信息（多个）
     *
     * @param list
     */
    void insertMapsTeams(List<TalentMapsTeamInfoDto> list, String empId, String customerId);


    /**
     * 获取用户保存团队信息
     *
     * @param teamId
     * @param empId
     * @param customerId
     * @return
     */
    int findUserTeamNum(String teamId,String empId, String customerId);

    /**
     * 移除用户存储团队信息
     *
     * @param empId
     * @param teamId
     * @param customerId
     */
    void deleteUserTeamInfo(String empId, String teamId, String customerId);

    /**
     * 存储用户团队信息(单个)
     *
     * @param dto
     */
    void insertUserTeam(TalentMapsTeamInfoDto dto);

    /**
     * 修复用户团队信息
     *
     * @param dto
     */
    void updateUserTeam(TalentMapsTeamInfoDto dto);

    /**
     * 获取用户团队信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<TalentMapsTeamInfoDto> queryUserTeamInfo(String empId, String customerId);

    /**
     * 获取人才地图基础信息
     *
     * @param customerId
     * @return
     */
    Map<String, Object> queryMapsBaseInfo(String customerId);

    /**
     * 获取人才地图基础信息-能力/绩效/时间
     *
     * @param customerId
     * @return
     */
    Map<String, Object> getMapsBaseInfoForTeamCP(String customerId, String date, String viewType);

    /**
     * 获取z轴纬度
     *
     * @param customerId
     * @return
     */
    List<TalentMapsDto> queryZInfo(String customerId);

    int queryTalentPerformanceAbilityCount(String customerId, String topId, int flag, String zName, Integer yearMonth);

    /**
     * 查询历史审核列表
     */
    List<TalentMapsDto> queryHistoricalAuditing(String customerId, List<OrganDto> topOrgan, String empId);

    /**
     * 查询历史
     */
    TalentMapsDto queryMapManage(String customerId, String id, String topId, Integer yearMonth);

    /**
     * 查询能力信息
     * return
     * curtName	abilityNumberName
     */
    List<TalentMapsDto> queryAbilityInfo(String customerId);

    /**
     * 查询绩效信息
     * return
     * curtName	performanceName
     */
    List<TalentMapsDto> queryPerformanceInfo(String customerId);

    /**
     * 检查是否有发布地图权限
     *
     * @param customerId
     * @param topId
     * @param empId
     * @param time
     * @return
     */
    int checkPublishPermission(String customerId, String topId, String empId, String time);
}
