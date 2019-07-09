/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.api;

import net.chinahrd.core.api.model.ApiModel;
import net.chinahrd.core.module.model.ModuleModel;


/**
 * API注册中心
 * @author htpeng
 *2016年10月13日上午11:58:18
 */
class ApiRegisterCenter {
	// private List<InputStream> list = new ArrayList<InputStream>();
	private static ApiRegisterCenter cacheRegister = null;

	private ApiRegisterCenter() {

	}

	public static ApiRegisterCenter getInstance() {
		if (null == cacheRegister) {
			cacheRegister = new ApiRegisterCenter();
		}
		return cacheRegister;
	}

	void register(ModuleModel moduleModel,ApiRegister apiRegister) {
		ApiModel apiModel= new ApiModel(apiRegister);
		moduleModel.setApiInjection(apiModel);
		ApiManagerCenter.put(moduleModel.getCode(), apiModel);
	}
}
