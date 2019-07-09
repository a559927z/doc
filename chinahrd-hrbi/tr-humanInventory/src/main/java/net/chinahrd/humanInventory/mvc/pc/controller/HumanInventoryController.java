package net.chinahrd.humanInventory.mvc.pc.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryInputTypeDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryParamDto;
import net.chinahrd.entity.enums.HumanInventoryEnum;
import net.chinahrd.humanInventory.mvc.pc.service.HumanInventoryService;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.jqgrid.TreeGridData;
import net.chinahrd.utils.jqgrid.TreeGridRow;

/**
 * 项目人力盘点
 * @author malong and lixingwen
 */
@Controller
@RequestMapping("/humanInventory")
public class HumanInventoryController extends BaseController {

	
	@Autowired
	private HumanInventoryService humanInventoryService;

	@RequestMapping(value = "/toHumanInventoryView")
	public String toHumanInventoryView(HttpServletRequest request) {
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("humanInventory/toHumanInventoryView");
        if(null!=functionId){request.setAttribute("quotaId", functionId); }
        
		List<HumanInventoryDto> listProgress = humanInventoryService.queryProjectProgress(getCustomerId());
		request.setAttribute("listProgress", listProgress);
		
		List<HumanInventoryDto> list = humanInventoryService.queryProjectInputTypeInfo(getCustomerId());
		if (list != null) {
			request.setAttribute("projectFeeType", list);
		}
		Integer type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
		request.setAttribute("inventoryDateType", type);
		return "biz/productivity/humanInventory2";
	}

	/**
	 * 获取本年度项目总数和人均项目数
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getProjectConAndAvgNum", method = RequestMethod.POST)
	public Map<String, Object> getProjectConAndAvgNum(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		return humanInventoryService.getProjectConAndAvgNum(getCustomerId(), organId, year);
	}

	/**
	 * 获取本年度项目投入产出
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getProjectInputOutputNum", method = RequestMethod.POST)
	public Map<String, Object> getProjectInputOutputNum(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.getProjectInputOutputNum(getCustomerId(), organId, year);
		return map;
	}

	/**
	 * 获取本年度项目投入产出
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "/getProjectLoadNum", method = RequestMethod.POST)
	public Map<String, Object> getProjectLoadNum(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.getProjectLoadNum(getCustomerId(), organId, year);
		return map;
	}

	/**
	 * 投入产出分析
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "queryInputOutput", method = RequestMethod.POST)
	public Map<String, Object> queryInputOutputAmount(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		int type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
		String quarter = null;
		switch (type) {
		case 1:
			// month
			break;
		case 2:
			// quarter
			quarter = "quarter";
			break;
		default:
			quarter = null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.queryInputOutputAmount(getCustomerId(), organId, year, quarter);
		return map;
	}

	/**
	 * 投入产出地图
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "queryInputOutputMap", method = RequestMethod.POST)
	public Map<String, Object> queryInputOutputMap(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.queryInputOutputMap(getCustomerId(), organId, year);
		return map;
	}

	/**
	 * 盈亏项目数分析，盈亏金额分析
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "queryProfitLossProject", method = RequestMethod.POST)
	public Map<String, Object> queryProfitLossProject(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		int type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
		String quarter = null;
		switch (type) {
		case 1:
			// month
			break;
		case 2:
			// quarter
			quarter = "quarter";
			break;
		default:
			quarter = null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.queryProfitLossProject(getCustomerId(), organId, year, quarter);
		return map;
	}

	/**
	 * 盈亏项目数分析，盈亏金额分析 获取盈利总金额，亏损总金额
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "getProfitAndLossCountAmount", method = RequestMethod.POST)
	public Map<String, Object> getProfitAndLossCountAmount(String organId, String month) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		String newMonth = month;
		if (month.indexOf("/") != -1) {
			newMonth = month.split("/")[1];
		}
		if(month.toLowerCase().indexOf("q") != -1){
			newMonth = getMonthByQuarter(month.toLowerCase().split("q")[month.length() - 1]);
		}
		Map<String, Object> lastMap = new HashMap<String, Object>();
		Map<String, Object> map = humanInventoryService.getProfitAndLossCountAmount(getCustomerId(), organId, year,
				newMonth);
		lastMap.put("year", year);
		lastMap.put("month", newMonth);
		lastMap.put("map", map);
		return lastMap;
	}

	/**
	 * 盈亏项目数分析，盈亏金额分析 获取盈亏项目明细
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "getProfitAndLossProjectDetail", method = RequestMethod.POST)
	public Map<String, Object> getProfitAndLossProjectDetail(String organId, String month) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		String newMonth = month;
		if (month.indexOf("/") != -1) {
			newMonth = month.split("/")[1];
		}
		if(month.toLowerCase().indexOf("q") != -1){
			newMonth = getMonthByQuarter(month.toLowerCase().split("q")[month.length() - 1]);
		}
		Map<String, Object> map = humanInventoryService.getProfitAndLossProjectDetail(getCustomerId(), organId, year,
				newMonth);
		return map;
	}
	
	/**
	 * 根据季度选择月份
	 * */
	public String getMonthByQuarter(String quarter){
		String month = "";
		switch(quarter){
		case "1" :
			month = "01";
			break;
		case "2" :
			month = "04";
			break;
		case "3" :
			month = "07";
			break;
		case "4" :
			month = "10";
			break;
		}
		return month;
	}

	/**
	 * 项目投入统计-人力统计
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "queryProjectManpower", method = RequestMethod.POST)
	public Map<String, Object> queryProjectManpower(String organId) {
		if (StringUtils.isEmpty(organId)) return null;

		int type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
		String quarter = null;
		switch (type) {
		case 1:
			// month
			break;
		case 2:
			// quarter
			quarter = "quarter";
			break;
		default:
			quarter = null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.queryProjectManpower(getCustomerId(), organId, year, quarter);
		return map;
	}

	/**
	 * 项目投入统计-费用统计
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "queryProjectInputCost", method = RequestMethod.POST)
	public Map<String, Object> queryProjectInputCost(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		int type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
		String quarter = null;
		switch (type) {
		case 1:
			// month
			break;
		case 2:
			// quarter
			quarter = "quarter";
			break;
		default:
			quarter = null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.queryProjectInputCost(getCustomerId(), organId, year, quarter);
		return map;
	}

	/**
	 * 项目类型分析
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping(value = "queryProjectType", method = RequestMethod.POST)
	public Map<String, Object> queryProjectType(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		Map<String, Object> map = humanInventoryService.queryProjectType(getCustomerId(), organId, year);
		return map;
	}

	/**
	 * 主导项目
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/findLeadingProject", method = RequestMethod.POST)
	public PaginationDto<HumanInventoryDto> findLeadingProject(HumanInventoryParamDto humanDto, Integer page, Integer rows) {
		if(StringUtils.isEmpty(humanDto.getOrganId())) return null;
		PaginationDto<HumanInventoryDto> dto = new PaginationDto<HumanInventoryDto>(page, rows);
		String time = DateUtil.getDBNow();
		humanDto.setCustomerId(getCustomerId());
		Double manpowerStartInput = null, manpowerEndInput = null, feeStartInput = null, feeEndInput = null,
				startProjectGain = null, endProjectGain = null;
		if(!StringUtils.isEmpty(humanDto.getStartManpowerInput())) {
			manpowerStartInput = Double.parseDouble(humanDto.getStartManpowerInput());
		}
		if(!StringUtils.isEmpty(humanDto.getEndManpowerInput())) {
			manpowerEndInput = Double.parseDouble(humanDto.getEndManpowerInput());
		}
		if(!StringUtils.isEmpty(humanDto.getStartFeeInput())) {
			feeStartInput = Double.parseDouble(humanDto.getStartFeeInput()); 
		}
    	if(!StringUtils.isEmpty(humanDto.getEndFeeInput())) {
    		feeEndInput = Double.parseDouble(humanDto.getEndFeeInput());
		}
    	if(!StringUtils.isEmpty(humanDto.getStartProjectGain())) {
    		startProjectGain = Double.parseDouble(humanDto.getStartProjectGain()); 
		}
    	if(!StringUtils.isEmpty(humanDto.getEndProjectGain())) {
    		endProjectGain = Double.parseDouble(humanDto.getEndProjectGain());
		}
		if ("lastMonth".equals(humanDto.getManpowerInputSelect())) {
			humanDto.setStartManpowerLastMonth(manpowerStartInput);
			humanDto.setEndManpowerLastMonth(manpowerEndInput);
    	} else if ("thisYear".equals(humanDto.getManpowerInputSelect())) {
			humanDto.setStartManpowerInYear(manpowerStartInput); 
			humanDto.setEndManpowerInYear(manpowerEndInput);
    	} else if ("thisMonth".equals(humanDto.getManpowerInputSelect())) {
			humanDto.setStartManpowerInMonth(manpowerStartInput); 
			humanDto.setEndManpowerInMonth(manpowerEndInput);
    	}
        if ("lastMonth".equals(humanDto.getFeeInputSelect())) {
    		humanDto.setStartFeeLastMonthInput(feeStartInput); 
    		humanDto.setEndFeeLastMonthInput(feeEndInput);
    	} else if ("thisYear".equals(humanDto.getFeeInputSelect())) {
			humanDto.setStartFeeYearInput(feeStartInput); 
			humanDto.setEndFeeYearInput(feeEndInput);
    	} else if ("thisMonth".equals(humanDto.getFeeInputSelect())) {
			humanDto.setStartFeeMonthInput(feeStartInput); 
			humanDto.setEndFeeMonthInput(feeEndInput);
    	}
        if ("lastMonth".equals(humanDto.getProjectGainSelect())) {
    		humanDto.setStartGainLastMonth(startProjectGain); 
    		humanDto.setEndGainLastMonth(endProjectGain);
    	} else if ("thisYear".equals(humanDto.getProjectGainSelect())) {
			humanDto.setStartGainInYear(startProjectGain); 
			humanDto.setEndGainInYear(endProjectGain);
    	} else if ("thisMonth".equals(humanDto.getProjectGainSelect())) {
			humanDto.setStartGainInMonth(startProjectGain); 
			humanDto.setEndGainInMonth(endProjectGain);
    	}
		
        return dto = humanInventoryService.findLeadingProject(time, humanDto, dto);
	}

	/**
	 * 参与项目
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/findParticipateProject", method = RequestMethod.POST)
	public PaginationDto<HumanInventoryDto> findParticipateProject(HumanInventoryParamDto humanDto, Integer page, Integer rows) {
		if(StringUtils.isEmpty(humanDto.getOrganId())) return null;
		PaginationDto<HumanInventoryDto> dto = new PaginationDto<HumanInventoryDto>(page, rows);
		String time = DateUtil.getDBNow();
		humanDto.setCustomerId(getCustomerId());
		Double startInput = null, endInput = null;
		if(!StringUtils.isEmpty(humanDto.getParticipateStartInput())) {
			startInput = Double.parseDouble(humanDto.getParticipateStartInput()); 
		}
		if(!StringUtils.isEmpty(humanDto.getParticipateEndInput())) {
			endInput = Double.parseDouble(humanDto.getParticipateEndInput()); 
		}
		String manpowerInput = humanDto.getParticipateManpowerInputSelect();
		if ("lastMonth".equals(manpowerInput)) {
			humanDto.setStartManpowerLastMonth(startInput);
			humanDto.setEndManpowerLastMonth(endInput);
    	} else if ("thisYear".equals(manpowerInput)) {
    		humanDto.setStartManpowerInYear(startInput); 
    		humanDto.setEndManpowerInYear(endInput); 
    	} else if ("thisMonth".equals(manpowerInput)) {
    		humanDto.setStartManpowerInMonth(startInput); 
    		humanDto.setEndManpowerInMonth(endInput); 
    	}
		
		return dto = humanInventoryService.findParticipateProject(time, humanDto, dto);
	}

	/**
	 * 人员统计表
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/findEmployeeStatistics", method = RequestMethod.POST)
	public PaginationDto<HumanInventoryDto> findEmployeeStatistics(HumanInventoryParamDto humanDto, Integer page, Integer rows) {
		if(StringUtils.isEmpty(humanDto.getOrganId())) return null;
		PaginationDto<HumanInventoryDto> dto = new PaginationDto<HumanInventoryDto>(page, rows);
		String time = DateUtil.getDBNow();
		humanDto.setCustomerId(getCustomerId());
		return dto = humanInventoryService.findEmployeeStatistics(time, humanDto, dto);
	}

	/**
	 * 当前人力投入
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurrentEmployeeList", method = RequestMethod.POST)
	public PaginationDto<HumanInventoryDto> getInternalTrainerList(String organId, String projectId, Integer page,
			Integer rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String time = DateUtil.getDBNow();
		PaginationDto<HumanInventoryDto> dto = new PaginationDto<HumanInventoryDto>(page, rows);
		return humanInventoryService.getCurrentEmployeeList(getCustomerId(), organId, projectId, time, dto);
	}

	/**
	 * 参与项目明细
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getParticipateProjectDetail", method = RequestMethod.POST)
	public List<HumanInventoryDto> getParticipateProjectDetail(String organId, String empId, Integer page,
			Integer rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String time = DateUtil.getDBNow();
//		PaginationDto<HumanInventoryDto> dto = new PaginationDto<HumanInventoryDto>(page, rows);
		return humanInventoryService.getParticipateProjectDetail(getCustomerId(), empId, time);
	}

	/**
	 * 人力投入环比趋势
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getManpowerInputByMonth", method = RequestMethod.GET)
	List<HumanInventoryDto> getManpowerInputByMonth(String organId, String projectId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return humanInventoryService.queryManpowerInputByMonth(getCustomerId(), organId, projectId);
	}

	/**
	 * 子项目明细
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getSubprojectById", method = RequestMethod.GET)
	public TreeGridData getSubprojectById(HttpServletRequest request, HttpServletResponse response) {
		String organId = request.getParameter("organId");
		if (organId == null || "".equals(organId)) {
			return null;
		}
		String projectId = request.getParameter("nodeid");
		String type = request.getParameter("type");
		if (projectId == null || "".equals(projectId)) {
			projectId = request.getParameter("projectId");
		}
		String lvl = request.getParameter("n_level");
		int level = 1;
		if (lvl != null && !"".equals(lvl)) {
			level = Integer.parseInt(lvl);
		}
		List<HumanInventoryDto> list = humanInventoryService.querySubprojectById(getCustomerId(), organId, projectId, type);
		TreeGridData grid = new TreeGridData();
		grid.setPage(1);
		grid.setRecords(list.size());
		grid.setTotal(1);
		List<TreeGridRow> rows = new ArrayList<TreeGridRow>();
		for (HumanInventoryDto d : list) {
			TreeGridRow row = new TreeGridRow();
			row.setId(d.getProjectId());
			// level parent_id isLeaf expanded
			Object[] cell = null;
			if (level > 1) {
				cell = new Object[] {d.getProjectId(), d.getProjectName(), d.getEmpName(),
						d.getManpowerInMonth(), d.getManpowerLastMonth(), d.getManageSeries(), d.getBusinessSeries(),
						 d.getProfessionalSeries(), d.getFunctionalSeries(),
						level, d.getProjectParentId(), d.getIsLeaf(), false };
			} else {
				if(d.getProjectId().equals(projectId)) {
					cell = new Object[] {d.getProjectId(), d.getProjectName(), d.getEmpName(),
							d.getManpowerInMonth(), d.getManpowerLastMonth(), d.getManageSeries(), d.getBusinessSeries(),
							d.getProfessionalSeries(), d.getFunctionalSeries(),
							0, null, true, false };
				} else {
					cell = new Object[] {d.getProjectId(), d.getProjectName(), d.getEmpName(),
							d.getManpowerInMonth(), d.getManpowerLastMonth(), d.getManageSeries(), d.getBusinessSeries(),
							d.getProfessionalSeries(), d.getFunctionalSeries(), 
							level, null, d.getIsLeaf(), false };
				}
			}
			row.setCell(cell);
			rows.add(row);
		}
		grid.setRows(rows);
		return grid;
	}

	/**
	 * 投入产出比
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getInputOutputByMonth", method = RequestMethod.GET)
	public Map<String, Object> getInputOutputByMonth(String organId, String projectId, String time) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return humanInventoryService.queryInputOutputByMonth(getCustomerId(), organId, projectId, time);
	}
	
	/**
	 * 费用类型
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProjectFeeType", method = RequestMethod.GET)
	public Map<String, Object> queryProjectFeeTypeUrl() {
		Map<String, Object> resMap = CollectionKit.newMap();
		List<String> listName = CollectionKit.newList();
		List<HumanInventoryInputTypeDto> listModel = CollectionKit.newList();
		List<HumanInventoryDto> list = humanInventoryService.queryProjectInputTypeInfo(getCustomerId());
		listName.add("projectId");
		listName.add("项目名称");
		for(HumanInventoryDto dto : list) {
			listName.add(dto.getProjectTypeName());
		}
		listName.add("合计（元）");
		
		for(int i = 0; i < listName.size(); i ++) {
			HumanInventoryInputTypeDto dto = new HumanInventoryInputTypeDto();
			if(i == 0) {
				dto.setName("id");
				dto.setHidden(true);
				dto.setKey(true);
			} else if(i == 1) {
				dto.setName("projectName");
				dto.setSortable(false);
				dto.setWidth(200);
				dto.setAlign("left");
			} else if(i == listName.size() - 1) {
				dto.setName("total");
				dto.setSortable(false);
				dto.setWidth(70);
				dto.setAlign("center");
			} else {
				dto.setName("cost" + (i - 2));
				dto.setSortable(false);
				dto.setWidth(60);
				dto.setAlign("center");
			}
			listModel.add(dto);
		}
		resMap.put("listName", listName.toArray(new String[listName.size()]));
		resMap.put("listModel", listModel.toArray(new Object[listModel.size()]));
		return resMap;
	}

	/**
	 * 费用明细
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getFeeDetailById", method = RequestMethod.GET)
	public TreeGridData getFeeDetailById(HttpServletRequest request, HttpServletResponse response) {
		String organId = request.getParameter("organId");
		if (organId == null || "".equals(organId)) {
			return null;
		}
		String projectId = request.getParameter("nodeid");
		String type = request.getParameter("type");
		if (projectId == null || "".equals(projectId)) {
			projectId = request.getParameter("projectId");
		}
		String lvl = request.getParameter("n_level");
		int level = 1;
		if (lvl != null && !"".equals(lvl)) {
			level = Integer.parseInt(lvl);
		}	
		List<HumanInventoryInputTypeDto> list = humanInventoryService.queryFeeDetailById(getCustomerId(), organId, projectId, type);
		
		TreeGridData grid = new TreeGridData();
		grid.setPage(1);
		grid.setRecords(list.size());
		grid.setTotal(1);
		List<TreeGridRow> rows = new ArrayList<TreeGridRow>();
		for (HumanInventoryInputTypeDto d : list) {
			TreeGridRow row = new TreeGridRow();
			row.setId(d.getProjectId());
			Object[] cell = null;
			if (level > 1) {
				cell = new Object[] { d.getProjectId(), d.getProjectName(), d.getCost0(),
						d.getCost1(), d.getCost2(), d.getCost3(), d.getCost4(), d.getCost5(),
						d.getCost6(), d.getCost7(), d.getCost8(), d.getTotal(),
						level, d.getProjectParentId(), d.getIsLeaf(), false };
			} else {
				if(d.getProjectId().equals(projectId)) {
					cell = new Object[] { d.getProjectId(), d.getProjectName(), d.getCost0(), 
							d.getCost1(), d.getCost2(), d.getCost3(), d.getCost4(), d.getCost5(),
							d.getCost6(), d.getCost7(), d.getCost8(), d.getTotal(),
							0, null, true, false };
				} else {
					cell = new Object[] { d.getProjectId(), d.getProjectName(), d.getCost0(), 
							d.getCost1(), d.getCost2(), d.getCost3(), d.getCost4(), d.getCost5(),
							d.getCost6(), d.getCost7(), d.getCost8(), d.getTotal(),
							level, null, d.getIsLeaf(), false };
				}
			}
			row.setCell(cell);
			rows.add(row);
		}
		grid.setRows(rows);
		return grid;
	}

	/**
	 * 部门人力投入
	 * 
	 * @param organId
	 * @param projectId
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentInput", method = RequestMethod.POST)
	public List<Object> getDepartmentInput(String organId, String projectId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String time = DateUtil.getDBNow();
		List<Object> list = humanInventoryService.getDepartmentInput(getCustomerId(), organId, projectId, time);
		return list;
	}

	/**
	 * 职位序列人力投入
	 * 
	 * @param organId
	 * @param projectId
	 */
	@ResponseBody
	@RequestMapping(value = "/getJobSeqInput", method = RequestMethod.POST)
	public List<Object> getJobSeqInput(String organId, String projectId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String time = DateUtil.getDBNow();
		List<Object> list = humanInventoryService.getJobSeqInput(getCustomerId(), organId, projectId, time);
		return list;
	}

	/**
	 * 职级人力投入
	 * 
	 * @param organId
	 * @param projectId
	 */
	@ResponseBody
	@RequestMapping(value = "/getRankInput", method = RequestMethod.POST)
	public List<Object> getRankInput(String organId, String projectId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String time = DateUtil.getDBNow();
		List<Object> list = humanInventoryService.getRankInput(getCustomerId(), organId, projectId, time);
		return list;
	}

	/**
	 * 工作地点人力投入
	 * 
	 * @param organId
	 * @param projectId
	 */
	@ResponseBody
	@RequestMapping(value = "/getWorkplaceInput", method = RequestMethod.POST)
	public List<Object> getWorkplaceInput(String organId, String projectId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String time = DateUtil.getDBNow();
		List<Object> list = humanInventoryService.getWorkplaceInput(getCustomerId(), organId, projectId, time);
		return list;
	}

	/**
	 * 项目人力盘点 下载《项目信息及费用数据》模板
	 */
	@ResponseBody
	@RequestMapping(value = "/downLoadProjectInfoAndCost", method = RequestMethod.GET)
	public void downLoadProjectInfoAndCost(HttpServletResponse response) {
		String title = HumanInventoryEnum.PROJECTTITLE.getValue();
		String dateTitle = HumanInventoryEnum.INVENTORYTIME.getValue();
		String date = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String[] headers = { HumanInventoryEnum.PROJECTNAME.getValue(),
				HumanInventoryEnum.PROJECTPRINCIPAL.getValue(),
				HumanInventoryEnum.PROJECTLEADORGAN.getValue(),
				HumanInventoryEnum.PROJECTPARTAKEORGAN.getValue(),
				HumanInventoryEnum.PROJECTTYPE.getValue(),
				HumanInventoryEnum.PROJECTPROGRESS.getValue(),
				HumanInventoryEnum.PROJECTSTARTDATE.getValue(),
				HumanInventoryEnum.PROJECTENDDATE.getValue(),
				HumanInventoryEnum.PROJECTINPUT.getValue(),
				HumanInventoryEnum.PROJECTOUTPUT.getValue() };
		humanInventoryService.downLoadProjectInfoAndCost(getCustomerId(), response, title, dateTitle, date, headers);
	}

	/**
	 * 获取数据库中年份
	 */
	@ResponseBody
	@RequestMapping(value = "/getDbDate", method = RequestMethod.POST)
	public Map<String, Object> getDbDate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = CollectionKit.newMap();
		String year = DateUtil.getDBNow().substring(0, 4);
		map.put("year", year);
		List<String> date = humanInventoryService.queryDateForImport();
		map.put("dateList", date);
		return map;
	}

	/**
	 * 下载《项目人员数据》模板
	 */
	@ResponseBody
	@RequestMapping(value = "/downLoadProjectPersonExcel", method = RequestMethod.GET)
	public void downLoadProjectPersonExcel(HttpServletResponse response) {
		String title = "项目人员数据";
		String dateTitle = "盘点时间";
		String date = DateUtil.convertToIntYearMonth(new Date()) + "";
		String[] headers = { "项目名称", "参与员工", "人力投入", "所服务子项目", "工作内容" };
		humanInventoryService.downLoadProjectPersonExcel(getCustomerId(), response, title, dateTitle, date, headers);
	}

	/**
	 * 数据导入
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/importProjectExcelDatas", method = RequestMethod.POST)
	public void importProjectExcelDatas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = CollectionKit.newMap();
		try {
			String organId = request.getParameter("organId");
			String tempType = request.getParameter("optionsRadiosinline");
			String total = request.getParameter("selectTotalName");
			String detail = request.getParameter("selectDetailName");
			String type = request.getParameter("type"); // 1-第一次导入；2-继续导入
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("projectModelFile");
			Integer inventoryType = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
			if(detail != null && !"".equals(detail)){
				if(inventoryType == 1){
					String[] dates = detail.split("月");
					String year = dates[0].substring(0, 4);
					String month = dates[0].substring(5);
					if(Integer.parseInt(month) < 10){
						month = "0" + month;
					}
					detail = year + month;
				} else if(inventoryType == 2){
					String[] dates = detail.split("季度");
					String year = dates[0].substring(0, 4);
					String quarter = dates[0].substring(5);
					switch(Integer.parseInt(quarter)){
					case 1 : 
					case 2 : 
					case 3 : 
						quarter = "q1";
						break;
					case 4 : 
					case 5 : 
					case 6 : 
						quarter = "q2";
						break;
					case 7 : 
					case 8 : 
					case 9 : 
						quarter = "q3";
						break;
					case 10 : 
					case 11 : 
					case 12 : 
						quarter = "q4";
						break;
					}
					detail = year + quarter;
				}
			}
			
			if (file != null && !"".equals(file)) {
				String fileName = file.getOriginalFilename();
				String lastFileName = fileName.substring(fileName.lastIndexOf("."));
				if (fileName.indexOf(".") != -1) {
					if (!(".xls".equals(lastFileName) || ".xlsx".equals(lastFileName))) {
						JSONObject jo = new JSONObject();
						jo.put(HumanInventoryEnum.FILEERROR.getValue(),
								HumanInventoryEnum.FILETYPEERRORINFO.getValue());
						response.getWriter().write(jo.toString());
						return;
					}
				}
				if (file.getOriginalFilename().indexOf(".") == -1) {
					JSONObject jo = new JSONObject();
					jo.put(HumanInventoryEnum.FILEERROR.getValue(),
							HumanInventoryEnum.FILETYPEERRORINFO.getValue());
					response.getWriter().write(jo.toString());
					return;
				}
				long len = file.getSize();
				// 限制导入文件大小
				if (len > 4 * 1024 * 1024) {
					JSONObject jo = new JSONObject();
					jo.put(HumanInventoryEnum.FILEERROR.getValue(),
							HumanInventoryEnum.OUTOFMAXLENGTHINFO.getValue());
					response.getWriter().write(jo.toString());
					return;
				}
				try {
					String date = "";
					String title = "";
					InputStream is = file.getInputStream();
					if(".xls".equals(lastFileName)) {
						POIFSFileSystem fs = new POIFSFileSystem(is);
					    HSSFWorkbook wb = new HSSFWorkbook(fs);
					    HSSFSheet sheet = wb.getSheetAt(0);
					    HSSFRow row = sheet.getRow(0);
					    date = getCellFormatValue(row.getCell(1));
					    row = sheet.getRow(1);
					    title = getCellFormatValue(row.getCell(0));
					} else {
						XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
					    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
					    XSSFRow xssfRow = xssfSheet.getRow(0);
					    date = getCellFormatValue(xssfRow.getCell(1));
					    xssfRow = xssfSheet.getRow(1);
					    title = getCellFormatValue(xssfRow.getCell(0));
					}
					if("".equals(date) || "".equals(title)) {
						JSONObject jo = new JSONObject();
						if("optionPerson".equals(tempType)) {
							jo.put("templateError", HumanInventoryEnum.PERSONTYPEERROR.getValue());
						} else {
							jo.put("templateError", HumanInventoryEnum.COSTTYPEERROR.getValue());
						}
						response.getWriter().write(jo.toString());
						return;
					}
				} catch (NullPointerException e) {
					JSONObject jo = new JSONObject();
					if("optionPerson".equals(tempType)) {
						jo.put("templateError", HumanInventoryEnum.PERSONTYPEERROR.getValue());
					} else {
						jo.put("templateError", HumanInventoryEnum.COSTTYPEERROR.getValue());
					}
					response.getWriter().write(jo.toString());
					return;
				} 
		        
				// 验证文档错误，格式错误等
				map = humanInventoryService.compareTemplateIsSame(getCustomerId(), organId, tempType, total, detail,
						type, file);
			} else {
				JSONObject jo = new JSONObject();
				jo.put(HumanInventoryEnum.FILEERROR.getValue(),
						HumanInventoryEnum.FILECONTENTISNULL.getValue());
				response.getWriter().write(jo.toString());
				return;
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String result = mapper.writeValueAsString(map);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("###.#####");
                    cellvalue = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: {
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    } else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case HSSFCell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
}
