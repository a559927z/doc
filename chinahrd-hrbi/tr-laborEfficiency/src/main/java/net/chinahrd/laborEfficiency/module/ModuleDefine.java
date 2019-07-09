/**
*net.chinahrd.core.cache
*/
package net.chinahrd.laborEfficiency.module;




import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import net.chinahrd.core.api.ApiRegister;
import net.chinahrd.core.cache.CacheRegister;
import net.chinahrd.core.job.JobRegister;
import net.chinahrd.core.menu.MenuRegister;
import net.chinahrd.core.module.ModuleRegister;
import net.chinahrd.core.module.ModuleService;

/**
 * 模块注册中心接口
 * @author jxzhang on 2016-11-08
 */
@Lazy(false)
@Component("hrbi-laborEfficiency-ModuleDefine")
public  class ModuleDefine extends ModuleRegister  {

	@Override
	protected CacheRegister registerCache() {
		return null;
	}

	@Override
	protected MenuRegister registerMenu() {
		return new MenuDefine();
	}


	@Override
	protected String getFilePath() {
		return "module.properties";
	}

	@Override
	protected ApiRegister registerApi() {
		return new ApiDefine();
	}

	@Override
	protected ModuleService install() {
		return null;
	}

	@Override
	protected ModuleService uninstall() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegister#registerJob()
	 */
	@Override
	protected JobRegister registerJob() {
		// TODO Auto-generated method stub
		return null;
	}

}
