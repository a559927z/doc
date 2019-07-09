/**
*net.chinahrd.core.job
*/
package net.chinahrd.core.job;


import java.util.List;
//import org.quartz.Job;
import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.core.timer.Timer;
import net.chinahrd.core.RegisterInterface;

/**
 * 注册定时任务
 * @author htpeng
 *2016年11月10日下午5:06:32
 */
public interface JobRegister extends RegisterInterface,Timer{
	void setModuleModel(ModuleModel moduleModel);
	JobRegister associate(JobRegister jobRegister);
	List<JobRegister> getAssociate();
}
