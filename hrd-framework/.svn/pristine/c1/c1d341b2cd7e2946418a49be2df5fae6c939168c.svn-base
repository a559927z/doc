/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import net.chinahrd.core.cache.model.CacheConfig;
//
//import java.util.Hashtable;
//import java.util.Map;

//import net.chinahrd.core.tools.PropertiesUtil;

/**
 * 内存存储器
 * @author htpeng
 *2016年10月24日下午3:42:28
 */
public class MemoryStorageBuilder<T> implements StorageBuilder<T>{
	//缓存对象
	private T t;
	private boolean valid=false;

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#get(java.lang.Object[])
	 */
	@Override
	public T get(String key) {
		return this.t;
	}




	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#clear()
	 */
	@Override
	public void clear() {
		t=null;
		this.valid=false;
	}


	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#set(java.lang.Object)
	 */
	@Override
	public T set(T t) {
		this.t=t;
		this.valid=true;
		 return t;
	}




	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#setCacheConfig(net.chinahrd.core.cache.model.CacheConfig)
	 */
	@Override
	public void setCacheConfig(CacheConfig cacheConfig) {
		
	}




	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return valid;
	}
}
