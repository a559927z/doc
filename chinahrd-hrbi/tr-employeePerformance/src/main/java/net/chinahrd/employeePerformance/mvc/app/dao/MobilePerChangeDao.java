package net.chinahrd.employeePerformance.mvc.app.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.performance.PerfChangeCardDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeQueryDto;
import net.chinahrd.entity.dto.app.performance.PreChangeStatusDto;
import net.chinahrd.entity.dto.app.performance.PreDetailDto;
import net.chinahrd.entity.dto.app.performance.PreStarCountDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 员工绩效DAO app
 * @author htpeng
 *2016年6月7日下午4:19:36
 */
@Repository("mobilePerChangeDao")
public interface MobilePerChangeDao {

    /**
     * 查询多次的高低绩效
     *
     *
     * @return
     */
	PerfChangeCardDto queryHighLowPreByMonth(PerfChangeQueryDto dto);


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
            @Param("preWeek") Integer preWeek);

  
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
            @Param("yearMonth") String yearMonth,
            @Param("customerId") String customerId, @Param("preWeek") Integer preWeek);


    int queryPerformanceDetailCount(Map<String, Object> mapParam);

    List<PreDetailDto> queryPerformanceDetail(Map<String, Object> mapParam);


 
    PerfChangeCountDto queryEmpCount(@Param("customerId") String customerId,
                          @Param("organizationId") String organizationId,
                          @Param("yearMonth") String yearMonth);

}
