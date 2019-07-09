/**
*net.chinahrd.core.timer.model
*/
package net.chinahrd.core.timer.model;

import java.util.List;

import net.chinahrd.core.timer.Timer;
import net.chinahrd.core.timer.TimerStack;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * 定时器模板
 * @author htpeng
 *2016年11月8日下午4:30:19
 */
public class TimerModel {
	private JobDetail jobDetail;
	private Trigger trigger;
	private String cron;
	private  TimerStack ts=new TimerStack();
	/**
	 * @return the jobDetail
	 */
	public JobDetail getJobDetail() {
		return jobDetail;
	}
	/**
	 * @param jobDetail the jobDetail to set
	 */
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
	/**
	 * @return the trigger
	 */
	public Trigger getTrigger() {
		return trigger;
	}
	/**
	 * @param trigger the trigger to set
	 */
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	public TimerModel(JobDetail jobDetail, Trigger trigger,String cron) {
		super();
		this.jobDetail = jobDetail;
		this.trigger = trigger;
		this.cron=cron;
	}
	public  void setTimer(Timer timer) {
		 ts.put(timer);
	}

	
	public List<Timer> getTimers() {
		return ts.get();
	}

	public TimerStack getTimerStack() {
			return ts;
	}
	/**
	 * @return the cron
	 */
	public String getCron() {
		return cron;
	}
	/**
	 * @param cron the pattern to set
	 */
	public void seCron(String cron) {
		this.cron = cron;
	} 
	
}
