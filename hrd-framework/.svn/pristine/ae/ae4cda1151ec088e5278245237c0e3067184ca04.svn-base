/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.timer;




/**定时器注册中心
 * @author htpeng 2016年10月8日下午1:42:50
 */

public class TimerRegisterCenter {
	private static TimerRegisterCenter timerRegister = null;

	private TimerRegisterCenter() {

	}

	public static TimerRegisterCenter getInstance() {
		if (null == timerRegister) {
			timerRegister = new TimerRegisterCenter();
		}
		return timerRegister;
	}

	public void register(Timer timer) {
		TimerManagerCenter.setTimer(timer);
		if(!timer.getTimerConfig().isLazy()){
			timer.execute(null);
		}
	}

	
}
