package net.chinahrd.positionCompetency.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpContrastDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionCompetencyDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.SequenceAndAblityDto;



/**
 * 岗位胜任度 PC
 * @author htpeng
 *2016年8月29日下午2:31:12
 */

public interface PositionCompetencyService {
	/**
	 * 查询所有周期点
	 */
	Map<String,List<String>>  queryAllTime(String customerId);

	  /**
     * 获取团队平均胜任度
     * @param times  时间
     * @param organ  机构
     * @param position 岗位
     * @param next   是否查询下级岗位
     * @return
     */
	List<PositionCompetencyDto> getTeamAvgCompetency(String customerId,String times, String organId,
			String positionId, boolean next);

	/**
	 * 查询所有下级组织
	 * @param customerId
	 * @param time
	 * @param organ
	 * @return
	 */
	List<PositionCompetencyDto> querySubOrgan(String customerId, String organId);


	/**
	 * 岗位胜任度
	 * @param customerId
	 * @param time
	 * @param organ
	 * @return
	 */
	Map<String,PositionCompetencyDto> queryPositionCompetency(String customerId, String organId, String yearMonth);

	/**获取所有岗位
	 * @param customerId
	 * @param keyName
	 * @param dto
	 * @return
	 */
	PaginationDto<KVItemDto> queryPositionByName(String customerId,
			String keyName, PaginationDto<KVItemDto> dto);
	
	/**查询人员 面板
	 * @param customerId
	 * @param keyName
	 * @param dto
	 * @return
	 */
	PaginationDto<EmpDetailDto> queryEmpByName(String customerId, String organId,String yearMonth,
    		String keyName,Double start,Double end, PaginationDto<EmpDetailDto> dto);
	
	/**查询岗位面板
	 * @param customerId
	 * @param keyName
	 * @param dto
	 * @return
	 */
	PaginationDto<PositionDetailDto> queryPositionTable(String customerId, String organId,
			String yearMonth, String keyName, PaginationDto<PositionDetailDto> dto);

	/**
	 * 序列
	 * @param customerId
	 * @param yearMonth
	 * @param organId
	 * @return
	 */
	List<SequenceAndAblityDto> querySequenceCompetency(String customerId, String organId,
			String yearMonth,String sequenceId);

	/**职级
	 * @param customerId
	 * @param yearMonth
	 * @param organId
	 * @param sequenceId
	 * @return
	 */
	List<SequenceAndAblityDto> queryAbilityCompetency(String customerId,
			String organId,String yearMonth,  String sequenceId);

	/**
	 * 岗位面板下 所有人员表格
	 * @param customerId
	 * @param organId
	 * @param yearMonth
	 * @param keyName
	 * @param dto
	 * @return
	 */
	PaginationDto<EmpDetailDto> queryPositionEmp(String customerId,
			String organId, String yearMonth,String positionId, String keyName,
			PaginationDto<EmpDetailDto> dto);

	/**
	 * @param position
	 * @param yearNum
	 * @param continueNum
	 * @param star
	 * @param customerId
	 * @return
	 */
	Map<String,Object> queryPositionImages(String position, Integer yearNum,
			Integer continueNum, Integer star, String customerId,boolean updatePositon);

	/**
	 * 人员对比信息
	 * @param customerId
	 * @param empId
	 * @param yearMonth
	 * @return
	 */
	EmpContrastDetailDto getEmpContrastInfo(String customerId, String empId,
			String yearMonth);

	/**
	 * @param customerId
	 * @param empId
	 * @param keyword
	 * @return
	 */
	Map<String,Object>  getKeyWordInfo(String customerId, String empId, String keyword);

	
}