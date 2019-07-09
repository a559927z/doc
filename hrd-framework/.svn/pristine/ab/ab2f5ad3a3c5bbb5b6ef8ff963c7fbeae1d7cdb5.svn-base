/**
*net.chinahrd.core.api.model
*/
package net.chinahrd.core.api.model;

import java.util.HashMap;
import java.util.Map;


import com.esotericsoftware.reflectasm.MethodAccess;

import net.chinahrd.core.InjectionModel;
import net.chinahrd.core.api.ApiRegister;
import net.chinahrd.core.tools.InjectionTools;

/**Api接口注入模板
 * @author htpeng
 *2016年10月13日下午1:06:40
 */
public class ApiModel implements InjectionModel{
	private MethodAccess methodAccess;
	private ApiRegister apiRegister;
	private Map<String,Integer> methodIndexMap=new HashMap<String,Integer>();
	private static String[] ignors=ApiRegister.IGNORE_METHOD.split(",");
	
	public ApiModel(ApiRegister apiRegister){
		this.apiRegister=apiRegister;
		methodAccess = MethodAccess.get(apiRegister.getClass());
		for(String method:methodAccess.getMethodNames()){
			boolean isIgnor=false;
			for(String ignor:ignors){
				if(ignor.equals(method)){
					isIgnor=true;
					break;
				}
			}
			if(isIgnor)continue;
			methodIndexMap.put(method, methodAccess.getIndex(method));
		}
	}
	/**
	 * @return the methodAccess
	 */
	public MethodAccess getMethodAccess() {
		return methodAccess;
	}
	/**
	 * @param methodAccess the methodAccess to set
	 */
	public void setMethodAccess(MethodAccess methodAccess) {
		this.methodAccess = methodAccess;
	}
	/**
	 * @return the apiRegister
	 */
	public ApiRegister getApiRegister() {
		return apiRegister;
	}
	/**
	 * @param apiRegister the apiRegister to set
	 */
	public void setApiRegister(ApiRegister apiRegister) {
		this.apiRegister = apiRegister;
	}
	/**
	 * @return the methodIndexMap
	 */
	public Integer getMethodIndex(String key) {
		
		return methodIndexMap.get(key);
	}
	/**
	 * @param methodIndexMap the methodIndexMap to set
	 */
	public void setMethodIndexMap(Map<String, Integer> methodIndexMap) {
		this.methodIndexMap = methodIndexMap;
	}
	
	@Override
	public void injecton(){
		InjectionTools.injection(apiRegister);
	}
	

}
