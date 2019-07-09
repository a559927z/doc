package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;


/**
 * 默认缓存配置类
 * @author htpeng
 * @param <T>
 * @param <K>
 */
 class CacheBlockDefault<T> extends CacheBlockRoot<T>{
	/**
	 * 
	 * @param uuid 唯一标识该配置类
	 * @param method 查询数据库调用的方法
	 * @param pattern 重新查询数据的  时间间隔
	 */
	public CacheBlockDefault(String method, String pattern) {
//		super(uuid, method, pattern);
		super( method, pattern);
	}
	/**
	 * 
	 * @param uuid 唯一标识该配置类
	 * @param method 查询数据库调用的方法
	 * @param pattern 重新查询数据的  时间间隔
	 */
//	public CacheBlockDefault(Class<? extends Object> daoClass,String method, String pattern) {
////		super(uuid, method, pattern);
//		super(daoClass, method, pattern);
//	}
	public CacheBlockDefault(CacheConfig cacheConfig) {
		super(cacheConfig);
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.CacheBlock#setCustomBlock(net.chinahrd.core.cache.CustomBlock)
	 */
	@Override
	public CustomBlock<T>  getCustomBlock() {
		return new CustomBlock<T>(){
			@SuppressWarnings("unchecked")
			@Override
			public T formatData(Object obj) {
				return (T)obj;
			}
			
		};
	}

}
