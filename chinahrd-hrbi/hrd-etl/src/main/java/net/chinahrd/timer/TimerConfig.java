/**
*net.chinahrd.core.timer.model
*/
package net.chinahrd.timer;


import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**
 * 定时任务配置
 * @author htpeng
 *2017年4月11日下午5:58:19
 */
public class TimerConfig {

	
	/**
	 * 定时器的调度规则
	 */
	private  String cron =null;
	
	
	private  TriggerKey triggerKey =null;
	
	private  JobKey jobKey =null;
	private  Class<DefaultJob> jobClass =DefaultJob.class;
	
	
	public TimerConfig(){
	}


	public TimerConfig(String cron){

		this.cron=cron;
	}


	/**
	 * @return the pattern
	 */
	public String getCron() {
		return cron;
	}

	/**
	 * 设置调度规则
	 * @param pattern the pattern to set
	 */
	public void setCron(String cron) {
		this.cron = cron;
	}



	/**
	 * @return the triggerKey
	 */
	public TriggerKey getTriggerKey() {
		return triggerKey;
	}

	/**
	 * @param triggerKey the triggerKey to set
	 */
	public void setTriggerKey(TriggerKey triggerKey) {
		this.triggerKey = triggerKey;
	}

	/**
	 * @return the jobKey
	 */
	public JobKey getJobKey() {
		return jobKey;
	}

	/**
	 * @param jobKey the jobKey to set
	 */
	public void setJobKey(JobKey jobKey) {
		this.jobKey = jobKey;
	}

	/**
	 * @return the jobClass
	 */
	public Class<? extends Job> getJobClass() {
		return this.jobClass;
	}
//
//	/**
//	 * @param jobClass the jobClass to set
//	 */
//	public void setJobClass(Class<? extends Job> jobClass) {
//		this.jobClass = jobClass;
//	}  
	
}
