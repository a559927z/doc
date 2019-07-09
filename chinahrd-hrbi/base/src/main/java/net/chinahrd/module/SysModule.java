/**
*net.chinahrd.core.cache
*/
package net.chinahrd.module;






import net.chinahrd.core.api.ApiRegister;
import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheRegister;
import net.chinahrd.core.job.JobRegister;
import net.chinahrd.core.menu.MenuRegister;
import net.chinahrd.core.module.ModuleRegister;
import net.chinahrd.core.module.ModuleService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 模块注册中心
 * @author htpeng
 *2016年10月8日下午1:42:50
 */
@Lazy(false)
@Component("hrd-base-module")
public  class SysModule extends ModuleRegister  {
	public static void main(String[] df){
		System.out.println(CacheBlock.class.getInterfaces()[0]);
	}
	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerCache()
	 */
	@Override
	protected CacheRegister registerCache() {
		return new SysCacheDefine();
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.module.ModuleRegisterAbstract#registerMenu()
	 */
	@Override
	protected MenuRegister registerMenu() {
		return new SysMenuDefine();
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
		return new SysApiDefine();
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
		return new SysJob().associate(new SysProcessJob());
	}

}
