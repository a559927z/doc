/**
*net.chinahrd.core.menu
*/
package net.chinahrd.core.module;

import java.util.List;

import net.chinahrd.core.module.model.ModuleModel;




/**模块与数据库同步
 *   @author htpeng
 *2016年10月12日下午2:59:04
 */

public interface ModuleSynchronization {

	/**
	 * 
	 */
	public void synchronization(List<ModuleModel> list) ;
	
}
