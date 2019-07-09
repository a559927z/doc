/**
*net.chinahrd.core.module.modle
*/
package net.chinahrd.core.module.model;


import java.util.ArrayList;
import java.util.List;

import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.InjectionModel;
import net.chinahrd.core.ReadModel;
import net.chinahrd.core.menu.model.MenuModel;
/**模块模型
 * @author htpeng
 *2016年10月12日下午5:47:51
 */
public class ModuleModel {
	private String moduleId;
	private String code;
	private String name;
	private String version;
	private int staus=1; //默认 为1
	
	private MenuModel menuModel;
	private InjectionModel apiInjection;
	private List<InjectionModel> jobInjectionList;
	Class<?> cacheDaoClass;
	Class<?> cacheClass;
	/**
	 * @return the cacheDaoClass
	 */
	public Class<?> getCacheDaoClass() {
		return cacheDaoClass;
	}
	/**
	 * @param cacheDaoClass the cacheDaoClass to set
	 */
	public void setCacheDaoClass(Class<?> cacheDaoClass) {
		this.cacheDaoClass = cacheDaoClass;
	}
	
	/**
	 * @return the cacheClass
	 */
	public Class<?> getCacheClass() {
		return cacheClass;
	}
	/**
	 * @param cacheClass the cacheClass to set
	 */
	public void setCacheClass(Class<?> cacheClass) {
		this.cacheClass = cacheClass;
	}
	public ModuleModel(){
		
	}
	/**
	 * @param xmlModel
	 */
	public ModuleModel(ReadModel xmlModel) {
		this.code=xmlModel.getAttribute(ConfigEnum.CODE);
		this.name=xmlModel.getAttribute(ConfigEnum.NAME);
		this.version=xmlModel.getAttribute(ConfigEnum.VERSION);
	
	}
	
	

	/**
	 * @return the apiInjection
	 */
	public InjectionModel getApiInjection() {
		return apiInjection;
	}
	/**
	 * @param apiInjection the apiInjection to set
	 */
	public void setApiInjection(InjectionModel apiInjection) {
		this.apiInjection = apiInjection;
	}
	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}


	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStaus() {
		return staus;
	}
	public void setStaus(int staus) {
		this.staus = staus;
	}
	

	/**
	 * @return the jobInjection
	 */
	public List<InjectionModel> getJobInjection() {
		return jobInjectionList;
	}
	/**
	 * @param jobInjection the jobInjection to set
	 */
	public void setJobInjection(InjectionModel jobInjection) {
		if(null==jobInjectionList){
			jobInjectionList=new ArrayList<InjectionModel>();
		}
		jobInjectionList.add(jobInjection);
//		this.jobInjection = jobInjection;
	}
	/**
	 * @return the menuModel
	 */
	public MenuModel getMenuModel() {
		return menuModel;
	}
	/**
	 * @param menuModel the menuModel to set
	 */
	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="code:"+code+";name:"+name+";version:"+version+";";
//		str+="\n"+menuModel;
		return str;
	}
	
	
}
