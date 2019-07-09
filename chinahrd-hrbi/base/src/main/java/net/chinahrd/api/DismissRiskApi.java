package net.chinahrd.api;

import java.util.List;

import net.chinahrd.entity.dto.pc.common.RiskTreeDto;

/**
 * 人才流失风险定义的api接口
 */
public interface DismissRiskApi {

	/**
	 * 查询员工的离职风险树
	 */
	List<RiskTreeDto> getEmpRiskDetail(String customerId, String empId);

}
