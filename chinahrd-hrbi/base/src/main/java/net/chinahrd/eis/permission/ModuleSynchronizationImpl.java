/**
*net.chinahrd.core.menu
*/
package net.chinahrd.eis.permission;

import java.util.List;




//import net.chinahrd.core.menu.MenuManagerCenter;
import net.chinahrd.core.menu.model.MenuModel;
import net.chinahrd.core.module.ModuleSynchronization;
import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.eis.permission.dao.ModuleSynchDatabase;
import net.chinahrd.utils.Identities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**维持数据库功能菜单同xml同步
 *   @author htpeng
 *2016年10月12日下午2:59:04
 */
@Component("moduleSynchronization")
public class ModuleSynchronizationImpl implements  ModuleSynchronization{

	@Autowired
	ModuleSynchDatabase moduleSynchDatabase;
	@Override
	public void synchronization(List<ModuleModel> list) {
		List<ModuleModel> db_list=moduleSynchDatabase.queryModule();
		if(null!=db_list){
			for(ModuleModel m:list){
				boolean isExist=false;
				for(ModuleModel db:db_list){
					if(db.getCode().equals(m.getCode())){
						isExist=true;
					}
				}
				if(!isExist){
					//插入
					m.setModuleId(Identities.uuid2());
					moduleSynchDatabase.insertModule(m);
					MenuModel menuModel=m.getMenuModel();
					menuModel.getMenus();
				}
			}
		}else{
			for(ModuleModel m:list){
//				m.setModuleId(Identities.uuid2());
				moduleSynchDatabase.insertModule(m);
			}
		}
		
////	List<>

		//		//整合菜单Menu
////		List<>
//		for(MenuModel m:list){
//			System.out.println(m);
//		}
//		for(MenuModel ms:list){
//			boolean isExist=false;
//			String parentFuntionId="";
//			for(MenuEntity me:db_list){
//				if(ms.getParentName().equals(me.getFunctionName())&&(null==me.getFunctionParentId()||me.getFunctionParentId().length()==0)){
//					parentFuntionId=me.getFunctionId();
//					isExist=true;
//					break;
//				}
//			}
//			if(!isExist){
//				//插入父级节点
//				parentFuntionId="";
//				//插入菜单
//				//插入块
//			}else{
//				isExist=false;
//				String menuFuntionId="";
//				for(Menu m:ms.getMenus()){
//					for(MenuEntity me:db_list){
//						if(me.getFunctionParentId().equals(parentFuntionId)&&(me.getUrl().equals(m.getUrl()))){
//							menuFuntionId=me.getFunctionId();
//							isExist=true;
//							break;
//						}
//					}
//					if(!isExist){
//						//插入菜单节点
//						menuFuntionId="";
//						//插入块
//					}else{
//						for(Block b:m.getBlocks()){
//							for(MenuEntity me:db_list){
//								if(me.getFunctionId().equals(menuFuntionId)){
//									isExist=false;
//									String blockId="";
//									for(BlockEntity be:me.getBlocks()){
//										if(be.getItemCode().equals(b.getCode())){
//											blockId=me.getFunctionId();
//											isExist=true;
//											break;
//										}
//									}
//									if(!isExist){
//										//插入菜单节点
//										blockId="";
//										//插入块
//									}
//								}
//							}
//							
//						}
//					}
//				}
//			}
//		}

	}
	
}
