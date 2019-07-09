/**
*net.chinahrd.entity.module
*/
package net.chinahrd.api;



import java.util.List;

import net.chinahrd.entity.dto.pc.OrgChartDto;

/**
 * 住址架构的 Api 接口
 * @author htpeng
 *2016年10月17日下午6:09:24
 */
public interface OrganizationalStructureApi {

	List<OrgChartDto> queryChildOrgById(String customerId, String organId);

}
