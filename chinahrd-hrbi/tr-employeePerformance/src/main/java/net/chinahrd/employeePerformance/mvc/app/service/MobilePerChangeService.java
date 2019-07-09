package net.chinahrd.employeePerformance.mvc.app.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCardDto;
import net.chinahrd.entity.dto.app.performance.PreChangeStatusDto;
import net.chinahrd.entity.dto.app.performance.PreDetailDto;


/**
 * 员工绩效 app
 * @author htpeng
 *2016年6月6日上午11:13:04
 */
public interface MobilePerChangeService {
	
	
	/**
	 * 根据cunstonerId查询绩效时间列表
	 * @param customerId
	 * @param perWeek
	 * @return
	 */
	List<Integer> queryPreYearMonthByCustomerId(String customerId,Integer perWeek);
	
	/**
	 * 查询两次绩效变化的人总数
	 * @param organizationId 部门id
	 * @param beginYearMonth 开始的绩效时间
	 * @param endYearMonth 结束的绩效时间
	 * @param changeNum 绩效变化的级别 比如：1--3 变化为2
	 * @param customerId TODO
	 * @return
	 */
	PreChangeStatusDto queryPreChangeCountByMonth(String organizationId,
			Integer beginYearMonth, Integer endYearMonth,Integer changeNum, String customerId);
	
	
	/**
	 * 查询绩效星级统计
	 * @param organizationId
	 * @param yearMonth
	 * @param customerId TODO
	 * @return
	 */
	Map<String,Object>queryPreStarCountByMonth( String organizationId, String yearMonth, String customerId);
	
	/**
	 * 绩效详情
	 * @param organizationId
	 * @param trim
	 * @param customerId
	 * @return
	 */
	PaginationDto<PreDetailDto> queryPerformanceDetail(String customerId,String organizationId,PaginationDto<PreDetailDto> dto,
			String yearMonth);
	

	PerfChangeCardDto queryHighLowPreByMonth(String organizationId,
			Integer yearMonth,String customerId);
}
