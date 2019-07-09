package net.chinahrd.api;

import java.util.List;

import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;

/**
 * 投入产出分析的 Api 接口
 * @author xwli
 * 2017年5月8日
 */
public interface PerBenefitApi {

	List<PerBenefitDto> getOrgBenefitData(String customerId, String organizationId);
	
	List<PerBenefitDto> getTrendData(String customerId, String organizationId, String type);
}
