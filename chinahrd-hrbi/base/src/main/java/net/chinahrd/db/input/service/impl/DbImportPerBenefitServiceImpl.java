package net.chinahrd.db.input.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.db.input.dao.DbImportBaseDao;
import net.chinahrd.db.input.dao.DbImportPerBenefitDao;
import net.chinahrd.db.input.enums.LogCode;
import net.chinahrd.db.input.service.DbImportPerBenefitService;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.DateUtil;

/**
 * 人均效益
 * 
 * @author jxzhang on 2016-03-18
 *
 */
@Transactional
@Service("dbImportPerBenefitServiceImpl")
public class DbImportPerBenefitServiceImpl implements DbImportPerBenefitService {

	@Autowired
	private DbImportPerBenefitDao dbImportPerBenefitDao;
	@SuppressWarnings("unused")
	@Autowired
	private DbImportBaseDao dbImportBaseDao;
	
	@Transactional
	@Override
	public boolean callTargetBenefitValue() {
		if(!(DateUtil.isBeginMonth(DateUtil.getDate()))){
			return false;
		}
		
		//明年时间
		Date nextYear = new DateTime(DateUtil.getDate()).plusYears(1).toDate();
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
		mp.put("p_nextYear", nextYear);
		
		return callTempletMethod("callTargetBenefitValue", mp,
				LogCode.TARGET_BENEFIT_VALUE.getModule(),
				LogCode.TARGET_BENEFIT_VALUE.getValue(),
				LogCode.TARGET_BENEFIT_VALUE.getShowIndex());
	}
	
	@Transactional
	@Override
	public boolean callTradeProfit() {
		if(!(DateUtil.isBeginMonth(DateUtil.getDate()))){
			return false;
		}
		// 每月第一天，会抽取上一个月的soure_trade_profit
		int lastYm = DateUtil.convertToIntYearMonth(DateUtil.getDate());
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
		mp.put("p_last_curdate", lastYm);
		
		return callTempletMethod("callTradeProfit", mp,
				LogCode.TRADE_PROFIT.getModule(),
				LogCode.TRADE_PROFIT.getValue(),
				LogCode.TRADE_PROFIT.getShowIndex());
	}
	
	
	@Transactional
	@Override
	public boolean callFactfte() {
		if(!(DateUtil.isBeginMonth(DateUtil.getDate()))){
			return false;
		}
		// 每月第一天，会抽取上一个月的fact_fte
		Date lastDate = DateUtil.getCalculateDate(DateUtil.getDate(), -1);
//		Date lastDate = DateUtil.strToDate("2015-11-30");
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
		mp.put("p_nowTime", DateUtil.dateToTimestamp(lastDate));
		
		return callTempletMethod("callFactfte", mp,
				LogCode.FACT_FTE.getModule(),
				LogCode.FACT_FTE.getValue(),
				LogCode.FACT_FTE.getShowIndex());
	}
	
	
	@SuppressWarnings("unused")
	private boolean callTempletMethod(String MethodName, Map<String, Object> mp,
			String module, String value, int showIndex) {

		Date start = new Date();
		try {
			Method method = dbImportPerBenefitDao.getClass().getMethod(MethodName,
					new Class[]{Map.class});
			method.invoke(dbImportPerBenefitDao, mp);

		} catch (Exception e1) {
			e1.printStackTrace();
			String useTime = DateUtil.getInterval(start, new Date());
			return false;
		}
		String useTime = DateUtil.getInterval(start, new Date());
	
		return true;
	}

}
