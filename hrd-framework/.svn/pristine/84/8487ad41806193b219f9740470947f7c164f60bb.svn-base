/**
*net.chinahrd.core.cache.model
*/
package net.chinahrd.core.cache.model;

import java.util.HashMap;
import java.util.Map;



/**
 * 文件缓存标识config
 * @author htpeng
 *2016年10月10日下午2:19:37
 */
public class FileStorageEntity<T> implements FileStorage<T> {
	private Map<String,T> map=new HashMap<String,T>();
	private T t;
	@Override
	public T get() {
		// TODO Auto-generated method stub
		return this.t;
	}
	
	public Map<String,T> getMap(){
		return map;
	}
	
	public void set(Object object){
		this.t=(T)object;
	}
	
	public void setMap(String key,T t){
		this.map.put(key, t);
	}
}
