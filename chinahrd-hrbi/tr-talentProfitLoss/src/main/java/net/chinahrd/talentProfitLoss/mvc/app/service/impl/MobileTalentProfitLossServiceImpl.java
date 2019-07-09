package net.chinahrd.talentProfitLoss.mvc.app.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.PieChartDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossConfigDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossDto;
import net.chinahrd.talentProfitLoss.mvc.app.dao.MobileTalentProfitLossDao;
import net.chinahrd.talentProfitLoss.mvc.app.service.MobileTalentProfitLossService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人才损益 移动端
 * @author htpeng
 *2016年8月23日下午4:00:47
 */
@Service("mobileTalentProfitLossService")
public class MobileTalentProfitLossServiceImpl implements MobileTalentProfitLossService {

	@Autowired
	private MobileTalentProfitLossDao talentProfitLossDao;

	/* 流入流出 配置信息
	 * @see net.chinahrd.biz.paper.mobile.service.MobileTalentProfitLossService#queryChangeConfig(java.lang.String, java.util.List, java.util.List)
	 */
	@Override
	public Map<String, List<TalentProfitLossConfigDto>> queryChangeConfig(String customerId,
			List<Integer> inflowList, List<Integer> outflowList) {
		// TODO Auto-generated method stub
		List<TalentProfitLossConfigDto> queryChangeConfig = talentProfitLossDao.queryChangeConfig(customerId,inflowList,outflowList);
		Map<String,List<TalentProfitLossConfigDto>> map=CollectionKit.newMap();
		List<TalentProfitLossConfigDto> input=CollectionKit.newList();
		List<TalentProfitLossConfigDto> output=CollectionKit.newList();
		map.put("input", input);
		map.put("output", output);
		for(TalentProfitLossConfigDto dto:queryChangeConfig){
			if(dto.getType()==0){
				//流入
				input.add(dto);
			}else{
				//流出
				output.add(dto);
			}
		}
		return map;
	}


	/**
	 * 本月/本年流入统计
	 */
	@Override
	public Integer queryTalentInflowVal(String customerId, String organId, String time,
			List<Integer> list) {
		return talentProfitLossDao.queryTalentInflowVal(customerId, organId, time,
				list);
		
	}

	/**
	 * 本月/本年流出统计
	 */
	@Override
	public Integer queryTalentOutflowVal(String customerId, String organId, String time,
			List<Integer> list) {
		return talentProfitLossDao.queryTalentOutflowVal(customerId, organId, time,
				list);
	}




	
	

	/**
	 * 人才损益-人员分布-饼图
	 */
	@Override
	public List<Object> queryPopulationPie(String customerId, String organId, String time) {
		List<Object> linkedList = CollectionKit.newLinkedList();
		Map<String, Object> map = null;
		 List<TalentProfitLossDto> list = talentProfitLossDao.queryPopulationPie(customerId, organId, time);
		 double total=0.0;
		 for (TalentProfitLossDto l : list) {
			 total+=l.getConNum();
		 }
		
		 if (list != null && list.size() > 0) {
			for (TalentProfitLossDto l : list) {
				map = CollectionKit.newMap();
				map.put("name", l.getProvinceName());
				map.put("value", integerIsNull(l.getConNum()));
				 if(total>0){
					 map.put("rate",  (integerIsNull(l.getConNum())/total)*100);
				 }
				linkedList.add(map);
			}
		}
		return linkedList;
	}

	
	/**
	 * 人才损益同比
	 */
	@Override
	public Map<String, Object> queryTalentProfitLossSameData(String customerId, String organId, String year,
			String month,  List<Integer> inflowList, List<Integer> outflowList) {
		Map<String, Object> rsMap = CollectionKit.newMap();
		String minCurDate = null, maxCurDate = null, minOldDate = null, maxOldDate = null;
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
		}catch(Exception e){
			
		}
		Map<String, Object> curMap = CollectionKit.newMap();
		curMap.put("customerId", customerId);
		curMap.put("organId", organId);
		curMap.put("minDate", minCurDate);
		curMap.put("maxDate", maxCurDate);
		curMap.put("inflowList", inflowList);
		curMap.put("outflowList", outflowList);
		Map<String, Object> oldMap = CollectionKit.newMap();
		oldMap.put("customerId", customerId);
		oldMap.put("organId", organId);
		oldMap.put("minDate", minOldDate);
		oldMap.put("maxDate", maxOldDate);
		oldMap.put("inflowList", inflowList);
		oldMap.put("outflowList", outflowList);
		List<TalentProfitLossDto> curList = null;
		List<TalentProfitLossDto> oldList = null;
		curList = talentProfitLossDao.queryTalentProfitLossSameData(curMap);
		oldList = talentProfitLossDao.queryTalentProfitLossSameData(oldMap);
		List<String> curDateList = CollectionKit.newLinkedList();
		List<String> oldDateList = CollectionKit.newLinkedList();
		List<Integer> curConList = CollectionKit.newLinkedList();
		List<Integer> oldConList = CollectionKit.newLinkedList();
		Map<String, Integer> curDateMap = CollectionKit.newMap();
		Map<String, Integer> oldDateMap = CollectionKit.newMap();
		int num = 0;
		try {
			sdf = new SimpleDateFormat("yyyyMM");
			if (curList != null && curList.size() > 0) {
				for (TalentProfitLossDto cl : curList) {
					curDateMap.put(cl.getChangeDate(), cl.getConNum());
				}
				Date dBegin = sdf.parse(minCurDate.substring(0, 4)  + minCurDate.substring(4));
				Date dEnd = sdf.parse(maxCurDate.substring(0, 4)  + maxCurDate.substring(4));
				List<Date> lDate = queryDatesWithMonth(dBegin, dEnd);
				for (Date date : lDate) {
					curDateList.add(sdf.format(date));
					curConList.add(curDateMap.get(sdf.format(date)) == null ? num : curDateMap.get(sdf.format(date)));
				}
			}
			if (oldList != null && oldList.size() > 0) {
				for (TalentProfitLossDto ol : oldList) {
					oldDateMap.put(ol.getChangeDate(), ol.getConNum());
				}
				Date dBegin = sdf.parse(minOldDate.substring(0, 4) + minOldDate.substring(4));
				Date dEnd = sdf.parse(maxOldDate.substring(0, 4)  + maxOldDate.substring(4));
				List<Date> lDate = queryDatesWithMonth(dBegin, dEnd);
				for (Date date : lDate) {
					oldDateList.add(sdf.format(date));
					oldConList.add(oldDateMap.get(sdf.format(date)) == null ? num : oldDateMap.get(sdf.format(date)));
				}
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
	 * 异动统计饼图表格
	 */
	@Override
	public Map<String, Object> queryInflowOutflowChangeType(String customerId, String organId, String time) {
		Map<String, Object> map = CollectionKit.newMap();
		Map<String, Object> parMap = CollectionKit.newMap();
		
		parMap.put("customerId", customerId);
		parMap.put("organId", organId);
		parMap.put("time", time);
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		
		List<Integer> allList = talentProfitLossDao.queryChangeType(customerId);
		Iterator<Integer> iteratorAll=allList.iterator();
		while(iteratorAll.hasNext()){
			Integer all=iteratorAll.next();
			for(Integer i:inflowList){
				if(all.equals(i)){
					iteratorAll.remove();
					continue;
				}
			}
			for(Integer i:outflowList){
				if(all.equals(i)){
					iteratorAll.remove();
					continue;
				}
			}
		}
		parMap.put("list", allList);
		List<PieChartDto> list=CollectionKit.newList();
		List<PieChartDto> ocList = talentProfitLossDao.queryInflowOutflowChangeType(parMap);
		List<PieChartDto> jcList = talentProfitLossDao.queryInflowOutflowChangeTypeByJc(parMap);
		Iterator<PieChartDto> iterator=ocList.iterator();
		while(iterator.hasNext()){
			if(iterator.next().getValue()==0){
				iterator.remove();
			}
		}
		Iterator<PieChartDto> iteratorJc=jcList.iterator();
		while(iteratorJc.hasNext()){
			if(iteratorJc.next().getValue()==0){
				iteratorJc.remove();
			}
		}
		list.addAll(ocList);
		list.addAll(jcList);
		int total=0;
		for(PieChartDto dto:list){
			total+=dto.getValue();
		}
		for(PieChartDto dto:list){
			dto.setRate(dto.getValue()/total*100);
		}
		map.put("list", new Sort<PieChartDto>().desc(list));
		map.put("total", total);
		return map;
	}


	/**
	 * 异动统计-入职名单
	 */
	@Override
	public PaginationDto<TalentProfitLossDto> queryEntryListDatas(String customerId, String organId,boolean input, String time,
			List<Integer> parentList,  String type, int page, int rows) {
		PaginationDto<TalentProfitLossDto> dto = new PaginationDto<TalentProfitLossDto>(page, rows);
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("input", input);
		map.put("time", time);
		if(type==null||type.equals("")){
			map.put("list", parentList);
			map.put("type", null);
		}else{
			map.put("type", type);
			map.put("list", null);
		}
		map.put("start", dto.getOffset());
		map.put("rows", dto.getLimit());
		int count = talentProfitLossDao.queryEntryListDatasCount(map);
		List<TalentProfitLossDto> dtoList = talentProfitLossDao.queryEntryListDatas(map);
		int records = integerIsNull(count) > 0 ? count : 1;
		dto.setRecords(records);
		dto.setRows(dtoList);
		return dto;
	}

	/**
	 * 盘点integer是否为空
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

}
