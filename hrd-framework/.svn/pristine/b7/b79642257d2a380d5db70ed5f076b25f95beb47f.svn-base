/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.cache.storage;

import net.chinahrd.core.cache.QueryBuilder;
import net.chinahrd.core.cache.StorageBuilder;
import net.chinahrd.core.cache.model.CacheConfig;



/**
 * 自定义缓存
 * 
 * @author htpeng 2016年10月10日下午2:56:24
 * @param <T>
 */
public class SimpleCacheStorage<T> implements CacheStorage<T>{
	/**
	 * 缓存的配置信息
	 */
	@SuppressWarnings("unused")
	private CacheConfig cacheConfig;
	
	/**
	 * 查询器
	 */
	private QueryBuilder<T> queryBuilder;
	/**
	 *存储器
	 * 
	 */
	private StorageBuilder<T> storageBuilder;
	/**
	 * 
	 */
	public SimpleCacheStorage() {

	}

	// public SimpleCacheStorage(CacheConfig cacheConfig) {
	// this.cacheConfig = cacheConfig;
	// }

	
	public synchronized T get() {
		return get(null);
	}

	/**
	 * 
	 * @param customerId
	 * @param daoObj
	 *            数据库对像
	 * @param parameter
	 *            查询条件参数
	 * @return
	 */
	public synchronized T get( String key) {
		if(this.storageBuilder.isValid()){
			return this.storageBuilder.get(key);
		}else{
			return this.storageBuilder.set(queryBuilder.query());
		}

	}


	@Override
	public void update() {
		this.storageBuilder.clear();
		this.storageBuilder.set(queryBuilder.query());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.core.cache.storage.Cache#clear()
	 */
	@Override
	public void clear() {
		this.storageBuilder.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.chinahrd.core.cache.storage.CacheStorage#setCacheConfig(net.chinahrd
	 * .core.cache.model.CacheConfig)
	 */
	@Override
	public void setCacheConfig(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}


	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.storage.CacheStorage#setQueryBuilder(net.chinahrd.core.cache.storage.QueryBuilder)
	 */
	@Override
	public void setQueryBuilder(
			QueryBuilder<T> queryBuilder) {
		this.queryBuilder=queryBuilder;
		
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.storage.CacheStorage#setStorageBuilder(net.chinahrd.core.cache.StorageBuilder)
	 */
	@Override
	public void setStorageBuilder(StorageBuilder<T> storageBuilder) {
		this.storageBuilder=storageBuilder;
	}


}
