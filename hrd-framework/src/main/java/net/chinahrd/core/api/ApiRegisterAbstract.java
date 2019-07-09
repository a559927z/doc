/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.api;


import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.core.module.model.ModuleModel;

/**
 * API注册抽象类
 * @author htpeng
 *2016年10月8日下午1:42:50
 */

public abstract class ApiRegisterAbstract implements ApiRegister{
	private ModuleModel moduleModel;
	/**
	 * 定义API类型
	 * @return
	 */
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public  void register(){
//		CacheRegisterCenter.getInstance().register(getXml(getXmlPath()));
		ApiType type= getApiType();
		if(null==type){
			type=ApiType.INTERFACE;
		}
		ApiRegisterCenter.getInstance().register(moduleModel,this);
	}
	
	@Override
	public	void setModuleModel(ModuleModel moduleModel){
		this.moduleModel=moduleModel;
	}

}
