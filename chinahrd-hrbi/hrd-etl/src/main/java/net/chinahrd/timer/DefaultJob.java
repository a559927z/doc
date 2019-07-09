/**
*net.chinahrd.core.timer
*/
package net.chinahrd.timer;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.chinahrd.etl.RootEtl;
import net.chinahrd.util.EtlStatusSync;

/**
 * 系统任务
 * 
 * @author htpeng 2016年11月8日下午2:23:10
 */
public class DefaultJob implements Job {
	private static final Logger log = Logger.getLogger(DefaultJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(EtlStatusSync.isETLActive()){
			log.info("=====================ETL正在执行中=========================");
		}else{
			log.info("=====================开启数据抽取=========================");
			new RootEtl().start();
		}
		
	}

}
