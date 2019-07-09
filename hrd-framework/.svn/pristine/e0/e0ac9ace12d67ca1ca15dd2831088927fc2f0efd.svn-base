/**
*net.chinahrd.core.module
*/
package net.chinahrd.core.module;


import net.chinahrd.core.module.model.ModuleModel;

/**获取当前线程模块
 * @author htpeng
 *2016年11月8日下午6:23:44
 */
public class ModuleLocal {
	private static ThreadLocal<ModuleModel> threadLocal =new ThreadLocal<ModuleModel>();
	public static void setModuleModel(ModuleModel webEntity){
		threadLocal.set(webEntity);
	}
	public static ModuleModel getModuleModel(){
		return threadLocal.get();
	}

}
