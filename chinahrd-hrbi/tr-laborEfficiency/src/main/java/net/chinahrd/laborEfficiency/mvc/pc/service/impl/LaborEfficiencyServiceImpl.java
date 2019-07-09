package net.chinahrd.laborEfficiency.mvc.pc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.WebConfigListener;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.laborEfficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dto.pc.laborEfficiency.LaborEfficiencyGridDto;
import net.chinahrd.entity.dto.pc.laborEfficiency.LaborEfficiencyImportDto;
import net.chinahrd.entity.dto.pc.laborEfficiency.LaborEfficiencyTypeDto;
import net.chinahrd.laborEfficiency.mvc.pc.dao.LaborEfficiencyDao;
import net.chinahrd.laborEfficiency.mvc.pc.service.LaborEfficiencyService;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.Sort;
import net.chinahrd.utils.Str;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Service("laborEfficiencyService")
public class LaborEfficiencyServiceImpl implements LaborEfficiencyService {

	@Autowired
	private LaborEfficiencyDao laborEfficiencyDao;
	@Autowired
	private CommonService commonService;

	/**
	 * 劳动力效能对比
	 */
	public Map<String, Object> queryLaborEfficiencyRatio(String customerId, String organId, String beginTime,
			String endTime, String populationIds, List<String> empInfoList) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		List<String> listPopulationIds = CollectionKit.newList();
		List<LaborEfficiencyDto> listOrgan = laborEfficiencyDao.queryOrgan(customerId, organId);
		List<LaborEfficiencyDto> queryList = CollectionKit.newList();
		
		Map<String, Object> paramMap = CollectionKit.newMap();
		if (populationIds != null && !"".equals(populationIds)) {
			listPopulationIds = listPopulationIds(populationIds);
			paramMap.put("listPopulationIds", listPopulationIds);
		}
		paramMap.put("customerId", customerId);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);
		paramMap.put("empInfoList", empInfoList);
		for(LaborEfficiencyDto dto : listOrgan) {
			paramMap.put("organId", dto.getOrganId());
			LaborEfficiencyDto result = laborEfficiencyDao.queryLaborEfficiencyRatio(paramMap);
			dto.setActualAttendance(result.getActualAttendance());
			dto.setBeInAttendance(result.getBeInAttendance());
			dto.setAttendanceRate(result.getAttendanceRate());
			queryList.add(dto);
		}
		
		List<LaborEfficiencyDto> list = new Sort<LaborEfficiencyDto>().desc(queryList);
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
		
		LaborEfficiencyDto com = laborEfficiencyDao.queryCompanyOrganId(customerId);
		paramMap.put("organId", com.getOrganId());
		LaborEfficiencyDto comRate = laborEfficiencyDao.queryLaborEfficiencyRatio(paramMap);
		com.setActualAttendance(comRate.getActualAttendance());
		com.setBeInAttendance(comRate.getBeInAttendance());
		com.setAttendanceRate(comRate.getAttendanceRate());
		resultMap.put("company", com);
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
			String endTime, String populationIds) {
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
		int oldTime = Integer.parseInt(beginTime) - 100;
		List<String> empInfoList = EisWebContext.getCurrentUser().getEmpInfoList();
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginTime", oldTime);
		paramMap.put("endTime", endTime);
		paramMap.put("empInfoList", empInfoList);
		List<LaborEfficiencyDto> queryList = laborEfficiencyDao.queryLaborEfficiencyTrend(paramMap);
		
		List<LaborEfficiencyDto> time = comparisonTime(String.valueOf(oldTime), endTime);
		
		for(LaborEfficiencyDto dto : time) {
			boolean f = true;
			LaborEfficiencyDto o = new LaborEfficiencyDto();
			for(LaborEfficiencyDto t : queryList) {
				if(dto.getYearMonth() == t.getYearMonth()) {
					o = t;
					f = false;
				}
			}
			if(f) {
				o.setAttendanceRate(0D);
				o.setRate(0D);
				o.setYearMonth(dto.getYearMonth());
				list.add(o);
			} else {
				list.add(o);
			}
		}
		
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
		for (int j = 0; j < listBasis2.size(); j++) {
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
			String populationIds, List<String> empInfoList) {
		Map<String, Object> paramMap = CollectionKit.newMap();
		Map<String, Object> resultMap = CollectionKit.newMap();
		List<String> listPopulationIds = CollectionKit.newList();
		if (populationIds != null && !"".equals(populationIds)) {
			listPopulationIds = listPopulationIds(populationIds);
			paramMap.put("listPopulationIds", listPopulationIds);
		}
		List<LaborEfficiencyDto> listOrgan = laborEfficiencyDao.queryOrgan(customerId, organId);
		List<LaborEfficiencyDto> resList = CollectionKit.newList();
		
		paramMap.put("customerId", customerId);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);
		paramMap.put("empInfoList", empInfoList);
		for(LaborEfficiencyDto dto : listOrgan) {
			paramMap.put("organId", dto.getOrganId());
			List<LaborEfficiencyDto> result = laborEfficiencyDao.queryOvertimeByOrgan(paramMap);
			dto.setAvgNum(result.get(0).getAvgNum());
			dto.setHourCount(result.get(0).getHourCount());
			resList.add(dto);
		}
		
		List<LaborEfficiencyDto> avgList = CollectionKit.newList();
		List<LaborEfficiencyDto> couList = CollectionKit.newList();
		for (LaborEfficiencyDto dto : resList) {
			if (dto.getOrganId().equals(organId)) {
				resultMap.put("avgNum", dto.getAvgNum());
				resultMap.put("conNum", dto.getHourCount());
				resultMap.put("organId", dto.getOrganId());
				resultMap.put("organName", dto.getOrganName());
			} else {
				avgList.add(dto);
				couList.add(dto);
			}
		}
		if(avgList != null && avgList.size() > 0) {
			Collections.sort(avgList, new Comparator<LaborEfficiencyDto>() {
				@Override
				public int compare(LaborEfficiencyDto o1, LaborEfficiencyDto o2) {
					return o1.getAvgNum() > o2.getAvgNum() ? -1 : o1.getAvgNum() == o2.getAvgNum() ? 0 : 1;
				}
			});
		}
		resultMap.put("avgList", avgList);
		if(couList != null && couList.size() > 0) {
			Collections.sort(couList, new Comparator<LaborEfficiencyDto>() {
				@Override
				public int compare(LaborEfficiencyDto o1, LaborEfficiencyDto o2) {
					return o1.getHourCount() > o2.getHourCount() ? -1 : o1.getHourCount() == o2.getHourCount() ? 0 : 1;
				}
			}); 
		}
		resultMap.put("couList", couList);
		
		LaborEfficiencyDto com = laborEfficiencyDao.queryCompanyOrganId(customerId);
		paramMap.put("organId", com.getOrganId());
		List<LaborEfficiencyDto> result = laborEfficiencyDao.queryOvertimeByOrgan(paramMap);
		com.setAvgNum(result.get(0).getAvgNum());
		com.setHourCount(result.get(0).getHourCount());
		resultMap.put("company", com);
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
		List<String> empInfoList = EisWebContext.getCurrentUser().getEmpInfoList();
		Map<String, Object> reMap = laborEfficiencyDao.getLaborEfficiencyValue(customerId, organId, date,empInfoList);
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
		List<String> empInfoList = EisWebContext.getCurrentUser().getEmpInfoList();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("curDate", date);
		parMap.put("beforeDate", beforeDate);
		parMap.put("empInfoList", empInfoList);
		Map<String, Object> queryMap = laborEfficiencyDao.queryOvertimeHours(parMap);
		double curAvg = 0D, oldAvg = 0D, avgDiffer = 0D, curNum = 0D, conDiffer = 0D;
		if (queryMap != null) {
			curNum = (double) queryMap.get("curNum");
			conDiffer = (double) queryMap.get("comNum");
			double oldNum = (double) queryMap.get("oldNum");
			double curPNum = queryMap.get("curPNum") == null ? 0d : (double) queryMap.get("curPNum");
			double oldPNum = queryMap.get("oldPNum") == null ? 0d : (double) queryMap.get("oldPNum");
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
		List<String> empInfoList = EisWebContext.getCurrentUser().getEmpInfoList();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("maxDate", maxDate);
		parMap.put("minDate", minDate);
		parMap.put("otTime", newOtTime);
		parMap.put("empInfoList", empInfoList);
		return laborEfficiencyDao.queryOvertimeWarningCount(parMap);
	}

	/**
	 * 加班预警明细
	 */
	@Override
	public Map<String, Object> queryOvertimeWarningDetail(String customerId, String organId, Integer otTime,
			Integer otWeek, String page, String rows) {
		Map<String, Object> map = CollectionKit.newMap();
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
		parMap.put("rows", Integer.parseInt(rows));
		int count = 0;
		if(page != null && !"".equals(page)){
			parMap.put("start", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
			if(Integer.parseInt(page) == 1){
				count = laborEfficiencyDao.queryOvertimeWarningDetailCount(parMap);
			}
		}
		map.put("count", count);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeWarningDetail(parMap);
		map.put("list", list);
		return map;
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
		List<String> crowdsList = CollectionKit.newLinkedList();
		String startYearMonth = null, endYearMonth = null;
		try {
			if (crowds != null && !"".equals(crowds)) {
				String[] crowdsArray = crowds.split("@");
				if (crowdsArray != null && crowdsArray.length > 0) {
					for(String arr : crowdsArray){
						crowdsList.add(arr);
					}
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
		List<Object> percentList = CollectionKit.newLinkedList();
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
				if (doubleIsNull(list.get(i - 1).getAvgNum()) >= 0.01) {
					double dNum = (doubleIsNull(list.get(i).getAvgNum()) - doubleIsNull(list.get(i - 1).getAvgNum())) * 100
							/ doubleIsNull(list.get(i - 1).getAvgNum());
					dNum = formatDouble(dNum);
					percentList.add(dNum);
				} else {
					percentList.add("-");
				}
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
		List<String> crowdsList = CollectionKit.newLinkedList();
		String startYearMonth = null, endYearMonth = null;
		try {
			if (crowds != null && !"".equals(crowds)) {
				String[] crowdsArray = crowds.split("@");
				if (crowdsArray != null && crowdsArray.length > 0) {
					for(String arr : crowdsArray){
						crowdsList.add(arr);
					}
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
		List<Object> percentList = CollectionKit.newLinkedList();
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
					double dNum = (doubleIsNull(list.get(i).getConNum()) - doubleIsNull(list.get(i - 1).getConNum())) * 100
							/ doubleIsNull(list.get(i - 1).getConNum());
					dNum = formatDouble(dNum);
					percentList.add(dNum);
				} else {
					percentList.add("-");
				}
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
	public Map<String, Object> queryOvertimeCondition(String customerId, String organId, String date,
			String crowds, String page, String rows) {
		Map<String, Object> map = CollectionKit.newMap();
		String newDate = null;
		List<String> crowdsList = CollectionKit.newLinkedList();
		if (date != null && !"".equals(date)) {
			newDate = date.replaceAll("/", "").replaceAll("-", "");
			if (date.length() > 4) {
				newDate = newDate.substring(0, 4) + addZeroToString(newDate.substring(4));
			}
		} else {
			newDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		}
		if (crowds != null && !"".equals(crowds)) {
			if(crowds.indexOf(",") > -1) {
				crowds = crowds.replaceAll(",", "@");
			}
			String[] crowdsArray = crowds.split("@");
			if (crowdsArray != null && crowdsArray.length > 0) {
				for(String arr : crowdsArray){
					crowdsList.add(arr);
				}
			}
		}
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("date", newDate);
		parMap.put("listPopulationIds", crowdsList);
		parMap.put("rows", Integer.parseInt(rows));
		int count = 0;
		if(page != null && !"".equals(page)){
			parMap.put("start", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
			if(Integer.parseInt(page) == 1){
				count = laborEfficiencyDao.queryOvertimeConditionCount(parMap);
			}
		}
		map.put("count", count);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeCondition(parMap);
		map.put("list", list);
		return map;
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
		List<String> empInfoList = EisWebContext.getCurrentUser().getEmpInfoList();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("date", newDate);
		parMap.put("empInfoList", empInfoList);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryCheckWorkTypeLayout(parMap);
		List<LaborEfficiencyTypeDto> pieList = CollectionKit.newLinkedList();
		List<LaborEfficiencyTypeDto> gridList = CollectionKit.newLinkedList();
		double con = 0D, num = 0D;
		if (list != null && list.size() > 0) {
			for (LaborEfficiencyDto l : list) {
				con += l.getConNum();
			}
			for (LaborEfficiencyDto l : list) {
				num = formatDouble(l.getConNum() * 100 / con);
				LaborEfficiencyTypeDto dto = new LaborEfficiencyTypeDto();
				dto.setName(l.getCheckWorkTypeName());
				dto.setValue(l.getConNum());
				pieList.add(dto);
				LaborEfficiencyTypeDto tDto = new LaborEfficiencyTypeDto();
				tDto.setName(l.getCheckWorkTypeName());
				tDto.setHours(l.getConNum());
				tDto.setPercent(num + "%");
				gridList.add(tDto);
			}
			Collections.sort(pieList);
			Collections.sort(gridList);
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
		List<String> empInfoList = EisWebContext.getCurrentUser().getEmpInfoList();
		PaginationDto<LaborEfficiencyGridDto> dto = new PaginationDto<LaborEfficiencyGridDto>(page, rows);
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("userName", userName);
		parMap.put("date", yearMonth);
		parMap.put("list", perList);
		parMap.put("start", dto.getOffset());
		parMap.put("rows", dto.getLimit());
		parMap.put("empInfoList", empInfoList);
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
	public void attendanceMonthly(HttpServletResponse response, String yearmonth, String coustomerId, String orgId)
	{
		Properties pps = new Properties();
        try {
        InputStream inputStream = WebConfigListener.class.getClassLoader()
                .getResourceAsStream("conf/vacation.properties");
			pps.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<HashMap<String, Object>> resultmap = this.laborEfficiencyDao.attendanceMonthly(yearmonth,coustomerId, orgId);
		List<HashMap<String, Object>> theory = this.laborEfficiencyDao.theoryAttendance(yearmonth);
		List<HashMap<String, Object>> cwtMap = this.laborEfficiencyDao.queryCheckWorkTypeMap(yearmonth);
		//获取假日天数
		Integer vacationCount = this.vacationCount(yearmonth, pps);

		HashMap<String, Object> cwtHashMap = findCheckWorkTypeMap(cwtMap);
		String title = "月度考勤表";
		String dateTitle = yearmonth.substring(0, 4)+"年"+yearmonth.substring(4, 6)+"月份"+"考勤汇总表";
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader(	"Content-Disposition","attachment;filename="+title+yearmonth+".xls"
					+ new String((title + ".xls").getBytes("GBK"), "iso-8859-1"));


			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(title);

			//题目样式
			HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
			cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont tfont = workbook.createFont();
			tfont.setFontHeightInPoints((short)18);
			tfont.setBold(true);
			tfont.setFontName("標楷體");
			cellStyleTitle.setFont(tfont);
			cellStyleTitle.setWrapText(true);

			//工作日样式-表头
			CellStyle workdaystyle = workbook.createCellStyle();
			workdaystyle.setFillForegroundColor(HSSFCellStyle.ALIGN_CENTER);
			workdaystyle.setWrapText(true);
			HSSFFont workdayfont = workbook.createFont();
			workdayfont.setFontHeightInPoints((short)9);
			workdayfont.setBold(true);
			workdayfont.setFontName("新細明體");
			workdaystyle.setFont(workdayfont);
			
			//周末/节假日样式-表头
			CellStyle weekendstyle = workbook.createCellStyle();
			weekendstyle.setFillForegroundColor(HSSFCellStyle.ALIGN_CENTER);
			weekendstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
			weekendstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			HSSFFont weekendfont = workbook.createFont();
			weekendfont.setFontHeightInPoints((short)9);
			weekendfont.setBold(true);
			weekendfont.setFontName("新細明體");
			weekendstyle.setFont(weekendfont);
			
			//未填任何东西的样式-正文
			CellStyle nullstyle = workbook.createCellStyle();
			nullstyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
			nullstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			//节假日的样式-正文
			CellStyle weekstyle = workbook.createCellStyle();
//			weekstyle.setFillForegroundColor(IndexedColors.GOLD.GETINDEX());
//			WEEKSTYLE.SETFILLPATTERN(CELLSTyle.SOLID_FOREGROUND);
			HSSFFont weekfont = workbook.createFont();
			weekfont.setFontName("宋体");
			weekfont.setColor(IndexedColors.RED.getIndex());
			weekstyle.setFont(weekfont);
			
			 
			//合并
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, theory.size()*2+1));
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell0 = row0.createCell(0);
			row0.setHeight((short)600);
			cell0.setCellValue(dateTitle);
			cell0.setCellStyle(cellStyleTitle);

			HSSFRow row1 = sheet.createRow(1);
			HSSFRow row2 = sheet.createRow(2);

			/**************创建表头*********************/
			for (int i = 1, size = theory.size(); i <= size; i++) {
					sheet.addMergedRegion(new CellRangeAddress(1, 1, i * 2 - 1,	i * 2));//每一日占两列
				HSSFCell celln = row1.createCell(i * 2 - 1);
				celln.setCellValue(DateUtil.getDate4CN(theory.get(i - 1).get("days")+"", "月日星期"));
				//根据周六日设置为特定的样式
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtil.strToDate(theory.get(i - 1).get("days")+""));
				int dw = cal.get(Calendar.DAY_OF_WEEK);
				//休息日表头（周六日且计划不用上班 或 计划不用上班）
				if((dw == 1 || dw == 7) && String.valueOf(theory.get(i - 1).get("hour_count")).equals("0.0") || String.valueOf(theory.get(i - 1).get("hour_count")).equals("0.0")){
					celln.setCellStyle(weekendstyle);
				}
				else{
					celln.setCellStyle(workdaystyle);
				}
				//某一天
				HSSFCell cellnM = row2.createCell(i * 2 - 1);
				cellnM.setCellValue("上午");
				HSSFCell cellnA = row2.createCell(i * 2);
				cellnA.setCellValue("下午");
			}
			/****************end 创建表头********************/
			int mergedcol = theory.size() * 2 + 1;
			sheet.addMergedRegion(new CellRangeAddress(1, 2, mergedcol,
					mergedcol));
			HSSFCell cellCount = row1.createCell(theory.size() * 2 + 1);
			cellCount.setCellValue("出勤合计（天）");
			cellCount.setCellStyle(workdaystyle);

			HSSFComment commentendH = cellCount.getCellComment();
			//获取最后一列表头说明
			cellCount.setCellComment(this.makeaHSSFComment(sheet, "出勤合计（天）=打勾+带薪节假日+正常考勤", commentendH));
			
			String currentEmp = "";
			Integer currentRowNum = 3;
			HSSFRow row = null;

			double dayCount = 0.0D;
			Integer dayofmonth = 0;
			Calendar cl = Calendar.getInstance();
			//存储各种类型的考勤调整的时间
			Map<String, Double> cwtCountMap = CollectionKit.newMap();

			for (int i=0, size = resultmap.size(); i < size; i++) {

				String empId = resultmap.get(i).get("emp_id")+"";
				String empName = resultmap.get(i).get("user_name_ch")+"";
				String days = resultmap.get(i).get("days")+"";
				String morn = resultmap.get(i).get("morning")+"";
				String after = resultmap.get(i).get("afteroon")+"";
				cl.setTime(DateUtil.strToDate(days));
				dayofmonth = cl.get(Calendar.DAY_OF_MONTH);
				/****************第一列人名****************/
				if (!currentEmp.equals(empId)) {
					currentEmp = empId;
					row = sheet.createRow(currentRowNum);
					//初始化行,根据thoery表获取到底是否假日
					ini1Row(row, "休息", nullstyle, weekstyle, theory, sheet);
					HSSFCell cellName = row.createCell(0);
					cellName.setCellValue(empName);
					//重置天数
					dayCount = 0.0D;
					//重置列
					currentRowNum++;
					//重置调整考勤天数
					cwtCountMap = CollectionKit.newMap();
				}
				/********************正文,逐天填写上下午的考勤情况********************/
				HSSFCell cell1 = row.createCell(dayofmonth * 2 - 1);
				HSSFCell cell2 = row.createCell(dayofmonth * 2);
				//打勾算0.5天；缺勤，迟到，早退0天；事假，病假0天；其余考勤状态0.5天
				//上午
				if ((!Str.IsEmpty(morn)) && (morn.equals("√"))) {
					cell1.setCellValue(morn);
					dayCount += 0.5D;
				} 
				else if(!Str.IsEmpty(morn) && morn.length() == 2){
					if(morn.equals("缺勤")){
						cell1.setCellValue("");
						cell1.setCellStyle(nullstyle);
					}else{
						cell1.setCellValue(morn);
						cell1.setCellStyle(nullstyle);
						HSSFComment comment = cell1.getCellComment();
						cell1.setCellComment(makeaHSSFComment(sheet, "打卡时间："+resultmap.get(i).get("clock_in_am"), comment));
					}
				}
				//是调整考勤的
				else if(!Str.IsEmpty(morn) && morn.length() > 2){
					//病假，事假：无
					//cwtId:c0bbcedb57024005a93d9278198412b6
					//cwtId:7e09efa0bce04eaca623ee84a5546444
					String cwtid = morn.substring(0, 32);
					cell1.setCellValue(cwtHashMap.get(cwtid)+"");
					if(!Str.IsEmpty(cwtid) && cwtid.equals(pps.getProperty("CHACK.WORK.TYPT.LEAVE.JOB"))){}
					if(!Str.IsEmpty(cwtid) && cwtid.equals(pps.getProperty("CHACK.WORK.TYPT.LEAVE.SICK"))){}
					else if(!Str.IsEmpty(cwtid))
						cwtCountMap = countAllCWT(cwtCountMap, cwtid, 0.5D);
				}
				//有bug的情况
				else{
				}
				//下午
				if ((!Str.IsEmpty(after)) && (after.equals("√"))) {
					cell2.setCellValue(after);
					dayCount += 0.5D;
				}
				else if(!Str.IsEmpty(after) && after.length() == 2){
					if(after.equals("缺勤")){
						cell2.setCellValue("");
						cell2.setCellStyle(nullstyle);
					}else{
						cell2.setCellValue(after);
						cell2.setCellStyle(nullstyle);
						HSSFComment comment = cell2.getCellComment();
						cell2.setCellComment(makeaHSSFComment(sheet, "打卡时间："+resultmap.get(i).get("clock_out_pm"), comment));
					}
				}
				else if(!Str.IsEmpty(after) && after.length()>2) {
					String cwtid = after.substring(0, 32);
					cell2.setCellValue(cwtHashMap.get(cwtid)+"");
					if(!Str.IsEmpty(cwtid) && cwtid.equals("c0bbcedb57024005a93d9278198412b6")){
					}
					if(!Str.IsEmpty(cwtid) && cwtid.equals("7e09efa0bce04eaca623ee84a5546444")){}
					else if(!Str.IsEmpty(cwtid))
						cwtCountMap = countAllCWT(cwtCountMap, cwtid, 0.5D);
				}
				//有bug的情况
				else{
				}

				/**********最后一列显示合计出勤数,并列出所有考勤状态的天数，病假，事假除外。例子：出勤15天，调休1天，婚假4天，带薪节假日1天******/
				if ((i + 1 != size)	&& (!currentEmp.equals(resultmap.get(i + 1).get("emp_id")+"")) || i + 1 == size) {
					HSSFCell cellC = row.createCell(theory.size() * 2 + 1);
					//考勤总计=勾+带薪节假日+正常出勤
					double nomal = Str.IsEmpty(cwtCountMap.get(pps.get("CHACK.WORK.TYPT.NOMAL"))+"") ? 0.0D : Double.valueOf(cwtCountMap.get(pps.getProperty("CHACK.WORK.TYPT.NOMAL"))+"");
					cellC.setCellValue(dayCount + vacationCount + nomal);
					HSSFComment comment = cellC.getCellComment();
					//获取最后一列的总结各种调整考勤的天数集合
					String summery = resultSummeryCWTCount(cwtCountMap, cwtHashMap);
					cellC.setCellComment(this.makeaHSSFComment(sheet, "考勤记录：" + dayCount + "天，带薪节假日:" + vacationCount + "天"+summery, comment));
				}
			}

			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化行
	 * @param row
	 * @param content 默认节假日填写的值
	 * @param workstyle 应工作日的样式
	 * @param weekstyle 节假日的样式
	 * @param theory 应出勤
	 * @param comment 标注
	 */
	public void ini1Row(HSSFRow row, String content,CellStyle workstyle, CellStyle weekstyle, List<HashMap<String, Object>> theory,HSSFSheet sheet){
		for(int i = 0,size = theory.size();i<size;i++){
			double hourC = 0.00D;
			hourC = Double.valueOf(theory.get(i).get("hour_count")+"");
			//公共节假日
			if(hourC == 0.00D){
				HSSFCell cellam = row.createCell(i*2+2);
				HSSFCell cellpm = row.createCell(i*2+1);
				cellam.setCellValue(content);
				cellpm.setCellValue(content);
				cellam.setCellStyle(weekstyle);
				cellpm.setCellStyle(weekstyle);
			}
			//设定工作日的背景
			else{

		        
				HSSFCell cellam = row.createCell(i*2+2);
				HSSFCell cellpm = row.createCell(i*2+1);
				cellam.setCellStyle(workstyle);
				cellpm.setCellStyle(workstyle);
			}
			
		}
	}
	/**
	 * 创建一个标注
	 */
	public HSSFComment makeaHSSFComment(HSSFSheet sheet, String text, HSSFComment comment){

		HSSFPatriarch patr = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置，详见文档
		if(comment == null)
			comment = patr.createComment(new HSSFClientAnchor(0,0,0,0, (short)80, 20 ,(short) 60, 50));
        // 设置注释内容
        comment.setString(new HSSFRichTextString(text));
        return comment;
	}
	
	/**
	 * 获取到考勤类型的Hashmap集合key=cwtid,value=考勤类型名
	 * @param list
	 * @return
	 */
	public HashMap<String, Object> findCheckWorkTypeMap(List<HashMap<String, Object>> list)
	{
	  HashMap<String, Object> hmap = new HashMap<String, Object>();
	  for (HashMap<String, Object> hm : list) {
	    hmap.put(hm.get("checkwork_type_id") + "", hm.get("checkwork_type_name") + "");
	  }

	  return hmap;
	}
	

	/**
	 * 获取到该月份的带薪假期日数
	 * @return
	 */
	public int vacationCount(String yearmonth, Properties pps){
		StringBuffer sb = new StringBuffer();
		sb.append(yearmonth.substring(0, 4));
		sb.append("-");
		sb.append(yearmonth.substring(4, yearmonth.length()));
		sb.append("-01");
		String beginTime = sb.toString();
		String endTime = DateUtil.getMonthLast(new DateTime(beginTime).toDate());
		return commonService.findVacationDays(beginTime, endTime);
		
//		int vacationCount = 0;
//		//从360日历获取到
////		String vactionStr = HolidayUtil.getElementTextContent("https://www.baidu.com/s?wd=%E6%97%A5%E5%8E%86", "dateFestival").trim();
//		String vactionStr = HolidayUtil.getElementTextContent("http://hao.360.cn/rili/", "dateFestival").trim();
//		/**
//		 * 例子：20160101||元旦\n20160208||春节\n20160404||清明节\n20160501||劳动节\n20160609||端午节\n20160915||中秋节\n20161001||国庆节
//		 */
//		String[] vaStrlist = vactionStr.split("\n");
//		for(String vday: vaStrlist){
//			if(vday.indexOf(yearmonth) != -1){
//				String holidayName = vday.substring(10);
//				switch (holidayName) {
//				case "元旦":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.NEWYEAR.F"));
//					break;
//				case "春节":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.SPRING.F"));
//					break;
//				case "清明节":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.TOMBSWEEPING.F"));
//					break;
//				case "劳动节":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.WORKING.F"));
//					break;
//				case "端午节":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.DRAGONBOAT.F"));
//					break;
//				case "中秋节":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.MOON.F"));
//					break;
//				case "国庆节":
//					vacationCount += Integer.valueOf(pps.getProperty("VAC.NATIONALDAY.F"));
//					break;
//
//				default:
//					break;
//				}
//			}
//		}
//		return vacationCount;
	}
	/**
	 * 叠加各种类型的考勤调整天数
	 * @param cwtCountMap 考勤类型计数集合
	 * @param key 考勤类型id
	 * @param price 单次调用的叠加天数
	 */
	private Map<String, Double> countAllCWT(Map<String, Double> cwtCountMap, String key, double price ){
		if(cwtCountMap == null){
			cwtCountMap = CollectionKit.newMap();
		}
		else if(cwtCountMap.get(key) == null){
			cwtCountMap.put(key, price);
		}
		else{
			cwtCountMap.put(key, cwtCountMap.get(key)+price);
		}
		return cwtCountMap;
	}
	/**
	 * 获取到考勤总天数+带薪假期天数+各类的考勤调整后天数
	 * @param cwtCountMap
	 * @param cwtMap
	 * @return
	 */
	private String resultSummeryCWTCount(Map<String, Double> cwtCountMap, Map<String, Object> cwtHashMap ){
		StringBuilder sb = new StringBuilder();
		if(cwtCountMap == null){
			return "";
		}
		Iterator<String> it = cwtCountMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next()+"";
			double count = cwtCountMap.get(key);
			String cwtName = cwtHashMap.get(key)+"";
			sb.append(","+cwtName+":"+count+";");
		}
		return sb.toString();
	}

}
