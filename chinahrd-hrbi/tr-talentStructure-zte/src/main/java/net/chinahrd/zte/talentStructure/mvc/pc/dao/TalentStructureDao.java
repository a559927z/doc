package net.chinahrd.zte.talentStructure.mvc.pc.dao;

import java.util.Date;
import java.util.List;

import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgListDto;
import net.chinahrd.entity.dto.pc.teamImg.TeamImgEmpDto;
import net.chinahrd.entity.dtozte.pc.talentstructure.TalentstructureDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 人力结构Dao
 * 
 * @author jxzhang by 2016-02-21
 */
@Repository("talentStructureDao")
public interface TalentStructureDao {

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
										  @Param("customerId") String customerId,
										  @Param("day") Date day,
										  @Param("crowds") List<String> crowds);

	/**
	 * 序列点击查询
	 * @param organId
	 * @param customerId
	 * @param seqId
	 * @return
	 */
	List<TalentstructureDto> queryAbEmpCountBarBySeqId(@Param("organId") String organId,
													   @Param("customerId") String customerId,
													   @Param("seqId") String seqId,
													   @Param("day") Date day,
													   @Param("crowds") List<String> crowds);

	List<TeamImgEmpDto> queryTeamImg(@Param("organId") String organId,
									 @Param("customerId") String customeId,
									 @Param("day") Date day,
									 @Param("crowds") List<String> crowds);


	//男女占比
	List<TalentStructureTeamImgDto> getSexData(@Param("organId") String organId,
									 @Param("customerId") String customeId,
									 @Param("day") Date day,
									 @Param("crowds") List<String> crowds);
	Integer getSexDataListCount(@Param("organId") String organId,
											    @Param("customerId") String customeId,
											    @Param("day") Date day,
											    @Param("crowds") List<String> crowds,
											    @Param("id")String id);
	List<TalentStructureTeamImgListDto> getSexDataList(@Param("organId") String organId,
													   @Param("customerId") String customeId,
													   @Param("day") Date day,
													   @Param("crowds") List<String> crowds,
													   @Param("id")String id,
													   @Param("offset")Integer offset,
													   @Param("limit")Integer limit);

	//婚姻状况
	List<TalentStructureTeamImgDto> getMarryStatusData(@Param("organId") String organId,
											   @Param("customerId") String customeId,
											   @Param("day") Date day,
											   @Param("crowds") List<String> crowds);
	Integer getMarryStatusDataListCount(@Param("organId") String organId,
								@Param("customerId") String customeId,
								@Param("day") Date day,
								@Param("crowds") List<String> crowds,
								@Param("id")String id);
	List<TalentStructureTeamImgListDto> getMarryStatusDataList(@Param("organId") String organId,
													   @Param("customerId") String customeId,
													   @Param("day") Date day,
													   @Param("crowds") List<String> crowds,
													   @Param("id")String id,
													   @Param("offset")Integer offset,
													   @Param("limit")Integer limit);

	//血型
	List<TalentStructureTeamImgDto> getBloodData(@Param("organId") String organId,
													   @Param("customerId") String customeId,
													   @Param("day") Date day,
													   @Param("crowds") List<String> crowds);
	Integer getBloodDataListCount(@Param("organId") String organId,
										@Param("customerId") String customeId,
										@Param("day") Date day,
										@Param("crowds") List<String> crowds,
										@Param("id")String id);
	List<TalentStructureTeamImgListDto> getBloodDataList(@Param("organId") String organId,
															   @Param("customerId") String customeId,
															   @Param("day") Date day,
															   @Param("crowds") List<String> crowds,
															   @Param("id")String id,
															   @Param("offset")Integer offset,
															   @Param("limit")Integer limit);

	//星座分布
	List<TalentStructureTeamImgDto> getStarData(@Param("organId") String organId,
												 @Param("customerId") String customeId,
												 @Param("day") Date day,
												 @Param("crowds") List<String> crowds);
	Integer getStarDataListCount(@Param("organId") String organId,
										@Param("customerId") String customeId,
										@Param("day") Date day,
										@Param("crowds") List<String> crowds,
										@Param("id")String id);
	List<TalentStructureTeamImgListDto> getStarDataList(@Param("organId") String organId,
															   @Param("customerId") String customeId,
															   @Param("day") Date day,
															   @Param("crowds") List<String> crowds,
															   @Param("id")String id,
															   @Param("offset")Integer offset,
															   @Param("limit")Integer limit);

	//性格
	List<TalentStructureTeamImgDto> getPersonalityData(@Param("organId") String organId,
												@Param("customerId") String customeId,
												@Param("day") Date day,
												@Param("crowds") List<String> crowds);
	Integer getPersonalityDataListCount(@Param("organId") String organId,
										@Param("customerId") String customeId,
										@Param("day") Date day,
										@Param("crowds") List<String> crowds,
										@Param("id")String id);
	List<TalentStructureTeamImgListDto> getPersonalityDataList(@Param("organId") String organId,
															   @Param("customerId") String customeId,
															   @Param("day") Date day,
															   @Param("crowds") List<String> crowds,
															   @Param("id")String id,
															   @Param("offset")Integer offset,
															   @Param("limit")Integer limit);
}
