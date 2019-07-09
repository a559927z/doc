package net.chinahrd.core.cache.storage;

import net.chinahrd.core.cache.QueryBuilder;
import net.chinahrd.core.cache.StorageBuilder;
import net.chinahrd.core.cache.model.CacheConfig;

/**
 * 缓存存储仓库接口
 * @author htpeng
 *2016年2月2日下午2:32:53
 * @param <T>
 */
public interface CacheStorage<T>  {
	

	/**
	 * 设置配置信息
	 */
	public void setCacheConfig(CacheConfig cacheConfig) ;
	
	/**
	 * 设置缓存查询器
	 */
	public void setQueryBuilder(QueryBuilder<T> queryBuilder) ;
	
	/**
	 * 设置缓存 存储器
	 */
	public void setStorageBuilder(StorageBuilder<T> storageBuilder) ;
//	/**查询
//	 * @param customerId
//	 * @param parameter
//	 * @return
//	 */
//	public T get( Object... parameter) ;
	
	/**查询
	 * @param customerId
	 * @param parameter
	 * @return
	 */
	public T get( String parameter) ;
	/**查询
	 * @param customerId
	 * @param parameter
	 * @return
	 */
	public T get( ) ;
	/**
	 * 更新
	 */
	public void update() ;
	/**
	 * 清空
	 */
	public void clear() ;
	
//	/**
//	 * 
//	 * 设置缓存
//	 */
//	public void set(Object key,T value) ;

}
