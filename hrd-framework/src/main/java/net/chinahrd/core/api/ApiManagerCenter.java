/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;

import net.chinahrd.core.api.config.ApiInvalidType;
import net.chinahrd.core.api.define.ApiDoc;
import net.chinahrd.core.api.model.ApiModel;
import net.chinahrd.core.api.model.ApiVaild;
import net.chinahrd.core.exception.api.ApiException;


/**
 * API管理中心
 * @author htpeng
 *2016年10月13日下午12:59:25
 * @param
 */
 public class ApiManagerCenter {
	private static final Logger log = Logger.getLogger(ApiManagerCenter.class);
	private static Map<String,ApiModel> map=new HashMap<String,ApiModel>();
	private static Map< Class<?>[],String> classMap=new HashMap<Class<?>[],String>();
	/**
	 * 调用API接口
	 * @param module   模块code
	 * @param method	方法名
	 * @param objs      参数
	 * @return
	 * @throws ApiException
	 */
	public static ApiVaild<String> callToJson(String module,String method,Object... objs) {
		ApiVaild<String> apiVaild=new ApiVaild<String>();
		ApiModel apiModel=map.get(module);
		vaild(apiVaild,apiModel,module,method,objs);
		if(apiVaild.isValid()){
			Object result=apiModel.getMethodAccess().invoke(apiModel.getApiRegister(), apiModel.getMethodIndex(method),objs);
			apiVaild.setResult(JSONArray.toJSONString(result));
		}
		return apiVaild;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> ApiVaild<List<T>> callToList(Class<T> resultType,String module,String method,Object... objs) {
		ApiVaild<List<T>> apiVaild=new ApiVaild<List<T>>();
		ApiModel apiModel=map.get(module);
		vaild(apiVaild,apiModel,module,method,objs);
		if(apiVaild.isValid()){
			Object result=apiModel.getMethodAccess().invoke(apiModel.getApiRegister(), apiModel.getMethodIndex(method),objs);
			apiVaild.setResult((List<T>)result);
		}
		return apiVaild;
	}
	@SuppressWarnings("unchecked")
	public static <T> ApiVaild<T> call(Class<T> resultType,String module,String method,Object... objs) {
		ApiVaild<T> apiVaild=new ApiVaild<T>();
		ApiModel apiModel=map.get(module);
		vaild(apiVaild,apiModel,module,method,objs);
		if(apiVaild.isValid()){
			Object result=apiModel.getMethodAccess().invoke(apiModel.getApiRegister(), apiModel.getMethodIndex(method),objs);
			apiVaild.setResult((T)result);
		}
		return apiVaild;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ApiVaild<T> call(ApiDoc<T> apiDoc,Object... objs) {
		ApiVaild<T> apiVaild=new ApiVaild<T>();
		ApiModel apiModel=map.get(apiDoc.getModule());
		if(vaild(apiVaild,apiModel,apiDoc.getModule(),apiDoc.getMethod(),objs).isValid()){
			try {
				Object result=apiModel.getMethodAccess().invoke(apiModel.getApiRegister(), apiModel.getMethodIndex(apiDoc.getMethod()),objs);
				apiVaild.setResult((T)result);
			} catch (Exception e) {
				apiVaild.setValid(false);
				log.error(e);
			}
		}
		return apiVaild;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean vaild(String module,String method,Object... objs){
		return vaild(new ApiVaild(),module,method,objs).isValid();
	}
	private static <T> ApiVaild<T> vaild(ApiVaild<T> apiVaild,String module,String method,Object... objs){
		
		return vaild(apiVaild,map.get(module),module, method, objs);
	}
	private static <T> ApiVaild<T> vaild(ApiVaild<T> apiVaild,ApiModel apiModel,String module,String method,Object... objs){
		if(null!=apiModel){
			Integer index=apiModel.getMethodIndex(method);
			if(null!=index){
				apiVaild.setValid(true);
			}else{
				log.error("找不到模块："+module+" 的 方法："+method);
				apiVaild.setApiInvalidType(ApiInvalidType.METHOD);
			}
		}else{
			log.error("找不到模块："+module);
			apiVaild.setApiInvalidType(ApiInvalidType.MODULE);
		}
		return apiVaild;
	}
	
	static void put(String module,ApiModel apiModel){
		map.put(module, apiModel);
		classMap.put(apiModel.getApiRegister().getClass().getInterfaces(), module);
	}
	

	/**
	 * 获取Api文档
	 * @param moduleName 模块名称
	 * @return
	 */
	private static ApiRegister getApiDoc(String moduleName) {
		ApiModel apiModel=map.get(moduleName);
		if(null==apiModel){
			log.error("找不到模块："+moduleName);
		}
		return apiModel.getApiRegister();
	}
	/**
	 * 获取Api文档
	 * @param <T>
	 * @param moduleName 模块名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getApiDoc(Class<T> APiClass,String moduleName) {
		ApiRegister apiModel=getApiDoc(moduleName);
		if(null!=apiModel){
			return (T)apiModel;
		}
		return null;
	}
	
	/**
	 * 获取Api文档
	 * @param <T>
	 * @return
	 */
	public static <T> T getApiDoc(Class<T> apiClass) {
		if(null==apiClass){
			return null;
		}
		for(Class<?>[] clazzs:classMap.keySet()){
			for(Class<?> clazz:clazzs){
				if(clazz.equals(apiClass)){
					return getApiDoc(apiClass,classMap.get(clazzs));
				}
			}
		}
		return null;
	}
}
