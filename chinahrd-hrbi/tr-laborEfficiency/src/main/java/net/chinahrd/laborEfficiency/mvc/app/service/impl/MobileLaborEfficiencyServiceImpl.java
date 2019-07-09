package net.chinahrd.laborEfficiency.mvc.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyDto;
import net.chinahrd.entity.dto.app.laborEfficiency.LaborEfficiencyGridDto;
import net.chinahrd.laborEfficiency.mvc.app.dao.MobileLaborEfficiencyDao;
import net.chinahrd.laborEfficiency.mvc.app.service.MobileLaborEfficiencyService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
@Service("mobileLaborEfficiencyService")
public class MobileLaborEfficiencyServiceImpl implements MobileLaborEfficiencyService {

	@Autowired
	private MobileLaborEfficiencyDao laborEfficiencyDao;

	/**
	 * 劳动力效能对比
	 */
	public Map<String, Object> queryLaborEfficiencyRatio(String customerId, String organId, String beginTime,String endTime) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		List<LaborEfficiencyDto> listOrgan = laborEfficiencyDao.queryOrgan(customerId, organId);
		List<LaborEfficiencyDto> list = CollectionKit.newList();
		for(LaborEfficiencyDto dto : listOrgan) {
			Map<String, Object> paramMap = CollectionKit.newMap();
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
		sort(list, "getAttendanceRate", null);
		if (list != null) {
			LaborEfficiencyDto curr=null;
			for (LaborEfficiencyDto dto : list) {
				if (dto.getOrganId().equals(organId)) {
					curr=dto;
					break;
				}
			}
			list.remove(curr);
			resultMap.put("list", list);
			resultMap.put("curr", curr);
		}
		return resultMap;
	}
	
	
	
	public Map<String, Integer> findMinMaxTime(String customerId) {
		return laborEfficiencyDao.findMinMaxTime(customerId);
	}

	public String queryParentOrganId(String customerId, String organId) {
		return laborEfficiencyDao.queryParentOrganId(customerId, organId);
	}


	

	/**
	 * 劳动力效能趋势
	 */
	public Map<String, Object> queryLaborEfficiencyTrend(String customerId, String organId, String beginTime,String endTime) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		Map<String, Object> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginTime", getBeforeJune(endTime));
		paramMap.put("endTime", endTime);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryLaborEfficiencyTrend(paramMap);
		for (int i = 1; i < list.size(); i++) {
			LaborEfficiencyDto dto = list.get(i);
			Double p=list.get(i - 1).getAttendanceRate();
			if(null==p||p==0){
				dto.setRate("-");
			}else{
				Double c=dto.getAttendanceRate();
				if(null==c)c=0.0;
				dto.setRate(((c-p )/ p)+"");
			}
			
		}
		if(list.size()>6){
			list.remove(0);
		}
		resultMap.put("list", list);
		return resultMap;
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
			double curPNum = (double) queryMap.get("curPNum");
			double oldPNum = (double) queryMap.get("oldPNum");
			if (curPNum > 0) {
				curAvg = formatDouble(curNum / curPNum);
			}
			if (oldPNum > 0) {
				oldAvg = formatDouble(oldNum / oldPNum);
			}
			avgDiffer = formatDouble(curAvg - oldAvg);
		}
		map.put("totalNum", curNum);
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
	 * 加班时长趋势-人均时长  总长
	 */
	@Override
	public Map<String, Object> queryOvertimeTrend(String customerId, String organId, String time) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("beginTime",  getBeforeJune(time));
		parMap.put("endTime", time);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryOvertimeTrend(parMap);
		for(int i=1;i<list.size();i++){
			LaborEfficiencyDto dto=list.get(i);
			LaborEfficiencyDto p=list.get(i-1);
			if(p.getAvgNum()!=0){
				dto.setAvgRate((dto.getAvgNum()-p.getAvgNum())*100/p.getAvgNum()+"");
			}else{
				dto.setAvgRate("-");
			}
			if(p.getTotalNum()!=0){
				dto.setTotalRate((dto.getTotalNum()-p.getTotalNum())*100/p.getTotalNum()+"");
			}else{
				dto.setTotalRate("-");
			}
		}
		if(list.size()>6){
			list.remove(0);
		}
		rsMap.put("list", list);
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
	 * 考勤类型分布
	 */
	@Override
	public Map<String, Object> queryCheckWorkTypeLayout(String customerId, String organId, String time) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		String newDate;
		if (time != null && !"".equals(time)) {
			newDate = time.replaceAll("/", "").replaceAll("-", "");
			if (time.length() > 4) {
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
		double con = 0D, num = 0D;
		if (list != null && list.size() > 0) {
			for (LaborEfficiencyDto l : list) {
				con += l.getTotalNum();
			}
			for (LaborEfficiencyDto l : list) {
				num = formatDouble(l.getTotalNum() * 100 / con);
				Map<String, Object> pieMap = CollectionKit.newMap();
				pieMap.put("name", l.getTypeName());
				pieMap.put("value", l.getTotalNum());
				pieMap.put("rate", num + "%");
				pieList.add(pieMap);
			}

		}
		rsMap.put("pieList", pieList);
		return rsMap;
	}

	/**
	 * 组织机构加班时长
	 */
	public Map<String, Object> queryOvertimeByOrgan(String customerId, String organId, String time) {
		Map<String, Object> resultMap = CollectionKit.newMap();
		List<LaborEfficiencyDto> list = CollectionKit.newList();
		List<LaborEfficiencyDto> listOrgan = laborEfficiencyDao.queryOrgan(customerId, organId);
		for(LaborEfficiencyDto dto : listOrgan) {
			Map<String, Object> paramMap = CollectionKit.newMap();
			paramMap.put("customerId", customerId);
			paramMap.put("organId", dto.getOrganId());
			paramMap.put("beginTime", time);
			paramMap.put("endTime", time);
			List<LaborEfficiencyDto> result = laborEfficiencyDao.queryOvertimeByOrgan(paramMap);
			double avg = 0.0;
			double con = 0.0;
			for(LaborEfficiencyDto r : result) {
				avg += r.getAvgNum();
				con += r.getNum();
			}
			if(result.size()==0){
				dto.setAvgNum(0.0);
			}else{
				dto.setAvgNum(avg/result.size());
			}
			
			dto.setTotalNum(con);
			list.add(dto);
		}
		
		for (LaborEfficiencyDto dto : list) {
			if (dto.getOrganId().equals(organId)) {
				resultMap.put("avgNum", dto.getAvgNum());
				resultMap.put("totalNum", dto.getTotalNum());
				list.remove(dto);
				break;
			} 
		}
		resultMap.put("list", list);
		return resultMap;
	}

	/**
	 * 出勤明细
	 */
	public PaginationDto<LaborEfficiencyGridDto> queryAttendanceDetail(String customerId, String organId,
			String time, List<Integer> checkList, int page, int rows) {
		String yearMonth = null;
		List<String> perList = CollectionKit.newLinkedList();
		for (Integer i : checkList) {
			perList.add("ctName" + i);
		}
		if (time != null && !"".equals(time)) {
			yearMonth = time.replaceAll("/", "").replaceAll("-", "");
			if (time.length() > 4) {
				yearMonth = yearMonth.substring(0, 4) + addZeroToString(yearMonth.substring(4));
			}
		}
		PaginationDto<LaborEfficiencyGridDto> dto = new PaginationDto<LaborEfficiencyGridDto>(page, rows);
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("time", yearMonth);
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
	private String getBeforeJune(String time){
		if(time.length()==6){
			time+="01";
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(f.parse(time));
		
		} catch (Exception e) {
			e.printStackTrace();
			return time;
		}
		c.add(Calendar.MONTH, -6);
		SimpleDateFormat rf = new SimpleDateFormat("yyyyMM");
		return rf.format(c.getTime());
	}

	/**
	 * 集合排序
	 * @param list
	 * @param method
	 * @param soft
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(List<?> list, final String method, final String soft) {
		Collections.sort(list, new Comparator() {

			@Override
			public int compare(Object a, Object b) {
				int ret = 0;
				// 获取a 的方法名
				try {
					Method ma = a.getClass().getMethod(method, null);
					Method mb = b.getClass().getMethod(method, null);
					if (soft != null && "desc".equals(soft)) {
						ret = mb.invoke(((Object) b), null).toString().compareTo(ma.invoke(((Object) a), null).toString());
					} else {
						ret = ma.invoke(((Object) a), null).toString().compareTo(mb.invoke(((Object) b), null).toString());
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return ret;
			}
		});
	}



	/* (non-Javadoc)
	 * @see net.chinahrd.laborEfficiency.mvc.app.service.MobileLaborEfficiencyService#queryCheckWorkType(java.lang.String, java.util.List)
	 */
	@Override
	public Map<String, Object> queryCheckWorkType(String customerId,
			List<Integer> checkList) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		parMap.put("customerId", customerId);
		parMap.put("list", checkList);
		List<LaborEfficiencyDto> list = laborEfficiencyDao.queryCheckWorkType(parMap);
		List<String> idList = CollectionKit.newLinkedList();
		List<String> nameList = CollectionKit.newLinkedList();
		List<LaborEfficiencyGridDto> colModelList = CollectionKit.newLinkedList();
		String[] idArray = { "userName",  "organName", "actualNum", "shouldNum", "percentNum",
				"overtimeNum"};
		String[] nameArray = {"姓名",  "所属机构", "实出勤", "应出勤", "劳动力效能", "加班"};
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
//		idList.add(idArray[idArray.length - 1]);
//		nameList.add(nameArray[nameArray.length - 1]);
		for (int i = 0; i < idList.size(); i++) {
			LaborEfficiencyGridDto gt = new LaborEfficiencyGridDto();
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
}
