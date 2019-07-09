/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.module;


import java.io.InputStream;

import net.chinahrd.core.Read;
import net.chinahrd.core.RegisterAbstract;
import net.chinahrd.core.api.ApiRegister;
import net.chinahrd.core.cache.CacheRegister;
import net.chinahrd.core.job.JobRegister;
import net.chinahrd.core.menu.MenuRegister;
import net.chinahrd.core.menu.config.MenuXmlRead;
import net.chinahrd.core.module.config.FileType;
import net.chinahrd.core.module.model.ModuleModel;
import net.chinahrd.core.props.PropsRead;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * 模块注册中心
 *  子类使用
 *  @Lazy(false)
	@Component
	两个注解
 * 建议实现类名为 `模块名_Moudule` 或者@Component("模块名_Moudule")
 * @author htpeng
 *2016年10月8日下午1:42:50
 */

public abstract class ModuleRegister extends RegisterAbstract implements InitializingBean  {
	private static Logger log=Logger.getLogger(ModuleRegister.class);
	/**
	 * 默认提供获取方法
	 * @return 文件在包项目中的路径    建议不以  "/" 绝对路径开头
	 */
	protected abstract String getFilePath();
	protected  String getXmlPath(){
		return getFilePath();
	}

	/**
	 * 注册缓存
	 * @return
	 */
	protected abstract CacheRegister registerCache();
	
	
	

	/**
	 * 注册菜单
	 * @return
	 */
	protected abstract MenuRegister registerMenu();
	
	
	/**
	 * 注册对外API
	 * @return
	 */
	protected abstract ApiRegister registerApi();
	
	/**
	 * 注册定时任务
	 * @return
	 */
	protected abstract JobRegister registerJob();
	
	
	
	/**
	 * 模块安装
	 * @return
	 */
	 protected abstract ModuleService install();
	
	/**
	 * 模块卸载
	 * @return
	 */
	 protected abstract ModuleService uninstall();
	
//	
//	/**
//	 * 注册Menu
//	 * @return
//	 */
//	protected abstract CacheRegisterInterface registerCache();
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		register();
	}
	
	private void register() {
		Read read=getRead(FileType.getFileType(getFilePath()));
		InputStream ins=getInputStream(getFilePath());
		if(null==ins){
			return;
		}
		read.load(ins);
		ModuleModel moduleModel=new ModuleModel(read.getModel());
		ModuleManagerCenter.setMenuModle(moduleModel);
		log.info("Loadind module:"+moduleModel);
		
		ApiRegister apiRegister=registerApi();
		if(null!=apiRegister){
			apiRegister.setModuleModel(moduleModel);
			apiRegister.register();
		}
		
		CacheRegister cacheRegisterInterface=registerCache();
		if(null!=cacheRegisterInterface){
			cacheRegisterInterface.setModuleModel(moduleModel);
			cacheRegisterInterface.register();
		}
		
		
		MenuRegister menuRegisterInterface=registerMenu();
		if(null!=menuRegisterInterface){
			menuRegisterInterface.setModuleModel(moduleModel);
			menuRegisterInterface.register();
		}
		
		JobRegister jobRegister=registerJob();
		if(null!=jobRegister){
			jobRegister.setModuleModel(moduleModel);
			jobRegister.register();
		}
		
		
//		log.debug("Load complete module:"+moduleModel);
		
	}
	
	private Read getRead(FileType fileType){
		switch (fileType) {
		case XML:
			return new MenuXmlRead();
		case PROPS:
			return new PropsRead();
		}
		return null;
	}
}
