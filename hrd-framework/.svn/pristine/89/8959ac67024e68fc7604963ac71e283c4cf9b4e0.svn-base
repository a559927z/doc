/**
*net.chinahrd.core.timer.model
*/
package net.chinahrd.core.timer.model;

import org.quartz.JobExecutionContext;

/**定时器任务上下文
 * 同一定时器的各个任务共享同一个上下文
 * @author htpeng
 *2017年4月19日下午4:58:08
 */
public interface JobContext {
	/**
	 * 获取quartz JobExecutionContext
	 * @return
	 */
	public JobExecutionContext getJobExecutionContext();
	
	/**
	 * 定时任务是否中断
	 * @return
	 */
	public boolean interrupt();
	
	/**
	 * 设置定时任务是否中断
	 * @return
	 */
	public void setInterrupt(boolean interrupt);
	
	  /**
     * Put the specified value into the context's data map with the given key.
     * Possibly useful for sharing data between listeners and jobs.
     *
     * <p>NOTE: this data is volatile - it is lost after the job execution
     * completes, and all TriggerListeners and JobListeners have been 
     * notified.</p> 
     *  
     * @param key the key for the associated value
     * @param value the value to store
     */
    public void put(Object key, Object value);

    /**
     * Get the value with the given key from the context's data map.
     * 
     * @param key the key for the desired value
     */
    public Object get(Object key);
	
}
