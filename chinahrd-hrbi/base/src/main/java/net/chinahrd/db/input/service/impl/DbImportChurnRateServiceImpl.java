package net.chinahrd.db.input.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.db.input.Entity.churnRate.MonthlyEmpCountEntity;
import net.chinahrd.db.input.dao.DbImportChurnRateDao;
import net.chinahrd.db.input.service.DbImportChurnRateService;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.DateUtil;

/**
 * 流失率
 * 
 * @author jxzhang on 2016-03-04
 *
 */
@Transactional
@Service("dbImportChurnRateServiceImpl")
public class DbImportChurnRateServiceImpl implements DbImportChurnRateService {

	@Autowired
	public DbImportChurnRateDao dbImportChurnRateDao;


	@SuppressWarnings("unused")
	@Transactional
	@Override
	public boolean replaceMonthlyEmpCount() {
		Date start = new Date();
		String text ="";
		String customer_id = EisWebContext.getCustomerId();
		String dbNow = DateUtil.getDBNow().split(" ")[0]; //"2015-11-30";
		int year_month = DateUtil.convertToIntYearMonth(new DateTime(dbNow));
		
		boolean bm = DateUtil.isBeginMonth(new DateTime(dbNow));
		try {
			// 月头
			if (bm) {
				dbImportChurnRateDao.deleteMonthlyEmpCount(customer_id,
						year_month);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("customer_id", customer_id);
				param.put("day1", dbNow);
				param.put("year_month", year_month);
				dbImportChurnRateDao.replaceMonthlyEmpCountByBeginMonth(param);
				text = "【流失率-月初-"+ dbNow +"】：数据刷新完成。操作用户：System-Event";
			} else {
			// 昨天
				String first_day = new DateTime(dbNow).dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
				
				List<MonthlyEmpCountEntity> dataList = dbImportChurnRateDao.queryMonthlyEmpCount(customer_id, first_day, dbNow, year_month);
				
				for (MonthlyEmpCountEntity data : dataList) {
					String organKey = data.getOrganization_full_path();
					int act_count_sum = 0;
					int un_act_count_sum = 0;
					for (MonthlyEmpCountEntity matchData : dataList) {
						if(matchData.getOrganization_full_path().contains(organKey)){
//							System.out.println(organKey + "  " +matchData.getOrganization_full_path());
							act_count_sum = act_count_sum + matchData.getAct_count();
							un_act_count_sum = un_act_count_sum + matchData.getUn_act_count();
						}
					}
					data.setAct_count_sum(act_count_sum);
					data.setUn_act_count_sum(un_act_count_sum);
					data.setRefreshStr(dbNow);
				}
				dbImportChurnRateDao.updateMonthlyEmpCountByYesterday(dataList);
				
				text = "【流失率-昨天-"+ dbNow +"】：数据刷新完成。操作用户：System-Event";
			}

		} catch (Exception e1) {
		
			return false;
		}
	
		return true;
	}

}
