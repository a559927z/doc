/**
*net.chinahrd.entity.module.define
*/
package net.chinahrd.api;

import java.util.List;
import java.util.Map;

/**劳动力效能模块API文档定义
 * @author htpeng
 *2016年10月18日上午10:24:26
 */
public interface LaborEfficiencyApi {
	Map<String, Object> getLaborEfficiencyRatio(String customerId, String organId, String beginTime, String endTime, String populationIds);
	
	Map<String, Object> queryOvertimeByOrgan(String customerId, String organId, String beginTime, String endTime, String populationIds);
}
