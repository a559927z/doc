/**
*net.chinahrd.core.timer.model
*/
package net.chinahrd.core.timer.model;

import net.chinahrd.core.timer.DefaultJob;
import net.chinahrd.core.timer.Timer;

import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**定时任务配置
 * @author htpeng
 *2016年11月10日下午12:31:16
 */
public class TimerConfig {
	/**
	 * 优先级
	 */
	private  int priority =Timer.NORM_PRIORITY;  
	
	/**
	 * 是否绑定定时器
	 */
	private  boolean bindTimer =false;
	/**
	 * 是否懒加载
	 */
	private  boolean lazy =true;  
	
	/**
	 * 定时器的调度规则
	 */
	private  String cron =null;
	
	
	private  TriggerKey triggerKey =null;
	
	private  JobKey jobKey =null;
	private  Class<DefaultJob> jobClass =DefaultJob.class;
	
	
	public TimerConfig(){
	}
	public TimerConfig(int priority){
		this(priority,null);
	}
	public TimerConfig(boolean lazy){
		this(null,lazy);
	}
	
	public TimerConfig(String pattern){
		this(Timer.NORM_PRIORITY,pattern);
	}
	
	public TimerConfig(int priority,String cron){
		if(null!=cron){
			bindTimer=true;
		}
		this.priority=priority;
		this.cron=cron;
		
	}
	public TimerConfig(String cron,boolean lazy){
		if(null!=cron){
			bindTimer=true;
		}
		this.cron=cron;
		this.lazy=lazy;
		
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the lazy
	 */
	public boolean isLazy() {
		return lazy;
	}

	/**
	 * @param lazy the lazy to set
	 */
	public void setLazy(boolean lazy) {
		this.lazy = lazy;
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
		if(null!=cron){
			bindTimer=true;
		}
		this.cron = cron;
	}

	/**
	 * @return the bindTimer
	 */
	public boolean isBindTimer() {
		return bindTimer;
	}

	/**
	 * @param bindTimer the bindTimer to set
	 */
	public void setBindTimer(boolean bindTimer) {
		this.bindTimer = bindTimer;
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
