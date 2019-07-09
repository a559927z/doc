/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.job;


import java.util.ArrayList;
import java.util.List;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.core.timer.model.TimerConfig;

/**
 * 定时任务注册抽象类
 * @author htpeng
 *2016年10月8日下午1:42:50
 */

public abstract class JobRegisterAbstract implements JobRegister{
	TimerConfig tc=new TimerConfig();
	private ModuleModel moduleModel;
	private List<JobRegister> list;
	
	
	public JobRegisterAbstract(){
		setTimerConfig(tc);
		if(null==tc.getJobKey()){
			tc.setJobKey(new JobKey(this.getClass().getName()));
		}
		if(null==tc.getTriggerKey()){
			tc.setTriggerKey(new TriggerKey(this.getClass().getName()));
		}
	}
	/**
	 * 定义API类型
	 * @return
	 */
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public  void register(){
	
//		if (tc.isBindTimer()
//				&& null == tc.getJobClass()) {
//			tc.setJobClass(this.getClass());
//		}

		JobRegisterCenter.getInstance().register(moduleModel,this);
	}
	
	@Override
	public	void setModuleModel(ModuleModel moduleModel){
		this.moduleModel=moduleModel;
	}
	@Override
	public JobRegister associate(JobRegister jobRegister){
		if(null==list){
			list=new ArrayList<JobRegister>();
		}
		list.add(jobRegister);
		return this;
	}
	
	@Override
	public List<JobRegister> getAssociate() {
		return list;
	}
	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.Timer#getTimerConfig()
	 */
	@Override
	public TimerConfig getTimerConfig() {
		return tc;
	}
	
	
	public abstract void setTimerConfig(TimerConfig tc);
}
