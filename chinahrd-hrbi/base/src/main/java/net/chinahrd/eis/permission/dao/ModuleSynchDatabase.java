/**
*net.chinahrd.core.menu
*/
package net.chinahrd.eis.permission.dao;

import java.util.List;

import net.chinahrd.core.menu.model.MenuEntity;
import net.chinahrd.core.module.model.ModuleModel;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author htpeng
 *2016年10月12日下午3:34:15
 */
@Repository("moduleSynchDatabase")
public interface ModuleSynchDatabase {
	List<MenuEntity> findAllFunction(@Param("customerId") String customerId);
	
	List<ModuleModel> queryModule();
	

	void insertModule(@Param("dto")ModuleModel m);
}
