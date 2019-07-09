package net.chinahrd.db.input.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("dbImportPerBenefitDao")
public interface DbImportPerBenefitDao {

	void callTargetBenefitValue(Map<String, Object> mp);
	
	/**
	 * 每月第一天，会抽取上一个月的营业利润
	 * 
	 * @param mp
	 */
	void callTradeProfit(Map<String, Object> mp);

	/**
	 * 每月等效全职员工数
	 * 
	 * @param mp
	 */
	void callFactfte(Map<String, Object> mp);

	
	
}
