/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import net.chinahrd.core.RegisterInterface;
import net.chinahrd.core.module.model.ModuleModel;





/**
 * 缓存注册接口
 * @author htpeng
 *2016年10月8日下午1:42:50
 */

public interface  CacheRegister extends RegisterInterface{
	void setModuleModel(ModuleModel moduleModel);
	
//	Class<? extends Object> getDaoClass();
	Class<? extends Object> getCacheClass();
}
