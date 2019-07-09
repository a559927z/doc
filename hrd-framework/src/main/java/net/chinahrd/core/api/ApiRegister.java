/**
*net.chinahrd.core.api
*/
package net.chinahrd.core.api;

import net.chinahrd.core.RegisterInterface;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.core.module.model.ModuleModel;

/**
 * API注册中心
 * @author htpeng
 *2016年10月13日上午11:00:50
 */
public interface ApiRegister extends RegisterInterface{
	String IGNORE_METHOD="getApiType,register,setModuleModel";
	ApiType getApiType();
	void setModuleModel(ModuleModel moduleModel);
}

