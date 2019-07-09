package net.chinahrd.zte.laborEfficiency.mvc.pc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyGridDto;
import net.chinahrd.entity.dtozte.pc.laborefficiency.LaborEfficiencyImportDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.zte.laborEfficiency.mvc.pc.dao.LaborEfficiencyDao;
import net.chinahrd.zte.laborEfficiency.mvc.pc.service.LaborEfficiencyService;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Service("laborEfficiencyService")
public class LaborEfficiencyServiceImpl implements LaborEfficiencyService {

	@Autowired
	private LaborEfficiencyDao laborEfficiencyDao;

	/**
	 * 劳动力效能对比
	 */
	public Map<String, Object> queryLaborEfficiencyRatio(String customerId, String organId, String beginTime,
			String endTime, String populationIds) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		List<String> listPopulationIds = CollectionKit.newList();
		List<LaborEfficiencyDto> listOrgan = laborEfficiencyDao.queryOrgan(customerId, organId);
		List<LaborEfficiencyDto> list = CollectionKit.newList();
		for(LaborEfficiencyDto dto : listOrgan) {
			Map<String, Object> paramMap = CollectionKit.newMap();
			if (populationIds != null && !"".equals(populationIds)) {
				listPopulationIds = listPopulationIds(populationIds);
				paramMap.put("listPopulationIds", listPopulationIds);
			}
			paramMap.put("customerId", customerId);
			paramMap.put("organId", dto.getOrganId());
			paramMap.put("beginTime", beginTime);
			paramMap.put("endTime", endTime);
			LaborEfficiencyDto result = laborEfficiencyDao.queryLaborEfficiencyRatio(paramMap);
			dto.setActualAttendance(result.getActualAttendance());
			dto.setBeInAttendance(result.getBeInAttendance());
			dto.setAttendanceRate(result.getAttendanceRate());
			list.add(dto);
		}
		
		List<LaborEfficiencyDto> listBar = CollectionKit.newList();
		List<LaborEfficiencyDto> listGrid = CollectionKit.newList();
		if (list != null) {
			for (LaborEfficiencyDto dto : list) {
				if (!dto.getOrganId().equals(organId)) {
					listBar.add(dto);
				}
			}
			for (LaborEfficiencyDto dto : list) {
				if (dto.getOrganId().equals(organId)) {
					listGrid.add(dto);
				}
			}
			listGrid.addAll(listBar);
			resultMap.put("listBar", listBar);
			resultMap.put("listGrid", listGrid);
			resultMap.put("list", list);
		}
		return resultMap;
	}
	
	public List<LaborEfficiencyDto> comparisonTime(String beginTime, String endTime) {
		List<LaborEfficiencyDto> listTime = CollectionKit.newList();
		int beginYear = Integer.parseInt(beginTime.substring(0, 4));
		int endYear = Integer.parseInt(endTime.substring(0, 4));
		int begin = Integer.parseInt(beginTime);
		int end = Integer.parseInt(endTime);
		if(endYear - beginYear == 0) {
			int num = end - begin;
			for(int i = 0; i <= num; i ++) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setDate(getDays(begin + i));
				dto.setYearMonth(begin + i);
				listTime.add(dto);
			}
		} else if(endYear - beginYear == 1) {
			int num = beginYear * 100 + 13 - begin + (end - endYear * 100);
			int flag = 0;
			for(int i = 0; i < num; i ++) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				if(begin + i <= beginYear * 100 + 12) {
					dto.setDate(getDays(begin + i));
					dto.setYearMonth(begin + i);
					if(begin + i == beginYear * 100 + 12) {
						flag = i;
					}
				} else {
					dto.setDate(getDays(endYear * 100 + (i - flag)));
					dto.setYearMonth(endYear * 100 + (i - flag));
				}
				listTime.add(dto);
			}
		} else {
			int yearNum = endYear - beginYear - 1;
			int num1 = beginYear * 100 + 13 - begin;
			int num3 = end - endYear * 100;
			for(int i = 0; i < num1; i ++) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setDate(getDays(begin + i));
				dto.setYearMonth(begin + i);
			}
			for(int j = 1; j <= yearNum; j ++) {
				for(int i = 1; i <= 12; i ++) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					String mon = i > 9 ? "" + i : "0" + i;
					int yearMonth = Integer.parseInt((beginYear + j) + mon);
					dto.setDate(getDays(yearMonth));
					dto.setYearMonth(yearMonth);
				}
			}
			for(int i = 1; i <= num3; i ++) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				String mon = i > 9 ? "" + i : "0" + i;
				int yearMonth = Integer.parseInt(endYear + mon);
				dto.setDate(getDays(yearMonth));
				dto.setYearMonth(yearMonth);
			}
		}
		return listTime;
	}
	
	public String getDays(int yearMonth) {
		String days = "";
		String now = DateUtil.getDBNow();
		if(String.valueOf(yearMonth).equals(now.substring(0, 4) + now.substring(5, 7))) {
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd ");
			Calendar cal = Calendar.getInstance();
			try {
				Date date = sdf.parse(now);
				cal.setTime(date);
				cal.add(cal.DATE, -1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return sdf.format(cal.getTime()); 
		}
		String time = String.valueOf(yearMonth);
		int month = Integer.parseInt(time.substring(4, 6));
		switch(month) {
			case 1: 
			case 3: 
			case 5: 
			case 7: 
			case 8: 
			case 10: 
			case 12: days = time.substring(0, 4) + "-" + time.substring(4, 6) + "-31";
				break;
			case 4: 
			case 6: 
			case 9: 
			case 11: days = time.substring(0, 4) + "-" + time.substring(4, 6) + "-30";
				break;
			default: if(yearMonth/100%4 == 0) days = time.substring(0, 4) + "-" + time.substring(4, 6) + "-29";
					else days = time.substring(0, 4) + "-" + time.substring(4, 6) + "-28";
		}
		return days;
	}
	
	public Map<String, Integer> findMinMaxTime(String customerId) {
		return laborEfficiencyDao.findMinMaxTime(customerId);
	}

	public String queryParentOrganId(String customerId, String organId) {
		return laborEfficiencyDao.queryParentOrganId(customerId, organId);
	}

	public Map<String, Object> getConditionValue(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<LaborEfficiencyDto> listOrgans = laborEfficiencyDao.queryOrgan(customerId, organId);
		Map<String, Integer> timeMap = laborEfficiencyDao.findMinMaxTime(customerId);
		int beginTime = timeMap.get("minTime");
		int endTime = timeMap.get("maxTime");

		map.put("listOrgans", listOrgans);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return map;
	}

	/**
	 * 劳动力效能明细
	 */
	public PaginationDto<LaborEfficiencyDto> queryLaborEfficiencyDetail(String customerId, String organId,
			String beginTime, String endTime, String populationIds, PaginationDto<LaborEfficiencyDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> map = CollectionKit.newMap();
		List<String> listPopulationIds = CollectionKit.newList();
		List<LaborEfficiencyDto> listTime = comparisonTime(beginTime, endTime);
		if (populationIds != null && !"".equals(populationIds)) {
			listPopulationIds = listPopulationIds(populationIds);
			map.put("listPopulationIds", listPopulationIds);
		}
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("rowBounds", rowBounds);
		map.put("listTime", listTime);
		int count = laborEfficiencyDao.queryLaborEfficiencyDetailCount(map);
		List<LaborEfficiencyDto> dtos = laborEfficiencyDao.queryLaborEfficiencyDetail(map);
		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
	}

	/**
	 * 劳动力效能趋势
	 */
	public Map<String, Object> queryLaborEfficiencyTrend(String customerId, String organId, String beginTime,
			String endTime, String populationIds, int type) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		Map<String, Object> paramMap = CollectionKit.newMap();
		List<LaborEfficiencyDto> listRatio = CollectionKit.newList();
		List<LaborEfficiencyDto> listBasis1 = CollectionKit.newList();
		List<LaborEfficiencyDto> listBasis2 = CollectionKit.newList();
		List<String> listBasis = CollectionKit.newList();
		List<LaborEfficiencyDto> list = CollectionKit.newList();
		
		List<String> listPopulationIds = CollectionKit.newList();
		if (populationIds != null && !"".equals(populationIds)) {
			listPopulationIds = listPopulationIds(populationIds);
			paramMap.put("listPopulationIds", listPopulationIds);
		}
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginTime", Integer.parseInt(beginTime) - 100);
		paramMap.put("endTime", endTime);
		list = laborEfficiencyDao.queryLaborEfficiencyTrend(paramMap);
		
		if (type == 0) {
			for (int i = 0; i < list.size(); i++) {
				LaborEfficiencyDto dto = list.get(i);
				if (dto.getYearMonth() >= Integer.parseInt(beginTime)) {
					dto.setRate(list.get(i - 1).getAttendanceRate() == 0 ? 0 : (dto.getAttendanceRate() - list.get(i - 1).getAttendanceRate()) / list.get(i - 1).getAttendanceRate());
					listRatio.add(dto);
					listBasis1.add(dto);
				}
				if (dto.getYearMonth() < Integer.parseInt(getNextMonth(endTime)) - 100
						&& dto.getYearMonth() >= Integer.parseInt(beginTime) - 100) {
					listBasis2.add(dto);
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				LaborEfficiencyDto dto = list.get(i);
				if (dto.getYearMonth() >= Integer.parseInt(beginTime)) {
					dto.setRate(list.get(i - 1).getAttendanceRate() == 0 ? 0 : (dto.getAttendanceRate() - list.get(i - 1).getAttendanceRate()) / list.get(i - 1).getAttendanceRate());
					listRatio.add(dto);
					listBasis1.add(dto);
				} else if(dto.getYearMonth() <= Integer.parseInt(endTime) - 100) {
					listBasis2.add(dto);
				}
			}
		}
		for (int j = 0; j < listBasis1.size(); j++) {
			listBasis.add(String.valueOf(listBasis2.get(j).getAttendanceRate() == 0 ? 0 : (listBasis1.get(j).getAttendanceRate() - listBasis2.get(j).getAttendanceRate()) / listBasis2.get(j).getAttendanceRate()));
		}
		resultMap.put("listRatio", listRatio);
		resultMap.put("listBasis1", listBasis1);
		resultMap.put("listBasis2", listBasis2);
		resultMap.put("listBasis", listBasis);
		return resultMap;
	}
	
	/**
	 * 组织机构加班时长
	 */
	public Map<String, Object> queryOvertimeByOrgan(String customerId, String organId, String beginTime, String endTime,
			String populationIds) {
		Map<String, Object> paramMap = CollectionKit.newMap();
		Map<String, Object> resultMap = CollectionKit.newMap();
		List<LaborEfficiencyDto> list = CollectionKit.newList();
		List<String> listPopulationIds = CollectionKit.newList();
		if (populationIds != null && !"".equals(populationIds)) {
			listPopulationIds = listPopulationIds(populationIds);
			paramMap.put("listPopulationIds", listPopulationIds);
		}
		List<LaborEfficiencyDto> listOrgan = laborEfficiencyDao.queryOrgan(customerId, organId);
		List<LaborEfficiencyDto> listTime = comparisonTime(beginTime, endTime);
		List<LaborEfficiencyDto> resList = CollectionKit.newList();
		int size = listTime.size();
		for(LaborEfficiencyDto dto : listOrgan) {
			paramMap.put("customerId", customerId);
			paramMap.put("organId", dto.getOrganId());
			paramMap.put("beginTime", beginTime);
			paramMap.put("endTime", endTime);
			List<LaborEfficiencyDto> result = laborEfficiencyDao.queryOvertimeByOrgan(paramMap);
			double avg = 0.0;
			double con = 0.0;
			for(LaborEfficiencyDto r : result) {
				avg += r.getAvgNum();
				con += r.getCount();
			}
			dto.setAvgNum(avg/size);
			dto.setConNum(con);
			resList.add(dto);
		}
		
		for (LaborEfficiencyDto dto : resList) {
			if (dto.getOrganId().equals(organId)) {
				resultMap.put("avgNum", dto.getAvgNum());
				resultMap.put("conNum", dto.getConNum());
			} else {
				list.add(dto);
			}
		}
		resultMap.put("list", list);
		return resultMap;
	}

	/**
	 * 取前一个月时间
	 * 
	 * @param yearMonth
	 * @return
	 */
	public String getPreMonth(String yearMonth) {
		String year = yearMonth.substring(0, 4);
		String month = yearMonth.substring(4, 6);
		if ("01".equals(month)) {
			return Integer.parseInt(year) - 1 + "12";
		}
		return Integer.parseInt(yearMonth) - 1 + "";
	}

	/**
	 * 取下个月时间
	 * 
	 * @param yearMonth
	 * @return
	 */
	public String getNextMonth(String yearMonth) {
		String year = yearMonth.substring(0, 4);
		String month = yearMonth.substring(4, 6);
		if ("12".equals(month)) {
			return Integer.parseInt(year) + 1 + "01";
		}
		return Integer.parseInt(yearMonth) + 1 + "";
	}

	/**
	 * 获取劳动力效能值
	 */
	@Override
	public Map<String, Object> getLaborEfficiencyValue(String customerId, String organId, String date) {
		Map<String, Object> map = CollectionKit.newMap();
		Map<String, Object> reMap = laborEfficiencyDao.getLaborEfficiencyValue(customerId, organId, date);
		double num = 0D;
		if (reMap != null) {
			double aNum = (double) reMap.get("actualNum");
			double sNum = (double) reMap.get("shouldNum");
			if (sNum > 0) {
				num = formatDouble(aNum * 100 / sNum);
			}
		}
		map.put("num", num);
		return map;
	}

	/**
	 * 加班时长
	 */
	@Override
	public Map<String, Object> queryOvertimeHours(String customerId, String organId, String date) {
		Map<String, Object> map = CollectionKit.newMap();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		try {
			if (date != null && !"".equals(date)) {
				c.setTime(f.parse(date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		changeDateWithMonth(c, -1);
		String beforeDate = f.format(c.getTime());
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("curDate", date);
		parMap.put("beforeDate", beforeDate);
		Map<String, Object> queryMap = laborEfficiencyDao.queryOvertimeHours(parMap);
		double curAvg = 0D, oldAvg = 0D, avgDiffer = 0D, curNum = 0D, conDiffer = 0D;
		if (queryMap != null) {
			curNum = (double) queryMap.get("curNum");
			conDiffer = (double) queryMap.get("comNum");
			double oldNum = (double) queryMap.get("oldNum");
			long curPNum = (long) queryMap.get("curPNum");
			long oldPNum = (long) queryMap.get("oldPNum");
			if (curPNum > 0) {
				curAvg = formatDouble(curNum / curPNum);
			}
			if (oldPNum > 0) {
				oldAvg = formatDouble(oldNum / oldPNum);
			}
			avgDiffer = formatDouble(curAvg - oldAvg);
		}
		map.put("conNum", curNum);
		map.put("conDiffer", conDiffer);
		map.put("avgNum", curAvg);
		map.put("avgDiffer", avgDiffer);
		return map;
	}

	/**
	 * 加班预警统计
	 */
	@Override
	public Map<String, Object> queryOvertimeWarningCount(String customerId, String organId, Integer otTime,
			Integer otWeek) {
		String maxDate = DateUtil.getDBNow().substring(0, 10);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(f.parse(maxDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int newOtTime = 10, newOtWeek = -14;
		if (integerIsNull(otTime) > 0) {
			newOtTime = otTime;
		}
		if (integerIsNull(otWeek) > 0) {
			newOtWeek = -1 * otWeek * 7;
		}
		changeDateWithDay(c, newOtWeek);
		String minDate = f.format(c.getTime());
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("maxDate", maxDate);
		parMap.put("minDate", minDate);
		parMap.put("otTime", newOtTime);
		return laborEfficiencyDao.queryOvertimeWarningCount(parMap);
	}

	/**
	 * 加班预警明细
	 */
	@Override
	public List<LaborEfficiencyDto> queryOvertimeWarningDetail(String customerId, String organId, Integer otTime,
			Integer otWeek) {
		String maxDate = DateUtil.getDBNow().substring(0, 10);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(f.parse(maxDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int newOtTime = 10, newOtWeek = -14;
		if (integerIsNull(otTime) > 0) {
			newOtTime = otTime;
		}
		if (integerIsNull(otWeek) > 0) {
			newOtWeek = -1 * otWeek * 7;
		}
		changeDateWithDay(c, newOtWeek);
		String minDate = f.format(c.getTime());
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("maxDate", maxDate);
		parMap.put("minDate", minDate);
		parMap.put("otTime", newOtTime);
		return laborEfficiencyDao.queryOvertimeWarningDetail(parMap);
	}

	/**
	 * 加班预警明细-人员加班线图
	 */
	@Override
	public Map<String, Object> queryOvertimeWarningPersonHours(String customerId, String empId) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		double conNum = 0D, num = 0D;
		List<String> dateList = CollectionKit.newLinkedList();
		List<Double> numList = CollectionKit.newLinkedList();
		try {
			String maxDate = DateUtil.getDBNow().substring(0, 10);
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(f.parse(maxDate));
			changeDateWithDay(c, -14);
			String minDate = f.format(c.getTime());
			Map<String, Object> parMap = CollectionKit.newMap();
			parMap.put("customerId", customerId);
			parMap.put("empId", empId);
			parMap.put("maxDate", maxDate);
			parMap.put("minDate", minDate);
			List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeWarningPersonHours(parMap);
			if (list != null && list.size() > 0) {
				Map<String, Double> map = CollectionKit.newMap();
				for (LaborEfficiencyDto l : list) {
					map.put(l.getDate(), l.getHourCount());
					conNum += l.getHourCount();
				}
				Calendar c2 = Calendar.getInstance();
				c2.setTime(f.parse(maxDate));
				changeDateWithDay(c2, -1);
				Date dBegin = c.getTime();
				Date dEnd = c2.getTime();
				List<Date> lDate = findDates(dBegin, dEnd);
				for (Date date : lDate) {
					dateList.add(f.format(date));
					numList.add(map.get(f.format(date)) == null ? num : map.get(f.format(date)));
				}
			}
		} catch (Exception e) {
			return null;
		}
		rsMap.put("conNum", conNum);
		rsMap.put("date", dateList.toArray(new String[dateList.size()]));
		rsMap.put("series", numList.toArray(new Double[numList.size()]));
		return rsMap;
	}

	/**
	 * 加班时长趋势-人均时长
	 */
	@Override
	public Map<String, Object> queryOvertimeAvgHours(String customerId, String organId, String times, String crowds) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String beforeYearMonth = null, beforeDate = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		List<String> crowdsList = null;
		String startYearMonth = null, endYearMonth = null;
		try {
			if (crowds != null && !"".equals(crowds)) {
				String[] crowdsArray = crowds.split("@");
				if (crowdsArray != null && crowdsArray.length > 0) {
					crowdsList = Arrays.asList(crowdsArray);
				}
			}
			if (times != null && !"".equals(times)) {
				String[] timesArray = times.split("@");
				if (timesArray != null && timesArray.length > 0) {
					startYearMonth = timesArray[0] + timesArray[1];
					c.setTime(f.parse(startYearMonth));
					changeDateWithMonth(c, -1);
					startYearMonth = f.format(c.getTime());
					beforeYearMonth = startYearMonth;
					endYearMonth = timesArray[2] + addZeroToString(timesArray[3]);
				}
			} else {
				c.setTime(f.parse(yearMonth));
				changeDateWithMonth(c, -6);
				beforeYearMonth = f.format(c.getTime());
				beforeDate = beforeYearMonth;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("startYearMonth", startYearMonth);
		parMap.put("endYearMonth", endYearMonth);
		parMap.put("date", beforeDate);
		parMap.put("listPopulationIds", crowdsList);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeAvgHours(parMap);
		List<String> simplDateList = CollectionKit.newLinkedList();
		List<String> dateList = CollectionKit.newLinkedList();
		List<Double> avgList = CollectionKit.newLinkedList();
		List<Double> percentList = CollectionKit.newLinkedList();
		double dNum = 0D;
		if (list != null && list.size() > 0) {
			String newDate = list.get(0).getDate().substring(0, 4) + list.get(0).getDate().substring(4);
			if (!newDate.equals(beforeYearMonth)) {
				percentList.add(0D);
				simplDateList.add(changeDateToSimplType(list.get(0).getDate()));
				dateList.add(changeDateToOtherType(list.get(0).getDate()));
				avgList.add(doubleIsNull(list.get(0).getAvgNum()));
			}
			for (int i = 1; i < list.size(); i++) {
				simplDateList.add(changeDateToSimplType(list.get(i).getDate()));
				dateList.add(changeDateToOtherType(list.get(i).getDate()));
				avgList.add(formatDouble(doubleIsNull(list.get(i).getAvgNum())));
				if (doubleIsNull(list.get(i - 1).getAvgNum()) > 0) {
					dNum = (doubleIsNull(list.get(i).getAvgNum()) - doubleIsNull(list.get(i - 1).getAvgNum())) * 100
							/ doubleIsNull(list.get(i - 1).getAvgNum());
					dNum = formatDouble(dNum);
				}
				percentList.add(dNum);
			}
		}
		rsMap.put("date", simplDateList);
		rsMap.put("fullDate", dateList);
		rsMap.put("avgList", avgList);
		rsMap.put("percentList", percentList);
		return rsMap;
	}

	/**
	 * 加班时长趋势-总时长
	 */
	@Override
	public Map<String, Object> queryOvertimeTotalHours(String customerId, String organId, String times, String crowds) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		String beforeYearMonth = null, beforeDate = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		List<String> crowdsList = null;
		String startYearMonth = null, endYearMonth = null;
		try {
			if (crowds != null && !"".equals(crowds)) {
				String[] crowdsArray = crowds.split("@");
				if (crowdsArray != null && crowdsArray.length > 0) {
					crowdsList = Arrays.asList(crowdsArray);
				}
			}
			if (times != null && !"".equals(times)) {
				String[] timesArray = times.split("@");
				if (timesArray != null && timesArray.length > 0) {
					startYearMonth = timesArray[0] + timesArray[1];
					c.setTime(f.parse(startYearMonth));
					changeDateWithMonth(c, -1);
					startYearMonth = f.format(c.getTime());
					beforeYearMonth = startYearMonth;
					endYearMonth = timesArray[2] + addZeroToString(timesArray[3]);
				}
			} else {
				c.setTime(f.parse(yearMonth));
				changeDateWithMonth(c, -6);
				beforeYearMonth = f.format(c.getTime());
				beforeDate = beforeYearMonth;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("startYearMonth", startYearMonth);
		parMap.put("endYearMonth", endYearMonth);
		parMap.put("date", beforeDate);
		parMap.put("listPopulationIds", crowdsList);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeTotalHours(parMap);
		List<String> simplDateList = CollectionKit.newLinkedList();
		List<String> dateList = CollectionKit.newLinkedList();
		List<Double> totalList = CollectionKit.newLinkedList();
		List<Double> percentList = CollectionKit.newLinkedList();
		double dNum = 0D;
		if (list != null && list.size() > 0) {
			String newDate = list.get(0).getDate().substring(0, 4) + list.get(0).getDate().substring(4);
			if (!newDate.equals(beforeYearMonth)) {
				percentList.add(0D);
				simplDateList.add(changeDateToSimplType(list.get(0).getDate()));
				dateList.add(changeDateToOtherType(list.get(0).getDate()));
				totalList.add(doubleIsNull(list.get(0).getConNum()));
			}
			for (int i = 1; i < list.size(); i++) {
				simplDateList.add(changeDateToSimplType(list.get(i).getDate()));
				dateList.add(changeDateToOtherType(list.get(i).getDate()));
				totalList.add(formatDouble(doubleIsNull(list.get(i).getConNum())));
				if (doubleIsNull(list.get(i - 1).getConNum()) > 0) {
					dNum = (doubleIsNull(list.get(i).getConNum()) - doubleIsNull(list.get(i - 1).getConNum())) * 100
							/ doubleIsNull(list.get(i - 1).getConNum());
					dNum = formatDouble(dNum);
				}
				percentList.add(dNum);
			}
		}
		rsMap.put("date", simplDateList);
		rsMap.put("fullDate", dateList);
		rsMap.put("totalList", totalList);
		rsMap.put("percentList", percentList);
		return rsMap;
	}

	/**
	 * 加班情况
	 */
	@Override
	public List<LaborEfficiencyDto> queryOvertimeCondition(String customerId, String organId, String date,
			String crowds) {
		String newDate = null;
		List<String> crowdsList = null;
		if (date != null && !"".equals(date)) {
			newDate = date.replaceAll("/", "").replaceAll("-", "");
			if (date.length() > 4) {
				newDate = newDate.substring(0, 4) + addZeroToString(newDate.substring(4));
			}
		} else {
			newDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		}
		if (crowds != null && !"".equals(crowds)) {
			String[] crowdsArray = crowds.split("@");
			if (crowdsArray != null && crowdsArray.length > 0) {
				crowdsList = Arrays.asList(crowdsArray);
			}
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("date", newDate);
		parMap.put("listPopulationIds", crowdsList);
		return laborEfficiencyDao.queryOvertimeCondition(parMap);
	}

	/**
	 * 加班情况-人员加班线图
	 */
	@Override
	public Map<String, Object> queryOvertimeConditionPersonHours(String customerId, String empId, String date) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7);
		String newDate = null;
		if (date != null && !"".equals(date)) {
			newDate = date.replaceAll("/", "").replaceAll("-", "");
			if (date.length() > 4) {
				newDate = newDate.substring(0, 4) + addZeroToString(newDate.substring(4));
			}
			if (date.indexOf("-") != -1) {
				yearMonth = date;
			} else {
				yearMonth = date.substring(0, 4) + "-" + date.substring(4);
			}
		} else {
			newDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("empId", empId);
		parMap.put("date", newDate);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeConditionPersonHours(parMap);
		double conNum = 0D, num = 0D;
		List<String> dateList = CollectionKit.newLinkedList();
		List<Double> numList = CollectionKit.newLinkedList();
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			if (list != null && list.size() > 0) {
				Map<String, Double> map = CollectionKit.newMap();
				for (LaborEfficiencyDto l : list) {
					map.put(l.getDate(), l.getHourCount());
					conNum += l.getHourCount();
				}
				Date dBegin = getFirstDayWithMonth(f.parse(yearMonth + "-01"));
				Date dEnd = getLastDayWithMonth(f.parse(yearMonth + "-01"));
				List<Date> lDate = findDates(dBegin, dEnd);
				for (Date d : lDate) {
					dateList.add(f.format(d));
					numList.add(map.get(f.format(d)) == null ? num : map.get(f.format(d)));
				}
			}
		} catch (Exception e) {
			return null;
		}
		rsMap.put("conNum", conNum);
		rsMap.put("date", dateList.toArray(new String[dateList.size()]));
		rsMap.put("series", numList.toArray(new Double[numList.size()]));
		return rsMap;
	}

	/**
	 * 考勤类型分布-日期
	 */
	@Override
	public Map<String, String> queryCheckWorkTypeForDate(String year, String month, String minYearMonth,
			String maxYearMonth) {
		Map<String, String> rsMap = CollectionKit.newMap();
		String maxYear = year;
		String minYear = null;
		if (minYearMonth != null && !"".equals(minYearMonth)) {
			minYear = minYearMonth.substring(0, 4);
		}
		String yearList = "", minCurDate = "";
		if (minYear != null && !"".equals(minYear)) {
			int minY = Integer.parseInt(minYear);
			int maxY = Integer.parseInt(maxYear);
			for (int i = minY; i <= maxY; i++) {
				if (!"".equals(yearList)) {
					yearList += ",";
				}
				yearList += i + "";
			}
		} else {
			yearList += maxYear;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(maxYearMonth));
			changeDateWithMonth(c, -5);
			minCurDate = sdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsMap.put("yearList", yearList);
		rsMap.put("minCurDate", minCurDate);
		return rsMap;
	}

	/**
	 * 考勤类型分布
	 */
	@Override
	public Map<String, Object> queryCheckWorkTypeLayout(String customerId, String organId, String date) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		String newDate;
		if (date != null && !"".equals(date)) {
			newDate = date.replaceAll("/", "").replaceAll("-", "");
			if (date.length() > 4) {
				newDate = newDate.substring(0, 4) + addZeroToString(newDate.substring(4));
			}
		} else {
			newDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		}
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("date", newDate);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryCheckWorkTypeLayout(parMap);
		List<Object> pieList = CollectionKit.newLinkedList();
		List<Object> gridList = CollectionKit.newLinkedList();
		double con = 0D, num = 0D;
		if (list != null && list.size() > 0) {
			for (LaborEfficiencyDto l : list) {
				con += l.getConNum();
			}
			for (LaborEfficiencyDto l : list) {
				num = formatDouble(l.getConNum() * 100 / con);
				Map<String, Object> pieMap = CollectionKit.newMap();
				pieMap.put("name", l.getCheckWorkTypeName() + " " + num + "%");
				pieMap.put("value", l.getConNum());
				pieList.add(pieMap);
				Map<String, Object> gridMap = CollectionKit.newMap();
				gridMap.put("name", l.getCheckWorkTypeName());
				gridMap.put("hours", l.getConNum());
				gridMap.put("percent", num + "%");
				gridList.add(gridMap);
			}

		}
		rsMap.put("pieList", pieList);
		rsMap.put("gridList", gridList);
		return rsMap;
	}

	/**
	 * 出勤明细
	 */
	public PaginationDto<LaborEfficiencyGridDto> queryAttendanceDetail(String customerId, String organId,
			String userName, String date, List<Integer> checkList, int page, int rows) {
		List<String> perList = CollectionKit.newLinkedList();
		for (Integer i : checkList) {
			perList.add("ctName" + i);
		}
		String yearMonth = null;
		if (date != null && !"".equals(date)) {
			yearMonth = date.replaceAll("/", "").replaceAll("-", "");
			if (date.length() > 4) {
				yearMonth = yearMonth.substring(0, 4) + addZeroToString(yearMonth.substring(4));
			}
		}
		PaginationDto<LaborEfficiencyGridDto> dto = new PaginationDto<LaborEfficiencyGridDto>(page, rows);
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("userName", userName);
		parMap.put("date", yearMonth);
		parMap.put("list", perList);
		parMap.put("start", dto.getOffset());
		parMap.put("rows", dto.getLimit());
		int count = laborEfficiencyDao.queryAttendanceDetailCount(parMap);
		List<LaborEfficiencyGridDto> list = laborEfficiencyDao.queryAttendanceDetail(parMap);
		String newPercentNum;
		for (LaborEfficiencyGridDto l : list) {
			if (l.getPercentNum().indexOf("%") != -1) {
				newPercentNum = l.getPercentNum().split("%")[0];
			} else {
				newPercentNum = l.getPercentNum();
			}
			l.setPercentNum(formatDouble(Double.parseDouble(newPercentNum)) + "%");
		}
		int records = integerIsNull(count) > 0 ? count : 1;
		dto.setRecords(records);
		dto.setRows(list);
		return dto;
	}

	/**
	 * 出勤明细-考勤类型
	 */
	public Map<String, Object> queryCheckWorkType(String customerId, List<Integer> checkList) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("list", checkList);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryCheckWorkType(parMap);
		List<String> idList = CollectionKit.newLinkedList();
		List<String> nameList = CollectionKit.newLinkedList();
		List<LaborEfficiencyGridDto> colModelList = CollectionKit.newLinkedList();
		String[] idArray = { "empId", "userName", "organId", "organName", "actualNum", "shouldNum", "percentNum",
				"overtimeNum", "operate" };
		String[] nameArray = { "empId", "姓名", "organId", "所属机构", "实出勤", "应出勤", "劳动力效能", "加班", "操作" };
		for (int i = 0; i < idArray.length - 1; i++) {
			idList.add(idArray[i]);
		}
		for (int i = 0; i < nameArray.length - 1; i++) {
			nameList.add(nameArray[i]);
		}
		if (list != null && list.size() > 0) {
			for (LaborEfficiencyDto l : list) {
				idList.add(l.getCurtName());
				nameList.add(l.getCheckWorkTypeName());
			}
		}
		idList.add(idArray[idArray.length - 1]);
		nameList.add(nameArray[nameArray.length - 1]);
		for (int i = 0; i < idList.size(); i++) {
			LaborEfficiencyGridDto gt = new LaborEfficiencyGridDto();
			if ("empId".equals(idList.get(i)) || "organId".equals(idList.get(i))) {
				gt.setHidden(true);
			} else {
				gt.setHidden(false);
			}
			gt.setName(idList.get(i));
			gt.setIndex(idList.get(i));
			gt.setFixed(true);
			gt.setSortable(false);
			if ("organName".equals(idList.get(i))) {
				gt.setWidth(120);
			} else {
				gt.setWidth(80);
			}
			gt.setAlign("center");
			colModelList.add(gt);
		}
		rsMap.put("idArray", idList.toArray(new String[idList.size()]));
		rsMap.put("nameArray", nameList.toArray(new String[nameList.size()]));
		rsMap.put("colModelArray", colModelList.toArray(new Object[colModelList.size()]));
		return rsMap;
	}

	/**
	 * 个人出勤明细-考勤类型
	 */
	public Map<String, Object> queryOnePersonDetailCheckWorkType(String customerId, List<Integer> checkList) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("list", checkList);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryCheckWorkType(parMap);
		List<String> idList = CollectionKit.newLinkedList();
		List<String> nameList = CollectionKit.newLinkedList();
		List<LaborEfficiencyGridDto> colModelList = CollectionKit.newLinkedList();
		String[] idArray = { "dateMonth", "actualNum", "shouldNum", "overtimeNum" };
		String[] nameArray = { "日期", "实出勤", "应出勤", "加班" };
		for (int i = 0; i < idArray.length; i++) {
			idList.add(idArray[i]);
		}
		for (int i = 0; i < nameArray.length; i++) {
			nameList.add(nameArray[i]);
		}
		if (list != null && list.size() > 0) {
			for (LaborEfficiencyDto l : list) {
				idList.add(l.getCurtName());
				nameList.add(l.getCheckWorkTypeName());
			}
		}
		for (int i = 0; i < idList.size(); i++) {
			LaborEfficiencyGridDto gt = new LaborEfficiencyGridDto();
			gt.setHidden(false);
			gt.setName(idList.get(i));
			gt.setIndex(idList.get(i));
			gt.setFixed(true);
			gt.setSortable(false);
			gt.setWidth(90);
			gt.setAlign("center");
			colModelList.add(gt);
		}
		rsMap.put("idArray", idList.toArray(new String[idList.size()]));
		rsMap.put("nameArray", nameList.toArray(new String[nameList.size()]));
		rsMap.put("colModelArray", colModelList.toArray(new Object[colModelList.size()]));
		return rsMap;
	}

	/**
	 * 个人出勤明细
	 */
	public PaginationDto<LaborEfficiencyGridDto> queryOnePersonDetail(String customerId, String organId, String empId,
			String date, List<Integer> checkList, int page, int rows) {
		List<String> perList = CollectionKit.newLinkedList();
		for (Integer i : checkList) {
			perList.add("ctName" + i);
		}
		String yearMonth = null;
		if (date != null && !"".equals(date)) {
			yearMonth = date.replaceAll("/", "").replaceAll("-", "");
			if (date.length() > 4) {
				yearMonth = yearMonth.substring(0, 4) + addZeroToString(yearMonth.substring(4));
			}
		}
		PaginationDto<LaborEfficiencyGridDto> dto = new PaginationDto<LaborEfficiencyGridDto>(page, rows);
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("empId", empId);
		parMap.put("date", yearMonth);
		parMap.put("list", perList);
		parMap.put("start", dto.getOffset());
		parMap.put("rows", dto.getLimit());
		int count = laborEfficiencyDao.queryOnePersonDetailCount(parMap);
		List<LaborEfficiencyGridDto> list = laborEfficiencyDao.queryOnePersonDetail(parMap);
		int records = integerIsNull(count) > 0 ? count : 1;
		dto.setRecords(records);
		dto.setRows(list);
		return dto;
	}

	/**
	 * 人群转换
	 */
	public List<String> listPopulationIds(String populationIds) {
		List<String> listPopulationIds = CollectionKit.newList();
		String[] ids = populationIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			listPopulationIds.add(ids[i].trim());
		}
		return listPopulationIds;
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
	 * 获取该月第一天
	 */
	public static Date getFirstDayWithMonth(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 获取该月第一天
	 */
	public static Date getLastDayWithMonth(Date day) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(day);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	/**
	 * 获取区间日期
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = CollectionKit.newLinkedList();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * Double 是否为空赋值
	 */
	public double doubleIsNull(Double data) {
		return data == null ? 0D : data;
	}

	/**
	 * double四舍五入保留两位
	 */
	public double formatDouble(double data) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(data));
	}

	/**
	 * 修改时间字符串格式 例：201617 -> 2016/17
	 */
	public String changeDateToOtherType(String date) {
		return date.substring(0, 4) + "/" + date.substring(4);
	}

	/**
	 * 修改时间字符串格式 例：201617 -> 16/17
	 */
	public String changeDateToSimplType(String date) {
		return date.substring(2, 4) + "/" + date.substring(4);
	}

	/**
	 * 判断字符串小于10，前面补充‘0’
	 */
	public String addZeroToString(String str) {
		return Integer.parseInt(str) >= 10 ? "" + Integer.parseInt(str) : "0" + Integer.parseInt(str);
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
	 * 查询审核数据
	 */
	public List<LaborEfficiencyImportDto> queryAuditingInfo(String customerId, String userId, int status) {
		String examineId = laborEfficiencyDao.queryEmpInfo(customerId, userId, null).get(0).getEmpId();
		return laborEfficiencyDao.queryAuditingInfo(customerId, examineId, status);
	}
	
	/**
	 * 生成《员工考勤数据》导入模板
	 */
	public void downloadTempletExcel(String customerId, HttpServletResponse response, String title, String dateTitle, String date, String[] headers, String[] content) {
		exportExcel(response, title, dateTitle, date, headers, content);
	}

	/**
     * 导出excel方法
     */
    public void exportExcel(HttpServletResponse response, String title, String dateTitle, String date, String[] headers, String[] content) {
        try {
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String((title + ".xls").getBytes("GBK"), "iso-8859-1"));
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);
            HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyleTitle.setWrapText(true);
            HSSFRow row0 = sheet.createRow(0);
            HSSFCell cell0 = row0.createCell(0);
            cell0.setCellValue(dateTitle);
            HSSFCell cell1 = row0.createCell(1);
            cell1.setCellValue(date);
            HSSFRow row = sheet.createRow(1);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyleTitle);
                sheet.setColumnWidth(i, 20 * 256);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            HSSFRow row2 = sheet.createRow(2);
            for (int i = 0; i < content.length; i++) {
            	HSSFCell cell = row2.createCell(i);
            	cell.setCellStyle(cellStyleTitle);
            	sheet.setColumnWidth(i, 20 * 256);
            	HSSFRichTextString text = new HSSFRichTextString(content[i]);
            	cell.setCellValue(text);
            }
            workbook.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
        }
    }
    
    /**
	 * 检查是否有导入数据的权限
	 */
	public int checkPermissImport(String customerId, String userId) {
		return laborEfficiencyDao.checkPermissImport(customerId, userId);
	}
	
	public List<LaborEfficiencyDto> checkEmpInfo(String customerId, String keyName) {
		return laborEfficiencyDao.checkEmpInfo(customerId, keyName);
	}
	
	private static final String XLS = ".xls";
	private static final String XLSX = ".xlsx";
	private static final String[] TEMPLETTITLE = {"账号", "日期", "应出勤（小时）", "实际出勤（小时）", "加班（小时）", "假期统计（小时）"};
	/**
	 * 导入文件
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> importFile(String customerId, String userId, MultipartFile file, String examineId, String attendanceTime, String type) {
		Map<String, Object> map = CollectionKit.newMap();
		List<LaborEfficiencyDto> content = CollectionKit.newList();
        String fileName = file.getOriginalFilename();
        String lastFileName = fileName.substring(fileName.lastIndexOf("."));
        List<LaborEfficiencyDto> errorList = compareTempletFile(file, attendanceTime);
        String empId = laborEfficiencyDao.queryEmpInfo(customerId, userId, null).get(0).getEmpId();
        try {
            if (".xls".equals(lastFileName)) {
            	map = readXlsContent(file.getInputStream(), customerId, attendanceTime);
            } else {
            	map = readXlsxContent(file.getInputStream(), customerId, attendanceTime);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        content = (List<LaborEfficiencyDto>) map.get("list");
        List<LaborEfficiencyDto> listerror = (List<LaborEfficiencyDto>) map.get("errorList");
        if(listerror != null && listerror.size() != 0) {
        	errorList.addAll(listerror);
        }
        if(errorList != null && errorList.size() != 0) {
        	map.put("errorList", errorList);
        }
    	List<LaborEfficiencyDto> info = CollectionKit.newList();
    	boolean flag = false;  //是否重复导入数据
    	if("1".equals(type)) {  //首次导入
    		for(LaborEfficiencyDto dto : content) {
        		int count = laborEfficiencyDao.checkIsExistEmpData(customerId, dto.getUserKey(), dto.getDate());
        		if(count > 0) {
        			info.add(dto);
        			flag = true;
        		}
        	}
    	}
    	if(flag) {
    		map.put("info", info);
    		map.put("importType", "2");
    	} else {
    		if(errorList.size() <= 0) {
    			if("1".equals(type)) {
    				LaborEfficiencyImportDto importDto = new LaborEfficiencyImportDto();
        			String attId = Identities.uuid2();
        			String createTime = DateUtil.getDBNow();
        			importDto.setAttId(attId);
        			importDto.setCustomerId(customerId);
        			importDto.setEmpId(empId);
        			importDto.setExamineId(examineId);
        			importDto.setStatus(0);
        			importDto.setCreateTime(createTime);
        			importDto.setDateName(fileName);
        			laborEfficiencyDao.addImportInfo(importDto);
        			laborEfficiencyDao.addAttendanceInfo(content, customerId, attId);
    			} else {
    				for(LaborEfficiencyDto dto : content) {
    					laborEfficiencyDao.updateImportData(dto);
    				}
    			}
    			map.put("success", "success");
    		}
    	}
    	return map;
	}
	
	/**
	 * 待审核的人员考勤数据
	 */
	public List<LaborEfficiencyDto> queryItemInfo(String customerId, String attId) {
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryItemInfo(customerId, attId);
		List<LaborEfficiencyDto> otherList = laborEfficiencyDao.queryOtherItemInfo(customerId, attId);
		
		List<LaborEfficiencyDto> tempList = CollectionKit.newList();
		for(LaborEfficiencyDto dto : otherList) {
			String other = dto.getOther();
			String[] otherArr = other.split("\n");
			for(int i = 0; i < otherArr.length; i ++) {
				LaborEfficiencyDto tempDto = new LaborEfficiencyDto();
				tempDto.setUserKey(dto.getUserKey());
				tempDto.setUserName(dto.getUserName());
				tempDto.setYearMonth(dto.getYearMonth());
				tempDto.setOther(otherArr[i]);
				tempList.add(tempDto);
			}
		}
		
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		List<LaborEfficiencyDto> listCheckworkType = laborEfficiencyDao.queryCheckWorkType(parMap);
		List<LaborEfficiencyDto> resultList = CollectionKit.newList();
		for(int i = 0; i < list.size(); i ++) {
			LaborEfficiencyDto dto = list.get(i);
			String other = "";
			for(LaborEfficiencyDto tempDto : tempList) {
				if(dto.getUserKey().equals(tempDto.getUserKey()) && dto.getYearMonth() == tempDto.getYearMonth()) {
					other += tempDto.getOther() + "\n";
				}
			}
			String otherCount = getOtherCount(listCheckworkType, other);
			dto.setOther(otherCount);
			resultList.add(dto);
		}
		return resultList;
	}
	
	public String getOtherCount(List<LaborEfficiencyDto> listCheckworkType, String other) {
		String str = "";
		for(LaborEfficiencyDto dto : listCheckworkType) {
			double sum = 0.0;
			String[] arrType = other.split("\n");
			for(int i = 0; i < arrType.length; i ++) {
				String typeName = arrType[i].substring(0, arrType[i].indexOf("-"));
				String w = arrType[i].substring(arrType[i].indexOf("-") + 1, arrType[i].length());
				double otherCount = Double.parseDouble(arrType[i].substring(arrType[i].indexOf("-") + 1, arrType[i].length()));
				if(typeName.equals(dto.getCheckWorkTypeName())) {
					sum += otherCount;
				}
			}
			if(sum > 0) {
				String otherCount = dto.getCheckWorkTypeName() + "-" + sum;
				str += otherCount + "\n";
			}
		}
		
		return str;
	}
	
	/**
	 * 待审核的个人考勤数据
	 */
	public List<LaborEfficiencyDto> queryPersonDetail(String customerId, String empKey, String yearMonth) {
		return laborEfficiencyDao.queryPersonDetail(customerId, empKey, yearMonth);
	}
	
	/**
	 * 更新审核状态并入库
	 */
	@Transactional
	public void updateImportInfo(String customerId, String attId, String date, String userId) {
		String examineId = laborEfficiencyDao.queryEmpInfo(customerId, userId, null).get(0).getUserKey();
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryBeinStorageData(customerId, examineId, attId);
		
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		List<LaborEfficiencyDto> listCheckworkType = laborEfficiencyDao.queryCheckWorkType(parMap);
		String attCheckworkTypeId = "";
		String overCheckworkTypeId = "";
		
		List<LaborEfficiencyDto> listAttendance = CollectionKit.newList();
		List<LaborEfficiencyDto> listOver = CollectionKit.newList();
		List<LaborEfficiencyDto> listOther = CollectionKit.newList();
		for(LaborEfficiencyDto dto : listCheckworkType) {
			if("正常出勤".equals(dto.getCheckWorkTypeName())) {  // 添加正常考勤数据
				attCheckworkTypeId = dto.getCheckWorkTypeId();
			} else if("加班".equals(dto.getCheckWorkTypeName())) {  // 添加加班数据
				overCheckworkTypeId = dto.getCheckWorkTypeId();
			} else {  // 添加其它考勤数据
				for(LaborEfficiencyDto other : list) {
					String ot = other.getOther();
					if(ot != null && !"".equals(ot)) {
						listOther = getOtherList(other, ot, listCheckworkType);
					}
				}
			}
		}
		
		for (int i = 0; i < list.size(); i ++) {
			LaborEfficiencyDto dto = list.get(i);
			LaborEfficiencyDto attDto = new LaborEfficiencyDto();
			LaborEfficiencyDto overDto = new LaborEfficiencyDto();
			String days = dto.getDate().substring(0, 10);
			int yearMonth = Integer.parseInt(dto.getDate().substring(0, 4) + dto.getDate().substring(5, 7));
			// 添加正常考勤数据
			attDto.setId(Identities.uuid2());
			attDto.setCustomerId(dto.getCustomerId());
			attDto.setUserKey(dto.getUserKey());
			attDto.setEmpId(dto.getEmpId());
			attDto.setEmpName(dto.getEmpName());
			attDto.setHourCount(dto.getHourCount());
			attDto.setBeInAttendance(dto.getBeInAttendance());
			attDto.setOrganId(dto.getOrganId());
			attDto.setCheckWorkTypeId(attCheckworkTypeId);
			attDto.setDate(days);
			attDto.setYearMonth(yearMonth);
			listAttendance.add(attDto);
			// 添加加班数据
			if(dto.getOverTime() != null && dto.getOverTime() > 0) {
				overDto.setId(Identities.uuid2());
				overDto.setCustomerId(dto.getCustomerId());
				overDto.setUserKey(dto.getUserKey());
				overDto.setEmpId(dto.getEmpId());
				overDto.setEmpName(dto.getEmpName());
				overDto.setHourCount(dto.getOverTime());
				overDto.setOrganId(dto.getOrganId());
				overDto.setCheckWorkTypeId(overCheckworkTypeId);
				overDto.setDate(days);
				overDto.setYearMonth(yearMonth);
				listOver.add(overDto);
			}
		}
		laborEfficiencyDao.insertEmpAttendance(listAttendance);
		laborEfficiencyDao.insertEmpOverTimeAttendance(listOver);
		laborEfficiencyDao.insertEmpOtherAttendance(listOther);
		Map<String, Object> map =  CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("attId", attId);
		map.put("date", date);
		map.put("status", 1);
		laborEfficiencyDao.updateImportInfo(map);
		
	}
	
	public List<LaborEfficiencyDto> getOtherList(LaborEfficiencyDto dto, String str, List<LaborEfficiencyDto> listCheckworkType) {
		List<LaborEfficiencyDto> list = CollectionKit.newList();
		String[] arr = str.split("\n");
		String days = dto.getDate().substring(0, 10);
		int yearMonth = Integer.parseInt(dto.getDate().substring(0, 4) + dto.getDate().substring(5, 7));
		for(int i = 0; i < arr.length; i++) {
			LaborEfficiencyDto otherDto = new LaborEfficiencyDto();
			String typeName = arr[i].substring(0, arr[i].indexOf("-"));
			double otherCount = Double.parseDouble(arr[i].substring(arr[i].indexOf("-") + 1, arr[i].length()));
			String typeId = "";
			for(LaborEfficiencyDto type : listCheckworkType) {
				if(typeName.equals(type.getCheckWorkTypeName())) {
					typeId = type.getCheckWorkTypeId();
					break;
				}
			}
			otherDto.setId(Identities.uuid2());
			otherDto.setCustomerId(dto.getCustomerId());
			otherDto.setUserKey(dto.getUserKey());
			otherDto.setEmpId(dto.getEmpId());
			otherDto.setEmpName(dto.getEmpName());
			otherDto.setHourCount(otherCount);
			otherDto.setOrganId(dto.getOrganId());
			otherDto.setCheckWorkTypeId(typeId);
			otherDto.setDate(days);
			otherDto.setYearMonth(yearMonth);
			otherDto.setOther(typeName);
			list.add(otherDto);
		}
		return list;
	}
	
	/**
	 * 检查文件的格式
	 */
	public List<LaborEfficiencyDto> compareTempletFile(MultipartFile file, String attendanceTime) {
		List<LaborEfficiencyDto> errorList = CollectionKit.newList();
		String fileName = file.getOriginalFilename();
		String lastName = fileName.substring(fileName.lastIndexOf("."));
		String date = "";
		String[] title = {};
		try {
			if(lastName.equals(XLS)) {
				date = readXlsTitleWithDate(file.getInputStream());
				title = readXlsTitle(file.getInputStream());
			}
			if(lastName.equals(XLSX)) {
				date = readXlsxTitleWithDate(file.getInputStream());
				title = readXlsxTitle(file.getInputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!isNum(date)) {
			LaborEfficiencyDto dto = new LaborEfficiencyDto();
			dto.setLocationError("第1行");
			dto.setErrorMsg("日期填写格式不正确");
			errorList.add(dto);
		}
		if(!date.equals(attendanceTime)) {
			LaborEfficiencyDto dto = new LaborEfficiencyDto();
			dto.setLocationError("第1行");
			dto.setErrorMsg("考勤时间与界面所选时间不一致");
			errorList.add(dto);
		}
		if(!title[0].equals(TEMPLETTITLE[0]) || !title[1].equals(TEMPLETTITLE[1]) || !title[2].equals(TEMPLETTITLE[2]) 
				|| !title[3].equals(TEMPLETTITLE[3]) || !title[4].equals(TEMPLETTITLE[4]) || !title[5].equals(TEMPLETTITLE[5])) {
			LaborEfficiencyDto dto = new LaborEfficiencyDto();
			dto.setLocationError("第2行");
			dto.setErrorMsg("标题和模版标题不一致");
			errorList.add(dto);
		}
		return errorList;
	}
	
	private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private XSSFRow xssfRow;
    
    /**
     * 根据HSSFCell类型设置数据
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
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
                case HSSFCell.CELL_TYPE_STRING: {
                    cellvalue = cell.getRichStringCellValue().getString().trim();
                    break;
                }
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
	
	/**
     * 读取Excel2003表格表头的盘点时间
     *
     * @return String 表头内容的数组
     */
    public String readXlsTitleWithDate(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        return getCellFormatValue(row.getCell(1));
    }

    /**
     * 读取Excel2007表格表头的盘点时间
     *
     * @return String 表头内容的数组
     */
    public String readXlsxTitleWithDate(InputStream is) {
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        xssfRow = xssfSheet.getRow(0);
        return getCellFormatValue(xssfRow.getCell(1));
    }

    /**
     * 读取Excel2003表格表头的标题内容
     *
     * @return String 表头内容的数组
     */
    public String[] readXlsTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(1);
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(row.getCell(i));
        }
        return title;
    }

    /**
     * 读取Excel2007表格表头的标题内容
     *
     * @return String 表头内容的数组
     */
    public String[] readXlsxTitle(InputStream is) {
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        xssfRow = xssfSheet.getRow(1);
        int colNum = xssfRow.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(xssfRow.getCell(i));
        }
        return title;
    }
    
    /**
     * 读取Excel2003的数据内容
     */
    public Map<String, Object> readXlsContent(InputStream is, String customerId, String attendanceTime) {
    	Map<String, Object> map = CollectionKit.newMap();
    	List<LaborEfficiencyDto> list = CollectionKit.newList();
        List<LaborEfficiencyDto> errorList = CollectionKit.newList();
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
//        row = sheet.getRow(1);
        Map<String, Object> param = CollectionKit.newMap();
        param.put("customerId", customerId);
		List<LaborEfficiencyDto> listType = laborEfficiencyDao.queryCheckWorkType(param);
		String[] strArr = new String[listType.size()];
		for(int i = 0; i < listType.size(); i ++) {
			strArr[i] = listType.get(i).getCheckWorkTypeName();
		}
		String typeStr = StringUtils.join(strArr, '、');
        
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            LaborEfficiencyDto laborEfficiencyDto = new LaborEfficiencyDto();
            String userKey = getCellFormatValue(row.getCell(0));
            int count = laborEfficiencyDao.checkUser(customerId, userKey);
			if(count == 0) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("账户在系统中不存在");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setUserKey(userKey);
			}
            String day = getCellFormatValue(row.getCell(1));
            if(!isNum(day)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("日期必须为数字");
				errorList.add(dto);
			} else {
				String endMonth = getDays(Integer.parseInt(attendanceTime));
				int endDate = Integer.parseInt(endMonth.substring(endMonth.lastIndexOf("-") + 1));
				if(Integer.parseInt(day) > endDate) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					dto.setLocationError("第" + (i + 1) + "行");
					dto.setErrorMsg("日期不能大于当月最大日期");
					errorList.add(dto);
				} else {
					laborEfficiencyDto.setDay(Integer.parseInt(day));
		            laborEfficiencyDto.setDate(attendanceTime.substring(0, 4) + "-" + attendanceTime.substring(4, 6) + "-" + (Integer.parseInt(day) > 9 ? day : "0" + day));
				}
			}
            String thory = getCellFormatValue(row.getCell(2));
			if(!isNum(thory)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("应出勤的值必须为数字");
				errorList.add(dto);
			} else if(Double.parseDouble(thory) > 24) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("应出勤的时间大于24小时");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setBeInAttendance(Double.parseDouble(thory));
			}
            String actual = getCellFormatValue(row.getCell(3));
            if(!isNum(actual)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("实际出勤的值必须为数字");
				errorList.add(dto);
			} else if(Double.parseDouble(actual) > 24) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("实际出勤的时间大于24小时");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setActualAttendance(Double.parseDouble(actual));
			}
            String over = getCellFormatValue(row.getCell(4));
			if(!isNum(over)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("加班的值必须为数字");
				errorList.add(dto);
			} else if(Double.parseDouble(over) > 24) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("加班的时间大于24小时");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setOverTime(Double.parseDouble(over));
			}
			if(isNum(actual) && isNum(over)) {
				if(Double.parseDouble(actual) + Double.parseDouble(over) > 24) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					dto.setLocationError("第" + (i + 1) + "行");
					dto.setErrorMsg("实际出勤和加班时间之和大于24小时，注：实际出勤应不需统计加班");
					errorList.add(dto);
				}
			}
			String otherStr = getCellFormatValue(row.getCell(5));
			String[] other = otherStr.split("\n");
			other.toString();
			String otherType = "";
			String otherTime = "0";
			double otherCount = 0;
			boolean flag = true;
			for(int j = 0; j < other.length; j ++) {
				if(other[j].indexOf("-") == -1) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					dto.setLocationError("第" + (i + 1) + "行");
					dto.setErrorMsg("假期统计的值的类型不正确，正确的格式：事假-4");
					errorList.add(dto);
					flag = false;
				} else {
					otherType = other[j].substring(0, other[j].indexOf("-"));
					otherTime = other[j].substring(other[j].lastIndexOf("-") + 1);
					otherCount += Double.parseDouble(otherTime);
					if(typeStr.indexOf(otherType) < 0) {
						LaborEfficiencyDto dto = new LaborEfficiencyDto();
						dto.setLocationError("第" + (i + 1) + "行");
						dto.setErrorMsg("没有该类型假期，目前支持导入的假期类型为：" + typeStr);
						errorList.add(dto);
						flag = false;
					} else if (!isNum(otherTime)) {
						LaborEfficiencyDto dto = new LaborEfficiencyDto();
						dto.setLocationError("第" + (i + 1) + "行");
						dto.setErrorMsg("假期类型-后面的值为数字");
						errorList.add(dto);
						flag = false;
					}
				}
			}
			if(otherCount > 24) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("假期统计之和大于24小时");
				errorList.add(dto);
				flag = false;
			}
			if(flag) {
				laborEfficiencyDto.setOther(otherStr);
			}
			
            list.add(laborEfficiencyDto);
        }
        map.put("list", list);
        map.put("errorList", errorList);
        return map;
    }

    /**
     * 读取Excel2007的数据内容
     */
    public Map<String, Object> readXlsxContent(InputStream is, String customerId, String attendanceTime) {
    	Map<String, Object> map = CollectionKit.newMap();
    	List<LaborEfficiencyDto> list = CollectionKit.newList();
        List<LaborEfficiencyDto> errorList = CollectionKit.newList();
        try {
        	xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        int rowNum = xssfSheet.getLastRowNum();
//        row = xssfSheet.getRow(1);
        Map<String, Object> param = CollectionKit.newMap();
        param.put("customerId", customerId);
		List<LaborEfficiencyDto> listType = laborEfficiencyDao.queryCheckWorkType(param);
		String[] strArr = new String[listType.size()];
		for(int i = 0; i < listType.size(); i ++) {
			strArr[i] = listType.get(i).getCheckWorkTypeName();
		}
		String typeStr = StringUtils.join(strArr, '、');
        
        for (int i = 2; i <= rowNum; i++) {
        	xssfRow = xssfSheet.getRow(i);
            LaborEfficiencyDto laborEfficiencyDto = new LaborEfficiencyDto();
            String userKey = getCellFormatValue(xssfRow.getCell(0));
            int count = laborEfficiencyDao.checkUser(customerId, userKey);
			if(count == 0) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("账户在系统中不存在");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setUserKey(userKey);
			}
            String day = getCellFormatValue(xssfRow.getCell(1));
            if(!isNum(day)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("日期必须为数字");
				errorList.add(dto);
			} else {
				String endMonth = getDays(Integer.parseInt(attendanceTime));
				int endDate = Integer.parseInt(endMonth.substring(endMonth.lastIndexOf("-") + 1));
				if(Integer.parseInt(day) > endDate) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					dto.setLocationError("第" + (i + 1) + "行");
					dto.setErrorMsg("日期不能大于当月最大日期");
					errorList.add(dto);
				} else {
					laborEfficiencyDto.setDay(Integer.parseInt(day));
		            laborEfficiencyDto.setDate(attendanceTime.substring(0, 4) + "-" + attendanceTime.substring(4, 6) + "-" + (Integer.parseInt(day) > 9 ? day : "0" + day));
				}
			}
            String thory = getCellFormatValue(xssfRow.getCell(2));
			if(!isNum(thory)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("应出勤的值必须为数字");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setBeInAttendance(Double.parseDouble(thory));
			}
            String actual = getCellFormatValue(xssfRow.getCell(3));
            if(!isNum(actual)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("实际出勤的值必须为数字");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setActualAttendance(Double.parseDouble(actual));
			}
            String over = getCellFormatValue(xssfRow.getCell(4));
			if(!isNum(over)) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("加班的值必须为数字");
				errorList.add(dto);
			} else {
				laborEfficiencyDto.setOverTime(Double.parseDouble(over));
			}
			if(isNum(actual) && isNum(over)) {
				if(Integer.parseInt(actual) + Integer.parseInt(over) > 24) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					dto.setLocationError("第" + (i + 1) + "行");
					dto.setErrorMsg("实际出勤和加班时间之和大于24小时，注：实际出勤应不需统计加班");
					errorList.add(dto);
				}
			}
			String otherStr = getCellFormatValue(xssfRow.getCell(5));
			String[] other = otherStr.split("\n");
			String otherType = "";
			String otherTime = "0";
			int otherCount = 0;
			boolean flag = true;
			for(int j = 0; j < other.length; j ++) {
				if(other[j].indexOf("-") == -1) {
					LaborEfficiencyDto dto = new LaborEfficiencyDto();
					dto.setLocationError("第" + (i + 1) + "行");
					dto.setErrorMsg("假期统计的值的类型不正确，正确的格式：事假-4");
					errorList.add(dto);
					flag = false;
				} else {
					otherType = other[j].substring(0, other[j].indexOf("-"));
					otherTime = other[j].substring(other[j].lastIndexOf("-") + 1);
					otherCount += Integer.parseInt(otherTime);
					if(typeStr.indexOf(otherType) < 0) {
						LaborEfficiencyDto dto = new LaborEfficiencyDto();
						dto.setLocationError("第" + (i + 1) + "行");
						dto.setErrorMsg("没有该类型假期，目前支持导入的假期类型为：" + typeStr);
						errorList.add(dto);
						flag = false;
					} else if (!isNum(otherTime)) {
						LaborEfficiencyDto dto = new LaborEfficiencyDto();
						dto.setLocationError("第" + (i + 1) + "行");
						dto.setErrorMsg("假期类型-后面的值为数字");
						errorList.add(dto);
						flag = false;
					}
				}
			}
			if(otherCount > 24) {
				LaborEfficiencyDto dto = new LaborEfficiencyDto();
				dto.setLocationError("第" + (i + 1) + "行");
				dto.setErrorMsg("假期统计之和大于24小时");
				errorList.add(dto);
				flag = false;
			}
			if(flag) {
				laborEfficiencyDto.setOther(otherStr);
			}
			
            list.add(laborEfficiencyDto);
        }
        map.put("list", list);
        map.put("errorList", errorList);
        return map;
    }
	
	/**
	 * 判断是否为数字
	 */
	public boolean isNum(String str) {
		//"\\d+\\.\\d+$|-\\d+\\.\\d+$"小数    "^\\d+$|-\\d+$"整数
		return str.matches("^\\d+$|-\\d+$") || str.matches("\\d+\\.\\d+$|-\\d+\\.\\d+$");
	}
	
	/**
	 * 查询机构
	 */
	public Map<String, Object> queryOrgan(String customerId, String organId) {
		Map<String, Object> result = CollectionKit.newMap();
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOrgan(customerId, organId);
		for(LaborEfficiencyDto dto : list) {
			if(organId.equals(dto.getOrganId())) {
				result.put("organId", dto.getOrganId());
				result.put("organName", dto.getOrganName());
				break;
			}
		}
		return result;
	}
}
