package net.chinahrd.core.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.core.cache.model.CacheConfig;

/**
 * 用于配置 格式化数据为 Map<Object,DTO>类型的数据
 * @author htpeng
 *
 * @param <T>
 * @param <K>
 */
 class CacheBlockMapList<T> extends CacheBlockRoot<T>{
	@SuppressWarnings("unused")
	private String key; //用于分割数据库结果集的 标识  dto 则为字段的get方法名  map则为字段的键

	/**
	 * 
	 * @param type
	 * @param method
	 * @param pattern
	 * @param key  用于分割数据库结果集的 标识  dto 则为字段的get方法名  map则为字段的键
	 */
	public CacheBlockMapList(String method, String pattern,String key) {
		super( method, pattern);
		this.key=key;
	}
	/**
	 * 
	 * @param uuid 唯一标识该配置类
	 * @param method 查询数据库调用的方法
	 * @param pattern 重新查询数据的  时间间隔
	 */
//	public CacheBlockMapList(Class<? extends Object> daoClass,String method, String pattern ) {
////		super(uuid, method, pattern);
//		super( daoClass,method, pattern);
//	}
	public CacheBlockMapList(CacheConfig cacheConfig) {
		super(cacheConfig);
	}
	@Override
	public CustomBlock<T>  getCustomBlock() {
		return new CustomBlock<T>(){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public T formatData(Object obj) {
				Map<Object, List<Object>> resultMap=new HashMap<Object, List<Object>>();
				if(obj!=null){
					if(obj instanceof List){
						for(Object o:(List)obj){
							if(o instanceof Map){
							}
//							else if(o instanceof KVItemDto){
//								KVItemDto kVItemDto=(KVItemDto)o;
//									List<Object> list=resultMap.get(kVItemDto.getK());
//									if(null==list){
//										list=CollectionKit.newList();
//										list.add(kVItemDto.getV());
//										resultMap.put(kVItemDto.getK(),list);
//									}
//									list.add(kVItemDto.getV());
//								//}
//								
//							}
							else{
//								Method[] methods=o.getClass().getMethods();
//								for(Method method:methods){
//									if(method.getName().equals(key)){
//										try {
//											resultMap.put(method.invoke(o, null),o);
//											break;
//										} catch (IllegalAccessException
//												| IllegalArgumentException
//												| InvocationTargetException e) {
//												e.printStackTrace();
//										}
//									}
//								}
							}
						}
					}
				}
				return (T)resultMap;
			}
			
		};
	}
	
}
