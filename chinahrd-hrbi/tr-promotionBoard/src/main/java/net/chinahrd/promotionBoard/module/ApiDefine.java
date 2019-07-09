/**
*net.chinahrd.cache
*/
package net.chinahrd.promotionBoard.module;

import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;

import org.apache.log4j.Logger;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}
}
