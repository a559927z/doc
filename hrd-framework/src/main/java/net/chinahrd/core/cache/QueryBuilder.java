package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;


/**
 * 缓存查询器
 * @author htpeng
 *2016年2月2日下午2:33:19
 * @param <T>
 */
public interface QueryBuilder<T> {
	/**
	 * 初始化查询器
	 * @param cacheConfig
	 * @param parameter
	 */
	public void setCacheConfig(CacheConfig cacheConfig);
	
	public void  setCustomBlock(CustomBlock<T> customBlock);
	/**
	 * 查询
	 * @return
	 */
	public T query();
}
