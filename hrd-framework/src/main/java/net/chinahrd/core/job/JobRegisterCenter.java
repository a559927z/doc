/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.job;

import net.chinahrd.core.job.model.JobModel;
import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.core.timer.TimerRegisterCenter;

/**
 * 定时任务注册中心
 * 
 * @author htpeng 2016年11月9日下午4:32:54
 */
class JobRegisterCenter {
	// private List<InputStream> list = new ArrayList<InputStream>();
	private static JobRegisterCenter cacheRegister = null;

	private JobRegisterCenter() {

	}

	public static JobRegisterCenter getInstance() {
		if (null == cacheRegister) {
			cacheRegister = new JobRegisterCenter();
		}
		return cacheRegister;
	}

	void register(ModuleModel moduleModel, JobRegister jobRegister) {
		TimerRegisterCenter.getInstance().register(jobRegister);
		JobModel jobModel = new JobModel(jobRegister);
		moduleModel.setJobInjection(jobModel);
		if (null != jobRegister.getAssociate()) {
			for (JobRegister j : jobRegister.getAssociate()) {
				register(moduleModel, j);
			}
		}
	}

}
