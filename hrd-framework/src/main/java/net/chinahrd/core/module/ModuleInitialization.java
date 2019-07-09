package net.chinahrd.core.module;

import java.util.List;

import javax.servlet.ServletConfig;

import net.chinahrd.core.InjectionModel;
import net.chinahrd.core.cache.CacheSessionManager;
import net.chinahrd.core.cache.CacheTimer;
//import net.chinahrd.core.customize.CustiomizeRegister;
//import net.chinahrd.core.customize.CustomizeManager;
//import net.chinahrd.core.customize.CustomizeRegisterService;
//import net.chinahrd.core.customize.model.CustomizeLayout;
import net.chinahrd.core.menu.model.Menu;
import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.core.web.eis.license.LicenseVerify;
import net.chinahrd.core.web.init.AbstractDataInitialization;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 初始化系统模块
 * 
 * @author htpeng 2016年2月2日下午2:27:07
 */
public class ModuleInitialization extends AbstractDataInitialization {
	
	public void init(ServletConfig config) {
		//模块信息同步
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		List<ModuleModel> list=ModuleManagerCenter.getModuleModel();
		ModuleSynchronization moduleSynchronization = (ModuleSynchronization) wac.getBean("moduleSynchronization");
		moduleSynchronization.synchronization(list);
		
		// 为缓存 注入sqlSessionFactory
		CacheSessionManager.setSqlSessionFactory((SqlSessionFactory) wac.getBean("sqlSessionFactory"));
		
		
//		CustomizeRegisterService custiomizeRegister=new CustiomizeRegister();
//		custiomizeRegister.setWebApplicationContext(wac);
//		custiomizeRegister.reister(list);
//		CustomizeManager.setCustomizeService(custiomizeRegister);
		for(ModuleModel m:list){
			ModuleLocal.setModuleModel(m);
			if(null!=m.getApiInjection())m.getApiInjection().injecton();
			if(null!=m.getJobInjection())
				for(InjectionModel i:m.getJobInjection()){
					i.injecton();
				}
			CacheTimer.loading(m.getCacheClass());
		}
		for(ModuleModel m:list){
			for(Menu menu:m.getMenuModel().getMenus()){
				LicenseVerify.setMenus(menu.getUrl());
			}
		}
		LicenseVerify.over();
	}

}