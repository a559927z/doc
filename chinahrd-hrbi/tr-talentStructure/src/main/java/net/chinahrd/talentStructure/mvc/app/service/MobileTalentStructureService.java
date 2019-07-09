package net.chinahrd.talentStructure.mvc.app.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.talent.structure.TalentstructureDto;


public interface MobileTalentStructureService {

	/**
	 * 编制分析
	 * 
	 * @param organId
	 * @param customerId
	 * @return
	 */
	TalentstructureDto findBudgetAnalyse(String organId, String customerId);

	/**
	 * 预警值设置
	 * @param customerId
	 * @param normal
	 * @param risk
	 * @return
	 */
	boolean updateConfigWarnVal(String customerId, Double normal, Double risk);


	/**
	 * 人力结构数据
	 * @param organId
	 * @param customerId
	 * @return
	 */
	Map<String, Object> getTalentStuctureData(String organId,
			String customerId);

	/**
	 * 根据主序列查询能力层级分布-柱状图 by jxzhang 2016-02-26
	 * @param organId
	 * @param customerId
	 * @param seqId
	 * @return
	 */
	Map<String, Integer> queryAbEmpCountBarBySeqId(String organId,
			String customerId, String seqId);

	/**查询所有序列
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> quertAllSeq(String customerId);
}
