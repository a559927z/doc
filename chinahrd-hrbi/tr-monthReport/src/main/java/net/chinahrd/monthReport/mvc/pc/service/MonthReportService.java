package net.chinahrd.monthReport.mvc.pc.service;

import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;
import net.chinahrd.entity.dto.pc.monthReport.*;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * 月报Service接口
 * Created by wqcai on 16/08/24 024.
 */
public interface MonthReportService {

    /**
     * 获取用户相关配置信息
     *
     * @param functionId
     * @param empId
     * @param customerId
     * @return
     */
    List<HomeConfigDto> queryUserConfig(String functionId, String empId, String customerId);

    /**
     * 获取人员异动信息（按组织）
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportChangesDto> queryChangesToSubOrgan(String organId, String yearMonth, String customerId);
    
    /**
     * 获取人员异动信息（按职级）
     *
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportChangesDto> queryChangesToAbility(String organId, String yearMonth, String customerId);


    /**
     * 获取异常详情信息
     *
     * @param itemId
     * @param organId
     * @param pos
     * @param customerId
     * @param dto
     * @return
     */
    PaginationDto<MonthReportChangesDetailDto> queryChangesDetails(String itemId, String organId, String pos, Integer yearMonth, String customerId, PaginationDto dto);

    /**
     * 关键人才离职情况
     *
     * @param organId
     * @param customerId
     * @param dto
     * @return
     */
    PaginationDto<DismissRiskDto> queryDimissionEmpsList(String organId, Integer yearMonth, String customerId, PaginationDto<DismissRiskDto> dto);
    
    public List<DismissRiskDto> queryDimissionEmpsListNoPage(String organId, Integer yearMonth, String customerId);

    /**
     * 获取培训概况信息
     *
     * @param organId
     * @param year
     * @param customerId
     * @return
     */
    List<MonthReportTrainGeneralDto> queryTrainGeneral(String organId, Integer year, String customerId);

    /**
     * 获取在职人员分布统计
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<MonthReportEmpCountDto> queryInJobsEmpCount(String organId, String customerId);

    /**
     * 获取销售情况
     *
     * @param type
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    List<MonthReportSalesCountDto> querySalesCount(Integer type, String organId, Integer yearMonth, String customerId);

    /**
     * 获取获取每月销售情况
     *
     * @param type
     * @param organId
     * @param beginTime
     * @param endTime
     * @param customerId
     * @return
     */
    List<MonthReportSalesCountDto> querySalesCountByMonth(Integer type, String itemId, String organId, Integer beginTime, Integer endTime, String customerId);

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
    MultiValueMap<Integer, MonthReportSavaDto> queryFavorites(String empId, String customerId);


    /**
     * 查询别人分享给我的信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    MultiValueMap<Integer, MonthReportShareDto> queryShare(String empId, String customerId);

    /**
     * 检查相关月报是否存在，存在则放回相关ID
     *
     * @param empId
     * @param organId
     * @param yearMonth
     * @param customerId
     * @return
     */
    MonthReportSavaDto findFavoritesInfo(String empId, String organId, Integer yearMonth, String customerId);

    /**
     * 移除用户收藏信息
     *
     * @param savaId
     * @param customerId
     */
    void deleteFavorites(String savaId, String customerId, String path);
    
    /**
     * 获取月报的用户配置信息
     * @param customerId
     * @param empId
     * @return
     */
    List<MonthReportConfigDto> queryMonthReportConfig(String customerId, String empId);
    
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
    List<MonthReportPromotionDto> queryPromotionGeneral(String customerId, String organId, Integer yearMonth);
    
    List<MonthReportShareDto> checkEmpInfo(String customerId, String keyName);
    
    /**
     * 添加分享信息
     * @param map
     */
    String addMonthReportShare(Map<String, Object> map);
    
    /**
     * 删除分享
     * @param customerId
     * @param shareId
     */
    void deleteMonthReportShare(String customerId, String shareId);
    
    /**
     * 流失率分析
     * @param customerId
     * @param organId
     * @param yearMonth
     * @return
     */
    List<MonthReportDimissionEmpDto> accordDismissAnalysis(String customerId, String organId, String yearMonth);
    
    /**
     * 流失率年度趋势
     * @param customerId
     * @param organId
     * @param year
     * @return
     */
    Map<String, Object> accordDismissByYearMonth(String customerId, String organId, String yearMonth);
    
    /**
     * 全年流失率
     * @param customerId
     * @param organId
     * @param yearMonth
     * @return
     */
    Double accordDismissInYear(String customerId, String organId, String yearMonth);
    
    /**
     * 人力成本概况
     * @param customerId
     * @param organId
     * @param yearMonth
     * @return
     */
    List<MonthReportManpowerCostDto> manpowerCostInfo(String customerId, String organId, String yearMonth);
}
