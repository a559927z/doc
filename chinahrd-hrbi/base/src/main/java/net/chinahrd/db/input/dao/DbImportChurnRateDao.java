package net.chinahrd.db.input.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.db.input.Entity.churnRate.MonthlyEmpCountEntity;

@Repository("dbImportChurnRateDao")
public interface DbImportChurnRateDao {

	void deleteMonthlyEmpCount(@Param("customer_id") String customer_id,
			@Param("year_month") int year_month);

	void replaceMonthlyEmpCountByBeginMonth(
			Map<String, Object> param);
	
	
	List<MonthlyEmpCountEntity> queryMonthlyEmpCount(@Param("customer_id") String customer_id,
			@Param("first_day") String first_day,
			@Param("dbNow") String dbNow,
			@Param("year_month") int year_month);

	
	void updateMonthlyEmpCountByYesterday(
			@Param("list") List<MonthlyEmpCountEntity> list);
	
}
