/**
*net.chinahrd.core.timer.model
*/
package net.chinahrd.core.timer.model;

import org.quartz.JobExecutionContext;

/** 简单的定时器任务上下文
 * 同一定时器的各个任务共享同一个上下文
 * @author htpeng
 *2017年4月19日下午4:58:08
 */
public class SimpleJobContext implements JobContext{
	
	private boolean interrupt=false;
	
	private JobExecutionContext context;
	
	public SimpleJobContext(JobExecutionContext context) {
		this.context=context;
	}
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.model.JobContext#getJobExecutionContext()
	 */
	@Override
	public JobExecutionContext getJobExecutionContext() {
		// TODO Auto-generated method stub
		return this.context;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.model.JobContext#interrupt()
	 */
	@Override
	public boolean interrupt() {
		return this.interrupt;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.model.JobContext#setInterrupt(boolean)
	 */
	@Override
	public void setInterrupt(boolean interrupt) {
		this.interrupt=interrupt;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.model.JobContext#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(Object key, Object value) {
		
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.model.JobContext#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		return null;
	}
	
}
