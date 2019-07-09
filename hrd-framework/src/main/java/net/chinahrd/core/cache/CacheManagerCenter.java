/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存管理中心
 * 统一管理系统缓存
 * 提供缓存互调功能   
 * @author htpeng
 *2016年10月10日下午6:01:41
 */
 class CacheManagerCenter<T> {

//	private static Map<String,CacheBlock<?>> map=new HashMap<String,CacheBlock<?>>();
//	
//	@SuppressWarnings("unchecked")
//	CacheBlock<T> getCachBlock(String key){
//		synchronized (map) {
//			CacheBlock<?> c=map.get(key);
//			if(c==null){
//				return null;
//			}
//			return (CacheBlock<T>) c;
//		}
//	}
//	
//	CacheBlock<T> setCachBlock(String key,CacheBlock<T> cacheBlock){
//		synchronized (map) {
//			map.put(key, cacheBlock);
//			return cacheBlock;
//		}
//		
//	}
	 
//	 private static Map<String,CacheBlock<?>> map=new HashMap<String,CacheBlock<?>>();
	 private static List<CacheBlock<?>> list=new ArrayList<CacheBlock<?>>();
//		@SuppressWarnings("unchecked")
//		CacheBlock<T> getCachBlock(String key){
//			synchronized (map) {
//				CacheBlock<?> c=map.get(key);
//				if(c==null){
//					return null;
//				}
//				return (CacheBlock<T>) c;
//			}
//		}
		
		CacheBlock<T> setCachBlock(CacheBlock<T> cacheBlock){
//			synchronized (map) {
//				map.put(key, cacheBlock);
//				return cacheBlock;
//			}
			synchronized (list) {
				list.add(cacheBlock);
				return cacheBlock;
			}
			
		}
}
