/**
*net.chinahrd.core.timer
*/
package net.chinahrd.timer;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;



/**
 * 
 * @author htpeng
 *2017年4月11日下午5:58:35
 */
public class TimerModelFactory {
	static TimerModel create(TimerConfig timerConfig){
		return new TimerModel(JobBuilder.newJob(timerConfig.getJobClass())
                .withIdentity(timerConfig.getJobKey())
                .requestRecovery()
                .build(),
                TriggerBuilder
                .newTrigger()
                .withIdentity(timerConfig.getTriggerKey())
                .withSchedule(CronScheduleBuilder.cronSchedule(timerConfig.getCron()))
                .startNow()
                .build(),timerConfig.getCron());
	}
}
