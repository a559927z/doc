/**
*net.chinahrd.module
*/
package net.chinahrd.module;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.ProcessErrorDto;
import net.chinahrd.utils.CollectionKit;

/**
 * 系统存储过程
 * 
 * @author htpeng 2017年4月19日下午3:27:38
 */
public class SysProcessJob extends JobRegisterAbstract {

	public static final Logger log = LoggerFactory.getLogger(SysProcessJob.class);

	@Injection
	private SysProcessDao sysProcessDao;

	@Override
	public void execute(JobContext context) {
//		List<String> moduleCodeList = ModuleManagerCenter.getModuleCodeList();
		
		int status=sysProcessDao.checkSysProcessStatus();
		if(status>=2){
			//状态   1 未开始  2完成  3执行中  4  报错
			log.info("过程已经执行");
			context.setInterrupt(true);
			return;
		}
		//所有原表
		List<String> sysTableList = sysProcessDao.querySysSourceTable();
		// 抽取出现错误原表的数量
		int errorNum = sysProcessDao.querySysSourceTableErrorNum(sysTableList);
		if (errorNum == 0) {
			// 抽取完成的原表数量
			int completeNum = sysProcessDao.querySysSourceTableCompleteNum(sysTableList);
			if (completeNum > sysTableList.size()) {
				// 发生异常
				// 抽取的表不在模块表中
				context.setInterrupt(true);
				log.info("发生异常  抽取的表不在模块表中");
			} else if (completeNum < sysTableList.size()) {
				// 原表没有抽取完成
				context.setInterrupt(true);
				log.info("原表正在抽取中,继续等待");
			} else {
				// 原表抽取完成
				// 执行存储过程
				log.info("开始执行过程");
				String customerId=EisWebContext.getCustomerId();
				List<ProcessErrorDto> callSysProcess = sysProcessDao.callSysProcess(customerId,"SYSTEM");
				if (!CollectionKit.isEmpty(callSysProcess)) {
					// 执行过程出现异常
					context.setInterrupt(true);
					log.info("过程执行发生异常");
				}
			}
		} else {
			// 发生异常
			// 抽取的表出现错误
			context.setInterrupt(true);
			log.info("发生异常   原表抽取发生错误");
		}

	}

	@Override
	public void setTimerConfig(TimerConfig tc) {
		tc.setPriority(100);
	}
}
