package net.chinahrd.employeePerformance.mvc.pc.dao;


import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeEmpDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeQueryDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreDetailDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreStarCountDto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * 绩效dao
 */
@Repository("perChangeDao")
public interface PerChangeDao {


    /**
     * 查询单月份绩效人员统计
     * @param map
     * @return
     */
    KVItemDto queryHighLowMonthCount(Map<String, Object> map);

    /**
     * 查询连续月份绩效人员统计
     * @param map
     * @return
     */
    KVItemDto queryHighLow2MonthCount(Map<String, Object> map);


    List<PerfChangeEmpDto> queryHighLowPreByMonth2(PerfChangeQueryDto dto);

    /**
     * 绩效详情
     *
     * @return
     */
    List<PerfChangeEmpDto> queryHighLowPreByMonth(Map<String, Object> map, RowBounds rowBounds);

    /**
     * 根据cunstonerId查询绩效时间列表
     *
     * @param customerId
     * @param perWeek
     * @return
     */
    List<Integer> queryPreYearMonthByCustomerId(@Param("customerId") String customerId, @Param("perWeek") Integer perWeek);

    /**
     * 查询两次绩效变化的人总数
     *
     * @param organizationId 部门id
     * @param beginYearMonth 开始的绩效时间
     * @param endYearMonth   结束的绩效时间
     * @param changeNum      绩效变化的级别 比如：1--3 变化为2
     * @param customerId     TODO
     * @param preWeek        TODO
     * @return
     */
    PreChangeStatusDto queryPreChangeCountByMonth(
            @Param("organizationId") String organizationId,
            @Param("beginYearMonth") Integer beginYearMonth,
            @Param("endYearMonth") Integer endYearMonth,
            @Param("changeNum") Integer changeNum,
            @Param("customerId") String customerId,
            @Param("preWeek") Integer preWeek,
            @Param("crowds") List<String> crowds);

    /**
     * 查询两次绩效变化的人列表
     *
     * @param organizationId 部门id
     * @param beginYearMonth 开始的绩效时间
     * @param endYearMonth   结束的绩效时间
     * @param changeType     绩效变化类型 业务待定
     * @return
     */
    List<PerfChangeEmpDto> queryPreChangeListByMonth(
            @Param("organizationId") String organizationId,
            @Param("beginYearMonth") Integer beginYearMonth,
            @Param("endYearMonth") Integer endYearMonth,
            @Param("changeType") Integer changeType);

    /**
     * 获取绩效分布的高低绩效统计
     * @return {type:1-低绩效 2-高绩效,total:统计人数}
     */
    List<PreStarCountDto> queryPerfDistributeCount(PerfChangeQueryDto dto);

    /**
     * 绩效分布人员明细
     * @param map
     * @param rowBounds
     * @return
     */
    List<PerfChangeEmpDto> queryPerfDistributeEmp(Map<String, Object> map, RowBounds rowBounds);

    /**
     * 查询绩效分布
     *
     * @param dto 绩效相关查询类
     * @return
     */
    List<PerfChangeEmpDto> queryPreDistributionListByMonth(PerfChangeQueryDto dto);

    /**
     * 查询绩效星级统计
     *
     * @param organizationId
     * @param yearMonth
     * @param customerId     TODO
     * @param preWeek        TODO
     * @return
     */
    List<PreStarCountDto> queryPreStarCountByMonth(
            @Param("organizationId") String organizationId,
            @Param("yearMonth") Integer yearMonth,
            @Param("customerId") String customerId, @Param("preWeek") Integer preWeek,
            @Param("crowds") List<String> crowds);


    int queryPerformanceDetailCount(Map<String, Object> mapParam);

    List<PreDetailDto> queryPerformanceDetail(Map<String, Object> mapParam);


    int queryPerformanceDetailByNameCount(Map<String, Object> mapParam);

    List<PreDetailDto> queryPerformanceDetailByName(Map<String, Object> mapParam);

    Integer queryEmpCount(Map<String, Object> mapParam);
    /*Integer queryEmpCount(@Param("customerId") String customerId,
                          @Param("organizationId") String organizationId,
                          @Param("yearMonth") String yearMonth,
                          @Param("time") String time,
                          @Param("empType") String empType,
                          @Param("crowds") List<String> crowds);*/
    
    List<PerfChangeCountDto> queryPerchangeByOrgan(@Param("customerId") String customerId,
    		@Param("organId") String organId,
            @Param("yearMonth") Integer yearMonth);

}
