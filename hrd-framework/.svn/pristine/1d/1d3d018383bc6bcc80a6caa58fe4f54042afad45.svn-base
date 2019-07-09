/**
*net.chinahrd.core.cache.model
*/
package net.chinahrd.core.cache.model;

import net.chinahrd.core.cache.config.CacheType;
import net.chinahrd.core.timer.model.TimerConfig;

/**缓存配置信息
 * @author htpeng
 *2016年10月10日下午2:19:37
 */
public interface CacheConfig {
	
	/**
	 * 获取缓存类型
	 * @return
	 */
	CacheType getCacheType();
	

	/**获取缓存唯一标识
	 * @return
	 */
	String getIdentify();
	
	/**获取缓存唯一标识
	 * @return
	 */
	void setIdentify(String identify);
	/**
	 * 获取缓存 使用数据库的方法
	 * @return
	 */
	String getMethod();
	
	TimerConfig getTimerConfig();
	/**
	 * @return
	 */
//	Class<? extends Object> getDaoClass();
	
	/**
	 * 获取查询参数
	 * @return
	 */
	Object[] getParameters();
	/**
	 * 设置查询参数
	 * @param objs
	 */
	void setParameters(Object... objs);
}
