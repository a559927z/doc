/**
*net.chinahrd.core.cache
*/
package net.chinahrd.clientImg.module;


import net.chinahrd.core.api.ApiRegister;
import net.chinahrd.core.cache.CacheRegister;
import net.chinahrd.core.job.JobRegister;
import net.chinahrd.core.menu.MenuRegister;
import net.chinahrd.core.module.ModuleRegister;
import net.chinahrd.core.module.ModuleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 模块注册中心接口
 * @author wqcai
 * 2016年12月16日 17:58:27
 */
@Lazy(false)
@Component("hrbi-clientImg-ModuleDefine")
public  class ModuleDefine extends ModuleRegister  {

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerCache()
	 */
	@Override
	protected CacheRegister registerCache() {
		return new CacheDefine();
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerMenu()
	 */
	@Override
	protected MenuRegister registerMenu() {
		// TODO Auto-generated method stub
		return new MenuDefine();
	}



	/* (non-Javadoc)
	 * @see net.chinahrd.core.RegisterAbstract#getXmlPath()
	 */
	@Override
	protected String getFilePath() {
		// TODO Auto-generated method stub
		return "module.properties";
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerApi()
	 */
	@Override
	protected ApiRegister registerApi() {
//		return new ApiDefine();
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegister#install()
	 */
	@Override
	protected ModuleService install() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegister#uninstall()
	 */
	@Override
	protected ModuleService uninstall() {
		// TODO Auto-generated method stub
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
