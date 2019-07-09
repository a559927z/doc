/**
*net.chinahrd.core.cache.model
*/
package net.chinahrd.core.cache.model;

import net.chinahrd.core.cache.config.CacheType;

/**
 * 简单的缓存标识config
 * @author htpeng
 *2016年10月10日下午2:19:37
 */
public class SimpleCacheConfig extends CacheConfigAbstract {
	
	private CacheType cacheType=CacheType.MEMORY;
	



	public SimpleCacheConfig(String method){
		this(null,method,false);
	}
	
	public SimpleCacheConfig(String pattern, String method){
		this(pattern,method,false);
	}
	
	public SimpleCacheConfig(String pattern, String method,boolean lazy){
		super(pattern,method,lazy);
	}


	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.model.CacheConfig#getCacheType()
	 */
	@Override
	public CacheType getCacheType() {
		return cacheType;
	}
	
	public void setCacheType(CacheType cacheType) {
		this.cacheType=cacheType;
	}

	

}
