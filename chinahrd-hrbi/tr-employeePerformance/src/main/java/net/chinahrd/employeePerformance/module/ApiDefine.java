/**
*net.chinahrd.cache
*/
package net.chinahrd.employeePerformance.module;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.chinahrd.api.EmployeePerformanceApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.employeePerformance.mvc.pc.dao.PerChangeApiDao;
import net.chinahrd.employeePerformance.mvc.pc.service.PerChangeService;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceEmpDto;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements EmployeePerformanceApi{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	@Injection
	private PerChangeApiDao perChangeApiDao;
	@Injection
	private PerChangeService perChangeService;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}


	  /**
     * 管理者首页-绩效目标 部门
     *
     * @param organId
     * @return
     */
	public List<PerformanceDto> queryPerformance(String organId, String customerId){
    	return perChangeApiDao.queryPerformance(organId, customerId);
    }

    /**
     * 管理者首页-绩效目标 下属
     *
     * @param organId
     * @return
     */
	public  List<PerformanceEmpDto> queryPerformanceEmp( String organId, String customerId){
    	return perChangeApiDao.queryPerformanceEmp(organId, customerId);
    }

	public List<PerfChangeCountDto> queryPerchangeByOrgan(String customerId, String organId, String yearMonth) {
    	return perChangeService.queryPerchangeByOrgan(customerId, organId, Integer.parseInt(yearMonth));
    }
	
	public List<Integer> getPerfChangeDate(String customerId) {
        int perWeek = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GRJXJBH_PERFMANWEEK).intValue();
        //查询绩效日期
        List<Integer> listYM = perChangeService.queryPreYearMonthByCustomerId(customerId, perWeek);
        return listYM;
    }
	
	public Map<String, PreChangeStatusDto> getPreChangeCountData(String customerId, String organizationId, List<Integer> yearMonths) {
        Integer beginYearMonth = yearMonths.get(1);
        Integer endYearMonth = yearMonths.get(0);

        //查询升降1级以上和没变化的
        PreChangeStatusDto dto1 = perChangeService.queryPreChangeCountByMonth(organizationId, beginYearMonth,
                endYearMonth, 1, customerId, null);

        //查询升降2级以上
        PreChangeStatusDto dto2 = perChangeService.queryPreChangeCountByMonth(organizationId, beginYearMonth,
                endYearMonth, 2, customerId, null);
        int empCount = 0;
        //组装数据到前台
        Map<String, PreChangeStatusDto> map = CollectionKit.newMap();
        if (dto1 != null) {
            //查询部门总人数 这里Equal仅仅是用于数据的传输,实际上是人员总数
            empCount = dto1.getDown() + dto1.getEqual() + dto1.getRise();
            map.put("change", dto1);
        }
        if (dto2 != null) {
            dto2.setEqual(empCount);
            map.put("bigChange", dto2);
        }

        return map;
    }
}
