package net.chinahrd.homepage.module;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.chinahrd.entity.dto.KVItemDto;

public interface CacheDao {

	/**
	 * 加班预警周期内有效工作日期
	 * 
	 * @return
	 */
	Integer queryAvailabilityDayNum(@Param("customerId") String customerId,
			@Param("curdate") String curdate, @Param("key") String key);

	
	
}
