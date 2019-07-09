package net.chinahrd.zte.talentStructure.mvc.pc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgListDto;
import net.chinahrd.entity.dtozte.pc.talentstructure.TalentstructureDto;


public interface TalentStructureService {

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

	/*
    * 管理者员工分布
    * */
	Map<String, Object> getAbilityEmpCount(String organId, String customerId, Date day, List<String> crowds);
	PaginationDto<TalentstructureDto> getAbilityEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	/*
    * 职级分布
    * */
	Map<String, Object> getAbilityCurtEmpCount(String organId, String customerId, Date day, List<String> crowds);
	PaginationDto<TalentstructureDto> getAbilityCurtEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	/*
    * 职位序列分布
    * */
	Map<String, Object> getSeqEmpCount(String organId, String customerId, Date day, List<String> crowds);

	/*
    * 组织分布
    * */
	Map<String, Object> getOrganEmpCount(String organId, String customerId, Date day, List<String> crowds);
	PaginationDto<TalentstructureDto> getOrganEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	/*
    * 工作地点分布
    * */
	Map<String, Object> getWorkplaceEmpCount(String organId, String customerId, Date day, List<String> crowds);
	PaginationDto<TalentstructureDto> getWorkplaceEmpList(String organId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	/**
	 * 根据主序列查询能力层级分布-柱状图 by jxzhang 2016-02-26
	 * @param organId
	 * @param customerId
	 * @param seqId
	 * @return
	 */
	Map<String, Integer> queryAbEmpCountBarBySeqId(String organId, String customerId, String seqId, Date day, List<String> crowds);
	PaginationDto<TalentstructureDto> getAbEmpCountBarBySeqIdList(String organId, String seqId, String customerId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	//男女占比
	List<TalentStructureTeamImgDto> getSexData(String organId, String customeId, Date day, List<String> crowds);
	PaginationDto<TalentStructureTeamImgListDto> getSexDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	//婚姻状况
	List<TalentStructureTeamImgDto> getMarryStatusData(String organId, String customeId, Date day, List<String> crowds);
	PaginationDto<TalentStructureTeamImgListDto> getMarryStatusDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	//血型
	List<TalentStructureTeamImgDto> getBloodData(String organId, String customeId, Date day, List<String> crowds);
	PaginationDto<TalentStructureTeamImgListDto> getBloodDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	//星座
	Map<String, Object> getStarData(String organId, String customeId, Date day, List<String> crowds);
	PaginationDto<TalentStructureTeamImgListDto> getStarDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows);

	//性格
	List<TalentStructureTeamImgDto> getPersonalityData(String organId, String customeId, Date day, List<String> crowds);
	PaginationDto<TalentStructureTeamImgListDto> getPersonalityDataList(String organId, String customeId, Date day, List<String> crowds, String id, Integer page, Integer rows);
}
