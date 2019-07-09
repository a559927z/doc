package net.chinahrd.monthReport.mvc.pc.dao;

import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.pc.monthReport.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 月报dao接口
 * Created by wqcai on 16/08/29 029.
 */
@Repository("monthReportDao")
public interface MonthReportDao {

    /**
     * 获取人员异动信息（按组织）
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportChangesDto> queryChangesToSubOrgan(@Param("organId") String organId, @Param("yearMonth") String yearMonth, @Param("customerId") String customerId);

    /**
     * 获取人员异动信息（按职级）
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportChangesDto> queryChangesToAbility(@Param("organId") String organId, @Param("yearMonth") String yearMonth, @Param("customerId") String customerId);

    /**
     * 统计异动人员明细
     *
     * @param changesPos
     * @param itemId
     * @param organId
     * @param customerId
     * @return
     */
    int queryChangesDetailsCount(@Param("changesPos") Integer changesPos, @Param("itemId") String itemId, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId);

    /**
     * 获取异动人员明细
     *
     * @param changesPos
     * @param organId
     * @param customerId
     * @return
     */
    List<MonthReportChangesDetailDto> queryChangesDetails(@Param("changesPos") Integer changesPos, @Param("itemId") String itemId, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId, RowBounds rb);

    /**
     * 统计离职人员信息
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    int queryDimissionEmpsCount(@Param("isKeyTalent") Integer isKeyTalent, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId);

    /**
     * 获取离职人员信息
     *
     * @param isKeyTalent
     * @param organId
     * @param customerId
     * @return
     */
    List<DismissRiskDto> queryDimissionEmpsList(@Param("isKeyTalent") Integer isKeyTalent, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId, RowBounds rb);
    
    List<DismissRiskDto> queryDimissionEmpsListNoPage(@Param("isKeyTalent") Integer isKeyTalent, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId);

    /**
     * 获取培训概况信息
     *
     * @param organId
     * @param year
     * @param customerId
     * @return
     */
    List<MonthReportTrainGeneralDto> queryTrainGeneral(@Param("organId") String organId, @Param("year") Integer year, @Param("customerId") String customerId);


    /**
     * 获取在职人员分布统计
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<MonthReportEmpCountDto> queryInJobsEmpCount(@Param("organId") String organId, @Param("customerId") String customerId);

    /**
     * 获取各商品的销售情况
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportSalesCountDto> querySalesCountByProduct(@Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId);

    /**
     * 获取各组织机构的销售情况
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportSalesCountDto> querySalesCountByOrgan(@Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId);

    /**
     * 获取各商品每月的销售情况
     *
     * @param organId
     * @param beginTime
     * @param endTime
     * @param customerId
     * @return
     */
    List<MonthReportSalesCountDto> queryProductSalesCountByMonth(@Param("itemId") String itemId, @Param("organId") String organId, @Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, @Param("customerId") String customerId);

    /**
     * 获取各组织机构每月的销售情况
     *
     * @param organId
     * @param beginTime
     * @param endTime
     * @param customerId
     * @return
     */
    List<MonthReportSalesCountDto> queryOrganSalesCountByMonth(@Param("organId") String organId, @Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, @Param("customerId") String customerId);

    /**
     * 保存收藏相关信息
     *
     * @param savaDto
     */
    void insertFavorites(MonthReportSavaDto savaDto);

    /**
     * 查询用户收藏相关信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<MonthReportSavaDto> queryFavorites(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 查询别人给我分享的信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<MonthReportShareDto> queryShare(@Param("empId") String empId, @Param("customerId") String customerId, @Param("yearMonth") Integer yearMonth);

    /**
     * 检查相关月报是否存在，存在则放回相关ID
     *
     * @param empId
     * @param yearMonth
     * @param customerId
     * @return
     */
    MonthReportSavaDto findFavoritesInfo(@Param("empId") String empId, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth, @Param("customerId") String customerId);

    /**
     * 移除用户收藏信息
     *
     * @param favoritesId
     * @param customerId
     */
    void deleteFavorites(@Param("favoritesId") String favoritesId, @Param("customerId") String customerId);
    
    /**
     * 获取月报的用户配置信息
     * @param customerId
     * @param empId
     * @return
     */
    List<MonthReportConfigDto> queryMonthReportConfig(@Param("customerId") String customerId, @Param("empId") String empId);
    
    /**
     * 更新月报的用户配置信息 
     * @param list
     */
    void updateMonthReportConfig(List<MonthReportConfigDto> list);
    
    /**
     * 获取晋级分析概况
     * @param customerId
     * @param organId
     * @param yearMonth
     * @return
     */
    List<MonthReportPromotionDto> queryPromotionGeneral(@Param("customerId") String customerId, @Param("organId") String organId, @Param("yearMonth") Integer yearMonth);
    
    List<MonthReportShareDto> checkEmpInfo(@Param("customerId") String customerId, @Param("keyName") String keyName);
    
    /**
     * 添加分享信息
     * @param map
     */
    void addMonthReportShare(Map<String, Object> map);
    
    String queryEmpEmail(@Param("customerId") String customerId, @Param("empId") String empId);
    
    /**
     * 删除分享
     * @param customerId
     * @param shareId
     */
    void deleteMonthReportShare(@Param("customerId") String customerId, @Param("shareId") String shareId);
    
    /**
     * 流失率分析
     * @param map
     * @return
     */
    List<MonthReportDimissionEmpDto> accordDismissAnalysis(Map<String, Object> map);
    
    /**
     * 流失率年度趋势
     * @param customerId
     * @param organId
     * @param year
     * @return
     */
    List<MonthReportDimissionEmpDto> accordDismissByYearMonth(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);
    
    /**
     * 全年流失率
     * @param map
     * @return
     */
    Double accordDismissInYear(Map<String, Object> map);
    
    /**
     * 人力成本概况
     * @param map
     * @return
     */
    List<MonthReportManpowerCostDto> manpowerCostInfo(Map<String, Object> map);
}
