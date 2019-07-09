/**
*net.chinahrd.core.api.model
*/
package net.chinahrd.core.job.model;

import net.chinahrd.core.InjectionModel;
import net.chinahrd.core.job.JobRegister;
import net.chinahrd.core.tools.InjectionTools;

/**定时任务注入模板
 * @author htpeng
 *2016年10月13日下午1:06:40
 */
public class JobModel implements InjectionModel{
	private JobRegister apiRegister;
	
	public JobModel(JobRegister apiRegister){
		this.apiRegister=apiRegister;
	}
	
	/**
	 * @return the apiRegister
	 */
	public JobRegister getJobRegister() {
		return apiRegister;
	}
	/**
	 * @param apiRegister the apiRegister to set
	 */
	public void setJobRegister(JobRegister apiRegister) {
		this.apiRegister = apiRegister;
	}
	
	@Override
	public void injecton(){
		InjectionTools.injection(apiRegister);
	}
	
}
