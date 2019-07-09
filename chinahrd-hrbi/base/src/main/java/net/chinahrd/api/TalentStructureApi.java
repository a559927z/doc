package net.chinahrd.api;

import java.util.Map;

import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;

/**
 * 人力结构定义的 Api 接口
 * @author xwli
 * 2017年5月5日
 */
public interface TalentStructureApi {
	TalentStructureDto getBudgetAnalyse(String organId, String customerId);
	
	Map<String, Object> getTalentStuctureByMonth(String organId, String customerId);
}
