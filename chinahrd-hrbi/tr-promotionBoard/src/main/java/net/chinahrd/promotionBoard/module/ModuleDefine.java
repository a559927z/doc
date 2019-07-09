/**
*net.chinahrd.core.cache
*/
package net.chinahrd.promotionBoard.module;

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
 * 
 * @author htpeng 2016年10月8日下午1:42:50
 */
@Lazy(false)
@Component("hrbi-promotionBoard-ModuleDefine")
public class ModuleDefine extends ModuleRegister {

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

	@Override
	protected JobRegister registerJob() {
		return new PromotionAnalysisJob();
	}

}
