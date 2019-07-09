package net.chinahrd.talentStructure.mvc.app.dao;
import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.talent.structure.TalentstructureDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
 * 
 * @author htpeng
 *2016年4月7日下午2:22:01
 */
@Repository("mobileTalentStructureDao")
public interface MobileTalentStructureDao {

	/**
	 * 编制分析
	 * 
	 * @param organId
	 * @param customerId
	 * @param now
	 * @param personTypeKey
	 *            配置人员范围
	 * @return
	 */
	TalentstructureDto findBudgetAnalyse(@Param("organId") String organId,
			@Param("customerId") String customerId, @Param("now") String now,
			@Param("personTypeKey") List<Integer> personTypeKey);

	/**
	 * 预警值设置
	 * 
	 * @param customerId
	 * @param normal
	 * @param risk
	 */
	void updateConfigWarnValByNormal(@Param("customerId") String customerId,
			@Param("normal") Double normal);
	void updateConfigWarnValByRisk(@Param("customerId") String customerId,
			@Param("risk") Double risk);
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	List<String> queryOrganId(@Param("customerId") String customerId);

	/**
	 * 人力结构核心接口
	 * 
	 * @param customerId
	 * @return
	 */
	List<TalentstructureDto> queryEmpInfo(@Param("organId") String organId,
			@Param("customerId") String customerId);
	
	/**
	 * 序列点击查询
	 * @param organId
	 * @param customerId
	 * @param seqId
	 * @return
	 */
	List<TalentstructureDto> queryAbEmpCountBarBySeqId(@Param("organId") String organId,
			@Param("customerId") String customerId, @Param("seqId") String seqId);

	/**
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> quertAllSeq(@Param("customerId")String customerId);
}
