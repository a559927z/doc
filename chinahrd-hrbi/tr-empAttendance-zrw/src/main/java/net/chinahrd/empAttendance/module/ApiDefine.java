/**
*net.chinahrd.cache
*/
package net.chinahrd.empAttendance.module;

import org.apache.log4j.Logger;

import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;

/**
 * @author xwli
 *2016年11月22日
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
