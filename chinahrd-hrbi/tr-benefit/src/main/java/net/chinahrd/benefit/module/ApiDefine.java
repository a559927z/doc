/**
*net.chinahrd.cache
*/
package net.chinahrd.benefit.module;

import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.api.PerBenefitApi;
import net.chinahrd.benefit.mvc.pc.service.PerBenefitService;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements PerBenefitApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	@Injection
	private PerBenefitService perBenefitService;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}

	public List<PerBenefitDto> getOrgBenefitData(String customerId, String organizationId) {
        return perBenefitService.getOrgBenefitData(customerId, organizationId);
    }
	
	public List<PerBenefitDto> getTrendData(String customerId, String organizationId, String type) {
        List<PerBenefitDto> trendData = null;
        if ("year".equals(type)) {
            trendData = perBenefitService.getTrendByYear(customerId, organizationId);
        } else {
            trendData = perBenefitService.getTrendByMonth(customerId, organizationId);
        }
        return trendData;
    }
}
