package net.chinahrd.talentProfitLoss.mvc.app.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.PieChartDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossConfigDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 人才损益 移动端
 * @author htpeng
 *2016年8月23日下午4:01:40
 */
@Repository("mobileTalentProfitLossDao")
public interface MobileTalentProfitLossDao {


	/**
	 * 本月/本年流入统计
	 */
	public Integer queryTalentInflowVal(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("time") String time, 
			@Param("list") List<Integer> list);

	/**
	 * 本月/本年流出统计
	 */
	public Integer queryTalentOutflowVal(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("time") String time, 
			@Param("list") List<Integer> list);

	

	/**
	 * 人才损益-人员分布-饼图
	 */
	public List<TalentProfitLossDto> queryPopulationPie(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("time") String time);



	/**
	 * 人才损益同比
	 */
	public List<TalentProfitLossDto> queryTalentProfitLossSameData(Map<String, Object> map);
	

	/**
	 * 异动统计饼图表格
	 */
	public List<PieChartDto> queryInflowOutflowChangeType(Map<String, Object> map);

	
	/**
	 * 异动统计-入职名单
	 */
	public Integer queryEntryListDatasCount(Map<String, Object> map);

	/**
	 * 异动统计-入职名单
	 */
	public List<TalentProfitLossDto> queryEntryListDatas(Map<String, Object> map);

	/**
	 * @param customerId
	 * @param inflowList
	 * @param outflowList
	 */
	public List<TalentProfitLossConfigDto> queryChangeConfig(@Param("customerId")String customerId,
			@Param("inflowList")List<Integer> inflowList,
			@Param("outflowList")List<Integer> outflowList);

	/**
	 * @param customerId
	 * @return
	 */
	public List<Integer> queryChangeType(@Param("customerId") String customerId);

	/**
	 * @param parMap
	 * @return
	 */
	public List<PieChartDto> queryInflowOutflowChangeTypeByJc(
			Map<String, Object> parMap);
}