/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;

/**缓存存储器
 * @author htpeng
 *2016年10月24日上午10:28:08
 */
public interface StorageBuilder <T>{
	
	/**
	 * 初始化存储器
	 * @param cacheConfig
	 * @param parameter
	 */
	public void setCacheConfig(CacheConfig cacheConfig);
	/**查询
	 * @return
	 */
	public T get(String key) ;
	
	/**查询
	 * @param customerId
	 * @param parameter
	 * @return
	 */
	public T set( T t) ;

	/**
	 * 清空
	 */
	public void clear() ; 
	
	/**
	 * 数据是否有效
	 * @return
	 */
	public boolean isValid ();
}
