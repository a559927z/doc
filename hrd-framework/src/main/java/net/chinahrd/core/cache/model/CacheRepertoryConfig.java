/**
*net.chinahrd.core.cache.model
*/
package net.chinahrd.core.cache.model;

import java.util.HashMap;
import java.util.Map;

/**缓存配置  查询数据库Dao类的信息
 * @author htpeng
 *2016年10月10日上午10:38:43
 */
public class CacheRepertoryConfig {
	private Class<? extends Object> daoClass;
	private  Map<String,MethodConfig> methodParmMap=new HashMap<String,MethodConfig>();

	public void setMethodConfig(String methodName,MethodConfig methodConfig){
		methodParmMap.put(methodName, methodConfig);
	}

	public Class<? extends Object> getDaoClass() {
		return daoClass;
	}

	public void setDaoClass(Class<? extends Object> daoClass) {
		this.daoClass = daoClass;
	}

	public Map<String, MethodConfig> getMethodParmMap() {
		return methodParmMap;
	}

	public void setMethodParmMap(Map<String, MethodConfig> methodParmMap) {
		this.methodParmMap = methodParmMap;
	}
	
	
}
