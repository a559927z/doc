package net.chinahrd.zte.talentProfitLoss.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dtozte.pc.talentprofitLoss.TalentProfitLossDto;

/**
 * 人才损益service
 * 
 * @author malong 2016-05-25
 */
public interface TalentProfitLossService {

	/**
	 * 本月/本年人才损益值
	 */
	public Map<String, Integer> queryTalentProfitLossVal(String customerId, String organId, String year, String month,
			List<Integer> inflowList, List<Integer> outflowList);

	/**
	 * 本月/本年流入统计
	 */
	public Map<String, Object> queryTalentInflowVal(String customerId, String organId, String year, String month,
			List<Integer> list);

	/**
	 * 本月/本年流出统计
	 */
	public Map<String, Object> queryTalentOutflowVal(String customerId, String organId, String year, String month,
			List<Integer> list);

	/**
	 * 流入统计弹窗明细-按钮组
	 */
	public List<TalentProfitLossDto> queryInflowDetailBtns(String customerId, List<Integer> list);

	/**
	 * 流出统计弹窗明细-按钮组
	 */
	public List<TalentProfitLossDto> queryOutflowDetailBtns(String customerId, List<Integer> list);

	/**
	 * 流入统计弹窗明细
	 */
	public PaginationDto<TalentProfitLossDto> queryTalentInflowDetail(String customerId, String organId, String year,
			String month, List<Integer> list, int page, int rows);

	/**
	 * 流出统计弹窗明细
	 */
	public PaginationDto<TalentProfitLossDto> queryTalentOutflowDetail(String customerId, String organId, String year,
			String month, List<Integer> list, int page, int rows);

	/**
	 * 人才损益-时间人群
	 */
	public Map<String, Object> queryTimecrowd(String customerId, String organId);

	/**
	 * 人才损益-人员分布-地图
	 */
	public Map<String, Object> queryPopulationMap(String customerId, String organId, String[] times, String[] crowds);

	/**
	 * 人才损益-人员分布-饼图
	 */
	public List<Object> queryPopulationPie(String customerId, String organId, String provinceName, String[] times,
			String[] crowds);

	/**
	 * 人才损益环比
	 */
	public Map<String, Object> queryTalentProfitLossRingData(String customerId, String organId, String year,
			String month, String[] times, String[] crowds, List<Integer> inflowList, List<Integer> outflowList);

	/**
	 * 人才损益同比
	 */
	public Map<String, Object> queryTalentProfitLossSameData(String customerId, String organId, String year,
			String month, String[] times, String[] crowds, List<Integer> inflowList, List<Integer> outflowList);

	/**
	 * 异动统计人群类型
	 */
	public List<Object> queryChangePopulation(String customerId);

	/**
	 * 异动统计饼图表格
	 */
	public Map<String, Object> queryInflowOutflowChangeType(String customerId, String organId, String startDate,
			String endDate, List<Integer> parentList, String childType, List<Integer> inflowList,
			List<Integer> outflowList, List<Integer> fullList);

	/**
	 * 异动统计-序列分布
	 */
	public Map<String, Object> querySequenceBar(String customerId, String organId, String startDate, String endDate,
			List<Integer> parentList, String childType, String changeType, List<Integer> inflowList,
			List<Integer> outflowList, List<Integer> fullList);

	/**
	 * 异动统计-职级分布
	 */
	public Map<String, Object> queryAbilityBar(String customerId, String organId, String startDate, String endDate,
			List<Integer> parentList, String childType, String changeType, List<Integer> inflowList,
			List<Integer> outflowList, List<Integer> fullList);

	/**
	 * 异动统计-入职名单
	 */
	public PaginationDto<TalentProfitLossDto> queryEntryListDatas(String customerId, String organId, String startDate,
			String endDate, List<Integer> parentList, String childType, String changeType, int page, int rows,
			List<Integer> inflowList, List<Integer> outflowList, List<Integer> fullList);

	/**
	 * 获取新的日期
	 */
	public String getNewDbNow(String day);
}
