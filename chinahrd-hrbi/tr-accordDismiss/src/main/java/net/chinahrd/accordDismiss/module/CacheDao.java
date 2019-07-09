package net.chinahrd.accordDismiss.module;



import org.apache.ibatis.annotations.Param;


public interface CacheDao {

	
	/**
	 * 查询已有记录的上一个季度最后一天
	 * @param customerId
	 * @return
	 */
	String queryQuarterLastDay(@Param("customerId") String customerId);
}
