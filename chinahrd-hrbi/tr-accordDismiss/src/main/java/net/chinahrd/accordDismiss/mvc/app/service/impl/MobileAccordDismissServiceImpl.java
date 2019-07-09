package net.chinahrd.accordDismiss.mvc.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.chinahrd.accordDismiss.mvc.app.dao.MobileAccordDismissDao;
import net.chinahrd.accordDismiss.mvc.app.service.MobileccordDismissService;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.dismiss.AccordDismissDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTrendDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTypeDto;
import net.chinahrd.entity.enums.CompanyAgeEnum;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Sort;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 主动流失率service
 * 
 * @author htpeng 2016年3月30日下午5:30:52
 */
@Service("mobileAccordDismissService")
public class MobileAccordDismissServiceImpl implements
		MobileccordDismissService {

	@Autowired
	private MobileAccordDismissDao mobileAccordDismissDao;

	@Override
	public PaginationDto<AccordDismissDto> queryRunOffDetail(String customerId,
			String organizationId, String date,
			PaginationDto<AccordDismissDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("customerId", customerId);
		mapParam.put("organId", organizationId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.putAll(getYearMonthDayArr(date));
		int count = mobileAccordDismissDao.queryRunOffCount(mapParam);
		List<AccordDismissDto> dtos = mobileAccordDismissDao
				.queryRunOffDetail(mapParam);
		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
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
		return formatDate(date, "yyyy-MM-dd");
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

	private Map<String, Object> getYearArr(String time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String year1_begin = formatDate(
				getCurrYearFirst(cal.get(Calendar.YEAR)), "yyyyMM");
		DateTime dt = new DateTime(cal.getTime());
		String year1_end = dt.toString("yyyyMM");
		Map<String, Object> map = CollectionKit.newMap();
		map.put("startYearMonth", year1_begin);
		map.put("endYearMonth", year1_end);
		return map;
	}

	private Map<String, Object> getYearMonthDayArr(String time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String year1_begin = formatDate(getCurrYearFirst(cal.get(Calendar.YEAR)));
		DateTime dt = new DateTime(cal.getTime());
		String year1_end = dt.toString("yyyy-MM-dd");
		Map<String, Object> map = CollectionKit.newMap();
		map.put("startYearMonth", year1_begin);
		map.put("endYearMonth", year1_end);
		return map;
	}

	@Override
	public Map<String, List<DismissTrendDto>> queryDismissTrend(String organId,
			String firstOrganId, String prevQuarter, String customerId) {
		Map<String, Object> parmMap = getYearArr(prevQuarter);
		boolean queryParent = !organId.equals(firstOrganId);
		if (queryParent) {
			List<String> child = CacheHelper.getSubOrganIdList(firstOrganId);
			if (child == null || child.size() == 0) {
				queryParent = false;
			}
		}
		parmMap.put("organId", organId);
		parmMap.put("customerId", customerId);
		parmMap.put("queryParent", queryParent);
		List<DismissTrendDto> list = mobileAccordDismissDao
				.queryDismissTrend(parmMap);
		List<DismissTrendDto> currentList = CollectionKit.newList();
		List<DismissTrendDto> parentList = CollectionKit.newList();
		for (DismissTrendDto dto : list) {
			if (dto.getOrganId().equals(organId)) {
				currentList.add(dto);
			} else {
				parentList.add(dto);
			}
		}
		Map<String, List<DismissTrendDto>> map = CollectionKit.newMap();
		map.put("current", currentList);
		map.put("parent", parentList);
		return map;
	}

	@Override
	public List<DismissTrendDto> getSubDismissData(String customerId,
			String organizationId, String date) {
		Map<String, Object> parmMap = getYearArr(date);
		int startYearMonth = Integer.parseInt(parmMap.get("startYearMonth")
				.toString());
		List<DismissTrendDto> dismissList = mobileAccordDismissDao
				.querySubDismissData(customerId, organizationId, startYearMonth);
		if (CollectionKit.isEmpty(dismissList)) {
			return null;
		}

		Map<String, DismissTrendDto> allDataMap = packDisQuaData(
				 dismissList);
		List<DismissTrendDto> result = packContrResult(allDataMap);
//		for (DismissTrendDto dto : result) {
//			if (dto.getOrganId().equals(organizationId)) {
//				result.remove(dto);
//				break;
//			}
//		}
		return new Sort<DismissTrendDto>().asc(result);
	}

	
	

	private Map<String, DismissTrendDto> packDisQuaData(
			List<DismissTrendDto> dismissList) {
		Map<String, DismissTrendDto> map = new LinkedHashMap<String, DismissTrendDto>();
		// 子机构的数值总和
		DismissTrendDto record = null;
		DismissTrendDto recordSource = null;
		for (DismissTrendDto dto : dismissList) {
			String organId = dto.getOrganId();
			DismissTrendDto existDto = map.get(organId);
			if (existDto == null) {
				existDto = new DismissTrendDto();
				existDto.setOrganId(organId);
				existDto.setOrganName(dto.getOrganName());
				existDto.setMonthBegin(dto.getMonthBegin());
				if (record != null) {
					record.setMonthEnd(recordSource.getMonthEnd());
				}
				record = existDto;
			}
			recordSource = dto;
			double runOffSum = dto.getAccordCount();
			if (null != existDto.getAccordCount()) {
				runOffSum += existDto.getAccordCount();
			}
			existDto.setAccordCount(runOffSum);
			map.put(organId, existDto);
//			if (parentOrgId.equals(organId)) {
//				continue;
//			}
		}
		record.setMonthEnd(recordSource.getMonthEnd());
		return map;
	}

	/**
	 * 计算各个机构的流失率
	 * 
	 * @param organizationId
	 * @param allDataMap
	 * @return
	 */
	private List<DismissTrendDto> packContrResult(
			Map<String, DismissTrendDto> allDataMap) {
		List<DismissTrendDto> resultList = CollectionKit.newList();
		for (Entry<String, DismissTrendDto> entry : allDataMap.entrySet()) {
			DismissTrendDto value = entry.getValue();
			// （季度初人数+季度末人数）/2
			double averTotal = ArithUtil.div(
					value.getMonthBegin() + value.getMonthEnd(), 2, 1);
			// 流失率= 季度内流失总人数 / ((季度初人数+季度末人数)/2)
			Double dismissRate = averTotal == 0 ? null : ArithUtil.div(
					value.getAccordCount() * 100, averTotal, 2);
			value.setRate(dismissRate);
			resultList.add(value);
		}
		return resultList;
	}

	@Override
	public String queryQuarterLastDay(String customerId) {
		return mobileAccordDismissDao.queryQuarterLastDay(customerId);
	}

	@Override
	public DismissTrendDto queryDisminss4Quarter(String organId,
			String quarterLastDay, String customerId) {
		List<DismissTrendDto> dtos = mobileAccordDismissDao
				.queryDisminss4Quarter(organId, quarterLastDay, customerId);
		double quarterBegin = 0, quarterEnd = 0, minMonth = 0, maxMonth = 0, accordCount = 0;
		for (DismissTrendDto dto : dtos) {
			// 累计流失人数
			accordCount = ArithUtil.sum(accordCount, dto.getAccordCount());
			// 计算季度总人数
			double yearMonth = Double.valueOf(dto.getYearMonth());
			if (quarterBegin == 0) {
				quarterBegin = dto.getMonthBegin();
				quarterEnd = dto.getMonthEnd();
				minMonth = yearMonth;
				maxMonth = yearMonth;
				continue;
			}
			if (minMonth > yearMonth) {
				minMonth = yearMonth;
				quarterBegin = dto.getMonthBegin();
				continue;
			}
			if (maxMonth < yearMonth) {
				maxMonth = yearMonth;
				quarterEnd = dto.getMonthEnd();
				continue;
			}
		}
		// 季度总人数
		double monthCount = ArithUtil.div(
				ArithUtil.sum(quarterBegin, quarterEnd), 2, 4);
		// 季度流失率
		double rate = accordCount == 0 ? 0d : ArithUtil.div(accordCount,
				monthCount, 4);
		DismissTrendDto dismiss = new DismissTrendDto();
		dismiss.setMonthBegin(quarterBegin);
		dismiss.setMonthEnd(quarterEnd);
		dismiss.setAccordCount(accordCount);
		dismiss.setMonthCount(monthCount);
		dismiss.setRate(rate);
		return dismiss;
	}

	@Override
	public Map<String, List<DismissTypeDto>> queryDismissInfo(String organId,
			String quarterLastDay, String customerId) {
		Map<String, List<DismissTypeDto>> map = CollectionKit.newMap();
		if (StringUtils.isEmpty(quarterLastDay)) {
			return map;
		}
		Map<String, Object> parmMap = getYearMonthDayArr(quarterLastDay);
		parmMap.put("organId", organId);
		parmMap.put("customerId", customerId);
		Integer countObj = mobileAccordDismissDao.queryDismissEmpCount(parmMap);
		int total = 0;
		if (countObj == null) {
			total = 0;
		} else {
			total = countObj;
		}
		if (total == 0) {
			return map;
		}
		map.put("pref",
				handDismissData(
						mobileAccordDismissDao.queryDismissPref(parmMap), total));
		map.put("companyAge", queryDismissCompanyAge(parmMap, total));
		map.put("ability",
				handDismissData(
						mobileAccordDismissDao.queryDismissAbility(parmMap),
						total));
		return map;
	}

	private List<DismissTypeDto> handDismissData(List<DismissTypeDto> list,
			int count) {
		for (DismissTypeDto dto : list) {
			if (null != dto.getRunOffCount()) {
				dto.setRate(dto.getRunOffCount() * 100.0 / count);
			} else {
				dto.setRate(0.0);
			}
		}
		return list;
	}

	private List<DismissTypeDto> queryDismissCompanyAge(
			Map<String, Object> parmMap, int total) {
		List<DismissTypeDto> dtos = CollectionKit.newList();
		List<DismissTypeDto> ageDtos = mobileAccordDismissDao
				.queryDismissCompanyAge(parmMap);
		if (ageDtos.size() > 0) {
			for (CompanyAgeEnum ageType : CompanyAgeEnum.values()) {
				DismissTypeDto dismissAge = new DismissTypeDto();
				dismissAge.setTypeId(String.valueOf(ageType.getTypeId()));
				dismissAge.setTypeName(ageType.getDesc());
				for (DismissTypeDto dto : ageDtos) {
					if (Integer.valueOf(dto.getTypeId()).intValue() == ageType
							.getTypeId()) {
						// dismissAge.setWorkingCount(dto.getWorkingCount());
						// dismissAge.setRunOffCount(dto.getRunOffCount());
						dismissAge.setRunOffCount(dto.getRunOffCount());
						if (null != dto.getRunOffCount()) {
							dismissAge.setRate(dto.getRunOffCount() * 100.0
									/ total);
						} else {
							dismissAge.setRate(0.0);
						}
						// dismissAge.setRate(dto.getRunOffCount()*100.0/total);
						break;
					}
				}
				dtos.add(dismissAge);
			}
		}
		return dtos;
	}

}
