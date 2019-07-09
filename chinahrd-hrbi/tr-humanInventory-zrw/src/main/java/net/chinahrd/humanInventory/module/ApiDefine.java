/**
*net.chinahrd.cache
*/
package net.chinahrd.humanInventory.module;


import org.apache.log4j.Logger;

import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;

/**
 * @author malong
 *2016-11-16
 */
public class ApiDefine extends ApiRegisterAbstract{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.INTERFACE;
	}

}
