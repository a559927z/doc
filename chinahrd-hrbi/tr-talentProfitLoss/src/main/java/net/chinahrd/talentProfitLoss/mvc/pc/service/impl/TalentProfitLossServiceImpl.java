package net.chinahrd.talentProfitLoss.mvc.pc.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.talentProfitLoss.TalentProfitLossDto;
import net.chinahrd.entity.dto.pc.talentProfitLoss.TalentProfitLossJSONDto;
import net.chinahrd.talentProfitLoss.mvc.pc.dao.TalentProfitLossDao;
import net.chinahrd.talentProfitLoss.mvc.pc.service.TalentProfitLossService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

/**
 * 人才损益
 * 
 * @author malong 2016-05-25
 */
@Service("talentProfitLossService")
public class TalentProfitLossServiceImpl implements TalentProfitLossService {

	@Autowired
	private TalentProfitLossDao talentProfitLossDao;

	/**
	 * 本月/本年人才损益值
	 */
	@Override
	public Map<String, Integer> queryTalentProfitLossVal(String customerId, String organId, String year, String month,
			List<Integer> inflowList, List<Integer> outflowList) {
		Map<String, Integer> map = CollectionKit.newMap();
		int yearNum = 0;
		int monthNum = 0;
		List<TalentProfitLossDto> list = talentProfitLossDao.queryTalentProfitLossVal(customerId, organId, year, month,
				inflowList, outflowList);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if ("year".equals(list.get(i).getFlag())) {
					yearNum = integerIsNull(list.get(i).getConNum());
				} else if ("month".equals(list.get(i).getFlag())) {
					monthNum = integerIsNull(list.get(i).getConNum());
				}
			}
		}
		map.put("year", yearNum);
		map.put("month", monthNum);
		return map;
	}

	/**
	 * 本月/本年流入统计
	 */
	@Override
	public Map<String, Object> queryTalentInflowVal(String customerId, String organId, List<Integer> list) {
		Map<String, Object> map = CollectionKit.newMap();
		String year = DateUtil.getDBNow().substring(0, 4);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String befYearMonth = year + "01";
		List<TalentProfitLossDto> newList = talentProfitLossDao.queryTalentInflowVal(customerId, organId, befYearMonth, yearMonth,
				list);
		int yearCountNum = 0, monthCountNum = 0;
		String yearCountName = null, monthCountName = null;
		if (newList != null && newList.size() > 0) {
			for (TalentProfitLossDto l : newList) {
				if ("year".equals(l.getFlag())) {
					yearCountNum = integerIsNull(l.getConNum());
					yearCountName = l.getSumName();
				} else if ("month".equals(l.getFlag())) {
					monthCountNum = integerIsNull(l.getConNum());
					monthCountName = l.getSumName();
				}
			}
		}
		map.put("yearCountNum", yearCountNum);
		map.put("yearCountName", yearCountName);
		map.put("monthCountNum", monthCountNum);
		map.put("monthCountName", monthCountName);
		return map;
	}

	/**
	 * 本月/本年流出统计
	 */
	@Override
	public Map<String, Object> queryTalentOutflowVal(String customerId, String organId, List<Integer> list) {
		Map<String, Object> map = CollectionKit.newMap();
		String year = DateUtil.getDBNow().substring(0, 4);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String befYearMonth = year + "01";
		List<TalentProfitLossDto> newList = talentProfitLossDao.queryTalentOutflowVal(customerId, organId, befYearMonth, yearMonth,
				list);
		int yearCountNum = 0, monthCountNum = 0;
		String yearCountName = null, monthCountName = null;
		if (newList != null && newList.size() > 0) {
			for (TalentProfitLossDto l : newList) {
				if ("year".equals(l.getFlag())) {
					yearCountNum = integerIsNull(l.getConNum());
					yearCountName = l.getSumName();
				} else if ("month".equals(l.getFlag())) {
					monthCountNum = integerIsNull(l.getConNum());
					monthCountName = l.getSumName();
				}
			}
		}
		map.put("yearCountNum", yearCountNum);
		map.put("yearCountName", yearCountName);
		map.put("monthCountNum", monthCountNum);
		map.put("monthCountName", monthCountName);
		return map;
	}

	/**
	 * 流入统计弹窗明细-按钮组
	 * 
	 */
	@Override
	public List<TalentProfitLossDto> queryInflowDetailBtns(String customerId, List<Integer> list) {
		return talentProfitLossDao.queryInflowDetailBtns(customerId, list);
	}

	/**
	 * 流出统计弹窗明细-按钮组
	 * 
	 */
	@Override
	public List<TalentProfitLossDto> queryOutflowDetailBtns(String customerId, List<Integer> list) {
		return talentProfitLossDao.queryOutflowDetailBtns(customerId, list);
	}

	/**
	 * 流入统计弹窗明细
	 * 
	 */
	@Override
	public PaginationDto<TalentProfitLossDto> queryTalentInflowDetail(String customerId, String organId, String year,
			String month, List<Integer> list, int page, int rows) {
		PaginationDto<TalentProfitLossDto> dto = new PaginationDto<TalentProfitLossDto>(page, rows);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String befYearMonth = year + "01";
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("befYearMonth", befYearMonth);
		map.put("endYearMonth", yearMonth);
		map.put("month", month);
		map.put("list", list);
		map.put("start", dto.getOffset());
		map.put("rows", dto.getLimit());
		Integer count = talentProfitLossDao.queryTalentInflowDetailCount(map);
		List<TalentProfitLossDto> dtoList = talentProfitLossDao.queryTalentInflowDetail(map);
		int records = integerIsNull(count) > 0 ? count : 1;
		dto.setRecords(records);
		dto.setRows(dtoList);
		return dto;
	}

	/**
	 * 流出统计弹窗明细
	 */
	@Override
	public PaginationDto<TalentProfitLossDto> queryTalentOutflowDetail(String customerId, String organId, String year,
			String month, List<Integer> list, int page, int rows) {
		PaginationDto<TalentProfitLossDto> dto = new PaginationDto<TalentProfitLossDto>(page, rows);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String befYearMonth = year + "01";
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("befYearMonth", befYearMonth);
		map.put("endYearMonth", yearMonth);
		map.put("month", month);
		map.put("list", list);
		map.put("start", dto.getOffset());
		map.put("rows", dto.getLimit());
		Integer count = talentProfitLossDao.queryTalentOutflowDetailCount(map);
		List<TalentProfitLossDto> dtoList = talentProfitLossDao.queryTalentOutflowDetail(map);
		int records = integerIsNull(count) > 0 ? count : 1;
		dto.setRecords(records);
		dto.setRows(dtoList);
		return dto;
	}

	/**
	 * 人才损益-时间人群
	 */
	@Override
	public Map<String, Object> queryTimecrowd(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<TalentProfitLossDto> list = talentProfitLossDao.queryTimecrowd(customerId, organId);
		String maxDate = DateUtil.getDBNow().substring(0, 10);
		maxDate = getNewDbNow(maxDate);
		String minDate = DateUtil.getDBNow().substring(0, 7) + "-01";
		String newMinYear = "", newMinMonth = "", newMaxYear = "", newMaxMonth = "";
		if (list != null && list.size() > 0) {
			for (TalentProfitLossDto l : list) {
				// maxDate = l.getMaxDate();
				minDate = l.getMinDate();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(maxDate));
				changeDateWithMonth(c, -5);
				String[] newMinDate = sdf.format(c.getTime()).split("-");
				newMinYear = newMinDate[0];
				newMinMonth = Integer.parseInt(newMinDate[1]) + "";
				String[] newMaxDate = maxDate.split("-");
				newMaxYear = newMaxDate[0];
				newMaxMonth = Integer.parseInt(newMaxDate[1]) + "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String[] selectedDateOne = new String[1];
		selectedDateOne[0] = maxDate.substring(0, 7);
		String[] selectedDate = new String[4];
		selectedDate[0] = newMinYear;
		selectedDate[1] = newMinMonth;
		selectedDate[2] = newMaxYear;
		selectedDate[3] = newMaxMonth;
		map.put("maxDate", maxDate);
		map.put("minDate", minDate);
		map.put("selectedDate", selectedDate);
		map.put("selectedDateOne", selectedDateOne);
		return map;
	}
	
	/**
	 * 人才损益-人员分布-地图
	 */
	@Override
	public Map<String, Object> queryPopulationMap(String customerId, String organId, String[] times, String[] crowds) {
		List<Object> linkedList = CollectionKit.newLinkedList();
		Map<String, Object> lastMap = CollectionKit.newMap();
		String date = null;
		int minNum = 0, maxNum = 0;
		List<Object> newList = CollectionKit.newList();
		List<String> crowdsList = null;
		if (times != null && times.length > 0) {
			date = times[0].replaceAll("-", "");
		}
		if (crowds != null && crowds.length > 0) {
			crowdsList = Arrays.asList(crowds);
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("date", date);
		parMap.put("list", crowdsList);
		List<TalentProfitLossDto> list = null;
		if (crowdsList != null && crowdsList.size() > 0) {
			list = talentProfitLossDao.queryPopulationMapWithPerson(parMap);
		} else {
			list = talentProfitLossDao.queryPopulationMap(parMap);
		}
		if (list != null && list.size() > 0) {
			for (TalentProfitLossDto l : list) {
				if (integerIsNull(l.getConNum()) > 0) {
					String name = l.getProvinceName().substring(0, 2);
					if (name.indexOf("内蒙") != -1) {
						name = "内蒙古";
					}
					if (name.indexOf("黑龙") != -1) {
						name = "黑龙江";
					}
					Map<String, Object> map = CollectionKit.newMap();
					map.put("id", l.getProvinceId());
					map.put("name", name);
					map.put("value", integerIsNull(l.getConNum()));
					linkedList.add(map);
					newList.add(integerIsNull(l.getConNum()));
				}
			}
		}
		if (newList != null && newList.size() > 0) {
			Integer[] i = newList.toArray(new Integer[newList.size()]);
			Arrays.sort(i);
			minNum = i[0];
			maxNum = i[i.length - 1];
		}
		lastMap.put("list", linkedList);
		lastMap.put("minNum", minNum);
		lastMap.put("maxNum", maxNum);
		return lastMap;
	}

	/**
	 * 人才损益-人员分布-饼图
	 */
	@Override
	public List<Object> queryPopulationPie(String customerId, String organId, String provinceId, String[] times,
			String[] crowds) {
		List<Object> linkedList = CollectionKit.newLinkedList();
		Map<String, Object> map = null;
		String date = null;
		List<String> crowdsList = null;
		if (times != null && times.length > 0) {
			date = times[0].replaceAll("-", "");
		}
		if (crowds != null && crowds.length > 0) {
			crowdsList = Arrays.asList(crowds);
		}
		List<TalentProfitLossDto> list = null;
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("provinceId", provinceId);
		parMap.put("date", date);
		parMap.put("list", crowdsList);
		if (provinceId != null && !"".equals(provinceId)) {
			if (crowdsList != null && crowdsList.size() > 0) {
				list = talentProfitLossDao.queryPopulationPieByProvinceWithPerson(parMap);
			} else {
				list = talentProfitLossDao.queryPopulationPieByProvince(parMap);
			}
			if (list != null && list.size() > 0) {
				for (TalentProfitLossDto l : list) {
					map = CollectionKit.newMap();
					map.put("name", l.getCityName() + " " + integerIsNull(l.getConNum()));
					map.put("value", integerIsNull(l.getConNum()));
					linkedList.add(map);
				}
			}
		} else {
			if (crowdsList != null && crowdsList.size() > 0) {
				list = talentProfitLossDao.queryPopulationPieWithPerson(parMap);
			} else {
				list = talentProfitLossDao.queryPopulationPie(parMap);
			}
			if (list != null && list.size() > 0) {
				for (TalentProfitLossDto l : list) {
					map = CollectionKit.newMap();
					map.put("name", l.getProvinceName() + " " + integerIsNull(l.getConNum()));
					map.put("value", integerIsNull(l.getConNum()));
					linkedList.add(map);
				}
			}
		}
		return linkedList;
	}

	/**
	 * 人才损益环比
	 */
	@Override
	public Map<String, Object> queryTalentProfitLossRingData(String customerId, String organId, String year,
			String month, String[] times, String[] crowds, List<Integer> inflowList, List<Integer> outflowList) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		String minDate = null, maxDate = null;
		List<String> crowdsList = null;
		if (times != null && times.length >= 4) {
			minDate = times[0] + addZeroToString(times[1]);
			maxDate = times[2] + addZeroToString(times[3]);
		} else {
			maxDate = year + "" + month;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(maxDate));
				changeDateWithMonth(c, -5);
				minDate = sdf.format(c.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (crowds != null && crowds.length > 0) {
			crowdsList = Arrays.asList(crowds);
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("minDate", minDate);
		parMap.put("maxDate", maxDate);
		parMap.put("list", crowdsList);
		parMap.put("inflowList", inflowList);
		parMap.put("outflowList", outflowList);
		List<TalentProfitLossDto> list = null;
		if (crowdsList != null && crowdsList.size() > 0) {
			list = talentProfitLossDao.queryTalentProfitLossRingDataWithPerson(parMap);
		} else {
			list = talentProfitLossDao.queryTalentProfitLossRingData(parMap);
		}
		List<String> dateList = CollectionKit.newLinkedList();
		List<Integer> conList = CollectionKit.newLinkedList();
		int num = 0;
		int maxNum = 0;
		if (list != null && list.size() > 0) {
			Map<String, Integer> map = CollectionKit.newMap();
			for (TalentProfitLossDto l : list) {
				map.put(l.getChangeDate(), l.getConNum());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
			Date dBegin;
			try {
				dBegin = sdf.parse(minDate.substring(0, 4) + "." + minDate.substring(4));
				Date dEnd = sdf.parse(maxDate.substring(0, 4) + "." + maxDate.substring(4));
				List<Date> lDate = queryDatesWithMonth(dBegin, dEnd);
				for (Date date : lDate) {
					dateList.add(sdf.format(date));
					conList.add(map.get(sdf.format(date)) == null ? num : map.get(sdf.format(date)));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (conList != null && conList.size() > 0) {
			Integer[] i = conList.toArray(new Integer[conList.size()]);
			Arrays.sort(i);
			maxNum = i[0];
			if (i[0] < 0) {
				maxNum = i[0];
			}
		}
		rsMap.put("date", dateList.toArray(new String[dateList.size()]));
		rsMap.put("conNum", conList.toArray(new Integer[conList.size()]));
		rsMap.put("maxNum", maxNum);
		return rsMap;
	}

	/**
	 * 人才损益同比
	 */
	@Override
	public Map<String, Object> queryTalentProfitLossSameData(String customerId, String organId, String year,
			String month, String[] times, String[] crowds, List<Integer> inflowList, List<Integer> outflowList) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		String minCurDate = null, maxCurDate = null, minOldDate = null, maxOldDate = null;
		List<String> crowdsList = null;
		if (times != null && times.length >= 4) {
			minCurDate = times[0] + addZeroToString(times[1]);
			maxCurDate = times[2] + addZeroToString(times[3]);
			minOldDate = (Integer.parseInt(times[0]) - 1) + addZeroToString(times[1]);
			maxOldDate = (Integer.parseInt(times[2]) - 1) + addZeroToString(times[3]);

		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			maxCurDate = year + "" + month;
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(maxCurDate));
				changeDateWithMonth(c, -5);
				minCurDate = sdf.format(c.getTime());
				c.setTime(sdf.parse(minCurDate));
				changeDateWithYear(c, -1);
				minOldDate = sdf.format(c.getTime());
				c.setTime(sdf.parse(maxCurDate));
				changeDateWithYear(c, -1);
				maxOldDate = sdf.format(c.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (crowds != null && crowds.length > 0) {
			crowdsList = Arrays.asList(crowds);
		}
		Map<String, Object> curMap = CollectionKit.newMap();
		curMap.put("customerId", customerId);
		curMap.put("organId", organId);
		curMap.put("minDate", minCurDate);
		curMap.put("maxDate", maxCurDate);
		curMap.put("list", crowdsList);
		curMap.put("inflowList", inflowList);
		curMap.put("outflowList", outflowList);
		Map<String, Object> oldMap = CollectionKit.newMap();
		oldMap.put("customerId", customerId);
		oldMap.put("organId", organId);
		oldMap.put("minDate", minOldDate);
		oldMap.put("maxDate", maxOldDate);
		oldMap.put("list", crowdsList);
		oldMap.put("inflowList", inflowList);
		oldMap.put("outflowList", outflowList);
		List<TalentProfitLossDto> curList = null;
		List<TalentProfitLossDto> oldList = null;
		if (crowdsList != null && crowdsList.size() > 0) {
			curList = talentProfitLossDao.queryTalentProfitLossRingDataWithPerson(curMap);
			oldList = talentProfitLossDao.queryTalentProfitLossRingDataWithPerson(oldMap);
		} else {
			curList = talentProfitLossDao.queryTalentProfitLossRingData(curMap);
			oldList = talentProfitLossDao.queryTalentProfitLossRingData(oldMap);
		}
		List<String> curDateList = CollectionKit.newLinkedList();
		List<String> oldDateList = CollectionKit.newLinkedList();
		List<Integer> curConList = CollectionKit.newLinkedList();
		List<Integer> oldConList = CollectionKit.newLinkedList();
		Map<String, Integer> curDateMap = CollectionKit.newMap();
		Map<String, Integer> oldDateMap = CollectionKit.newMap();
		int num = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
			if (curList != null && curList.size() > 0) {
				for (TalentProfitLossDto cl : curList) {
					curDateMap.put(cl.getChangeDate(), cl.getConNum());
				}
//				Date dBegin = sdf.parse(minCurDate.substring(0, 4) + "." + minCurDate.substring(4));
//				Date dEnd = sdf.parse(maxCurDate.substring(0, 4) + "." + maxCurDate.substring(4));
//				List<Date> lDate = queryDatesWithMonth(dBegin, dEnd);
//				for (Date date : lDate) {
//					curDateList.add(sdf.format(date));
//					curConList.add(curDateMap.get(sdf.format(date)) == null ? num : curDateMap.get(sdf.format(date)));
//				}
			}
			Date curBegin = sdf.parse(minCurDate.substring(0, 4) + "." + minCurDate.substring(4));
			Date curEnd = sdf.parse(maxCurDate.substring(0, 4) + "." + maxCurDate.substring(4));
			List<Date> curDate = queryDatesWithMonth(curBegin, curEnd);
			for (Date date : curDate) {
				curDateList.add(sdf.format(date));
				curConList.add(curDateMap.get(sdf.format(date)) == null ? num : curDateMap.get(sdf.format(date)));
			}
			if (oldList != null && oldList.size() > 0) {
				for (TalentProfitLossDto ol : oldList) {
					oldDateMap.put(ol.getChangeDate(), ol.getConNum());
				}
//				Date dBegin = sdf.parse(minOldDate.substring(0, 4) + "." + minOldDate.substring(4));
//				Date dEnd = sdf.parse(maxOldDate.substring(0, 4) + "." + maxOldDate.substring(4));
//				List<Date> lDate = queryDatesWithMonth(dBegin, dEnd);
//				for (Date date : lDate) {
//					oldDateList.add(sdf.format(date));
//					oldConList.add(oldDateMap.get(sdf.format(date)) == null ? num : oldDateMap.get(sdf.format(date)));
//				}
			}
			Date oldBegin = sdf.parse(minOldDate.substring(0, 4) + "." + minOldDate.substring(4));
			Date oldEnd = sdf.parse(maxOldDate.substring(0, 4) + "." + maxOldDate.substring(4));
			List<Date> oldDate = queryDatesWithMonth(oldBegin, oldEnd);
			for (Date date : oldDate) {
				oldDateList.add(sdf.format(date));
				oldConList.add(oldDateMap.get(sdf.format(date)) == null ? num : oldDateMap.get(sdf.format(date)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int maxNum = 0, cNum = 0, oNum = 0;
		if (curConList != null && curConList.size() > 0) {
			Integer[] ci = curConList.toArray(new Integer[curConList.size()]);
			Arrays.sort(ci);
			cNum = ci[0];
		}
		if (oldConList != null && oldConList.size() > 0) {
			Integer[] oi = oldConList.toArray(new Integer[oldConList.size()]);
			Arrays.sort(oi);
			oNum = oi[0];
		}
		if (cNum < oNum) {
			maxNum = cNum;
		} else if (cNum > oNum) {
			maxNum = oNum;
		}
		if (maxNum >= 0) {
			maxNum = 0;
		}

		rsMap.put("oldDate", oldDateList.toArray(new String[oldDateList.size()]));
		rsMap.put("oldConNum", oldConList.toArray(new Integer[oldConList.size()]));
		rsMap.put("curDate", curDateList.toArray(new String[curDateList.size()]));
		rsMap.put("curConNum", curConList.toArray(new Integer[curConList.size()]));
		rsMap.put("maxNum", maxNum);
		return rsMap;
	}

	/**
	 * 异动统计人群类型
	 */
	@Override
	public List<Object> queryChangePopulation(String customerId) {
		List<Object> linkedList = CollectionKit.newLinkedList();
		List<TalentProfitLossDto> list = talentProfitLossDao.queryChangePopulation(customerId);
		Map<String, String> map = CollectionKit.newMap();
		if (list != null && list.size() > 0) {
			for (TalentProfitLossDto l : list) {
				map = CollectionKit.newMap();
				map.put("value", l.getPopulationId());
				map.put("name", l.getPopulationName());
				linkedList.add(map);
			}
		}
		return linkedList;
	}

	/**
	 * 异动统计饼图表格
	 */
	@Override
	public Map<String, Object> queryInflowOutflowChangeType(String customerId, String organId, String startDate,
			String endDate, String parentType, String childType) {
		Map<String, Object> map = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7);
		String minDate = null, maxDate = null;
		if (startDate != null && !"".equals(startDate)) {
			minDate = startDate.replaceAll("-", "");
		}
		if (endDate != null && !"".equals(endDate)) {
			maxDate = endDate.replaceAll("-", "");
		}
		if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			minDate = yearMonth.replaceAll("-", "");
			maxDate = yearMonth.replaceAll("-", "");
		}
		List<Integer> newInList = CollectionKit.newLinkedList();
		List<Integer> newOutList = CollectionKit.newLinkedList();
		List<Integer> noFlowList = CollectionKit.newLinkedList();
		List<Integer> fullList = CollectionKit.newList();
		List<Integer> parentList = CollectionKit.newList();
		List<Integer> flowList = CollectionKit.newList();
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		List<Integer> empList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_EMP);
		List<Integer> mgrList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_MGR);
		fullList.addAll(empList);
		fullList.addAll(mgrList);
		if("p1".equals(parentType)){
			// 非管理人员-查询jc
			parentList = empList;
		} else if("p2".equals(parentType)){
			// 管理人员-查询oc
			parentList = mgrList;
		}
		if (parentList != null && parentList.size() > 0) {
			for (Integer i : parentList) {
				if (outflowList.contains(i)) {
					newOutList.add(i);
				} else if(inflowList.contains(i)){
					newInList.add(i);
				} else {
					noFlowList.add(i);
				}
			}
			flowList.addAll(newInList);
			flowList.addAll(newOutList);
		} else {
			for (Integer i : fullList) {
				if (outflowList.contains(i)) {
					newOutList.add(i);
				} else if(inflowList.contains(i)){
					newInList.add(i);
				} else {
					noFlowList.add(i);
				}
			}
			flowList.addAll(newInList);
			flowList.addAll(newOutList);
		}
		List<String> childList = CollectionKit.newList();
		if(childType != null && !"".equals(childType)){
			String[] childArray = childType.split(",");
			childList = Arrays.asList(childArray);
		} else {
			childList = null;
		}
		int count = 0;
		List<TalentProfitLossDto> finalList = CollectionKit.newList();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("minDate", minDate);
		parMap.put("maxDate", maxDate);
		parMap.put("list", flowList);
		parMap.put("noFlowList", noFlowList);
		parMap.put("childType", childList);
		if(flowList != null && flowList.size() > 0){
			if (newInList != null && newInList.size() > 0) {
				parMap.put("newInList", newInList);
				parMap.put("newOutList", null);
				count += talentProfitLossDao.queryInflowOutflowChangeTypeCount(parMap);
			}
			if (newOutList != null && newOutList.size() > 0) {
				parMap.put("newOutList", newOutList);
				parMap.put("newInList", null);
				count += talentProfitLossDao.queryInflowOutflowChangeTypeCount(parMap);
			}
			parMap.put("newInList", newInList);
			parMap.put("newOutList", newOutList);
			List<TalentProfitLossDto> list = talentProfitLossDao.queryInflowOutflowChangeType(parMap);
			finalList.addAll(list);
		}
		if(noFlowList != null && noFlowList.size() > 0){
			count += talentProfitLossDao.queryInflowOutflowChangeTypeCountByJc(parMap);
			List<TalentProfitLossDto> list = talentProfitLossDao.queryInflowOutflowChangeTypeByJc(parMap);
			finalList.addAll(list);
		}
		List<TalentProfitLossJSONDto> jsonList = CollectionKit.newLinkedList();
		if (finalList != null && finalList.size() > 0) {
			TalentProfitLossJSONDto talentProfitLossJSONDto;
			int num = 1;
			double per = 0.00D;
			for (TalentProfitLossDto l : finalList) {
				if (count > 0) {
					per = formatDouble((double)l.getConNum() * 100 / (double)count);
				}
				talentProfitLossJSONDto = new TalentProfitLossJSONDto();
				talentProfitLossJSONDto.setAlias(l.getChangeTypeName());
				talentProfitLossJSONDto.setIsExecutiveSeq(true);
				talentProfitLossJSONDto.setIsManagerSeq(false);
				talentProfitLossJSONDto.setJobTitleId(null);
				talentProfitLossJSONDto.setLvName(null);
				talentProfitLossJSONDto.setName(l.getChangeTypeName());
				talentProfitLossJSONDto.setPercent("" + per);
				talentProfitLossJSONDto.setPercentatge(per);
				talentProfitLossJSONDto.setPosJobLvId(null);
				talentProfitLossJSONDto.setSelected(false);
				talentProfitLossJSONDto.setSeqId(l.getCurtName());
				talentProfitLossJSONDto.setShowIndex(num);
				talentProfitLossJSONDto.setSum(l.getConNum());
				talentProfitLossJSONDto.setTitleName(null);
				talentProfitLossJSONDto.setTotal(count);
				talentProfitLossJSONDto.setValue("" + per);
				jsonList.add(talentProfitLossJSONDto);
				num++;
			}
		}
		Collections.sort(jsonList);
		map.put("datas", jsonList);
		map.put("total", count);
		return map;
	}

	/**
	 * 异动统计-序列分布
	 */
	@Override
	public Map<String, Object> querySequenceBar(String customerId, String organId, String startDate, String endDate,
			String parentType, String childType, String changeType) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> dateList = CollectionKit.newLinkedList();
		List<Double> valList = CollectionKit.newLinkedList();
		String yearMonth = DateUtil.getDBNow().substring(0, 7);
		String minDate = null, maxDate = null;
		if (startDate != null && !"".equals(startDate)) {
			minDate = startDate.replaceAll("-", "");
		}
		if (endDate != null && !"".equals(endDate)) {
			maxDate = endDate.replaceAll("-", "");
		}
		if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			minDate = yearMonth.replaceAll("-", "");
			maxDate = yearMonth.replaceAll("-", "");
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String flag = "";
		List<String> newInList = CollectionKit.newLinkedList();
		List<String> newOutList = CollectionKit.newLinkedList();
		if (changeType != null && !"".equals(changeType)) {
			if (outflowList.contains(Integer.parseInt(changeType))) {
				newOutList.add(changeType);
			} else if(inflowList.contains(Integer.parseInt(changeType))){
				newInList.add(changeType);
			} else {
				flag = "p2";
			}
		}
		List<String> childList = CollectionKit.newList();
		if(childType != null && !"".equals(childType)){
			String[] childArray = childType.split(",");
			childList = Arrays.asList(childArray);
		} else {
			childList = null;
		}
		List<TalentProfitLossDto> list = null;
		int count = 0;
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("minDate", minDate);
		parMap.put("maxDate", maxDate);
		parMap.put("childType", childList);
		parMap.put("changeType", changeType);
		if("p2".equals(flag)){
			count = talentProfitLossDao.querySequenceBarCountByJc(parMap);
			list = talentProfitLossDao.querySequenceBarByJc(parMap);
		} else {
			if(newInList != null && newInList.size() > 0){
				parMap.put("newInList", newInList);
			}
			if(newOutList != null && newOutList.size() > 0){
				parMap.put("newOutList", newOutList);
			}
			count = talentProfitLossDao.querySequenceBarCount(parMap);
			list = talentProfitLossDao.querySequenceBar(parMap);
		}
		if (list != null && list.size() > 0) {
			double per = 0D;
			for (TalentProfitLossDto l : list) {
				if (count > 0) {
					per = formatDouble((double)l.getConNum() * 100 / count);
				}
				dateList.add(l.getSequenceName() + " " + l.getConNum() + "人");
				valList.add(per);
			}
		}
		map.put("date", dateList.toArray(new String[dateList.size()]));
		map.put("seriesData", valList.toArray(new Double[valList.size()]));
		return map;
	}

	/**
	 * 异动统计-职级分布
	 */
	@Override
	public Map<String, Object> queryAbilityBar(String customerId, String organId, String startDate, String endDate,
			String parentType, String childType, String changeType) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> dateList = CollectionKit.newLinkedList();
		List<Double> valList = CollectionKit.newLinkedList();
		String yearMonth = DateUtil.getDBNow().substring(0, 7);
		String minDate = null, maxDate = null;
		if (startDate != null && !"".equals(startDate)) {
			minDate = startDate.replaceAll("-", "");
		}
		if (endDate != null && !"".equals(endDate)) {
			maxDate = endDate.replaceAll("-", "");
		}
		if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			minDate = yearMonth.replaceAll("-", "");
			maxDate = yearMonth.replaceAll("-", "");
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String flag = "";
		List<String> newInList = CollectionKit.newLinkedList();
		List<String> newOutList = CollectionKit.newLinkedList();
		if (changeType != null && !"".equals(changeType)) {
			if (outflowList.contains(Integer.parseInt(changeType))) {
				newOutList.add(changeType);
			} else if(inflowList.contains(Integer.parseInt(changeType))){
				newInList.add(changeType);
			} else {
				flag = "p2";
			}
		}
		List<String> childList = CollectionKit.newList();
		if(childType != null && !"".equals(childType)){
			String[] childArray = childType.split(",");
			childList = Arrays.asList(childArray);
		} else {
			childList = null;
		}
		List<TalentProfitLossDto> list = null;
		int count = 0;
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("minDate", minDate);
		parMap.put("maxDate", maxDate);
		parMap.put("childType", childList);
		parMap.put("changeType", changeType);
		if("p2".equals(flag)){
			count = talentProfitLossDao.queryAbilityBarCountByJc(parMap);
			list = talentProfitLossDao.queryAbilityBarByJc(parMap);
		} else {
			if(newInList != null && newInList.size() > 0){
				parMap.put("newInList", newInList);
			}
			if(newOutList != null && newOutList.size() > 0){
				parMap.put("newOutList", newOutList);
			}
			count = talentProfitLossDao.queryAbilityBarCount(parMap);
			list = talentProfitLossDao.queryAbilityBar(parMap);
		}
		if (list != null && list.size() > 0) {
			double per = 0D;
			for (TalentProfitLossDto l : list) {
				if (count > 0) {
					per = formatDouble((double)l.getConNum() * 100 / count);
				}
				dateList.add(l.getCurtName() + "级 " + l.getConNum() + "人");
				valList.add(per);
			}
		}
		map.put("date", dateList.toArray(new String[dateList.size()]));
		map.put("seriesData", valList.toArray(new Double[valList.size()]));
		return map;
	}

	/**
	 * 异动统计-入职名单
	 */
	@Override
	public PaginationDto<TalentProfitLossDto> queryEntryListDatas(String customerId, String organId, String startDate,
			String endDate, String parentType, String childType, String changeType, int page, int rows) {
		PaginationDto<TalentProfitLossDto> dto = new PaginationDto<TalentProfitLossDto>(page, rows);
		String yearMonth = DateUtil.getDBNow().substring(0, 7);
		String yearMonthDay = DateUtil.getDBNow().substring(0, 10);
		yearMonthDay = getNewDbNow(yearMonthDay);
		String minDate = null, maxDate = null;
		if (startDate != null && !"".equals(startDate)) {
			minDate = startDate.replaceAll("-", "");
		}
		if (endDate != null && !"".equals(endDate)) {
			maxDate = endDate.replaceAll("-", "");
		}
		if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			minDate = yearMonth.replaceAll("-", "");
			maxDate = yearMonth.replaceAll("-", "");
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String flag = "";
		List<String> newInList = CollectionKit.newLinkedList();
		List<String> newOutList = CollectionKit.newLinkedList();
		if (changeType != null && !"".equals(changeType)) {
			if (outflowList.contains(Integer.parseInt(changeType))) {
				newOutList.add(changeType);
			} else if(inflowList.contains(Integer.parseInt(changeType))){
				newInList.add(changeType);
			} else {
				flag = "p2";
			}
		}
		List<String> childList = CollectionKit.newList();
		if(childType != null && !"".equals(childType)){
			String[] childArray = childType.split(",");
			childList = Arrays.asList(childArray);
		} else {
			childList = null;
		}
		List<TalentProfitLossDto> list = null;
		int count = 0;
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("minDate", minDate);
		map.put("maxDate", maxDate);
		map.put("childType", childList);
		map.put("changeType", changeType);
		map.put("start", dto.getOffset());
		map.put("rows", dto.getLimit());
		if("p2".equals(flag)){
			count = talentProfitLossDao.queryEntryListDatasCountByJc(map);
			list = talentProfitLossDao.queryEntryListDatasByJc(map);
		} else {
			if(newInList != null && newInList.size() > 0){
				map.put("newInList", newInList);
			}
			if(newOutList != null && newOutList.size() > 0){
				map.put("newOutList", newOutList);
			}
			count = talentProfitLossDao.queryEntryListDatasCount(map);
			list = talentProfitLossDao.queryEntryListDatas(map);
		}
		int records = integerIsNull(count) > 0 ? count : 1;
		dto.setRecords(records);
		dto.setRows(list);
		return dto;
	}

	/**
	 * 获取最新日期
	 */
	@Override
	public String getNewDbNow(String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDay = "";
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(day));
			changeDateWithDay(c, -1);
			newDay = sdf.format(c.getTime());
		} catch (Exception e) {
			return "";
		}
		return newDay;
	}
	
	/**
	 * 获取当前日期的本月最后一天
	 */
	public String getLastDayForThisMonth(String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDay = "";
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(day));
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
			newDay = sdf.format(c.getTime());
		} catch (Exception e) {
			return "";
		}
		return newDay;
		
	}

	/**
	 * 判断integer是否为空
	 */
	public int integerIsNull(Integer i) {
		if ((i + "") != null && !"".equals((i + "")) && i != null) {
			return i;
		}
		return 0;
	}

	/**
	 * 比较两字符串
	 */
	public int compareTwoString(String msg1, String msg2) {
		int a = 0, b = 0, c = 0;
		if (msg1.indexOf("/") != -1) {
			a = Integer.parseInt(msg1.split("/")[0] + msg1.split("/")[1]);
		} else if (isInteger(msg1)) {
			a = Integer.parseInt(msg1);
		}
		if (msg2.indexOf("/") != -1) {
			b = Integer.parseInt(msg2.split("/")[0] + msg2.split("/")[1]);
		} else if (isInteger(msg2)) {
			b = Integer.parseInt(msg2);
		}
		if (a > b) {
			c = 1;
		} else if (a < b) {
			c = 2;
		}
		return c;
	}

	/**
	 * 判断字符串是否能转化成整数
	 */
	public boolean isInteger(String msg) {
		try {
			Integer.parseInt(msg);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串小于10，前面补充‘0’
	 */
	public String addZeroToString(String str) {
		return Integer.parseInt(str) >= 10 ? "" + Integer.parseInt(str) : "0" + Integer.parseInt(str);
	}

	/**
	 * 根据季度转化成月
	 */
	public String changeQuarterToMonth(String quarter) {
		String month = null;
		switch (quarter) {
		case "Q1":
			month = "03";
			break;
		case "Q2":
			month = "06";
			break;
		case "Q3":
			month = "09";
			break;
		case "Q4":
			month = "12";
			break;
		}
		return month;
	}

	/**
	 * double四舍五入保留两位
	 */
	public double formatDouble(double data) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(data));
	}

	/**
	 * 获取区间日期
	 */
	public static List<Date> queryDatesWithMonth(Date dBegin, Date dEnd) {
		List<Date> lDate = CollectionKit.newLinkedList();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())) {
			calBegin.add(Calendar.MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * 变换日期-天
	 */
	public static Calendar changeDateWithDay(Calendar c, int day) {
		c.add(Calendar.DATE, day);
		return c;
	}

	/**
	 * 变换日期-月
	 */
	public static Calendar changeDateWithMonth(Calendar c, int month) {
		c.add(Calendar.MONTH, month);
		return c;
	}

	/**
	 * 变换日期-年
	 */
	public static Calendar changeDateWithYear(Calendar c, int year) {
		c.add(Calendar.YEAR, year);
		return c;
	}

}
