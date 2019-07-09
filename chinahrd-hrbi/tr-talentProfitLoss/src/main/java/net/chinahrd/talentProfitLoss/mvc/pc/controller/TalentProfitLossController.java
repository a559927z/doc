package net.chinahrd.talentProfitLoss.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dto.pc.talentProfitLoss.TalentProfitLossDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.talentProfitLoss.mvc.pc.service.TalentProfitLossService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 人才损益
 * 
 * @author malong 2016-05-25
 */
@Controller
@RequestMapping("/talentProfitLoss")
public class TalentProfitLossController extends BaseController {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private TalentProfitLossService talentProfitLossService;

	@RequestMapping("/toTalentProfitLossView")
	public String toTalentProfitLossView(HttpServletRequest request) {
		FunctionTreeDto dto = functionService.findFunctionObj(getCustomerId(), null,
				"talentProfitLoss/toTalentProfitLossView");
		if (dto != null) {
			request.setAttribute("quotaId", dto.getId());
		}
		String startDate = DateUtil.getDBNow().substring(0, 7);
		String endDate = DateUtil.getDBNow().substring(0, 10);
		endDate = talentProfitLossService.getNewDbNow(endDate).substring(0, 7);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		return "biz/competency/talentProfitLoss";
	}

	/**
	 * 本月/本年人才损益值
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentProfitLossVal", method = RequestMethod.POST)
	public Map<String, Integer> queryTalentProfitLossVal(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);
		Map<String, Integer> map = talentProfitLossService.queryTalentProfitLossVal(getCustomerId(), organId, year,
				month, inflowList, outflowList);
		return map;
	}

	/**
	 * 本月/本年流入统计
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentInflowVal", method = RequestMethod.POST)
	public Map<String, Object> queryTalentInflowVal(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> list = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		Map<String, Object> map = talentProfitLossService.queryTalentInflowVal(getCustomerId(), organId, list);
		return map;
	}

	/**
	 * 本月/本年流出统计
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentOutflowVal", method = RequestMethod.POST)
	public Map<String, Object> queryTalentOutflowVal(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> list = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		Map<String, Object> map = talentProfitLossService.queryTalentOutflowVal(getCustomerId(), organId, list);
		return map;
	}

	/**
	 * 流入统计弹窗明细-按钮组
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInflowDetailBtns", method = RequestMethod.POST)
	public List<TalentProfitLossDto> queryInflowDetailBtns() {
		List<Integer> list = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		return talentProfitLossService.queryInflowDetailBtns(getCustomerId(), list);
	}

	/**
	 * 流出统计弹窗明细-按钮组
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOutflowDetailBtns", method = RequestMethod.POST)
	public List<TalentProfitLossDto> queryOutflowDetailBtns() {
		List<Integer> list = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		return talentProfitLossService.queryOutflowDetailBtns(getCustomerId(), list);
	}

	/**
	 * 流入统计弹窗明细
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentInflowDetail", method = RequestMethod.POST)
	public PaginationDto<TalentProfitLossDto> queryTalentInflowDetail(String organId, String date, String flag,
			int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);
		if ("year".equals(date)) {
			month = null;
		}
		List<Integer> list = CollectionKit.newList();
		if (flag != null && !"".equals(flag)) {
			list.add(Integer.parseInt(flag));
		} else {
			list = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		}
		return talentProfitLossService.queryTalentInflowDetail(getCustomerId(), organId, year, month, list, page, rows);
	}

	/**
	 * 流出统计弹窗明细
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentOutflowDetail", method = RequestMethod.POST)
	public PaginationDto<TalentProfitLossDto> queryTalentOutflowDetail(String organId, String date, String flag,
			int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);
		if ("year".equals(date)) {
			month = null;
		}
		List<Integer> list = CollectionKit.newList();
		if (flag != null && !"".equals(flag)) {
			list.add(Integer.parseInt(flag));
		} else {
			list = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		}
		return talentProfitLossService.queryTalentOutflowDetail(getCustomerId(), organId, year, month, list, page,
				rows);
	}

	/**
	 * 时间人群
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTimecrowd", method = RequestMethod.POST)
	public Map<String, Object> queryTimecrowd(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return talentProfitLossService.queryTimecrowd(getCustomerId(), organId);
	}

	/**
	 * 人才损益-人员分布-地图
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPopulationMap", method = RequestMethod.POST)
	public Map<String, Object> queryPopulationMap(String organId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String[] t = null;
		String[] c = null;
		if (times != null && !"".equals(times)) {
			t = times.split("@");
		}
		if (crowds != null && !"".equals(crowds)) {
			c = crowds.split("@");
		}
		return talentProfitLossService.queryPopulationMap(getCustomerId(), organId, t, c);
	}

	/**
	 * 人才损益-人员分布-饼图
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPopulationPie", method = RequestMethod.POST)
	public List<Object> queryPopulationPie(String organId, String provinceId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String[] t = null;
		String[] c = null;
		if (times != null && !"".equals(times)) {
			t = times.split("@");
		}
		if (crowds != null && !"".equals(crowds)) {
			c = crowds.split("@");
		}
		return talentProfitLossService.queryPopulationPie(getCustomerId(), organId, provinceId, t, c);
	}

	/**
	 * 人才损益环比
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentProfitLossRingData", method = RequestMethod.POST)
	public Map<String, Object> queryTalentProfitLossRingData(String organId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);
		String[] c = null;
		String[] t = null;
		if (times != null && !"".equals(times)) {
			t = times.split("@");
		}
		if (crowds != null && !"".equals(crowds)) {
			c = crowds.split("@");
		}
		return talentProfitLossService.queryTalentProfitLossRingData(getCustomerId(), organId, year, month, t, c,
				inflowList, outflowList);
	}

	/**
	 * 人才损益同比
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentProfitLossSameData", method = RequestMethod.POST)
	public Map<String, Object> queryTalentProfitLossSameData(String organId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);
		String[] c = null;
		String[] t = null;
		if (times != null && !"".equals(times)) {
			t = times.split("@");
		}
		if (crowds != null && !"".equals(crowds)) {
			c = crowds.split("@");
		}
		return talentProfitLossService.queryTalentProfitLossSameData(getCustomerId(), organId, year, month, t, c,
				inflowList, outflowList);
	}

	/**
	 * 异动统计人群类型
	 */
	@ResponseBody
	@RequestMapping(value = "/queryChangePopulation", method = RequestMethod.POST)
	public List<Object> queryChangePopulation() {
		return talentProfitLossService.queryChangePopulation(getCustomerId());
	}

	/**
	 * 异动统计饼图表格
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInflowOutflowChangeType", method = RequestMethod.GET)
	public void queryInflowOutflowChangeType(HttpServletResponse response, String organId, String startDate,
			String endDate, String parentType, String childType) {
		if (StringUtils.isEmpty(organId)) {
			return;
		}

		try {
			Map<String, Object> map = talentProfitLossService.queryInflowOutflowChangeType(getCustomerId(), organId,
					startDate, endDate, parentType, childType);
			ObjectMapper mapper = new ObjectMapper();
			String result;
			result = mapper.writeValueAsString(map);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 异动统计-序列分布
	 */
	@ResponseBody
	@RequestMapping(value = "/querySequenceBar", method = RequestMethod.POST)
	public Map<String, Object> querySequenceBar(String organId, String startDate, String endDate, String parentType,
			String childType, String changeType) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		
		return talentProfitLossService.querySequenceBar(getCustomerId(), organId, startDate, endDate, parentType,
				childType, changeType);
	}

	/**
	 * 异动统计-职级分布
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAbilityBar", method = RequestMethod.POST)
	public Map<String, Object> queryAbilityBar(String organId, String startDate, String endDate, String parentType,
			String childType, String changeType) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return talentProfitLossService.queryAbilityBar(getCustomerId(), organId, startDate, endDate, parentType,
				childType, changeType);
	}

	/**
	 * 异动统计-入职名单
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEntryListDatas", method = RequestMethod.POST)
	public PaginationDto<TalentProfitLossDto> queryEntryListDatas(String organId, String startDate, String endDate,
			String parentType, String childType, String changeType, int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return talentProfitLossService.queryEntryListDatas(getCustomerId(), organId, startDate, endDate, parentType,
				childType, changeType, page, rows);
	}
}
