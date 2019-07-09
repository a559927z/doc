/**
*net.chinahrd.core.timer
*/
package net.chinahrd.core.timer;



import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.SimpleJobContext;
import net.chinahrd.core.timer.model.TimerModel;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**系统任务
 * @author htpeng
 *2016年11月8日下午2:23:10
 */
public class DefaultJob implements Job{
	private static final Logger log = Logger.getLogger(DefaultJob.class);
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		TimerModel timerModel =TimerManagerCenter.getTimerModel(context.getJobDetail().getKey());
		if(null!=timerModel){
			log.info("启动定时任务:"+context.getJobDetail().getKey());
			JobContext jobContext=new SimpleJobContext(context);
			long time=System.currentTimeMillis();
			for(Timer timer:timerModel.getTimers()){
				if(!jobContext.interrupt()){
					timer.execute(jobContext);
				}
			}
//			for(TimerModel timerModel:TimerManagerCenter.getTimerModelList()){
//				if(timerModel.getJobDetail().equals(context.getJobDetail())){
//					for(Timer timer:timerModel.getTimers()){
//						timer.execute(context);
//					}
//				}
//			}
			long userTime=System.currentTimeMillis()-time;
			log.info("定时任务:"+context.getJobDetail().getKey()+"执行完成，用时："+userTime+"毫秒");
		}else{
			log.info("系统无法找到定时任务:"+context.getJobDetail().getKey());
		}
		
	}

}
