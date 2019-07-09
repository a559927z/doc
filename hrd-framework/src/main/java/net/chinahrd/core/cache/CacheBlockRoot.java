package net.chinahrd.core.cache;


import org.apache.log4j.Logger;

//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.WebApplicationContext;

import net.chinahrd.core.cache.model.CacheConfig;
import net.chinahrd.core.cache.model.SimpleCacheConfig;
import net.chinahrd.core.cache.storage.CacheStorage;
import net.chinahrd.core.cache.storage.SimpleCacheStorage;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;

/**
 *  描述需要做定时缓存的所有配置信息
 * @author htpeng
 *2016年10月11日下午3:18:33
 * @param <T>  需要的返回格式类型
 */
 abstract class CacheBlockRoot<T>  implements CacheBlock<T>{
	 private static final Logger log = Logger.getLogger(CacheBlockRoot.class);
	private CacheConfig cacheConfig;
	private CacheStorage<T> cache;

	private CacheBlockRoot() {
		super();
//		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
//		Object o=wac.getBean("cacheBlock");
//		if(null==o){
//			cache=new SimpleCacheStorage<T>();
//		}else{
//			cache=(CacheStorage<T>)o;
//		}
		cache=new SimpleCacheStorage<T>();
	}
	/**
	 * 
	 * @param method
	 *            查询数据库调用的方法
	 * @param pattern
	 *            重新查询数据的 时间间隔
	 */
	public CacheBlockRoot( String method, String pattern) {
		this(new SimpleCacheConfig(pattern,method));
	}
//
//	public CacheBlockRoot(String method, String pattern) {
//		this(new SimpleCacheConfig(pattern,method));
//	}

	@SuppressWarnings("unchecked")
	public CacheBlockRoot(CacheConfig cacheConfig) {
		this();
		this.cacheConfig=cacheConfig;
		cache.setCacheConfig(cacheConfig);
		QueryBuilder<T> queryBuilder=new DBQueryBuilder<T>();
		queryBuilder.setCustomBlock(getCustomBlock());
		queryBuilder.setCacheConfig(cacheConfig);
		cache.setQueryBuilder(queryBuilder);
		StorageBuilder<T> storageBuilder=(StorageBuilder<T>)StorageBuilderFactory.builder(cacheConfig);
		storageBuilder.setCacheConfig(cacheConfig);
		cache.setStorageBuilder(storageBuilder);
	}

	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}

	protected abstract CustomBlock<T> getCustomBlock();

	/**
	 * 从缓存中获取需要数据
	 * 
	 * @param parameter
	 *            所需参数
	 * @return
	 */
	public T  get() {
		return cache.get();
//		return formatData(cache.get(parameter));
	}
	/**
	 * 从缓存中获取需要数据
	 * 
	 * @param key
	 *            所需参数
	 * @return
	 */
	public T  get(
			String key) {
		return cache.get(key);
	}

	public void update(){
		cache.update();
	}
	

	/* (non-Javadoc)
	 * @see net.chinahrd.core.timer.Timer#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobContext context) {
		log.info("loading CacheBlock:"+cacheConfig.getIdentify());
		cache.update();
//		log.info("load complete CacheBlock:"+cacheConfig.getIdentify());
	}

	@Override
	public TimerConfig getTimerConfig(){
		return cacheConfig.getTimerConfig();
	}
	

}
