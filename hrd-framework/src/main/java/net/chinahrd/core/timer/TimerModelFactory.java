/**
*net.chinahrd.core.timer
*/
package net.chinahrd.core.timer;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;

import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.core.timer.model.TimerModel;

/**定时器模板工厂
 * @author htpeng
 *2016年11月10日下午4:43:13
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
