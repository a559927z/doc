package net.chinahrd.zte.laborEfficiency.mvc.pc.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyGridDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyImportDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.zte.laborEfficiency.mvc.pc.service.LaborEfficiencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Controller
@RequestMapping("/laborEfficiency")
public class LaborEfficiencyController extends BaseController {

	@Autowired
	private FunctionService functionService;
	@Autowired
	private LaborEfficiencyService laborEfficiencyService;

	@RequestMapping(value = "/toLaborEfficiencyView")
	public String toLaborEfficiencyView(HttpServletRequest request, String type) {
		FunctionTreeDto dto = functionService.findFunctionObj(getCustomerId(), null,
				"laborEfficiency/toLaborEfficiencyView");
		if (dto != null) {
			request.setAttribute("quotaId", dto.getId());
		}
		if(type != null) {
			request.setAttribute("type", type);
		}
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);
		request.setAttribute("curYear", year);
		request.setAttribute("curMonth", month);
		Map<String, Integer> map = laborEfficiencyService.findMinMaxTime(getCustomerId());
		if (map != null) {
			Map<String, String> rsMap = laborEfficiencyService.queryCheckWorkTypeForDate(year, month,
					String.valueOf(map.get("minTime")), String.valueOf(map.get("maxTime")));
			String minMaxTime = String.valueOf(map.get("minTime")) + "," + String.valueOf(map.get("maxTime")) + ","
					+ String.valueOf(rsMap.get("minCurDate"));
			request.setAttribute("minMaxTime", minMaxTime);
			request.setAttribute("yearList", rsMap.get("yearList"));
		} else {
			request.setAttribute("yearList", year);
		}
		return "biz/competency/laborEfficiency";
	}

	/**
	 * 劳动力效能对比
	 * 
	 * @param organId
	 * @param type
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLaborEfficiencyRatio")
	public Map<String, Object> getLaborEfficiencyRatio(String organId, String beginTime, String endTime,
			String populationIds) {
		Map<String, Object> map = CollectionKit.newMap();
		String time = DateUtil.getDBNow().replace("-", "").substring(0, 6);
		if (beginTime == null) {
			beginTime = time;
		}
		if (endTime == null) {
			endTime = time;
		}
		map.putAll(laborEfficiencyService.queryLaborEfficiencyRatio(getCustomerId(), organId, beginTime, endTime,
				populationIds));
		return map;
	}

	/**
	 * 劳动力效能明细
	 * 
	 * @param organId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findLaborEfficiencyUrl")
	public PaginationDto<LaborEfficiencyDto> findLaborEfficiencyUrl(String organId, String beginTime, String endTime,
			String populationIds, Integer page, Integer rows) {
		PaginationDto<LaborEfficiencyDto> dto = new PaginationDto<LaborEfficiencyDto>(page, rows);
		String time = DateUtil.getDBNow().replace("-", "").substring(0, 6);
		if (beginTime == null) {
			beginTime = time;
		}
		if (endTime == null) {
			endTime = time;
		}
		return laborEfficiencyService.queryLaborEfficiencyDetail(getCustomerId(), organId, beginTime, endTime,
				populationIds, dto);
	}

	@ResponseBody
	@RequestMapping(value = "/getConditionValue")
	public Map<String, Object> getConditionValue(String organId) {
		return laborEfficiencyService.getConditionValue(getCustomerId(), organId);
	}

	/**
	 * 劳动力效能趋势
	 * 
	 * @param organId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLaborEfficiencyTrend")
	public Map<String, Object> getLaborEfficiencyTrend(String organId, String beginTime, String endTime,
			String populationIds) {
		int type = 0;
		String time = DateUtil.getDBNow().replace("-", "").substring(0, 6);
		if (beginTime != null || endTime != null || (populationIds != null && !"".equals(populationIds))) {
			type = 1;
		} else {
			if (Integer.parseInt(time.substring(4, 6)) > 6) {
				beginTime = Integer.parseInt(time) - 5 + "";
			} else {
				beginTime = Integer.parseInt(time) - 93 + "";
			}
			endTime = time;
		}
		return laborEfficiencyService.queryLaborEfficiencyTrend(getCustomerId(), organId, beginTime, endTime,
				populationIds, type);
	}

	/**
	 * 获取劳动力效能值
	 */
	@ResponseBody
	@RequestMapping(value = "getLaborEfficiencyValue", method = RequestMethod.POST)
	public Map<String, Object> getLaborEfficiencyValue(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String curDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		return laborEfficiencyService.getLaborEfficiencyValue(getCustomerId(), organId, curDate);
	}

	/**
	 * 加班时长
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeHours", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeHours(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		String curDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		return laborEfficiencyService.queryOvertimeHours(getCustomerId(), organId, curDate);
	}

	/**
	 * 加班预警统计
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeWarningCount", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeWarningCount(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		Integer otTime = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_OTTIME);
		Integer otWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);
		return laborEfficiencyService.queryOvertimeWarningCount(getCustomerId(), organId, otTime, otWeek);
	}

	/**
	 * 加班预警明细
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeWarningDetail", method = RequestMethod.POST)
	public List<LaborEfficiencyDto> queryOvertimeWarningDetail(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		Integer otTime = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_OTTIME);
		Integer otWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);
		return laborEfficiencyService.queryOvertimeWarningDetail(getCustomerId(), organId, otTime, otWeek);
	}

	/**
	 * 加班预警明细-人员加班线图
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeWarningPersonHours", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeWarningPersonHours(String empId) {
		return laborEfficiencyService.queryOvertimeWarningPersonHours(getCustomerId(), empId);
	}

	/**
	 * 加班时长趋势-人均时长
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeAvgHours", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeAvgHours(String organId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return laborEfficiencyService.queryOvertimeAvgHours(getCustomerId(), organId, times, crowds);
	}

	/**
	 * 加班时长趋势-总时长
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeTotalHours", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeTotalHours(String organId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return laborEfficiencyService.queryOvertimeTotalHours(getCustomerId(), organId, times, crowds);
	}

	/**
	 * 加班情况
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeCondition", method = RequestMethod.POST)
	public List<LaborEfficiencyDto> queryOvertimeCondition(String organId, String date, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return laborEfficiencyService.queryOvertimeCondition(getCustomerId(), organId, date, crowds);
	}

	/**
	 * 加班情况-人员加班线图
	 */
	@ResponseBody
	@RequestMapping(value = "queryOvertimeConditionPersonHours", method = RequestMethod.POST)
	public Map<String, Object> queryOvertimeConditionPersonHours(String empId, String date) {
		return laborEfficiencyService.queryOvertimeConditionPersonHours(getCustomerId(), empId, date);
	}

	/**
	 * 考勤类型分布
	 */
	@ResponseBody
	@RequestMapping(value = "queryCheckWorkTypeLayout", method = RequestMethod.POST)
	public Map<String, Object> queryCheckWorkTypeLayout(String organId, String date) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		return laborEfficiencyService.queryCheckWorkTypeLayout(getCustomerId(), organId, date);
	}

	/**
	 * 获取父组织机构
	 */
	@ResponseBody
	@RequestMapping(value = "/getParentId")
	public List<String> getParentId(String organId) {
		List<String> list = CollectionKit.newList();
		String parentId = laborEfficiencyService.queryParentOrganId(getCustomerId(), organId);
		list.add(parentId);
		return list;
	}

	/**
	 * 组织机构加班时长
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOvertimeByOrgan")
	public Map<String, Object> queryOvertimeByOrgan(String organId, String beginTime, String endTime,
			String populationIds) {
		String time = DateUtil.getDBNow().replace("-", "").substring(0, 6);
		if (beginTime == null) {
			beginTime = time;
		}
		if (endTime == null) {
			endTime = time;
		}
		return laborEfficiencyService.queryOvertimeByOrgan(getCustomerId(), organId, beginTime, endTime, populationIds);
	}

	/**
	 * 出勤明细
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAttendanceDetail")
	public PaginationDto<LaborEfficiencyGridDto> queryAttendanceDetail(String organId, String userName, String date,
			int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> checkList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.LDLXN_CHECK);
		return laborEfficiencyService.queryAttendanceDetail(getCustomerId(), organId, userName, date, checkList, page,
				rows);
	}

	/**
	 * 出勤明细-考勤类型
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCheckWorkType")
	public Map<String, Object> queryCheckWorkType() {
		List<Integer> checkList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.LDLXN_CHECK);
		return laborEfficiencyService.queryCheckWorkType(getCustomerId(), checkList);
	}

	/**
	 * 个人出勤明细-考勤类型
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOnePersonDetailCheckWorkType")
	public Map<String, Object> queryOnePersonDetailCheckWorkType() {
		List<Integer> checkList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.LDLXN_CHECK);
		return laborEfficiencyService.queryOnePersonDetailCheckWorkType(getCustomerId(), checkList);
	}

	/**
	 * 个人出勤明细
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOnePersonDetail")
	public PaginationDto<LaborEfficiencyGridDto> queryOnePersonDetail(String organId, String empId, String date,
			int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> checkList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.LDLXN_CHECK);
		return laborEfficiencyService.queryOnePersonDetail(getCustomerId(), organId, empId, date, checkList, page,
				rows);
	}
	
	/**
	 * 跳转到审核历史页面
	 */
	@RequestMapping(value = "/toAuditingHistoryView")
	public String toAuditingHistory(HttpServletRequest request) {
		return "biz/competency/laborEfficiencyAuditingHistory";
	}
	
	/**
	 * 跳转到审核页面
	 */
	@RequestMapping(value = "/toAuditingView")
	public String toAuditing(HttpServletRequest request, String createTime, String attId) {
		String empName = "";
		try {
			empName = new String(request.getParameter("empName").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("empName", empName);
		request.setAttribute("createTime", createTime);
		request.setAttribute("attId", attId);
		return "biz/competency/laborEfficiencyAuditing";
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkEmpInfo", method = RequestMethod.POST)
	public List<LaborEfficiencyDto> checkEmpInfo(String keyName) {
		return laborEfficiencyService.checkEmpInfo(getCustomerId(), keyName);
	}
	
	/**
	 * 下载《员工考勤数据》导入模板
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadTempletExcel", method = RequestMethod.GET)
	public void downloadTempletExcel(HttpServletResponse response) {
		String title = "劳动力效能导入模板";
		String dateTitle = "考勤时间";
		String date = DateUtil.convertToIntYearMonth(new Date()) + "";
		String[] headers = { "账号", "日期", "应出勤（小时）", "实际出勤（小时）", "加班（小时）", "假期统计（小时）" };
		String[] content = { "gyhe","1", "8", "8", "2", "事假-4" };
		laborEfficiencyService.downloadTempletExcel(getCustomerId(), response, title, dateTitle, date, headers, content);
	}
	
	/**
	 * 导入《员工考勤数据》
	 */
	@ResponseBody
	@RequestMapping(value = "/importLaborEfficiencyData", method = RequestMethod.POST)
	public Map<String, Object> importLaborEfficiencyData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		int flag = laborEfficiencyService.checkPermissImport(getCustomerId(), getUserId());
		if(flag == 0) {
			resultMap.put("checkPermiss", "该用户没有导入数据的权限！");
			return resultMap;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("attendanceFile");
		String type = request.getParameter("type");
		String attendanceTime = request.getParameter("attendanceTime");
		String year = request.getParameter("month").substring(0, 4);
		attendanceTime = year + (Integer.parseInt(attendanceTime) > 9 ? attendanceTime : "0" + attendanceTime);
		String examineId = request.getParameter("examineId");
		
		if (file != null && !"".equals(file)) {
			String fileName = file.getOriginalFilename();
			if (fileName.indexOf(".") != -1) {
				String lastFileName = fileName.substring(fileName.lastIndexOf("."));
				if (!(".xls".equals(lastFileName) || ".xlsx".equals(lastFileName))) {
					resultMap.put("fileError", "文件类型错误，请重新选择文件！");
					return resultMap;
				}
			}
			if (fileName.indexOf(".") == -1) {
				resultMap.put("fileError", "文件类型错误，请重新选择文件！");
				return resultMap;
			}
			long len = file.getSize();
			// 限制导入文件大小
			if (len > 100 * 1024 * 1024) {
				resultMap.put("fileError", "文件大小超过了100M");
				return resultMap;
			}
			// 验证文档错误，格式错误等
			resultMap.putAll(laborEfficiencyService.importFile(getCustomerId(), getUserId(), file, examineId, attendanceTime, type));
		} else {
			resultMap.put("fileError", "导入文件为空，请选择文件！");
		}
		
		return resultMap;
	}
	
	/**
	 * 查询待审核数据
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAuditingInfo", method = RequestMethod.POST)
	public List<LaborEfficiencyImportDto> queryAuditingInfo(int status) {
		return laborEfficiencyService.queryAuditingInfo(getCustomerId(), getUserId(), status);
	}
	
	/**
	 * 待审核的人员考勤数据
	 */
	@ResponseBody
	@RequestMapping(value = "/queryItemInfo", method = RequestMethod.POST)
	public List<LaborEfficiencyDto> queryItemInfo(String attId) {
		List<LaborEfficiencyDto> dto = laborEfficiencyService.queryItemInfo(getCustomerId(), attId);
		return dto;
	}
	
	/**
	 * 待审核的个人考勤数据
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPersonDetail", method = RequestMethod.POST)
	public List<LaborEfficiencyDto> queryPersonDetail(String userKey, String yearMonth) {
		List<LaborEfficiencyDto> dto = laborEfficiencyService.queryPersonDetail(getCustomerId(), userKey, yearMonth);
		return dto;
	}
	
	/**
	 * 更新审核状态并入库
	 */
	@ResponseBody
	@RequestMapping(value = "/updateImportInfo", method = RequestMethod.POST)
	public String updateImportInfo(String attId) {
		String result = "0";
		String date = DateUtil.getDBNow();
		
		try{
			laborEfficiencyService.updateImportInfo(getCustomerId(), attId, date, getUserId());
			result = "1";
		} catch(Exception e) {
			result = "0";
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询机构
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOrgan", method = RequestMethod.POST)
	public Map<String, Object> queryOrgan(String organId) {
		return laborEfficiencyService.queryOrgan(getCustomerId(), organId);
	}
}
