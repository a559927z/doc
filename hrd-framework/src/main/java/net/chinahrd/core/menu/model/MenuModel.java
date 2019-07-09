/**
*net.chinahrd.core.menu.modle
*/
package net.chinahrd.core.menu.model;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;

/**
 * 菜单模板
 * @author htpeng
 *2016年10月12日下午2:04:39
 */
public class MenuModel {
	private String parentName="";
	private List<Menu> menus=new ArrayList<Menu>();
	
	public MenuModel(){
		
	}
	
	public MenuModel(XmlModel xmlModel){
		this.parentName=xmlModel.getAttribute(ConfigEnum.NAME);
		for(XmlModel x:xmlModel.getNodes()){
			menus.add(new Menu(x));
		}
	}
	
	
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}



	/**
	 * @return the menus
	 */
	public List<Menu> getMenus() {
		return menus;
	}

	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		String str="\n父级节点:"+this.parentName;
		for(Menu m:menus){
			str+=m.toString();
		}
		return str;
	}


	
	
	
}
