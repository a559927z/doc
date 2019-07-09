/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.menu;

import net.chinahrd.core.RegisterAbstract;
import net.chinahrd.core.module.model.ModuleModel;

/**
 * 菜单注册中心 子类使用
 * 
 * @author htpeng 2016年10月8日下午1:42:50
 */

public abstract class MenuRegisterAbstract extends RegisterAbstract implements MenuRegister {
	private ModuleModel moduleModel;

	@Override
	public void register() {
		this.moduleModel.setMenuModel(MenuRegisterCenter.getInstance().register(getInputStream(getXmlPath())));
		;
	}

	@Override
	public void setModuleModel(ModuleModel moduleModel) {
		this.moduleModel = moduleModel;
	}

}
