/**
*net.chinahrd.core.timer
*/
package net.chinahrd.core.timer;


import javax.servlet.ServletConfig;

import net.chinahrd.core.timer.model.TimerModel;
import net.chinahrd.core.web.init.DataInitialization;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**系统定时器初始化
 * @author htpeng
 *2016年11月8日下午2:15:21
 */
public class TimerInitialization implements DataInitialization{

	/* (non-Javadoc)
	 * @see net.chinahrd.core.web.init.DataInitialization#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) {
	       //创建LzstoneTimeTask的定时任务
		try {
       	 Scheduler scheduler=new StdSchedulerFactory().getScheduler();
       	 for(TimerModel timerModel:TimerManagerCenter.getTimerModelList()){
       		 scheduler.scheduleJob(timerModel.getJobDetail(),timerModel.getTrigger());
       	 }
       	 scheduler.startDelayed(1000);
       	 scheduler.start();
       	 TimerManagerCenter.setScheduler(scheduler);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
