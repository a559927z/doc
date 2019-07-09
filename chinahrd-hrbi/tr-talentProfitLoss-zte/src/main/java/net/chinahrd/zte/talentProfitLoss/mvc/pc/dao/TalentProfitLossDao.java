package net.chinahrd.zte.talentProfitLoss.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dtozte.pc.talentprofitLoss.TalentProfitLossDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 人才损益
 * 
 * @author malong 2016-05-25
 */
@Repository("talentProfitLossDao")
public interface TalentProfitLossDao {

	/**
	 * 本月/本年人才损益值
	 */
	public List<TalentProfitLossDto> queryTalentProfitLossVal(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("month") String month,
			@Param("inflowList") List<Integer> inflowList, @Param("outflowList") List<Integer> outflowList);

	/**
	 * 本月/本年流入统计
	 */
	public List<TalentProfitLossDto> queryTalentInflowVal(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("month") String month,
			@Param("list") List<Integer> list);

	/**
	 * 本月/本年流出统计
	 */
	public List<TalentProfitLossDto> queryTalentOutflowVal(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("year") String year, @Param("month") String month,
			@Param("list") List<Integer> list);

	/**
	 * 流入统计弹窗明细-按钮组
	 */
	public List<TalentProfitLossDto> queryInflowDetailBtns(@Param("customerId") String customerId,
			@Param("list") List<Integer> list);

	/**
	 * 流出统计弹窗明细-按钮组
	 */
	public List<TalentProfitLossDto> queryOutflowDetailBtns(@Param("customerId") String customerId,
			@Param("list") List<Integer> list);

	/**
	 * 流入统计弹窗明细-总数
	 */
	public Integer queryTalentInflowDetailCount(Map<String, Object> map);

	/**
	 * 流入统计弹窗明细
	 */
	public List<TalentProfitLossDto> queryTalentInflowDetail(Map<String, Object> map);

	/**
	 * 流出统计弹窗明细-总数
	 */
	public Integer queryTalentOutflowDetailCount(Map<String, Object> map);

	/**
	 * 流出统计弹窗明细
	 */
	public List<TalentProfitLossDto> queryTalentOutflowDetail(Map<String, Object> map);

	/**
	 * 人才损益-时间人群
	 */
	public List<TalentProfitLossDto> queryTimecrowd(@Param("customerId") String customerId,
			@Param("organId") String organId);

	/**
	 * 人才损益-人员分布-地图
	 */
	public List<TalentProfitLossDto> queryPopulationMap(Map<String, Object> map);
	
	/**
	 * 人才损益-人员分布-地图-人群
	 */
	public List<TalentProfitLossDto> queryPopulationMapWithPerson(Map<String, Object> map);

	/**
	 * 人才损益-人员分布-饼图
	 */
	public List<TalentProfitLossDto> queryPopulationPie(Map<String, Object> map);
	
	/**
	 * 人才损益-人员分布-饼图-人群
	 */
	public List<TalentProfitLossDto> queryPopulationPieWithPerson(Map<String, Object> map);

	/**
	 * 人才损益-人员分布-饼图
	 */
	public List<TalentProfitLossDto> queryPopulationPieByProvince(Map<String, Object> map);
	
	/**
	 * 人才损益-人员分布-饼图-人群
	 */
	public List<TalentProfitLossDto> queryPopulationPieByProvinceWithPerson(Map<String, Object> map);

	/**
	 * 人才损益环比
	 */
	public List<TalentProfitLossDto> queryTalentProfitLossRingData(Map<String, Object> map);
	
	/**
	 * 人才损益环比-人群
	 */
	public List<TalentProfitLossDto> queryTalentProfitLossRingDataWithPerson(Map<String, Object> map);

	/**
	 * 人才损益同比
	 */
	public List<TalentProfitLossDto> queryTalentProfitLossSameData(Map<String, Object> map);

	/**
	 * 异动统计人群类型
	 */
	public List<TalentProfitLossDto> queryChangePopulation(@Param("customerId") String customerId);

	/**
	 * 异动统计饼图表格
	 */
	public Integer queryInflowOutflowChangeTypeCount(Map<String, Object> map);

	/**
	 * 异动统计饼图表格
	 */
	public List<TalentProfitLossDto> queryInflowOutflowChangeType(Map<String, Object> map);

	/**
	 * 异动统计-序列分布
	 */
	public Integer querySequenceBarCount(Map<String, Object> map);

	/**
	 * 异动统计-序列分布
	 */
	public List<TalentProfitLossDto> querySequenceBar(Map<String, Object> map);

	/**
	 * 异动统计-职级分布
	 */
	public Integer queryAbilityBarCount(Map<String, Object> map);

	/**
	 * 异动统计-职级分布
	 */
	public List<TalentProfitLossDto> queryAbilityBar(Map<String, Object> map);

	/**
	 * 异动统计-入职名单
	 */
	public Integer queryEntryListDatasCount(Map<String, Object> map);

	/**
	 * 异动统计-入职名单
	 */
	public List<TalentProfitLossDto> queryEntryListDatas(Map<String, Object> map);
}