package net.chinahrd.core.cache;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
@SuppressWarnings("unchecked")
 class CacheBlockSingleMap<T> extends CacheBlockRoot<T>{
	private String key; //用于分割数据库结果集的 标识  dto 则为字段的get方法名  map则为字段的键

	/**
	 * 
	 * @param method
	 * @param pattern
	 * @param key  用于分割数据库结果集的 标识  dto 则为字段的get方法名  map则为字段的键
	 */
	public CacheBlockSingleMap( String method, String pattern,String key) {
		super(method, pattern);
		this.key=key;
	}

	/**
	 * @param methodName
	 * @param pattern
	 * @param defaultParm
	 */
//	public CacheBlockSingleMap(Class<? extends Object> daoClass,String method, String pattern,String key) {
//		super(daoClass,method, pattern);
//		this.key=key;
//	}
	public CacheBlockSingleMap(CacheConfig cacheConfig,String key) {
		super(cacheConfig);
		this.key=key;
	}
	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.TimeBufferConfig#getCustomBlock()
	 */
	@Override
	public CustomBlock<T>  getCustomBlock() {
		return new CustomBlock<T>(){
			
			@Override
			public T formatData(Object obj) {
				Map<Object,Object> resultMap=new HashMap<Object, Object>();
				if(obj!=null){
					if(obj instanceof List){
						for(Object o:(List<?>)obj){
							if(o instanceof Map){
								resultMap.put(((Map<?, ?>)o).get(key),o);
							}else{
								Method[] methods=o.getClass().getMethods();
								for(Method method:methods){
									if(method.getName().equals(key)){
										try {
											resultMap.put(method.invoke(o, null),o);
											break;
										} catch (IllegalAccessException
												| IllegalArgumentException
												| InvocationTargetException e) {
												e.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
				return (T)resultMap;
			}
			
		};
	}
	
	

}
