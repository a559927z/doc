/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.menu;



import net.chinahrd.core.RegisterInterface;
import net.chinahrd.core.module.model.ModuleModel;


/**
 * 菜单和功能权限注册中心
 *  子类使用
	两个注解
 * @author htpeng
 *2016年10月8日下午1:42:50
 */

public interface MenuRegister  extends RegisterInterface{
	void setModuleModel(ModuleModel moduleModel);

}
