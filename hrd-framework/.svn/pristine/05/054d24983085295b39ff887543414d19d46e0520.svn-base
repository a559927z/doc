/**
*net.chinahrd.core.timer
*/
package net.chinahrd.core.timer;

import java.io.IOException;
import java.util.Properties;

import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.core.tools.file.FileTool;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**系统定时器
 * @author htpeng
 *2016年11月11日上午11:50:28
 */
public class DefaultTimer {
	private static final Logger log = Logger.getLogger(DefaultTimer.class);
	private  TimerConfig tc=new TimerConfig();
	private static final String configFilePath="conf/timer.properties";
	private  String cron="0 * 22 * * ?";
	public DefaultTimer(){
		try {
			Properties properties=FileTool.getProperties(configFilePath);
			Object p=properties.get("cron");
			if(null==p){
				log.warn("配置文件中没有定义cron");
			}else{
				cron=p.toString();
			}
		} catch (IOException e) {
			log.warn("项目根目录下沒有"+configFilePath+"目录或文件",e);
		}
		tc.setBindTimer(true);
//		tc.setJobClass(DefaultJob.class);
		tc.setCron(cron);
		tc.setJobKey(new JobKey("system_root_job","system_root_job"));
		tc.setTriggerKey(new TriggerKey("system_root_job","system_root_job"));
	}

	/**
	 * @return the tc
	 */
	public TimerConfig getTimerConfig() {
		return tc;
	}
	
}
