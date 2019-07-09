package net.chinahrd.keyTalent.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.common.EncourageDto;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentLogDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentPanelDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentTagDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentsCardDto;

/**
 * 关键人才库Service接口类
 * Created by wqcai on 2016/1/14 014.
 */
public interface KeyTalentsService {

	/**
	 * 离职风险预警 查询
	 */
	
	List<KeyTalentsCardDto> queryRunoffRiskWarnEmp(String customerId,String userId,String empId);
	
	/**
	 * 我关注的人员查询
	 */
	List<KeyTalentsCardDto> queryFocuseEmp(String customerId,
			String userId,String empId);
	
	/**
	 * 最近更新人员 查询
	 * @param pagedto 
	 */
	Map<String, Object> queryLastRefreshEmp(String customerId,
			String userId,String empId, PaginationDto<KeyTalentsCardDto> pagedto);

	/**
	 * 添加关注
	 */
	boolean addFocuseEmp(String customerId,String empId,String keyTalentId);

	
	/**
	 * 取消关注
	 */
	boolean removeFocuseEmp(String customerId,String empId,String keyTalentId);
	
	/**
	 * 删除关键人才
	 */
	boolean deleteKeyTalent(String customerId,String createEmpId,String keyTalentId);
	
	/**
	 * 添加关键人才
	 */
	boolean addKeyTalent(String customerId,String createEmpID,String empId,String keyTalentTypeId);
	
	

	/**
	 * 手动便签 查询
	 */
	List<KeyTalentTagDto> queryTag(String customerId,String keyTalentId);

	/**
	 * 历史便签 查询
	 */
	List<KeyTalentTagDto> queryHistoryTag(String customerId,String keyTalentId);

	/**
	 * 添加手动标签
	 */
	boolean addKeyTalentTag(String customerId,String keyTalentId,String createEmpId,String createEmpName,String tags ,String type);

	/**
	 * 删除手动标签
	 */
	boolean deleteKeyTalentTag(String customerId,String createEmpName, String tagId,String type);

	/**
	 * 根据关键人才Id
	 */
	KeyTalentDto queryKeyTalentById(String customerId,String keyTalentId);
	/**
	 * 关键人才核心激励要素查询
	 * @return
	 */
	Map<String,Object> queryKeyTalentEncourage(String customerId,String keyTalentId);
	
	/**
	 * 核心激励要素查询
	 * @return
	 */
	List<EncourageDto> queryEncourage(String customerId,String keyTalentId);
//	/**
//	 * 删除 关键人才核心激励要素
//	 * @param customerId
//	 * @param keyTalentId
//	 */
//	void deleteKeyTalentEncourage(String customerId,String keyTalentId);
	
	/**
	 * 添加 关键人才核心激励要素
	 * @param customerId
	 * @param keyTalentId
	 */
	boolean updateKeyTalentEncourage(String customerId,String createEmpId,String encourages,String keyTalentId,String note);
	

	/**
	 * 添加 跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	boolean addKeyTalentLog(String customerId,String createEmpId,String keyTalentId,String content);
	
	/**
	 * 查询跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	List<KeyTalentLogDto> queryKeyTalentLog(String customerId,String keyTalentId);
	
	/**
	 * 修改跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	boolean updateKeyTalentLog(String customerId,String keyTalentId,String createEmpId,String keyTalentLogId,String content);
	
	/**
	 * 删除跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	boolean deleteKeyTalentLog(String customerId,String keyTalentId,String createEmpId,String keyTalentLogId);

	/**
	 * 		 查询各个类型的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentTypePanel(String customerId,
			String userId);
	
	/**
	 * 根据类型查询关键人才
	 * @param customerId
	 * @param organizationId
	 * @param curdate
	 * @return
	 */
	PaginationDto<KeyTalentsCardDto> queryKeyTalentByType(String customerId,
			String userId,String keyTalentTypeId,String empId, String order,PaginationDto<KeyTalentsCardDto>dto);

	/**
	 * 		 查询各个激励要素的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentEncouragePanel(String customerId,
			String userId);
	
	/**
	 * 		根据激励要素查询关键人才 
	 * @return
	 */
	PaginationDto<KeyTalentsCardDto> queryKeyTalentByEncourage(String customerId,
			String userId,String encouragesId,String empId,
			String order,PaginationDto<KeyTalentsCardDto>dto);
	

	/**
	 * 		 查询各个部门的关键人才数量 （含有下级）
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentOrganPanel(String customerId,
			String userId);
	
	/**
	 * 		根据部门查询关键人才 
	 * @return
	 */
	PaginationDto<KeyTalentsCardDto> queryKeyTalentByOrgan(String customerId,
			String organizationId,String empId,String order,PaginationDto<KeyTalentsCardDto>dto);
	

	/**
	 * 		查询关键人才类型列表
	 * @return
	 */
	List<KVItemDto> queryKeyTalentType(String customerId);
	
	/**
	 * 非关键人才列表
	 */

	PaginationDto<EmpDetailDto> queryNotKeyTalentByName(String customerId,
			String userId,String keyName,PaginationDto<EmpDetailDto>dto);
	/**
	 * 关键人才列表
	 */

	PaginationDto<KeyTalentsCardDto> queryKeyTalentByName(String customerId,
			String userId,String keyName,String empId, String order,PaginationDto<KeyTalentsCardDto>dto);
}
