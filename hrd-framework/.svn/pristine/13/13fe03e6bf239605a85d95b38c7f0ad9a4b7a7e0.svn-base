/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.core.cache.model.CacheConfig;
import net.chinahrd.core.cache.model.FileCacheConfig;
import net.chinahrd.core.cache.model.FileStorageEntity;
import net.chinahrd.core.exception.cache.FileCacheException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 文件存储器
 * @author htpeng
 *2016年10月24日下午3:42:28
 */
public class FileStorageBuilder<T> implements StorageBuilder<T>{
	private static CacheManager manager = CacheManager.create("ehcache.xml");
	private static Cache cache = new Cache("systemFileCache", 0, true, true, 0, 0);	
	private FileCacheConfig cacheConfig;
	private boolean valid=false;
	static{
		manager.addCache(cache);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T get(String key) {
		Element element= cache.get(cacheConfig.getIdentify()+key);
		FileStorageEntity<?> file=new FileStorageEntity();
		Object o=element.getObjectValue();
		file.set(o);
		return (T) file;
	}

	
	@Override
	public void clear() {
		this.valid=false;
		if(null==cacheConfig.getKeyList()){
			cache.remove(cacheConfig.getIdentify());
			return;
		}
		for(String key:cacheConfig.getKeyList()){
			cache.remove(cacheConfig.getIdentify()+key);
		}
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#set(java.lang.Object)
	 */
	@Override
	public T set(T t) {
		if(t instanceof FileStorageEntity){
			FileStorageEntity<?> fileStorageEntity=(FileStorageEntity<?>)t;
			List<String> keyList=new ArrayList<String>();
			Map<String,?> map=fileStorageEntity.getMap();
			for(String key:map.keySet()){
				keyList.add(key);
				cache.put(new Element(cacheConfig.getIdentify()+key, map.get(key)));
			}
			this.valid=true;
		}else throw new FileCacheException();
		return t;
	}


	@Override
	public void setCacheConfig(CacheConfig cacheConfig) {
		this.cacheConfig=(FileCacheConfig)cacheConfig;
	}


	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.StorageBuilder#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return this.valid;
	}
}
