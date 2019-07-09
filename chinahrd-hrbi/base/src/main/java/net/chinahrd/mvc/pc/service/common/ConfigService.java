package net.chinahrd.mvc.pc.service.common;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.common.ConfigDto;

public interface ConfigService {

	/**
	 * 获取Config配置表
	 * 
	 * @param customerId
	 *            可为空，为空时表示所有客户的配置
	 * @param configKeyPrefix
	 * @return
	 */
	Map<String, List<ConfigDto>> getConfigs(String customerId, String configKeyPrefix);

	/**
	 * 修改系统配置
	 * 
	 * @param list
	 */
	boolean updateSysConfig(String customerId, List<ConfigDto> list);

	/**
	 * 获取需要发送邮箱的员工邮件
	 * 
	 * @param customerId
	 * @param organIds
	 * @return
	 */
	List<String> querySendToEmpList(String customerId, List<String> organIds);
}
