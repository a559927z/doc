/**
*net.chinahrd.core.cache
*/
package net.chinahrd.talentMaps.module;




import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import net.chinahrd.core.api.ApiRegister;
import net.chinahrd.core.cache.CacheRegister;
import net.chinahrd.core.job.JobRegister;
import net.chinahrd.core.menu.MenuRegister;
import net.chinahrd.core.module.ModuleRegister;
import net.chinahrd.core.module.ModuleService;

/**
 * 
 * @author jxzhang on 2016年11月8日
 * @Verdion 1.0 版本
 */
@Lazy(false)
@Component("hrbi-talentMaps-ModuleDefine")
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
		return null;
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
