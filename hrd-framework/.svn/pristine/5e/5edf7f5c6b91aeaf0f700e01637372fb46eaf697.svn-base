/**
*net.chinahrd.core.cache.model
*/
package net.chinahrd.core.cache.model;

import java.util.List;

import net.chinahrd.core.cache.config.CacheType;

/**
 * 文件缓存标识config
 * @author htpeng
 *2016年10月10日下午2:19:37
 */
public class FileCacheConfig extends CacheConfigAbstract {
	
	private CacheType cacheType=CacheType.FILE;
	
	private List<String> keyList;
	
	/**
	 * 构造
	 * @param method  数据库DAO对应方法名称
	 */
	public FileCacheConfig( String method){
		super(method);
	}
	
	/**
	 * 构造
	 * @param method 数据库DAO对应方法名称
	 * @param lazy 缓存是否懒加载
	 */
	public FileCacheConfig( String method,boolean lazy){
		super(method,lazy);
	}
	public FileCacheConfig(String pattern, String method){
		super(pattern,method);
	}
	

	
	@Override
	public CacheType getCacheType() {
		// TODO Auto-generated method stub
		return cacheType;
	}
	
	public void setCacheType(CacheType cacheType) {
		// TODO Auto-generated method stub
		this.cacheType=cacheType;
	}
	
	public List<String> getKeyList() {
		// TODO Auto-generated method stub
		return this.keyList;
	}
	
	public void setKeyList(List<String> keyList) {
		// TODO Auto-generated method stub
		 this.keyList=keyList;
	}
}
