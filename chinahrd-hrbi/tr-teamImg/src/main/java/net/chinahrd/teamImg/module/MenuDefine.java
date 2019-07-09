/**
*net.chinahrd.cache
*/
package net.chinahrd.teamImg.module;


import net.chinahrd.core.menu.MenuRegisterAbstract;

/**
 * @author htpeng
 *2016年10月11日下午11:52:52
 */
public class MenuDefine extends MenuRegisterAbstract{

	/* (non-Javadoc)
	 * @see net.chinahrd.core.menu.MenuRegisterAbstract#getFileInputSteam()
	 */
	@Override
	protected String getXmlPath() {
		return "menu.xml";
	}


}
