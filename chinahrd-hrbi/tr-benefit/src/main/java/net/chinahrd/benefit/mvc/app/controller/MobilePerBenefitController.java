package net.chinahrd.benefit.mvc.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.chinahrd.benefit.mvc.app.service.MobilePerBenefitService;
import net.chinahrd.entity.dto.app.benefit.BenefitDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端 人均效益Controller
 * 
 * @author htpeng 2016年3月30日下午5:32:32
 */
@Controller
@RequestMapping("mobile/perBenefit")
public class MobilePerBenefitController extends BaseController {

	@Autowired
	private MobilePerBenefitService mobilePerBenefitService;

	@RequestMapping(value = "/toPerBenefitView")
	public String toAccordDismissView() {

		Object obj = request.getParameter("organId");
		String customerId = getCustomerId();
		List<OrganDto> organPermit = getOrganPermit();
		if (CollectionKit.isNotEmpty(organPermit)) {
			if (null == obj) {
				// 当前机构id
				OrganDto topOneOrgan = organPermit.get(0);
				request.setAttribute("reqOrganId",
						topOneOrgan.getOrganizationId());
				request.setAttribute("reqOrganName",
						topOneOrgan.getOrganizationName());
			} else {
				String orgId = obj.toString();
				request.setAttribute("reqOrganId", orgId);
				boolean bool = false;
				for (OrganDto topOneOrgan : organPermit) {
					if (topOneOrgan.getOrganizationId().equals(orgId)) {
						request.setAttribute("reqOrganName",
								topOneOrgan.getOrganizationName());
						bool = true;
						break;
					}
				}
				if (!bool) {
					request.setAttribute("reqOrganName", "没有找到");
				}
			}
		}
		String lastMonth = mobilePerBenefitService.queryLastMonth(customerId);
		request.setAttribute("lastMonth", lastMonth.substring(0,4)+"."+lastMonth.substring(4,6));
		Map<String, Object> map = getLastDay(lastMonth);
		// 数据时间范围
		request.setAttribute("endYearMonth", map.get("endYearMonth"));
		request.setAttribute("startYearMonth", map.get("startYearMonth"));
		request.setAttribute("startMonth", map.get("startMonth"));
		request.setAttribute("year", map.get("year"));
		return "mobile/perBenefit/perBenefit";
	}

	/**
	 * 获取人均效益数据
	 *
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPerBenefitData", method = RequestMethod.GET)
	public BenefitDto getPerBenefitData(String organizationId, String year) {
		BenefitDto perBenefitDate = mobilePerBenefitService
				.getPerBenefitData(getCustomerId(), organizationId, year);
		return perBenefitDate;
	}

	/**
	 * 获取人均效益趋势数据(月)
	 *
	 * @param organizationId
	 *            机构id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTrendData", method = RequestMethod.GET)
	public List<BenefitDto> getTrendData(String organizationId, String year) {

		List<BenefitDto> trendData = mobilePerBenefitService
				.getTrendByMonth(getCustomerId(), organizationId, year);

		return trendData;
	}

	/**
	 * 获取平均效益
	 *
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAvgValueData", method = RequestMethod.GET)
	public Integer getAvgValueData(String organizationId) {
		return mobilePerBenefitService.getAvgValueData(getCustomerId(),
				organizationId);
	}

	/**
	 * 整体趋势
	 *
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPerBenefitInfo", method = RequestMethod.POST)
	public List<BenefitDto> getPerBenefitInfo(String organId, String year) {
		return mobilePerBenefitService.getPerBenefitInfo(getCustomerId(),
				organId, year);
	}

	/**
	 * 获取当前组织人均效益数据
	 *
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrgBenefitData", method = RequestMethod.GET)
	public List<BenefitDto> getOrgBenefitData(String organizationId) {
		return mobilePerBenefitService.getOrgBenefitData(getCustomerId(),
				organizationId);
	}

	private Map<String, Object> getLastDay(String lastMonth) {
		int year = Integer.parseInt(lastMonth.substring(0, 4));
		int month = Integer.parseInt(lastMonth.substring(4, 6));

		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int yearC = cal.get(Calendar.YEAR);
		int monthC = cal.get(Calendar.MONTH) + 1;
		Map<String, Object> map = CollectionKit.newMap();
		if (year == yearC && month == monthC) {
			DateTime dt = new DateTime(cal.getTime());
			map.put("endYearMonth", dt.toString("yyyy.MM.dd"));
		} else {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 设置日历中月份的最大天数
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			DateTime dt = new DateTime(cal.getTime());
			map.put("endYearMonth", dt.toString("yyyy.MM.dd"));
		}
		map.put("startYearMonth", formatDate(getCurrYearFirst(year)));
		map.put("startMonth", year + "." + month + ".01");
		map.put("year", year);

		return map;
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	private static Date getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期对象
	 * @return String 日期字符串
	 */
	private static String formatDate(Date date) {
		return formatDate(date, "yyyy.MM.dd");
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期对象
	 * @return String 日期字符串
	 */
	private static String formatDate(Date date, String pattern) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		String sDate = f.format(date);
		return sDate;
	}
}
