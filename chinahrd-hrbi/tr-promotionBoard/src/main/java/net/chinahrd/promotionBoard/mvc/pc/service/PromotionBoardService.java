package net.chinahrd.promotionBoard.mvc.pc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PerfChangeAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionBoardDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionDateDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionElementSchemeDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionForewarningDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPayListDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPaySelectDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionSpeedDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionStatusDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionTrackDto;

/**
 *
 */
public interface PromotionBoardService {
	PromotionDateDto getDate(String customerId);

	// 符合条件
	PromotionForewarningDto getEligibilityApplication(String customerId, String organId, Integer status);

	// 符合条件 列表
	PaginationDto<PromotionBoardDto> getEligibilityApplicationList(String customerId, String organId, Integer page,
			Integer row, Integer status);

	// 部分符合条件
	PromotionForewarningDto getSomeEligibility(String customerId, String organId);

	// 趋势分析
	List<PromotionSpeedDto> getTrendAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds);

	// 下级组织分析
	Map<String, Object> getOrgAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds);

	// 级组织分析，个人晋级速度
	PaginationDto<PromotionTrackDto> getOrgAnalysisPerJinsuList(String customerId, String organId, Integer page,
			Integer row);

	// 序列统计
	Map<String, Object> getSequenceAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds);

	// 关键人才
	Map<String, Object> getKeyTalentAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds);

	// 绩效
	Map<String, Object> getPerformanceAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds);

	// 晋级轨迹(图)
	List<PromotionTrackDto> getTrackChart(String customerId, String organId, List<String> empIds);

	// 晋级轨迹(搜索下拉)
	Map<String, Object> getTrackSelect(String customerId, String organId, String key, Integer page, Integer rows);

	// 晋级轨迹(列表)
	PaginationDto<PromotionTrackDto> getTrackList(String customerId, String organId, String empId, Integer page,
			Integer rows);

	// 人员晋级状态(列表)
	PaginationDto<PromotionStatusDto> getStatusList(String customerId, String organId, String empId, String rankName,
			String rankNameNext, Integer condition, double conditionBegin, double conditionEnd, Integer page,
			Integer rows, boolean isAll);

	// 晋级薪酬模拟器（筛选显示的职级）
	List<PromotionPaySelectDto> getPromotionSelectRank(String customerId, String organId);

	// 晋级薪酬模拟器（列表）
	List<PromotionPayListDto> getPromotionRankList(String customerId, String organId, List<String> ranks);

	// 晋级薪酬模拟器(添加下拉)
	Map<String, Object> getPromotionAddPersonList(String customerId, String organId, String rank, Integer page,
			Integer rows, String key);

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
	void delEmpAnalysis(String empId);

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
