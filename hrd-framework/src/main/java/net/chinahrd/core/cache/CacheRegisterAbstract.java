/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;



import net.chinahrd.core.RegisterAbstract;
import net.chinahrd.core.module.model.ModuleModel;

/**
 * 缓存注册抽象类
 * @author htpeng
 *2016年10月8日下午1:42:50
 */

public abstract class CacheRegisterAbstract extends RegisterAbstract implements CacheRegister{
	private ModuleModel moduleModel;
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public  void register(){
		CacheRegisterCenter.getInstance().register(getInputStream(getXmlPath()),moduleModel);
		moduleModel.setCacheClass(getCacheClass());
	}
	@Override
	public	void setModuleModel(ModuleModel moduleModel){
		this.moduleModel=moduleModel;
	}
}
