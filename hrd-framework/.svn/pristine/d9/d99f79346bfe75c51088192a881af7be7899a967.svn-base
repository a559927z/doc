/**
  *net.chinahrd.core.cache
 */
package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;
import net.chinahrd.core.cache.model.SimpleCacheConfig;

/**
 * 缓存块构造器
 * 
 * @author htpeng 2016年10月9日下午3:14:36
 */
public class CacheBlockConstructor<T> {
	private final static boolean defaultLazy=false;
	private CacheManagerCenter<T> cacheManager=new CacheManagerCenter<T>();

	private CacheBlock<T> cacheBlock=null;
	private CacheConfig cacheConfig;
	/**
	 * 
	 * @param methodName 方法名
	 */
	public CacheBlockConstructor(String methodName){
		this(methodName,null);

	}

	public CacheBlockConstructor(String methodName,boolean lazy){
		this(methodName,null,lazy);
	}
	/**
	 * 
	 * @param methodName 方法名
	 * @param cron  缓存更新日期格式
	 */
	public CacheBlockConstructor(String methodName,String cron){
		this(new SimpleCacheConfig(cron, methodName,defaultLazy));
	}
	
	/**
	 * 
	 * @param methodName 方法名
	 * @param cron  缓存更新日期格式
	 */
	public CacheBlockConstructor(String methodName,String cron,boolean lazy){
		this(new SimpleCacheConfig(cron, methodName,lazy));
	}

	
	/**
	 * 
	 * @param cacheConfig  缓存配置
	 */
	public CacheBlockConstructor(CacheConfig cacheConfig){
		this.cacheConfig=cacheConfig;
	}
	/**
	 * 创建默认缓存块
	 * @param parms      查询参数
	 * @return
	 */
	public CacheBlock<T> getDefaultBlock(Object...parms) {
		if(vaild()){
			this.cacheConfig.setParameters(parms);
			return put(new CacheBlockDefault<T>(this.cacheConfig));
		}
		return cacheBlock;
	}
//	/**
//	 * 获取Map<Object,List<Object>>格式缓存块
//	 * @return
//	 */
//	public CacheBlock<T> getMapListBlock(Object...parms) {
//		if(vaild()){
//			this.cacheConfig.setParameters(parms);
//			return put(new CacheBlockMapList<T>(this.cacheConfig));
//		}
//		return cacheBlock;
//	}
//	
//	/**
//	 * 获取Map<Object,Object>格式缓存块
//	 * @param key
//	 * @param parms
//	 * @return
//	 */
//	public CacheBlock<T> getSingleMapBlock(String key,Object...parms) {
//		if(vaild()){
//			this.cacheConfig.setParameters(parms);
//			return put( new CacheBlockSingleMap<T>(this.cacheConfig,key));
//		}
//		return cacheBlock;
//	}
	/**
	 * 创建自定义缓存块
	 * @param customBlock   自定义块
	 * @param parms      查询参数
	 * @return
	 */
	public CacheBlock<T> getCustomBlock(final CustomBlock<T> customBlock,Object...parms) {
		if(vaild()){
			this.cacheConfig.setParameters(parms);
			return put(new CacheBlockRoot<T>(this.cacheConfig){
			@Override
				protected CustomBlock<T> getCustomBlock() {
					return customBlock;
				}
			});
		}
		return cacheBlock;
	}
	
	private boolean vaild(){
//		cacheBlock=cacheManager.getCachBlock(cacheConfig.getModuleIdentify());
//		return  null==cacheBlock;
		return true;
	}
	
	private CacheBlock<T> put(CacheBlock<T> cacheBlock){
//		return  cacheManager.setCachBlock(cacheConfig.getIdentify(), cacheBlock);
		return  cacheManager.setCachBlock( cacheBlock);
	}
}
