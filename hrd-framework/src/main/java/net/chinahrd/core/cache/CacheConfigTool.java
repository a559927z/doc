package net.chinahrd.core.cache;


import java.util.HashMap;
import java.util.Map;

import net.chinahrd.core.cache.model.CacheRepertoryConfig;
/**
 * 定时更新缓存配置类
 * 
 * @author htpeng
 *
 */
 class CacheConfigTool {
	private static Map<String,CacheRepertoryConfig> cacheConfigMap=new HashMap<String,CacheRepertoryConfig>();
	
	static Map<String,CacheRepertoryConfig> getMethodParmMap(){
		return cacheConfigMap;
	}
	static void setMethodParmMap(String daoClass,CacheRepertoryConfig map){
		cacheConfigMap.put(daoClass, map);
	}
}