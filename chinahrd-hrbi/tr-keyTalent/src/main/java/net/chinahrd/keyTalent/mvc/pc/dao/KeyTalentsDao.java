package net.chinahrd.keyTalent.mvc.pc.dao;


import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.common.EncourageDto;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.emp.FocusesDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentEncourageDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentLogDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentPanelDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentTagDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentsCardDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 关键人才库Dao接口类
 * Created by wqcai on 16/01/14 0023.
 */
@Repository("keyTalentsDao")
public interface KeyTalentsDao {

	
	
	/**
	 * 离职风险预警 查询
	 */
	
	List<KeyTalentsCardDto> queryRunoffRiskWarnEmp(@Param("customerId")String customerId,
			@Param("userId")String userId,@Param("empId")String empId,
			@Param("organPermitIds")List<String> organPermitIds
			);
	
	/**
	 * 我关注的人员查询
	 */
	List<KeyTalentsCardDto> queryFocuseEmp(@Param("customerId")String customerId,
			@Param("userId")String userId,@Param("empId")String empId,
			@Param("organPermitIds")List<String> organPermitIds);
	
	/**
	 * 最近更新人员 查询
	 */
	List<KeyTalentsCardDto> queryLastRefreshEmp(Map<String, Object> mapParam);

	/**
	 * 最近更新人员 总数
	 */
	Integer queryLastRefreshEmp_Count(Map<String, Object> mapParam);
	
	/**
	 * 添加关注
	 */
	void addFocuseEmp(@Param("dto")FocusesDto focusesDto);

	
	/**
	 * 取消关注
	 */
	void removeFocuseEmp(@Param("customerId")String customerId,@Param("empId")String empId,@Param("keyTalentId")String keyTalentId);
	
	/**
	 * 删除关键人才
	 */
	void deleteKeyTalent(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);
	/**
	 * 刷新关键人才日志最近更新时间
	 */
	void refreshKeyTalentLog(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId,@Param("refresh")String refresh);
	
	/**
	 * 刷新关键人才优势标签最近更新时间
	 */
	void refreshKeyTalentTag1(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId,@Param("refresh")String refresh);
	/**
	 * 刷新关键人才劣势标签最近更新时间
	 */
	void refreshKeyTalentTag2(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId,@Param("refresh")String refresh);
	/**
	 * 刷新关键人才核心激励要素最近更新时间
	 */
	void refreshKeyTalentEncourage(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId,@Param("refresh")String refresh,@Param("note")String note,@Param("modityEmpId")String modityEmpId);
	
   
	/**
	 * 添加关键人才
	 */
	void addKeyTalent(@Param("dto")KeyTalentDto keyTalentDto);
	
	
	/**
	 * 标签查询 查询
	 */
	List<KeyTalentTagDto> queryTag(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);

	/**
	 * 历史便签 查询
	 */
	List<KeyTalentTagDto> queryHistoryTag(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);

	/**
	 * 根据标签ID查找标签
	 */
	KeyTalentTagDto queryTagById(@Param("customerId")String customerId,@Param("keyTalentTagId")String keyTalentTagId);

	/**
	 * 添加手动标签
	 */
	void addKeyTalentTag(@Param("list")List<KeyTalentTagDto> list);

	/**
	 * 添加历史标签记录
	 */
	void addKeyTalentHistoryTag(@Param("list")List<KeyTalentTagDto> list);

	/**
	 * 删除手动标签
	 */
	void deleteKeyTalentTag(@Param("customerId")String customerId,@Param("tagId")String tagId);

	/**
	 * 根据关键人才Id
	 */
	KeyTalentDto queryKeyTalentById(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);
	/**
	 * 关键人才核心激励要素查询
	 * @return
	 */
	List<KVItemDto> queryKeyTalentEncourage(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);
	
	/**
	 * 核心激励要素查询
	 * @return
	 */
	List<EncourageDto> queryEncourage(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);
	/**
	 * 删除 关键人才核心激励要素
	 * @param customerId
	 * @param keyTalentId
	 */
	void deleteKeyTalentEncourage(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);
	
	/**
	 * 添加 关键人才核心激励要素
	 * @param customerId
	 * @param keyTalentId
	 */
	void addKeyTalentEncourage(@Param("list")List<KeyTalentEncourageDto> list);
	

	/**
	 * 添加 跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	void addKeyTalentLog(@Param("dto")KeyTalentLogDto KeyTalentLogDto);
	
	/**
	 * 查询跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	List<KeyTalentLogDto> queryKeyTalentLog(@Param("customerId")String customerId,@Param("keyTalentId")String keyTalentId);
	
	/**
	 * 修改跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	void updateKeyTalentLog(@Param("customerId")String customerId,@Param("keyTalentLogId")String keyTalentLogId,@Param("content")String content,@Param("curdate")String curdate);
	
	/**
	 * 删除跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	void deleteKeyTalentLog(@Param("customerId")String customerId,@Param("keyTalentLogId")String keyTalentLogId);

	/**
	 * 		 查询各个类型的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentTypePanel(@Param("customerId")String customerId,
			@Param("userId")String userId,
			@Param("organPermitIds")List<String> organPermitIds
			);
	
	/**
	 * 根据类型查询关键人才
	 * @param customerId
	 * @param organizationId
	 * @param curdate
	 * @return
	 */
	List<KeyTalentsCardDto> queryKeyTalentByType(Map<String, Object> mapParam);
	/**
	 * 根据类型查询关键人才数量
	 * @param customerId
	 * @param organizationId
	 * @param curdate
	 * @return
	 */
	Integer queryKeyTalentByTypeCount(Map<String, Object> mapParam);

	/**
	 * 		 查询各个激励要素的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentEncouragePanel(@Param("customerId")String customerId,
			@Param("userId")String userId,
			@Param("organPermitIds")List<String> organPermitIds);
	
	/**
	 * 		根据激励要素查询关键人才 
	 * @return
	 */
	List<KeyTalentsCardDto> queryKeyTalentByEncourage(Map<String, Object> mapParam);
	
	/**
	 * 		根据激励要素查询关键人才  数量
	 * @return
	 */
	Integer queryKeyTalentByEncourageCount(Map<String, Object> mapParam);
	
	/**
	 * 		 查询各个部门的关键人才数量 （含有下级）
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentOrganPanel(@Param("customerId")String customerId,
			@Param("userId")String userId,@Param("organPermitIds")List<String> organPermitIds);
	
	/**
	 * 		根据部门查询关键人才 
	 * @return
	 */
	List<KeyTalentsCardDto> queryKeyTalentByOrgan(Map<String, Object> mapParam);
	
	/**
	 * 		根据部门查询关键人才  数量
	 * @return
	 */
	Integer queryKeyTalentByOrganCount(Map<String, Object> mapParam);
	
	/**
	 * 查询上级领导empId
	 */
	String queryEmpInfo(@Param("customerId")String customerId,@Param("empId")String empId);
	/**
	 * 关键人才类型列表
	 */
	List<KVItemDto> queryKeyTalentType(@Param("customerId")String customerId);
	/**
	 * 非关键人才列表
	 */
	List<EmpDetailDto> queryNotKeyTalentByName(Map<String, Object> mapParam);
	Integer queryNotKeyTalentByNameCount(Map<String, Object> mapParam);
	/**
	 * 关键人才列表
	 */

	List<KeyTalentsCardDto> queryKeyTalentByName(Map<String, Object> mapParam);
	Integer queryKeyTalentByNameCount(Map<String, Object> mapParam);
	
	/** 添加 删除 关键人才 更新员工表
	 * */
	void updateEmpTable(@Param("customerId")String customerId,@Param("empId")String empId,@Param("isKeyTalent")int isKeyTalent);

}
