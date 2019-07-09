/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import net.chinahrd.core.timer.Timer;

/**
 * 实际定义的缓存块
 * @author htpeng
 *2016年10月9日下午3:14:36
 */
public interface CacheBlock<T> extends Timer{

	/**
	 * 从缓存中获取需要数据
	 * 
	 * @param parameter
	 *            所需参数
	 * @return
	 */
	 T  get() ;
	 T  get(String key) ;

	 void update();
//	 
//	 /**
//	  * 设置缓存
//	  * @param key
//	  * @param t
//	  */
//	 void set(Object key,T t);
}
