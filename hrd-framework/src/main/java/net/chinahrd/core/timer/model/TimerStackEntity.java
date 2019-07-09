/**
*net.chinahrd.core.timer.model
*/
package net.chinahrd.core.timer.model;

import net.chinahrd.core.timer.Timer;

/**任务栈 存放的实体
 * @author htpeng
 *2016年11月11日上午10:25:13
 */
public class TimerStackEntity {
	private Timer timer;
	private TimerConfig timerConfig;
	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	/**
	 * @return the timerConfig
	 */
	public TimerConfig getTimerConfig() {
		return timerConfig;
	}
	/**
	 * @param timerConfig the timerConfig to set
	 */
	public void setTimerConfig(TimerConfig timerConfig) {
		this.timerConfig = timerConfig;
	}
	public TimerStackEntity(Timer timer, TimerConfig timerConfig) {
		super();
		this.timer = timer;
		this.timerConfig = timerConfig;
	}
	
}
