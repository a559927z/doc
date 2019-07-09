package net.chinahrd.mvc.pc.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.common.ConfigDto;

@Repository("configDao")
public interface ConfigDao {

	/**
	 * 查询config配置参数
	 * 
	 * @param customerId
	 * @param configKeyPrefix
	 * @return
	 */
	List<ConfigDto> queryConfig(@Param("customerId") String customerId,
			@Param("configKeyPrefix") String configKeyPrefix);

	/**
	 * 修改系统配置
	 * 
	 * @param list
	 */
	void updateSysConfig(@Param("customerId") String customerId, @Param("list") List<ConfigDto> list);

	/**
	 * 获取需要发送邮箱的员工邮件
	 * 
	 * @param customerId
	 * @return
	 */
	List<String> querySendToEmpList(@Param(value = "customerId") String customerId, @Param("organIds") List<String> organIds);
}
