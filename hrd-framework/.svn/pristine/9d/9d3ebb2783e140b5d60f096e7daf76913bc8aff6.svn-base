/**
 *net.chinahrd.core.cache
 */
package net.chinahrd.core.menu;

import java.io.InputStream;

import net.chinahrd.core.menu.config.MenuXmlRead;
import net.chinahrd.core.menu.model.MenuModel;
import net.chinahrd.core.xml.XmlRead;

/**
 * @author htpeng 2016年10月8日下午1:42:50
 */

class MenuRegisterCenter {
	private static MenuRegisterCenter menuRegister = null;
//	private static List<>
	private MenuRegisterCenter() {

	}

	public static MenuRegisterCenter getInstance() {
		if (null == menuRegister) {
			menuRegister = new MenuRegisterCenter();
		}
		return menuRegister;
	}

	MenuModel register(InputStream ins) {
		XmlRead xmlRead = new MenuXmlRead();
		xmlRead.load(ins);
//		return MenuManagerCenter.setMenuModle(new MenuModel(xmlRead.getXmlModel()));
		return new MenuModel(xmlRead.getXmlModel());
	}

}
