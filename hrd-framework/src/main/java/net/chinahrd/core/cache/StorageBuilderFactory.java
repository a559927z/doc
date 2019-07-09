package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;


/**
 * 缓存查询数据库
 * @author htpeng
 *2016年2月2日下午2:33:19
 * @param <T>
 */
 class StorageBuilderFactory{
	@SuppressWarnings("rawtypes")
	static StorageBuilder<?> builder(CacheConfig cacheConfig){
		  
		 switch(cacheConfig.getCacheType()){
		 	case MEMORY:
		 		return new MemoryStorageBuilder();
		 	case FILE:	
		 		return new FileStorageBuilder();
		 }
		return null;
		 
	 }
}
