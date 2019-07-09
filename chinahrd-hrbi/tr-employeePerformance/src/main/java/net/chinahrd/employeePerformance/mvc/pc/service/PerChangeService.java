package net.chinahrd.employeePerformance.mvc.pc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeEmpDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreDetailDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreStarCountDto;


/**
 * 个人绩效及变化
 *
 * @author guanjian
 */
public interface PerChangeService {

    /**
     * 查询某个月份绩效人员统计
     *
     * @return
     */
    KVItemDto queryHighLow2MonthCount(String organId, List<Integer> yearMonths, Boolean iHighLow, String customerId);

    /**
     * 查询某个月份绩效人员统计
     *
     * @return
     */
    KVItemDto queryHighLowMonthCount(String organId, Integer yearMonth, Boolean iHighLow, String customerId);

    /**
     * 查询最近低绩效人员 该方法需要提前查询好yearMonths
     *
     * @param organizationId
     * @param yearMonth      当前绩效
     * @param prevYearMonth  上次绩效
     * @param customerId     TODO
     * @return
     */
    PaginationDto<PerfChangeEmpDto> queryLowPre(String organizationId, Integer yearMonth, Integer prevYearMonth, Boolean isManyPerf, String customerId, PaginationDto<PerfChangeEmpDto> dto);

    /**
     * 查询最近高绩效人员 该方法需要提前查询好yearMonths
     *
     * @param organizationId
     * @param yearMonth      当前绩效
     * @param customerId     TODO
     * @return
     */
    PaginationDto<PerfChangeEmpDto> queryHighPre(String organizationId, Integer yearMonth, Integer prevYearMonth, Boolean isManyPerf, String customerId, PaginationDto<PerfChangeEmpDto> dto);

    /**
     * 根据cunstonerId查询绩效时间列表
     *
     * @param customerId
     * @param perWeek
     * @return
     */
    List<Integer> queryPreYearMonthByCustomerId(String customerId, Integer perWeek);

    /**
     * 查询两次绩效变化的人总数
     *
     * @param organizationId 部门id
     * @param beginYearMonth 开始的绩效时间
     * @param endYearMonth   结束的绩效时间
     * @param changeNum      绩效变化的级别 比如：1--3 变化为2
     * @param customerId     TODO
     * @return
     */
    PreChangeStatusDto queryPreChangeCountByMonth(String organizationId,
                                                  Integer beginYearMonth, Integer endYearMonth, Integer changeNum, String customerId, List<String> crowds);

    /**
     * 获取绩效分布的高低绩效统计
     *
     * @param organizationId
     * @param currYearMonth
     * @param empType
     * @param crowds
     * @param customerId
     * @return {type:1-低绩效 2-高绩效,total:统计人数}
     */
    List<PreStarCountDto> queryPerfDistributeCount(String organizationId, Integer currYearMonth, Integer empType, List<String> crowds, String customerId);


    /**
     * 查询绩效相关人员信息
     * @param organizationId
     * @param yearMonth
     * @param empType
     * @param crowds
     * @param idx
     * @param customerId
     * @param dto
     * @return
     */
    PaginationDto<PerfChangeEmpDto> queryPerfDistributeEmp(String organizationId, Integer yearMonth, Integer empType, List<String> crowds, Integer idx, String customerId, PaginationDto<PerfChangeEmpDto> dto);

    /**
     * 查询绩效分布
     *
     * @param organizationId
     * @param currYearMonth  绩效周期
     * @param prevYearMonth  上次绩效周期
     * @param empType        员工类型 0 全部  1 员工 2 管理者
     * @param customerId     TODO
     * @return
     */
    List<List<PerfChangeEmpDto>> queryPreDistributionListByMonth(String organizationId, Integer currYearMonth, Integer prevYearMonth, Integer empType, String customerId, List<String> crowds);

    /**
     * 查询绩效星级统计
     *
     * @param organizationId
     * @param yearMonth
     * @param customerId     TODO
     * @return
     */
    List<PreStarCountDto> queryPreStarCountByMonth(String organizationId, Integer yearMonth, String customerId, List<String> crowds);

    /**
     * 绩效详情
     *
     * @param organizationId
     * @param trim
     * @param customerId
     * @return
     */
    PaginationDto<PreDetailDto> queryPerformanceDetail(String queryType, String keyName, String customerId, String organizationId, PaginationDto<PreDetailDto> dto,
                                                       String yearMonth, String sequenceId, String abilityId, String performanceKey);

    /**
     * 获取绩效考核期的总人数
     *
     * @return
     */
    Integer queryEmpCount(String customerId, String organizationId,
                          String yearMonth, String empType, List<String> crowds);


    List<KVItemDto> queryHighLowPreByMonth(String organizationId,
                                           Integer yearMonth, Integer prevYearMonth, String customerId);
    
    List<PerfChangeCountDto> queryPerchangeByOrgan(String customerId, String organId, Integer yearMonth);
}
