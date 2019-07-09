package net.chinahrd.employeePerformance.module;

import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;

import org.apache.ibatis.annotations.Param;

public interface CacheDao {

	/**
	 * 加班预警周期内有效工作日期
	 * 
	 * @return
	 */
	Integer queryAvailabilityDayNum(@Param("customerId") String customerId,
			@Param("curdate") String curdate, @Param("key") String key);

	
	/**
	 * 关键人才类型表
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> queryKeyTalentType(@Param("customerId") String customerId);
	
	/**
	 * 查询已有记录的上一个季度最后一天
	 * @param customerId
	 * @return
	 */
	String queryQuarterLastDay(@Param("customerId") String customerId);
}
