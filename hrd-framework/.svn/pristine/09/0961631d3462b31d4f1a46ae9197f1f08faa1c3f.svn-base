package net.chinahrd.core.cache;

import java.util.Map;

import net.chinahrd.core.cache.model.CacheConfig;


/**
 * 文件查询器
 * @author htpeng
 *2016年2月2日下午2:33:19
 * @param <T>
 */
@SuppressWarnings("unused")
public abstract class FileQueryBuilder<T> implements QueryBuilder<T>{
	Map<String,Object> map;
	private CacheConfig cacheConfig;
	private String customerId;
	private Object[] parameter;
//	public QueryData(SqlSessionFactory  ssf,TimeBufferConfig<T> timeBufferConfig,MethodConfig methodConfig,String customerId, Object... parameter){
//		this.ssf=ssf;
//		this.methodConfig=methodConfig;
//		this.timeBufferConfig=timeBufferConfig;
//		this.parameter=parameter;
//		this.customerId=customerId;
//	}


	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.QueryData#init(net.chinahrd.core.cache.model.CacheConfig, java.lang.Object[])
	 */
	@Override
	public void setCacheConfig(CacheConfig cacheConfig) {
		// TODO Auto-generated method stub
		
		//		this.methodConfig=timeBufferConfig.getMethod();
		this.cacheConfig=cacheConfig;
//		this.customerId=customerId;
		this.parameter=cacheConfig.getParameters();
	}
}
