/**
*net.chinahrd.core
*/
package net.chinahrd.core;

import net.chinahrd.core.ConfigEnum;

/**
 * @author htpeng
 *2016年10月13日下午2:38:28
 */
public interface ReadModel {
	String getAttribute(String key);
	String getAttribute(ConfigEnum key);
}
