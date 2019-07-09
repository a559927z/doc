package net.chinahrd.positionCompetency.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.pc.emp.TrainExperienceDto;
import net.chinahrd.entity.dto.pc.positionCompetency.DimensionDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpContrastDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.HighPerfEmpDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionCompetencyDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionEmpCount;
import net.chinahrd.entity.dto.pc.positionCompetency.SequenceAndAblityDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Repository("positionCompetencyDao")
public interface PositionCompetencyDao {

	/**
	 * 查询所有周期点
	 */
	List<String> queryAllTime(@Param("customerId")String customerId);

	/**
	 * 查询岗位
	 */
	List<KVItemDto> queryPositionByName(Map<String, Object> mapParam);


	/**
	 * 查询岗位 数量
	 */
	Integer queryPositionByNameCount(Map<String, Object> mapParam);

	/**查询所有下级机构
	 * @param customerId
	 * @param organId
	 * @return
	 */
	List<PositionCompetencyDto> queryOrgan(@Param("customerId")String customerId,@Param("organId")String organId);
	
	/**查询所有下级机构
	 * @param customerId
	 * @param organId
	 * @return
	 */
	List<PositionCompetencyDto> queryOrganByTime(@Param("customerId")String customerId,@Param("organId")String organId,@Param("yearMonth")String yearMonth);
	
	
	/**
	 * 团队平均胜任度
	 */
	PositionCompetencyDto queryAvgCompetencyByOrgan(PositionCompetencyDto dto);

	
	/**
	 * 岗位胜任度
	 */
	List<PositionCompetencyDto> queryPositionCompetency(@Param("customerId")String customerId,@Param("organId")String organId,@Param("yearMonth")String yearMonth);

	/**
	 * 序列岗位胜任度
	 */
	List<SequenceAndAblityDto> querySequence(@Param("customerId")String customerId,@Param("organId")String organId,@Param("yearMonth")String yearMonth,@Param("sequenceId")String sequenceId);
	/**
	 * 职级岗位胜任度
	 */
	List<SequenceAndAblityDto> queryAbilityBySequence(@Param("customerId")String customerId,@Param("organId")String organId,@Param("yearMonth")String yearMonth,@Param("sequenceId")String sequenceId);

	
	

	/**
	 * 人员面板 
	 */
	
	List<EmpDetailDto> queryEmpByName(Map<String, Object> mapParam);
	/**
	 * 人员面板 数量
	 */
	
	int queryEmpByNameCount(Map<String, Object> mapParam);
	/**
	 * 人员面板 数量
	 */
	
	int queryEmpByNameCount2(Map<String, Object> mapParam);
	
	
	
	/**
	 *每个人员详细得分 
	 */
	
	List<DimensionDto> queryEmpDetail(Map<String, Object> mapParam);

	
	/**
	 *岗位面板
	 */
	
	List<PositionDetailDto> queryPositionTable(Map<String, Object> mapParam);
	
	/**
	 *岗位面板数量
	 */
	
	int queryPositionTableCount(Map<String, Object> mapParam);

	
	/**
	 *岗位所有维度的胜任度信息
	 */
	
	List<DimensionDto> queryPositionDimension(Map<String, Object> mapParam);

	
	/**
	 *岗位下人员数量
	 */
	List<PositionEmpCount> queryPositionEmpCount(Map<String, Object> mapParam);

	/**
	 *岗位下 胜任度最高和最低的人
	 */
	List<EmpDetailDto> queryPositionEmpHighAndLow(Map<String, Object> mapParam);
	
	/**
	 * 岗位下所有人的详细信息列表
	 * 
	 */
	List<EmpDetailDto> queryPositionEmp(Map<String, Object> mapParam);

	/**
	 * @param queryMap
	 * @return
	 */
	List<HighPerfEmpDto> queryHighPerfImagesEmps(
			Map<String, Object> queryMap);
	
	/**
	 * 岗位下所有维度应得分
	 * @param queryMap
	 * @return
	 */
	List<DimensionDto> queryPositionDimensionExpect(
			Map<String, Object> queryMap);

	/**
	 * @param mapParam
	 * @return
	 */
	EmpContrastDetailDto queryEmpContrastInfo(Map<String, Object> mapParam);

	/**
	 * @param customerId
	 * @param empId
	 * @param keyword
	 * @return
	 */
	List<KVItemDto> getKeyWordInfo(@Param("customerId")String customerId, @Param("empId")String empId, @Param("keyword")String keyword);

	/**
	 * @param customerId
	 * @param empId
	 * @param keyword
	 * @return
	 */
	List<EmpPastResumeDto> getPastResume(@Param("customerId")String customerId, @Param("empId")String empId,
			@Param("keyword")String keyword);

	/**
	 * @param customerId
	 * @param empId
	 * @param keyword
	 * @return
	 */
	List<TrainExperienceDto> getTrainExp(@Param("customerId")String customerId, @Param("empId")String empId,
			@Param("keyword")String keyword);

	
}
