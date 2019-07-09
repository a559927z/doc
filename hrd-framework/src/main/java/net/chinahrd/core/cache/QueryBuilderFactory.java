package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;


/**
 * 缓存查询数据库
 * @author htpeng
 *2016年2月2日下午2:33:19
 * @param <T>
 */
public class QueryBuilderFactory{
	@SuppressWarnings("rawtypes")
	static QueryBuilder<?> builder(CacheConfig cacheConfig){
		  
		 switch(cacheConfig.getCacheType()){
		 	case MEMORY:
		 		return new DBQueryBuilder();
		 	case FILE:	
		 		return new DBQueryBuilder();
		 }
		return null;
		 
	 }
}
