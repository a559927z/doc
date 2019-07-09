package net.chinahrd.db.input.service;

public interface DbImportPerBenefitService {

	/**
	 * 每月营业利润 <br>
	 * 每月第一天，会抽取上一个月的营业利润 <br>
	 * 今天 = 2016-03-01，抽取上一个月 = 201602
	 * 
	 * @return
	 */
	boolean callTradeProfit();

	/**
	 * 等效员工<br>
	 * 每月第一天，会抽取上一个月的营业利润 <br>
	 * 今天 = 2016-03-01，抽取上一个月 = 201602
	 * 
	 * @return
	 */
	boolean callFactfte();

	/**
	 * 明年目标人均效益<br>
	 * 
	 * @return
	 */
	boolean callTargetBenefitValue();

	

}
