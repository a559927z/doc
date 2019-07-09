/**
*net.chinahrd.cache
*/
package net.chinahrd.dismissRisk.module;

import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.api.DismissRiskApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.dismissRisk.mvc.pc.service.DismissRiskService;
import net.chinahrd.entity.dto.pc.common.RiskTreeDto;

/**
 * @author htpeng 2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements DismissRiskApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	@Injection
	private DismissRiskService dismissRiskService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.INTERFACE;
	}

	@Override
	public List<RiskTreeDto> getEmpRiskDetail(String customerId, String empId) {
		return dismissRiskService.getEmpRiskDetail(customerId, empId);
	}

}
