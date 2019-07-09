package net.chinahrd.empAttendance.module;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.DateUtil;

/**
 * 每天晚上抽取考勤月表数据
 * 
 * @author jxzhang on 2016年12月20日
 * @Verdion 1.0 版本
 */
public class MonthTableJob extends JobRegisterAbstract {

	@Injection
	MonthTableDao monthTableDao;

	@Override
	public void execute(JobContext context) {
		String customerId = EisWebContext.getCustomerId();
		monthTableDao.callEmpAttendMonth(customerId, "SYSTEM", null,
				DateUtil.convertToIntYearMonth(DateUtil.getDBCurdate()));
	}

	/**
	 * 定时器
	 */
	@Override
	public void setTimerConfig(TimerConfig tc) {
		 tc.setCron("0 0 6 * * ?"); // 每天早上6点
	}

}
