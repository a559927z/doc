package net.chinahrd.talentProfitLoss.mvc.app.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossConfigDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossDto;

/**
 * 人才损益service
 * 
 * @author malong 2016-05-25
 */
public interface MobileTalentProfitLossService {


	/**
	 * 本月/本年流入统计
	 */
	public Integer queryTalentInflowVal(String customerId, String organId, String time,
			List<Integer> list);

	/**
	 * 本月/本年流出统计
	 */
	public Integer queryTalentOutflowVal(String customerId, String organId, String time,
			List<Integer> list);



	/**
	 * 人才损益-人员分布-饼图
	 */
	public List<Object> queryPopulationPie(String customerId, String organId, String time);

	
	/**
	 * 人才损益同比
	 */
	public Map<String, Object> queryTalentProfitLossSameData(String customerId, String organId, String year,
			String month, List<Integer> inflowList, List<Integer> outflowList);

	
	/**
	 * 异动统计饼图表格
	 */
	public Map<String, Object> queryInflowOutflowChangeType(String customerId, String organId, String time);

	
	/**
	 * 异动统计-入职名单
	 */
	public PaginationDto<TalentProfitLossDto> queryEntryListDatas(String customerId, String organId,boolean input, String time,
			List<Integer> parentList, String type, int page, int rows);

	/**
	 * 流入流出 配置信息
	 * @param customerId
	 * @param inflowList
	 * @param outflowList
	 * @return
	 */
	public Map<String, List<TalentProfitLossConfigDto>> queryChangeConfig(String customerId,
			List<Integer> inflowList, List<Integer> outflowList);
}
