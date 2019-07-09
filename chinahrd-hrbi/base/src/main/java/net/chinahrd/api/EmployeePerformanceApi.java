/**
*net.chinahrd.api
*/
package net.chinahrd.api;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceEmpDto;

/**
 * @author htpeng
 *2016年10月19日下午4:44:40
 */
public interface EmployeePerformanceApi {
	
	/**
	 * 
	 */
	List<PerformanceDto> queryPerformance(String organId, String customerId);
  

   /**
    * 管理者首页-绩效目标 下属
    *
    * @param organId
    * @return
    */
	
	List<PerformanceEmpDto> queryPerformanceEmp( String organId, String customerId);
	  
	List<PerfChangeCountDto> queryPerchangeByOrgan(String customerId, String organId, String yearMonth);
	
	List<Integer> getPerfChangeDate(String customerId);
	
	Map<String, PreChangeStatusDto> getPreChangeCountData(String customerId, String organizationId, List<Integer> yearMonths);
}
