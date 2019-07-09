package net.chinahrd.promotionBoard.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PerfChangeAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionBoardDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionDateDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionElementSchemeDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPayDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionReqDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionSpeedDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionStatusDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionTotalDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionTrackDto;

/**
 * 晋级看板dao
 */
@Repository("promotionBoardDao")
public interface PromotionBoardDao {
	PromotionDateDto getTrendDate(@Param("customerId") String customerId);

	// 符合条件
	List<PromotionTotalDto> getEligibilityApplication(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("status") Integer status,
			@Param("subOrganIdList") List<String> subOrganIdList);

	// 符合条件 总数
	Integer getEligibilityApplicationListCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("status") Integer status, @Param("subOrganIdList") List<String> subOrganIdList);

	// 符合条件 列表
	List<PromotionBoardDto> getEligibilityApplicationList(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("status") Integer status, @Param("offset") Integer offset,
			@Param("limit") Integer limit, @Param("subOrganIdList") List<String> subOrganIdList);

	// 部分符合条件
	PromotionTotalDto getSomeEligibility(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("conditionProp") double conditionProp, @Param("subOrganIdList") List<String> subOrganIdList);

	// 趋势分析
	PromotionSpeedDto getTrendAnalysis(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("endDate") String endDate, @Param("populationIds") List<String> populationIds);

	// 下级组织分析
	List<PromotionSpeedDto> getOrgAnalysis(Map<String, Object> map);

	// 级组织分析，个人晋级速度总数
	Integer getOrgAnalysisPerJinsuCount(@Param("customerId") String customerId, @Param("organId") String organId);

	// 下级组织分析，个人晋级速度
	List<PromotionTrackDto> getOrgAnalysisPerJinsuList(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("offset") Integer offset, @Param("limit") Integer limit);

	// 序列统计
	List<PromotionSpeedDto> getSequenceAnalysis(Map<String, Object> map);

	// 关键人才
	List<PromotionSpeedDto> getKeyTalentAnalysis(Map<String, Object> map);

	// 绩效
	List<PromotionSpeedDto> getPerformanceAnalysis(Map<String, Object> map);

	// 晋级轨迹(图)
	List<PromotionTrackDto> getTrackChart(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("empIds") List<String> empIds, @Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级轨迹(搜索下拉)
	List<PromotionTrackDto> getTrackSelect(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("key") String key, @Param("offset") Integer offset, @Param("limit") Integer limit,
			@Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级轨迹(总人数)
	Integer getTrackListCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("empId") String empId, @Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级轨迹(列表)
	List<PromotionTrackDto> getTrackList(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("empId") String empId, @Param("offset") Integer offset, @Param("limit") Integer limit,
			@Param("subOrganIdList") List<String> subOrganIdList);

	// 人员晋级状态(列表总数)
	Integer getStatusListCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("empId") String empId, @Param("rankName") String rankName,
			@Param("rankNameNext") String rankNameNext, @Param("condition") Integer condition,
			@Param("conditionBegin") double conditionBegin, @Param("conditionEnd") double conditionEnd,
			@Param("offset") Integer offset, @Param("limit") Integer limit,
			@Param("subOrganIdList") List<String> subOrganIdList, @Param("isAll") boolean isAll);

	// 人员晋级状态(列表)
	List<PromotionStatusDto> getStatusList(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("empId") String empId, @Param("rankName") String rankName,
			@Param("rankNameNext") String rankNameNext, @Param("condition") Integer condition,
			@Param("conditionBegin") double conditionBegin, @Param("conditionEnd") double conditionEnd,
			@Param("offset") Integer offset, @Param("limit") Integer limit,
			@Param("subOrganIdList") List<String> subOrganIdList, @Param("isAll") boolean isAll);

	// 人员晋级状态(要求)
	List<PromotionReqDto> getStatusReqList(@Param("customerId") String customerId,
			@Param("empIds") List<String> empIds);

	// 晋级薪酬模拟器（筛选显示的职级）
	List<PromotionPayDto> getPromotionSelectRank(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("conditionProp") double conditionProp);

	// 晋级薪酬模拟器(列表 当前职级人员名单)
	List<PromotionPayDto> getPromotionCurrentRankList(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("conditionProp") double conditionProp,
			@Param("ranks") List<String> ranks, @Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级薪酬模拟器（列表 晋级职级人员名单）
	List<PromotionPayDto> getPromotionNextRankList(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("ranks") List<String> ranks,
			@Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级薪酬模拟器（列表 人员薪酬列表）
	List<PromotionPayDto> getPromotionRankSalaryList(@Param("customerId") String customerId,
			@Param("ranks") List<String> ranks, @Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级薪酬模拟器（列表 人员薪酬列表）
	List<PromotionPayDto> getPromotionRankSalaryListByEmpid(@Param("customerId") String customerId,
			// @Param("organId")String organId,
			// @Param("conditionProp")double conditionProp,
			// @Param("rank")String rank,
			@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("key") String key,
			@Param("subOrganIdList") List<String> subOrganIdList);

	// 晋级薪酬模拟器(添加下拉)
	List<PromotionPayDto> getPromotionAddPersonList(@Param("customerId") String customerId,
			// @Param("organId")String organId,
			@Param("conditionProp") double conditionProp,
			// @Param("rank")String rank,
			@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("key") String key,
			@Param("subOrganIdList") List<String> subOrganIdList);

	/**
	 * 员工和方案关系
	 * 
	 * @param date
	 * @return
	 */
	List<KVItemDto> getEmpSchemeRel(@Param("date") String date);

	/**
	 * 方案
	 * 
	 * @return
	 */
	List<PromotionElementSchemeDto> getSchemeAll();

	/**
	 * 删除当前员工分析的所有记录
	 * 
	 * @param empId
	 */
	void delEmpAnalysis(@Param("empId") String empId);

	/**
	 * 员工司龄
	 * 
	 * @param date
	 * @return
	 */
	List<KVItemDto> getEmpCompanyAge();

	/**
	 * 员工能力评价
	 * 
	 * @return
	 */
	List<KVItemDto> getEmpEval();

	/**
	 * 能力评价维 -- 字典表里'排序'来分区方案里的要求，得出中文名称
	 * 
	 * @return key:show_index value: 名称
	 */
	List<KVItemDto> getEvalAll();

	/**
	 * 员工获取证书
	 */
	List<KVItemDto> getEmpCertificate();

	/**
	 * 员工获取证书维
	 * 
	 * @return
	 */
	List<KVItemDto> getCertificateAll();

	/**
	 * 员工证书类型
	 * 
	 * @return
	 */
	List<KVItemDto> getEmpCertificateType();

	/**
	 * 员工绩效
	 * 
	 * @return
	 */
	List<PerfChangeAnalysisDto> getEmpPerf();

	/**
	 * 批量插入分析
	 * 
	 * @param list
	 */
	void batchInsertPA(@Param("list") List<PromotionAnalysisDto> list);

	/**
	 * 批量更新符合条件占比
	 * 
	 * @param list
	 */
	void batchUpdateCP(@Param("list") List<KVItemDto> list, @Param("date") String date);
}
