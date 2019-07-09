/**
*net.chinahrd.cache
*/
package net.chinahrd.accordDismiss.module;

import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.accordDismiss.mvc.pc.service.AccordDismissService;
import net.chinahrd.api.AccordDismissApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements AccordDismissApi{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	  @Injection
	  private AccordDismissService accordDismissService;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}


	
	public DismissTrendDto queryDisminss4Quarter(String organId, String customerId){
		DismissTrendDto dto=accordDismissService.queryDisminss4Quarter(organId,Cache.queryQuarterLastDay.get(),customerId);
		return dto;
	}
	
	public List<DismissRecordDto> getDismissRecord(String customerId, String organizationId, String prevQuarter, String yearMonths) {
        return accordDismissService.queryDismissRecord(customerId, organizationId, yearMonths, prevQuarter);
    }
}
