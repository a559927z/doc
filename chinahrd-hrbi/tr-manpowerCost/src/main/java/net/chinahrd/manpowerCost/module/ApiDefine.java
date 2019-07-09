/**
*net.chinahrd.cache
*/
package net.chinahrd.manpowerCost.module;

import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.api.ManpowerCostApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;
import net.chinahrd.manpowerCost.mvc.pc.dao.ManpowerCostApiDao;
import net.chinahrd.manpowerCost.mvc.pc.service.ManpowerCostService;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements ManpowerCostApi{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	@Injection
	private ManpowerCostApiDao manpowerCostApiDao;
	
	@Injection
	private ManpowerCostService manpowerCostService;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.INTERFACE;
	}


//	  /**
//     * 管理者首页-绩效目标 部门
//     *
//     * @param organId
//     * @return
//     */
//    List<PerformanceDto> queryPerformance(String organId, String customerId){
//    	return perChangeApiDao.queryPerformance(organId, customerId);
//    }
//
//    /**
//     * 管理者首页-绩效目标 下属
//     *
//     * @param organId
//     * @return
//     */
//    List<PerformanceEmpDto> queryPerformanceEmp( String organId, String customerId){
//    	return perChangeApiDao.queryPerformanceEmp(organId, customerId);
//    }
	
	public List<ManpowerItemDto> queryItemDetail(String customerId, String organId, String time) {
        return manpowerCostService.queryItemDetail(customerId, organId, time);
    }
	
	public List<ManpowerDto> getAllDetailData(String customerId, String organId, String time) {
        return manpowerCostService.queryAllDetailData(customerId, organId, time);
    }
	
	public List<ManpowerCompareDto> getProportionYear(String customerId, String organId, String time) {
        return manpowerCostService.queryProportionYear(customerId, organId, time);
    }
}
