/**
*net.chinahrd.cache
*/
package net.chinahrd.talentProfitLoss.module;

import org.apache.log4j.Logger;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
//	  @Injection
//	  private AccordDismissService accordDismissService;
//	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.INTERFACE;
	}


	
//	public DismissTrendDto queryDisminss4Quarter(String organId,  String customerId){
//		String s=Cache.getInstance().queryQuarterLastDay.get();
//		return accordDismissService.queryDisminss4Quarter(organId,s,customerId);
//	}
}
