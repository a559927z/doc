/**
*net.chinahrd.cache
*/
package net.chinahrd.module;

import net.chinahrd.core.menu.MenuRegisterAbstract;

/**
 * @author htpeng
 *2016年10月11日下午11:52:52
 */
public class SysMenuDefine extends MenuRegisterAbstract{

	/* (non-Javadoc)
	 * @see net.chinahrd.core.menu.MenuRegisterAbstract#getFileInputSteam()
	 */
	@Override
	protected String getXmlPath() {
		// TODO Auto-generated method stub
		return "menu.xml";
	}



}
