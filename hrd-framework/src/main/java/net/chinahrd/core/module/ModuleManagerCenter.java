/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.module;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.core.exception.module.ModuleException;
import net.chinahrd.core.module.model.ModuleModel;

/**
 * 模块管理中心
 * 
 * @author htpeng 2016年10月8日下午1:42:50
 */

public class ModuleManagerCenter {
	private static List<ModuleModel> list = new ArrayList<ModuleModel>();

	
//	/**
//	 * 获取系统加载的所有模块的code
//	 * @return
//	 */
//	public static List<String> getModuleCodeList(){
//		List<String> moduleCodeList=new ArrayList<String>();
//		for(ModuleModel moduleModel:list){
//			moduleCodeList.add(moduleModel.getCode());
//		}
//		return moduleCodeList;
//		
//	}
	/**
	 * @param list
	 *            the list to set
	 */
	static void setMenuModle(ModuleModel moduleModel) {
		vaild(moduleModel);
		list.add(moduleModel);
	}

	static List<ModuleModel> getModuleModel() {
		return list;
	}

	private static void vaild(ModuleModel moduleModel) {
		if (null != moduleModel) {
			for (ModuleModel m : list) {
				if (m.getCode().equals(moduleModel.getCode())) {
					throw new ModuleException("已经存在Code为 " + m.getCode() + " 的模块！");
				}
			}
		}

	}
}
