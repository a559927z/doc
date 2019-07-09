package net.chinahrd.core.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import net.chinahrd.core.cache.model.CacheConfig;
import net.chinahrd.core.cache.model.MethodConfig;
import net.chinahrd.core.exception.cache.NotFoundClassOrMethod;
import net.chinahrd.core.module.ModuleLocal;
import net.chinahrd.core.module.model.ModuleModel;

/**
 * 数据库查询器
 * 
 * @author htpeng 2016年2月2日下午2:33:19
 * @param <T>
 */
public final class DBQueryBuilder<T> implements QueryBuilder<T> {
	private String cacheDaoName;
	@SuppressWarnings("unused")
	private Class<T> dbResultClass;
	private String methodName;
	Map<String, Object> map;
	private SqlSessionFactory ssf;
	private MethodConfig methodConfig;
	@SuppressWarnings("unused")
	private CacheConfig cacheConfig;
	private Object[] parameter;
	// private String customerId;
	private CustomBlock<T> customBlock;

	// public QueryData(SqlSessionFactory ssf,TimeBufferConfig<T>
	// timeBufferConfig,MethodConfig methodConfig,String customerId, Object...
	// parameter){
	// this.ssf=ssf;
	// this.methodConfig=methodConfig;
	// this.timeBufferConfig=timeBufferConfig;
	// this.parameter=parameter;
	// this.customerId=customerId;
	// }
	public DBQueryBuilder() {

	}

	public void setCacheConfig(CacheConfig cacheConfig) {
		this.ssf = CacheSessionManager.getSqlSessionFactory();
		ModuleModel m=ModuleLocal.getModuleModel();
		this.cacheDaoName = m.getCacheDaoClass()
				.getName();
		this.methodName = cacheConfig.getMethod();
		
		try {
			this.methodConfig = CacheConfigTool.getMethodParmMap()
					.get(this.cacheDaoName).getMethodParmMap()
					.get(this.methodName);
		} catch (Exception e) {
			throw new NotFoundClassOrMethod(this.cacheDaoName, this.methodName,
					e);
		}
		if (null == this.methodConfig) {
			throw new NotFoundClassOrMethod(this.cacheDaoName, this.methodName);
		}
		this.cacheConfig = cacheConfig;
		this.parameter = cacheConfig.getParameters();
		cacheConfig.setIdentify("[module:(code:"+ m.getCode() 
				+ ",name:"+ m.getName() + ",version:"+m.getVersion()+")" 
				+ ",cacheDao:"+ cacheDaoName 
				+ ",method:" + methodName 
				+ ",parameter:"+Arrays.toString(parameter) 
				+ "]");
		
	}

	void setCacheDaoName(String cacheDaoName) {
		this.cacheDaoName = cacheDaoName;
	}

	public String getCacheDaoName() {
		return cacheDaoName;
	}

	public T query() {
		// SqlSession session=ssf.openSession();
		Object soure = null;
		// T cacheObj=null;
		if (null != methodConfig) {
			SqlSession session = ssf.openSession();
			try {

				List<String> parmList = methodConfig.getParmList();
				Map<String, Object> map = new HashMap<String, Object>();
				if (parmList != null) {
					if (null != parameter) {
						if (parmList.size() == parameter.length) {
							for (int i = 0; i < parmList.size(); i++) {
								map.put(parmList.get(i), parameter[i]);
							}
						} else {
							// System.out.println(this.cacheDaoName+" ："+this.methodName);
							// System.out.println(parmList.size()+"  "+parameter.length);
							// System.out.println(" 参数： ");
							// for(Object s:parameter){
							// System.out.println(s);
							// }
							// System.out.println(" 参数ok");
							throw new Exception(parmList.size()+" , " +parameter.length+"参数类型不匹配");
						}
					} else {
						if (parmList.size() > 0) {
							// System.out.println(this.cacheDaoName+" ："+this.methodName);
							// System.out.println(parmList.size()+"  "+parameter.length);
							// System.out.println(" 参数： ");
							// for(Object s:parameter){
							// System.out.println(s);
							// }
							// System.out.println(" 参数ok");
							throw new Exception("参数类型不匹配");
						}
					}
				}
				Class<?> resultType = methodConfig.getResultType();
				// Class xmlresultType=methodConfig.getXmlResultType();

				if (resultType.equals(List.class)) {
					soure = session.selectList(cacheDaoName + "."
							+ this.methodName, map);
				} else if (resultType.equals(Map.class)) {
					// soure=(T)
					// session.selectMap(cacheDaoName+"."+timeBufferConfig.getMethod(),map);
				} else {
					soure = session.selectOne(cacheDaoName + "."
							+ this.methodName, map);
				}
				// xmlresultType.cast(soure);
				// cacheObj = simpleCacheConfig.formatData(soure);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
		return customBlock.formatData(soure);
		// return cacheObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.chinahrd.core.cache.QueryBuilder#setCustomBlock(net.chinahrd.core
	 * .cache.CustomBlock)
	 */
	@Override
	public void setCustomBlock(CustomBlock<T> customBlock) {
		this.customBlock = customBlock;
	}
}
