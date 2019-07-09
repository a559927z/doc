package net.chinahrd.salesBoard.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardQueryDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesConfigDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsPointDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsSimpleCountDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.salesBoard.mvc.pc.service.SalesBoardService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
@Controller
@RequestMapping("/salesBoard")
public class SalesBoardController extends BaseController {

	@Autowired
	private SalesBoardService salesBoardService;

	@RequestMapping(value = "/toSalesBoardView")
	public String toSalesBoard(HttpServletRequest reuqest) {

        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("salesBoard/toSalesBoardView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
		
		String month = DateUtil.getDBNow().substring(5, 7);
		request.setAttribute("month", Integer.parseInt(month));
		int red = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XSKB_RED);
		List<Integer> yellow = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_YELLOW);
		int red_ywnlkh = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XSKB_RED_YWNLKH);
		List<Integer> yellow_ywnlkh = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_YELLOW_YWNLKH);
		request.setAttribute("red", red);
		request.setAttribute("yellow", changeListToString(yellow));
		request.setAttribute("red_ywnlkh", red_ywnlkh);
		request.setAttribute("yellow_ywnlkh", changeListToString(yellow_ywnlkh));
		Map<String, Object> map = salesBoardService.queryWarningInfo(getCustomerId(), getUserEmpId());
		request.setAttribute("num", map.get("num"));
		request.setAttribute("names", map.get("names"));
		request.setAttribute("yellowRanges", map.get("yellowRanges"));
		request.setAttribute("redRanges", map.get("redRanges"));
		request.setAttribute("notes", map.get("notes"));
		return "biz/productivity/salesBoard";
	}

	/**
     * 获取销售产品
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesProducts", method = RequestMethod.POST)
    public List<SalesBoardDto> getSalesProducts() {
        return salesBoardService.querySalesProducts(getCustomerId());
    }
    
	public String changeListToString(List<Integer> list) {
		String str = "";
		if (list != null && list.size() > 0) {
			for (Integer l : list) {
				if (!"".equals(str)) {
					str += ",";
				}
				str += l;
			}
		}
		return str;
	}

	/**
	 * 获取时间
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTimes", method = RequestMethod.POST)
	public Map<String, Object> queryTimes(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.queryTimes(getCustomerId(), organId);
	}

	/**
	 * 查询预警设置值
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/queryWarningInfo", method = RequestMethod.POST)
	public Map<String, Object> queryWarningInfo() {
		return salesBoardService.queryWarningInfo(getCustomerId(), getUserEmpId());
	}

	/**
	 * 修改预警设置值
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/updateWarningInfo", method = RequestMethod.POST)
	public void updateWarningInfo(SalesConfigDto dto) {
		dto.setCustomerId(getCustomerId());
		dto.setEmpId(getUserEmpId());
		salesBoardService.updateWarningInfo(getCustomerId(), getUserEmpId(), dto);
	}

	/**
	 * 当月销售额及比较
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurMonthSalesVal", method = RequestMethod.POST)
	public Map<String, Object> getCurMonthSalesVal(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.getCurMonthSalesVal(getCustomerId(), organId);
	}

	/**
	 * 当月销售异常组织
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurMonthSalesExctOrgan", method = RequestMethod.POST)
	public Map<String, Object> getCurMonthSalesExctOrgan(String organId, String yellowRange, String redRange) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.getCurMonthSalesExctOrgan(getCustomerId(), organId, yellowRange, redRange);
	}

	/**
	 * 人员流失异常
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurMonthPersonFlow", method = RequestMethod.POST)
	public Map<String, Object> getCurMonthPersonFlow(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.getCurMonthPersonFlow(getCustomerId(), organId);
	}

	/**
	 * 业务能力考核
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurMonthAbilityCheck", method = RequestMethod.POST)
	public Map<String, Object> getCurMonthAbilityCheck(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.getCurMonthAbilityCheck(getCustomerId(), organId);
	}

	/**
	 * 查询年销售额
	 */
	@ResponseBody
	@RequestMapping(value = "/getSalesAndTarget", method = RequestMethod.POST)
	public Map<String, Object> getSalesAndTarget(String organId, String year, String productId, Integer salesType) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		int time = 0;
		if (year == null || "".equals(year)) {
			time = Integer.parseInt(DateUtil.getDBNow().substring(0, 4));
		} else {
			time = Integer.parseInt(year);
		}
		return salesBoardService.querySalesAndTarget(getCustomerId(), organId, time, productId, salesType);
	}

	/**
	 * 查询团队销售额和达标率
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeamSalesAndTarget", method = RequestMethod.POST)
	public PaginationDto<SalesBoardDto> getTeamSalesAndTarget(String organId, Integer yearMonth, Integer teamStandardRate, Double beginSales, Double endSales, 
			Double beginReturnAmount, Double endReturnAmount, Integer page, Integer rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		if(yearMonth == null) {
			String now = DateUtil.getDBNow();
			yearMonth = Integer.parseInt(now.substring(0, 4) + now.substring(5, 7));
		}
		PaginationDto<SalesBoardDto> dto = new PaginationDto<SalesBoardDto>(page, rows);
		return salesBoardService.queryTeamSalesAndTarget(getCustomerId(), organId, yearMonth, teamStandardRate, beginSales, endSales, 
				beginReturnAmount, endReturnAmount, dto);
	}
/*	public List<SalesBoardDto> getTeamSalesAndTarget(String organId, Integer yearMonth, Integer teamStandardRate, Double beginSales, Double endSales, 
			Double beginReturnAmount, Double endReturnAmount) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		if(yearMonth == null) {
			String now = DateUtil.getDBNow();
			yearMonth = Integer.parseInt(now.substring(0, 4) + now.substring(5, 7));
		}
		return salesBoardService.queryTeamSalesAndTarget(getCustomerId(), organId, yearMonth, teamStandardRate, beginSales, endSales, 
				beginReturnAmount, endReturnAmount);
	}
*/
	/**
	 * 查询个人销售额和达标率
	 */
	@ResponseBody
	@RequestMapping(value = "/getPersonSalesAndTarget", method = RequestMethod.POST)
	public PaginationDto<SalesBoardDto> getPersonSalesAndTarget(String organId, Integer yearMonth, String keyName, Integer personStandard, Double beginPersonSales, 
			Double endPersonSales, Double beginPersonAmount, Double endPersonAmount, Integer page, Integer rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		if (yearMonth == null || "".equals(yearMonth)) {
			String now = DateUtil.getDBNow();
			yearMonth = Integer.parseInt(now.substring(0, 4) + now.substring(5, 7));
		}
		PaginationDto<SalesBoardDto> dto = new PaginationDto<SalesBoardDto>(page, rows);
		return salesBoardService.queryPersonSalesAndTarget(getCustomerId(), organId, yearMonth, keyName, personStandard, beginPersonSales, 
				endPersonSales, beginPersonAmount, endPersonAmount, dto);
	}

	/**
	 * 今日销售量/今日销售额/今日利润
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTodaySalesInfo", method = RequestMethod.POST)
	public Map<String, Object> queryTodaySalesInfo(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.queryTodaySalesInfo(getCustomerId(), organId);
	}

	/**
	 * 今日销售量/今日销售额/今日利润-地图显示数据
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/querySalesMapByProvince", method = RequestMethod.POST)
	public Map<String, Object> querySalesMapByProvince(String organId, String provinceId) {
		return salesBoardService.querySalesMapByProvince(getCustomerId(), organId, provinceId);
	}

	/**
	 * 组织销售统计
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOrganSalesStatistics", method = RequestMethod.POST)
	public Map<String, Object> queryOrganSalesStatistics(String organId, String date, int flag) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.queryOrganSalesStatistics(getCustomerId(), organId, date, flag);
	}

	/**
	 * 销售排名榜
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/querySalesRanking", method = RequestMethod.POST)
	public Map<String, Object> querySalesRanking(String organId, String date) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.querySalesRanking(getCustomerId(), organId, date);
	}

	/**
	 * 查询下级组织机构
	 */
	@ResponseBody
	@RequestMapping(value = "/querySubOrganization", method = RequestMethod.POST)
	List<SalesBoardDto> querySubOrganization(String organId) {
		return salesBoardService.querySubOrganization(getCustomerId(), organId);
	}

	/**
	 * 查询团队时间
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTeamTime", method = RequestMethod.POST)
	public List<SalesBoardDto> queryTeamTime() {
		return salesBoardService.queryTeamTime(getCustomerId());
	}

	/**
	 * 查询能力趋势
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAbilityByEmpid", method = RequestMethod.POST)
	public Map<String, Object> queryAbilityByEmpid(String empId) {
		return salesBoardService.queryAbilityByEmpid(getCustomerId(), empId);
	}

	/**
	 * 销售排名趋势
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRankByEmpid", method = RequestMethod.POST)
	public Map<String, Object> queryRankByEmpid(String organId, String empId) {
		if(StringUtils.isEmpty(organId)){
			return null;
		}
		return salesBoardService.queryRankByEmpid(getCustomerId(), organId, empId);
	}

	/**
	 * 查询组织销售额
	 */
	@ResponseBody
	@RequestMapping(value = "/querySalesByOrgan", method = RequestMethod.POST)
	public List<SalesBoardDto> querySalesByOrgan(String organId, Integer salesType) {
		String now = DateUtil.getDBNow();
		return salesBoardService.querySalesByOrgan(getCustomerId(), organId, now, salesType);
	}
	
	/**
	 * 销售排名地图-团队PK
	 */
	@ResponseBody
	@RequestMapping(value = "/querySalesRankMapTeamPK", method = RequestMethod.POST)
	public Map<String, Object> querySalesRankMapTeamPK(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<OrganDto> topOrgans = getUserInfo().getOrganPermitTop();
		Map<String, Object> map = CollectionKit.newMap();
		if(topOrgans != null && topOrgans.size() > 0){
			map =  salesBoardService.querySalesRankMapTeamPK(getCustomerId(), organId);
			map.put("topOrgans", topOrgans);
		} else {
			return null;
		}
		return map;
	}
	
	/**
	 * 销售排名地图-团队总览chart
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTeamViewChart", method = RequestMethod.POST)
	public List<SalesMapsSimpleCountDto> queryTeamViewChart(String organId, String date) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.queryTeamViewChart(getCustomerId(), organId, date);
	}
	
	/**
	 * 销售排名地图-团队总览全屏chart
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTeamViewFullChart", method = RequestMethod.POST)
	public List<SalesBoardDto> queryTeamViewFullChart(String organId, String date, String rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return salesBoardService.queryTeamViewFullChart(getCustomerId(), organId, date, rows);
	}
	
	/**
	 * 销售排名地图-团队PKchart
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTeamPKChart", method = RequestMethod.POST)
	public List<SalesMapsPointDto> queryTeamPKChart(SalesBoardQueryDto dto) {
		dto.setCustomerId(getCustomerId());
		return salesBoardService.queryTeamPKChart(dto);
	}
	
	/**
	 * 销售排名地图-团队PK获取地图员工点信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeamEmpPoint", method = RequestMethod.POST)
	public List<SalesMapsPointDto> getTeamEmpPoint(SalesBoardQueryDto dto) {
		dto.setCustomerId(getCustomerId());
		return salesBoardService.queryTeamEmpPoint(dto);
	}
	
	/**
	 * 销售排名地图-获取x轴和y轴基础信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getMapsBaseInfo", method = RequestMethod.GET)
	public Map<String, Object> getMapsBaseInfo() {
		return salesBoardService.getMapsBaseInfo(getCustomerId());
	}
	
	/**
	 * 销售排名地图-列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMapsGrid", method = RequestMethod.POST)
	public Map<String, Object> queryMapsGrid(SalesBoardQueryDto dto) {
		if(dto.getFlag() != null && !"".equals(dto.getFlag())){
			if(StringUtils.isEmpty(dto.getOrganId())){
				return null;
			}
		}
		dto.setCustomerId(getCustomerId());
		return salesBoardService.queryMapsGrid(dto);
	}
	
	/**
	 * 销售排名地图-统计员工数量
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeamSettingEmpCount", method = RequestMethod.GET)
	public int getTeamSettingEmpCount(SalesBoardQueryDto dto) {
		if (StringUtils.isEmpty(dto.getOrganId())) {
			return 0;
		}
		dto.setCustomerId(getCustomerId());
		return salesBoardService.getTeamSettingEmpCount(dto);
	}
	
	
	/**
	 * 查询销售人员异动情况
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEmpChangeInfo", method = RequestMethod.POST)
	public PaginationDto<SalesBoardDto> queryEmpChangeInfo(Integer page, Integer rows, String organId, String userName) {
		PaginationDto<SalesBoardDto> dto = new PaginationDto<SalesBoardDto>(page, rows);
		String now = DateUtil.getDBNow();
		return salesBoardService.queryEmpChangeInfo(dto, getCustomerId(), organId, userName, now);
	}
	
	/**
	 * 人员变动-销售额
	 */
	@ResponseBody
	@RequestMapping(value = "/queryChangeByMonth", method = RequestMethod.POST)
	public Map<String, Object> queryChangeByMonth(String organId, Integer salesType) {
		String time = DateUtil.getDBNow();
		return salesBoardService.queryChangeByMonth(getCustomerId(), organId, time, salesType);
	}
	
	/**
	 * 人员对比
	 */
	@ResponseBody
	@RequestMapping(value = "/getSalesByEmpid", method = RequestMethod.POST)
	public List<Object> getSalesByEmpid(String organId, Integer salesType, String empIds, String changeTypes) {
		String[] empArr = empIds.split(",");
		String[] changeTypeArr = changeTypes.split(",");
		String time = DateUtil.getDBNow();
		return salesBoardService.querySalesByEmpid(getCustomerId(), organId, time, salesType, empArr, changeTypeArr);
	}
	
	/**
	 * 人员变动-异动情况
	 */
	@ResponseBody
	@RequestMapping(value = "/getChangeInfo", method = RequestMethod.POST)
	public List<SalesBoardDto> queryChangeInfo(String organId, Integer flag, String index, Integer salesType) {
		String time = DateUtil.getDBNow();
		return salesBoardService.queryChangeInfo(getCustomerId(), organId, time, flag, index, salesType);
	}

}
