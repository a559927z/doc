/**
*net.chinahrd.cache
*/
package net.chinahrd.laborEfficiency.module;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.chinahrd.api.LaborEfficiencyApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.laborEfficiency.mvc.pc.service.LaborEfficiencyService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;


/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements LaborEfficiencyApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	@Injection
	private LaborEfficiencyService laborEfficiencyService;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}

	public Map<String, Object> getLaborEfficiencyRatio(String customerId, String organId, String beginTime, String endTime, String populationIds) {
		Map<String, Object> map = CollectionKit.newMap();
		String time = DateUtil.getDBNow().replace("-", "").substring(0, 6);
		if (beginTime == null) {
			beginTime = time;
		}
		if (endTime == null) {
			endTime = time;
		}
		map.putAll(laborEfficiencyService.queryLaborEfficiencyRatio(customerId, organId, beginTime, endTime, populationIds, null));
		return map;
	}
	
	public Map<String, Object> queryOvertimeByOrgan(String customerId, String organId, String beginTime, String endTime, String populationIds) {
		String time = DateUtil.getDBNow().replace("-", "").substring(0, 6);
		if (beginTime == null) {
			beginTime = time;
		}
		if (endTime == null) {
			endTime = time;
		}
		return laborEfficiencyService.queryOvertimeByOrgan(customerId, organId, beginTime, endTime, populationIds, null);
	}
	
}
