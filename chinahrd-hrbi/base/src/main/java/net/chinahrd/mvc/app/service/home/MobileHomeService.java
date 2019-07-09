/**
*net.chinahrd.biz.paper.service
*/
package net.chinahrd.mvc.app.service.home;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;


/**
 * @author htpeng
 *2016年7月14日下午4:03:30
 */
public interface MobileHomeService {

	/**
	 * 获取已添加的管理看板数据
	 * @param customerId
	 * @param empId
	 * @return
	 */
	List<KanbanConfigMobileDto> getAlreadyAddedList(String path,String organId,String customerId,
			String empId,List<String> organList);

	/**
	 * @param ctx
	 * @param customerId
	 * @return
	 */
	List<KanbanConfigMobileDto> getFunctionList(String ctx, String customerId);

	/**
	 * @param ctx
	 * @param orgId
	 * @param customerId
	 * @param empId
	 * @param organPermitId
	 * @return
	 */
	List<KanbanConfigMobileDto> getQuotaByName(String path, String orgId,String name,
			String customerId, String empId, List<String> organPermitId);

}
