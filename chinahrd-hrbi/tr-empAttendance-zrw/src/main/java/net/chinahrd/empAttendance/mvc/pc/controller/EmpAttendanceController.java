package net.chinahrd.empAttendance.mvc.pc.controller;

import net.chinahrd.empAttendance.mvc.pc.service.EmpAttendanceService;
import net.chinahrd.entity.dto.pc.ws.WeatherDto;
import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpAttendanceDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.GetWeather;
import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.RemoteUtil;
import net.chinahrd.utils.TableKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 考勤
 *
 * @author xwli 2016-11-2
 */
@Controller
@RequestMapping("/empAttendance")
public class EmpAttendanceController extends BaseController {
	// todo 中人网系统默认7号为截至修改日期
	public static int ZRW_WARN_DAY = 7;
	// todo 中人网系统默认开始时间2016年
	public static int ZRW_BEGIN_YEAR = 2016;
	@Autowired
	private EmpAttendanceService empAttendanceService;

	static {
		try {
			String property = PropertiesUtil.getProperty("YGKQ.ZRW_WARN_DAY").trim();
			ZRW_WARN_DAY = Integer.parseInt(property);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/index")
	public String index() {
		return "biz/index";
	}

	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultIndex() {
		return "include/default";
	}

	/**
	 * 跳转到员工考勤调整页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/toEmpAttendanceView")
	public String toTalentDetailView(HttpServletRequest request, HttpServletResponse response) {
		String customerId = getCustomerId();
		String empId = getUserEmpId();

		request.setAttribute("isFisrt", getEmpCookie(request, response));
		request.setAttribute("isSZ",
				empAttendanceService.checkEmpInOrgan(TableKeyUtil.SHENZHEN, TableKeyUtil.ORGAN_ID, empId, customerId));

		EmpAttendanceDto empDto = empAttendanceService.queryEmpInfo(customerId, empId);
		request.setAttribute("empDto", empDto);
		request.setAttribute("warnDay", ZRW_WARN_DAY);
		request.setAttribute("beginYear", ZRW_BEGIN_YEAR);

		return "biz/competency/empAttendance";
	}

	/**
	 * 请求天气预报
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWeather", method = RequestMethod.POST)
	public WeatherDto getWeather(HttpServletRequest request) {
		try {
			String ip = RemoteUtil.getRemoteIP(request);
			String city = GetWeather.getCity(ip);
			String httpArg = StringUtils.isNotBlank(city) ? "city=" + city : null;
			WeatherDto weatherDto = GetWeather.getWeather(httpArg); // 获取天气预报
			return weatherDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private int getEmpCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String userEmpId = getUserEmpId();
		int bool = 1;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(userEmpId + ".empTooltip")) {
					bool = 0;
				}
			}
		}
		if (bool == 1) {
			Cookie cookie = new Cookie(userEmpId + ".empTooltip", "1");
			cookie.setPath("/");
			cookie.setMaxAge(365 * 24 * 60 * 60);
			response.addCookie(cookie); // 添加Cookie
		}
		return bool;
	}

	/**
	 * 统计员工可调休假期
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEmpAttendance", method = RequestMethod.POST)
	public EmpAttendanceDto queryEmpAttendance() {
		return empAttendanceService.queryEmpVacationInfo(getCustomerId(), getUserEmpId());
	}

	/**
	 * 统计员工考勤信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAttendanceRecord", method = RequestMethod.POST)
	public Map<String, Object> queryAttendanceRecord() {
		String empId = getUserEmpId();

		return empAttendanceService.queryAttendanceRecord(getCustomerId(), empId);
	}

	/**
	 * 查询考勤情况
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAttendance", method = RequestMethod.POST)
	public List<EmpAttendanceDto> queryAttendance(Integer yearMonth) {
		return empAttendanceService.queryAttendance(getCustomerId(), getUserEmpId(), yearMonth);
	}

	/**
	 * 查询员工考勤信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAttendanceByEmpId", method = RequestMethod.POST)
	public EmpAttendanceDto queryAttendanceByEmpId(String day) {
		String empId = getUserEmpId();
		return empAttendanceService.queryAttendanceByEmpId(getCustomerId(), empId, day);
	}

	/**
	 * 查询正常打卡记录
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAttendanceFromCard", method = RequestMethod.POST)
	public Map<String, Object> queryAttendanceFromCard(Integer yearMonth) {
		return empAttendanceService.queryAttendanceFromCard(getCustomerId(), getUserEmpId(), yearMonth);
	}

	/**
	 * 查询异常类型
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSoureItem", method = RequestMethod.POST)
	public List<EmpAttendanceDto> querySoureItem() {
		return empAttendanceService.querySoureItem();
	}

	/**
	 * 考勤类型
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCheckTyp", method = RequestMethod.POST)
	public List<EmpAttendanceDto> queryCheckType() {
		return empAttendanceService.queryCheckType();
	}

	/**
	 * 考勤调整
	 *
	 * @param inTime
	 * @param outTime
	 * @param reason
	 * @param typeId
	 * @param note
	 * @param day
	 * @param yearMonth
	 * @param hour
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEmpAttendance", method = RequestMethod.POST)
	public boolean updateEmpAttendance(String inTime, String outTime, String reason, String typeId, String typeName,
			String note, String day, String yearMonth, String hour, String organId, Integer type) {
		if (StringUtils.isBlank(inTime))
			inTime = null;
		if (StringUtils.isBlank(outTime))
			outTime = null;

		String time = DateUtil.getDBNow();
		int d = Integer.parseInt(time.substring(8, 10));
		int now = Integer.parseInt(DateUtil.getDBTime("yyyyMM"));
		String[] dayArr = day.split(",");
		int inYm = Integer.parseInt(dayArr[0].substring(0, 4) + dayArr[0].substring(5, 7));
		if (d <= ZRW_WARN_DAY) {
			int preMonth = DateUtil.getPreYearMonth(now, 1);
			if (inYm < preMonth) {
				return false;
			}
		} else {
			if (inYm != now) {
				return false;
			}
		}

		String empKey = getUserInfo().getUserKey();
		return empAttendanceService.updateEmpAttendance(getCustomerId(), getUserEmpId(), inTime, outTime, reason,
				typeId, note, typeName, time, empKey, getUserEmpName(), day, yearMonth, hour, organId, type);
	}

	/**
	 * 历史休假情况
	 * 
	 * @param startYm
	 * @param endYm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEmpOtherDay", method = RequestMethod.POST)
	public List<EmpAttendanceDto> queryEmpOtherDay(int startYm, int endYm) {
		return empAttendanceService.queryEmpOtherDay(getCustomerId(), getUserEmpId(), startYm, endYm);
	}
	
	/**
	 * 历史休假期年份
	 * 
	 * @param startYm
	 * @param endYm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEmpOtherDayYear", method = RequestMethod.POST)
	public List<String> queryEmpOtherDayYear() {
		return empAttendanceService.queryEmpOtherDayYear(getCustomerId(), getUserEmpId());
	}
	
}
