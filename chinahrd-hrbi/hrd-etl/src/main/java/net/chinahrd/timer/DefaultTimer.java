/**
*net.chinahrd.core.timer
*/
package net.chinahrd.timer;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import net.chinahrd.utils.PropertiesUtil;

/**
 * 系统定时器
 * 
 * @author htpeng 2017年4月11日下午5:57:52
 */
public class DefaultTimer {
	private static final Logger log = Logger.getLogger(DefaultTimer.class);
	private TimerConfig tc = new TimerConfig();
	private static final String configFilePath = "conf/config.properties";
	private String cron = "0 * 22 * * ?";

	public DefaultTimer() {

		Object p = PropertiesUtil.getProperty(configFilePath, "cron");
		if (null == p) {
			log.warn("配置文件中没有定义cron");
		} else {
			cron = p.toString();
		}
		// tc.setJobClass(DefaultJob.class);
		tc.setCron(cron);
		tc.setJobKey(new JobKey("system_root_job", "system_root_job"));
		tc.setTriggerKey(new TriggerKey("system_root_job", "system_root_job"));
	}

	/**
	 * @return the tc
	 */
	public TimerConfig getTimerConfig() {
		return tc;
	}

}
