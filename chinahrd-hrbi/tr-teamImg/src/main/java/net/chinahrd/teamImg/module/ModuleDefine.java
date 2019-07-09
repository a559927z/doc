/**
*net.chinahrd.core.cache
*/
package net.chinahrd.teamImg.module;




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
 * @author htpeng
 *2016年10月8日下午1:42:50
 */
@Lazy(false)
@Component("hrbi-teamImg-ModuleDefine")
public  class ModuleDefine extends ModuleRegister  {

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerCache()
	 */
	@Override
	protected CacheRegister registerCache() {
//		return new CacheDefine();
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerMenu()
	 */
	@Override
	protected MenuRegister registerMenu() {
		return new MenuDefine();
	}



	/* (non-Javadoc)
	 * @see net.chinahrd.core.RegisterAbstract#getXmlPath()
	 */
	@Override
	protected String getFilePath() {
		return "module.properties";
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerApi()
	 */
	@Override
	protected ApiRegister registerApi() {
		return new ApiDefine();
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegister#install()
	 */
	@Override
	protected ModuleService install() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegister#uninstall()
	 */
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
