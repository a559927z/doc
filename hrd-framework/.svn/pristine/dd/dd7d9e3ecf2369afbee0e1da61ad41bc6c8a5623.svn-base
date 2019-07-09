/**
*net.chinahrd.entity.module.define
*/
package net.chinahrd.core.api.define;

import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.core.api.model.ApiVaild;

/**API文档
 * @author htpeng
 *2016年10月18日上午10:11:41
 */
public class ApiDoc<T> {
	private String module;
	private String method;
	
	public ApiVaild<T> get(Object...objs){
		return ApiManagerCenter.call(this, objs);
	}
	public ApiDoc(String module, String method) {
		super();
		this.module = module;
		this.method = method;
	}
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
}
