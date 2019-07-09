/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.core.timer.model.TimerModel;

import org.quartz.JobKey;
import org.quartz.Scheduler;

/**
 * 定时任务管理中心
 * @author htpeng 2016年10月8日下午1:42:50
 */

class TimerManagerCenter {
	private static TimerModel system=TimerModelFactory.create(new DefaultTimer().getTimerConfig());
	private static Scheduler scheduler = null;
	private static List<TimerModel> timerModelList=new ArrayList<TimerModel>();
	private static Map<JobKey,TimerModel> timerModelMap=new HashMap<JobKey,TimerModel>();
	/**
	 * @return the timerModelList
	 */
//	public static List<TimerModel> getTimerModelList() {
//		return timerModelList;
//	}
	public static TimerModel getTimerModel(JobKey jobKey) {
		return timerModelMap.get(jobKey);
	}
	/**
	 * @param timerModelList the timerModelList to set
	 */
	public static void setTimer(Timer timer) {
		TimerConfig timerConfig =timer.getTimerConfig();
		create(timerConfig).getTimerStack().put(timer);
	}




	/**
	 * @return the scheduler
	 */
	public static Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * @param scheduler the scheduler to set
	 */
	public static void setScheduler(Scheduler scheduler) {
		TimerManagerCenter.scheduler = scheduler;
	}

	/**
	 * 添加定时器
	 * @param timerModelList the timerModelList to set
	 */
	private static TimerModel setTimerModel(TimerModel timerModel) {
		if(timerModelList.size()==0) setSystemModel();
		timerModelList.add(timerModel);
		timerModelMap.put(timerModel.getJobDetail().getKey(), timerModel);
			return timerModel;
	}

	/**
	 * 添加系统定时器
	 */
	private static void setSystemModel() {
			timerModelList.add(system);
			timerModelMap.put(system.getJobDetail().getKey(),system);
	}
	
	/**
	 * 创建定时器
	 * @param timerConfig
	 * @return
	 */
	 static TimerModel create(TimerConfig timerConfig){
			if(timerConfig.isBindTimer()){
				for(TimerModel timerModel:timerModelList){
					if(timerModel.getCron().equals(timerConfig.getCron())){
						return timerModel;
					}
				}
				TimerModel timerModel= TimerModelFactory.create(timerConfig);
				 return setTimerModel(timerModel);
			}
			return system;
		}
	/**
	 * @return
	 */
	public static List<TimerModel> getTimerModelList() {
		// TODO Auto-generated method stub
		return timerModelList;
	}
}
