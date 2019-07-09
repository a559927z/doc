/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import net.chinahrd.core.module.model.ModuleModel;



/**
 * 缓存类申明  
 * 建议上设计成单例模式
 * @author htpeng
 *2016年10月9日下午3:14:36
 */
public interface CacheDefine {
	ModuleModel getModel();
	void setModule(ModuleModel moduleModel);
}
