package net.chinahrd.salesBoard.mvc.pc.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardQueryDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesConfigDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsConfigAxisDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsConfigDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsPointDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsSimpleCountDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesSortDescDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesSortDto;
import net.chinahrd.salesBoard.mvc.pc.dao.SalesBoardDao;
import net.chinahrd.salesBoard.mvc.pc.service.SalesBoardService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.PropertiesUtil;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
@Service("salesBoardService")
public class SalesBoardServiceImpl implements SalesBoardService {

	@Autowired
	private SalesBoardDao salesBoardDao;

	@Override
	public List<SalesBoardDto> querySalesProducts(String customerId) {
		return salesBoardDao.querySalesProducts(customerId);
	}

	/**
	 * 获取时间
	 */
	@Override
	public Map<String, Object> queryTimes(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<SalesBoardDto> list = salesBoardDao.queryTimes(customerId, organId);
		String[] selectDate = new String[1];
		String[] selectYear = new String[1];
		selectDate[0] = DateUtil.getDBNow().substring(0, 7);
		selectYear[0] = DateUtil.getDBNow().substring(0, 4);
		String minDate = "", maxDate = "";
		if (list != null && list.size() > 0) {
			String min = list.get(0).getDate();
			String max = list.get(list.size() - 1).getDate();
			minDate = min.substring(0, 4) + "-" + min.substring(4);
			maxDate = max.substring(0, 4) + "-" + max.substring(4);
		}
		map.put("minDate", minDate);
		map.put("maxDate", maxDate);
		map.put("selectDate", selectDate);
		map.put("selectYear", selectYear);
		return map;
	}

	/**
	 * 查询预警设置值
	 */
	@Override
	public Map<String, Object> queryWarningInfo(String customerId, String empId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<SalesConfigDto> list = salesBoardDao.queryWarningInfo(customerId, empId);
		int num = 0;
		String names = "", yellowRanges = "", redRanges = "", notes = "";
		if (list != null && list.size() > 0) {
			num = 1;
			for (SalesConfigDto l : list) {
				if(!"".equals(names) && names != null){
					names += ",";
				}
				names += l.getRegion();
				if(!"".equals(yellowRanges) && yellowRanges != null){
					yellowRanges += "#";
				}
				yellowRanges += l.getYellowRange();
				if(!"".equals(redRanges) && redRanges != null){
					redRanges += ",";
				}
				redRanges += l.getRedRange();
				if(!"".equals(notes) && notes != null){
					notes += ",";
				}
				notes += l.getNote();
			}
		}
		map.put("names", names);
		map.put("yellowRanges", yellowRanges);
		map.put("redRanges", redRanges);
		map.put("notes", notes);
		map.put("num", num);
		map.put("list", list);
		return map;
	}

	/**
	 * 修改预警设置值
	 */
	@Override
	public void updateWarningInfo(String customerId, String empId, SalesConfigDto dto) {
		if (dto.getNames() != null && !"".equals(dto.getNames())) {
			String[] names = dto.getNames().split("@");
			String[] yellows = dto.getYellows().split("@");
			String[] reds = dto.getReds().split("@");
			List<SalesConfigDto> list = salesBoardDao.queryWarningInfo(customerId, empId);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < names.length; i++) {
					SalesConfigDto scd = new SalesConfigDto();
					scd.setEmpId(empId);
					scd.setCustomerId(customerId);
					scd.setRegion(names[i]);
					scd.setYellowRange(yellows[i]);
					scd.setRedRange(reds[i]);
					scd.setNote(names[i]);
					try {
						salesBoardDao.updateWarningInfo(scd);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				salesBoardDao.deleteWarningInfo(customerId, empId);
				for (int i = 0; i < names.length; i++) {
					SalesConfigDto scd = new SalesConfigDto();
					scd.setEmpId(empId);
					scd.setCustomerId(customerId);
					scd.setRegion(names[i]);
					scd.setYellowRange(yellows[i]);
					scd.setRedRange(reds[i]);
					scd.setNote(names[i]);
					try {
						salesBoardDao.addWarningInfo(scd);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 当月销售额及比较
	 */
	@Override
	public Map<String, Object> getCurMonthSalesVal(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		String befDate = null;
		try {
			befDate = df.format(getBeforeMonth(df.parse(yearMonth), -1));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		List<SalesBoardDto> list = salesBoardDao.getCurMonthSalesVal(customerId, organId, yearMonth, befDate, subOrganIdList);
		double sumNum = 0, befSumNum = 0;
		double percentNum = 0;
		if (list != null && list.size() > 0) {
			sumNum = formatDouble(list.get(0).getSumNum());
			befSumNum = formatDouble(list.get(0).getBefSumNum());
			if(befSumNum > 0){
				percentNum = formatDouble(list.get(0).getPercentNum() * 100);
			} else if(sumNum > 0){
				percentNum = 100.0;
			}
		}
		map.put("sumNum", sumNum);
		map.put("befSumNum", befSumNum);
		map.put("percentNum", percentNum);
		return map;
	}

	/**
	 * 当月销售异常组织
	 */
	@Override
	public Map<String, Object> getCurMonthSalesExctOrgan(String customerId, String organId, String yellowRange,
			String redRange) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
		List<String> organIdList = CollectionKit.newLinkedList();
		if(underSubOrganIdList != null && underSubOrganIdList.size() > 0){
			for(String fl : underSubOrganIdList){
				organIdList.add(fl.split(",")[0]);
			}
		}
		List<SalesBoardDto> list = CollectionKit.newLinkedList();
		List<SalesBoardDto> lastList = CollectionKit.newLinkedList();
		int conNum = 0;
		String organName = "", percentNum = "";
		List<Double> pList = CollectionKit.newLinkedList();
		if(organIdList != null && organIdList.size() > 0){
			String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			String befDate = null;
			try {
				befDate = df.format(getBeforeMonth(df.parse(yearMonth), -1));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			Map<String, Object> parMap = CollectionKit.newMap();
			parMap.put("customerId", customerId);
			parMap.put("curDate", yearMonth);
			parMap.put("befDate", befDate);
			for(String org : organIdList){
				List<String> subOrganIdList = CacheHelper.getSubOrganIdList(org);
				parMap.put("organId", org);
				parMap.put("subOrganIdList", subOrganIdList);
				if (redRange != null && !"".equals(redRange)) {
					parMap.put("red", -(Double.parseDouble(redRange) / 100));
					list = salesBoardDao.getCurMonthSalesExctOrganByRed(parMap);
					lastList.addAll(list);
				}
				if (list == null || list.size() == 0) {
					if (yellowRange != null && !"".equals(yellowRange) && yellowRange.indexOf(",") != -1) {
						String[] yelArr = yellowRange.split(",");
						String minYel = yelArr[0];
						String maxYel = yelArr[1];
						parMap.put("minYel", -(Double.parseDouble(maxYel) / 100));
						parMap.put("maxYel", -(Double.parseDouble(minYel) / 100));
						list = salesBoardDao.getCurMonthSalesExctOrganByYellow(parMap);
						lastList.addAll(list);
					}
				}
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						if (organName != null && !"".equals(organName)) {
							organName += ",";
						}
						organName += list.get(i).getOrganName();
						pList.add(formatDouble(list.get(i).getPercentNum() * 100));
					}
					conNum++;
				}
			}
			if(pList != null && pList.size() > 0){
				Double[] newArr = pList.toArray(new Double[pList.size()]);
				Arrays.sort(newArr);
				percentNum = newArr[newArr.length - 1] + "," + newArr[0];
			}
		}
		map.put("conNum", conNum);
		map.put("organName", organName);
		map.put("percentNum", percentNum);
		map.put("list", lastList);
		return map;
	}

	/**
	 * 人员流失异常
	 */
	@Override
	public Map<String, Object> getCurMonthPersonFlow(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		String date = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		List<SalesBoardDto> list = salesBoardDao.getCurMonthPersonFlow(customerId, organId, date);
		double sumNum = 0D;
		if (list != null && list.size() > 0) {
			sumNum = list.get(0).getSumNum() * 100;
		}
		map.put("sumNum", sumNum);
		return map;
	}

	/**
	 * 业务能力考核
	 */
	@Override
	public Map<String, Object> getCurMonthAbilityCheck(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		List<SalesBoardDto> list = salesBoardDao.getCurMonthAbilityCheck(customerId, subOrganIdList);
		double percentNum = 0D;
		if (list != null && list.size() > 0) {
			percentNum = list.get(0).getPercentNum() * 100;
		}
		map.put("percentNum", percentNum);
		return map;
	}

	/**
	 * 查询年销售额
	 */
	public Map<String, Object> querySalesAndTarget(String customerId, String organId, int time, String productId,
			Integer salesType) {
		Map<String, Object> map = CollectionKit.newMap();
		Map<String, Object> param = CollectionKit.newMap();
		List<SalesBoardDto> ratioList = CollectionKit.newList(); // 环比
		List<SalesBoardDto> basisList = CollectionKit.newList(); // 同比
		List<SalesBoardDto> beforeBasis = CollectionKit.newList();
		int beginTime = (time - 1) * 100 + 1;
		int endTime = time * 100 + 12;
		param.put("customerId", customerId);
		param.put("organId", organId);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("salesType", salesType);
		param.put("productId", productId);
		List<SalesBoardDto> list = CollectionKit.newList();
		if (productId != null && !"".equals(productId.trim()))
			list = salesBoardDao.querySalesByProduct(param);
		else
			list = salesBoardDao.querySalesAndTarget(param);
		double sales = 0D;
		double target = 0D;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SalesBoardDto dto = list.get(i);
				if(dto.getYearMonth() >= time * 100 + 1){
					sales += dto.getSales();
					target += dto.getTarget();
				}
				if (dto.getYearMonth() >= time * 100 + 1) {
					if(i > 0){
						SalesBoardDto before = list.get(i - 1);
						double ratio = before.getSales() == 0 ? 0
								: (dto.getSales() - before.getSales()) / before.getSales();
						dto.setRatio(ratio * 100 + "");
					} else{
						dto.setRatio("");
					}
					ratioList.add(dto);
				}
				if (dto.getYearMonth() < time * 100 + 1) {
					beforeBasis.add(dto);
				}
			}
			for (int i = 0; i < ratioList.size(); i++) {
				SalesBoardDto dto = ratioList.get(i);
				if(beforeBasis != null && beforeBasis.size() == ratioList.size()){
					SalesBoardDto before = beforeBasis.get(i);
					if (dto.getYearMonth() == (before.getYearMonth() + 100)) {
						double basis = before.getSales() == 0 ? 0
								: (dto.getSales() - before.getSales()) / before.getSales();
						dto.setBasis(basis * 100 + "");
						basisList.add(dto);
					}
				} else {
					dto.setBasis("");
					basisList.add(dto);
				}
			}
		}
		map.put("basisList", basisList);
		map.put("sales", sales);
		map.put("target", target);
		return map;
	}

	/**
	 * 查询团队销售额和达标率
	 */
	public PaginationDto<SalesBoardDto> queryTeamSalesAndTarget(String customerId, String organId, int yearMonth,
			Integer teamStandardRate, Double beginSales, Double endSales, Double beginReturnAmount,
			Double endReturnAmount, PaginationDto<SalesBoardDto> dto) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		String befYearMonth = null;
		try {
			befYearMonth = df.format(getBeforeMonth(df.parse(yearMonth + ""), -1));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("subOrganIdList", subOrganIdList);
		map.put("yearMonth", yearMonth);
		map.put("befYearMonth", befYearMonth);
		map.put("teamStandardRate", teamStandardRate);
		map.put("beginSales", beginSales);
		map.put("endSales", endSales);
		map.put("beginReturnAmount", beginReturnAmount);
		map.put("endReturnAmount", endReturnAmount);
		map.put("offset", dto.getOffset());
		map.put("limit", dto.getLimit());
		int count = salesBoardDao.queryTeamSalesAndTargetCount(map);
		List<SalesBoardDto> list = salesBoardDao.queryTeamSalesAndTarget(map);
		dto.setRecords(count);
		dto.setRows(list);
		return dto;
	}

	/**
	 * 查询个人销售额和达标率
	 */
	public PaginationDto<SalesBoardDto> queryPersonSalesAndTarget(String customerId, String organId, int yearMonth,
			String keyName, Integer personStandard, Double beginPersonSales, Double endPersonSales,
			Double beginPersonAmount, Double endPersonAmount, PaginationDto<SalesBoardDto> dto) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		String beforeYearMonth = null;
		try {
			beforeYearMonth = df.format(getBeforeMonth(df.parse(yearMonth + ""), -1));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("yearMonth", yearMonth);
		map.put("beforeYearMonth", beforeYearMonth);
		map.put("keyName", keyName);
		map.put("personStandard", personStandard);
		map.put("beginPersonSales", beginPersonSales);
		map.put("endPersonSales", endPersonSales);
		map.put("beginPersonAmount", beginPersonAmount);
		map.put("endPersonAmount", endPersonAmount);
		map.put("offset", dto.getOffset());
		map.put("limit", dto.getLimit());
		int count = salesBoardDao.queryPersonSalesAndTargetCount(map);
		List<SalesBoardDto> list = salesBoardDao.queryPersonSalesAndTarget(map);
		dto.setRecords(count);
		dto.setRows(list);
		return dto;
	}

	/**
	 * 今日销售量/今日销售额/今日利润
	 */
	@Override
	public Map<String, Object> queryTodaySalesInfo(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		String date = DateUtil.getDBNow().substring(0, 10);
		List<SalesBoardDto> list = salesBoardDao.queryTodaySalesInfo(customerId, organId, date, subOrganIdList);
		int numberNum = 0;
		double moneyNum = 0D, profitNum = 0D;
		if (list != null && list.size() > 0) {
			for (SalesBoardDto l : list) {
				numberNum = l.getNumberNum();
				moneyNum = formatDouble(l.getMoneyNum());
				profitNum = formatDouble(l.getProfitNum());
			}
		}
		map.put("numberNum", numberNum);
		map.put("moneyNum", moneyNum);
		map.put("profitNum", profitNum);
		return map;
	}

	/**
	 * 今日销售量/今日销售额/今日利润-地图显示数据
	 */
	@Override
	public Map<String, Object> querySalesMapByProvince(String customerId, String organId, String provinceId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<Object> pointData = CollectionKit.newLinkedList();
		List<Object> seriesData = CollectionKit.newLinkedList();
		List<SalesBoardDto> list = CollectionKit.newList();
		List<SalesBoardDto> proList = salesBoardDao.queryProvinceInfo(customerId);
		if (proList != null && proList.size() > 0) {
			for (SalesBoardDto l : proList) {
				if(l != null){
					Map<String, Object> oMap = CollectionKit.newMap();
					oMap.put("id", l.getId());
					oMap.put("name", l.getName());
					oMap.put("value", 0);
					seriesData.add(oMap);
				}
			}
		}
		String date = DateUtil.getDBNow().substring(0, 10);
		if (provinceId != null && !"".equals(provinceId)) {
			list = salesBoardDao.querySalesMapByCity(customerId, provinceId, date);
		} else {
			list = salesBoardDao.querySalesMapByProvince(customerId, date);
		}
		List<Double> dList = CollectionKit.newLinkedList();
		if (list != null && list.size() > 0) {
			for (SalesBoardDto l : list) {
				if(l != null){
					Map<String, Object> oMap = CollectionKit.newMap();
					oMap.put("id", l.getId());
					oMap.put("name", l.getName());
					oMap.put("value", formatDouble(l.getMoneyNum()));
					pointData.add(oMap);
					dList.add(l.getMoneyNum());
				}
			}
		}
		double minNum = 0, maxNum = 0;
		if (dList != null && dList.size() > 0) {
			Double[] d = dList.toArray(new Double[dList.size()]);
			Arrays.sort(d);
			minNum = d[0];
			maxNum = d[d.length - 1];
		}
		map.put("seriesData", seriesData);
		map.put("pointData", pointData);
		map.put("minNum", minNum);
		map.put("maxNum", maxNum);
		return map;
	}

	/**
	 * 组织销售统计
	 */
	@Override
	public Map<String, Object> queryOrganSalesStatistics(String customerId, String organId, String date, int flag) {
		Map<String, Object> map = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (date != null && !"".equals(date)) {
			yearMonth = date.replaceAll("-", "");
		}
		List<String> names = CollectionKit.newLinkedList();
		List<SalesSortDescDto> datas1 = CollectionKit.newLinkedList();
		List<SalesSortDto> datas2 = CollectionKit.newLinkedList();
		List<Double> numList = CollectionKit.newLinkedList();
		double num = 0D;
		if (flag == 0) {
			List<SalesBoardDto> organList = salesBoardDao.queryNextOrganInfo(customerId, organId);
			if (organList != null && organList.size() > 0) {
				for (SalesBoardDto ol : organList) {
					List<String> subOrganIdList = CacheHelper.getSubOrganIdList(ol.getOrganId());
					List<SalesBoardDto> list = salesBoardDao.queryOrganSalesStatistics(customerId, ol.getOrganId(),
							yearMonth, subOrganIdList);
					if (list != null && list.size() > 0) {
						if (list.get(0).getSumNum() > 0) {
							if (list.get(0).getSumNum() > num) {
								num = formatDouble(list.get(0).getSumNum());
							}
							numList.add(formatDouble(list.get(0).getSumNum()));
						} else {
							numList.add(0D);
						}
					}
				}
				num = formatDouble(num * 1.1);
				for (int i = 0; i < numList.size(); i++) {
					SalesSortDescDto dto = new SalesSortDescDto();
					dto.setId(organList.get(i).getOrganId());
					dto.setName(organList.get(i).getOrganName());
					dto.setValue(numList.get(i));
					datas1.add(dto);
					SalesSortDto dto1 = new SalesSortDto();
					dto1.setId(organList.get(i).getOrganId());
					dto1.setName(organList.get(i).getOrganName());
					dto1.setValue(ArithUtil.sub(num, numList.get(i)));
					datas2.add(dto1);
					// names.add(organList.get(i).getOrganName());
				}
				Collections.sort(datas1);
				Collections.sort(datas2);
				for (SalesSortDto s : datas2) {
					names.add(s.getName());
				}
			}
		} else if (flag == 1) {
			List<SalesBoardDto> list = salesBoardDao.queryOrganSalesStatisticsWithTeam(customerId, organId, yearMonth);
			if (list != null && list.size() > 0) {
				num = formatDouble(list.get(0).getSumNum() * 1.1);
				for (int i = 0; i < list.size(); i++) {
					names.add(list.get(i).getName());
					SalesSortDescDto dto = new SalesSortDescDto();
					dto.setId(list.get(i).getId());
					dto.setName(list.get(i).getName());
					dto.setValue(formatDouble(list.get(i).getSumNum()));
					datas1.add(dto);
					SalesSortDto dto1 = new SalesSortDto();
					dto1.setId(list.get(i).getId());
					dto1.setName(list.get(i).getName());
					dto1.setValue(ArithUtil.sub(num, formatDouble(list.get(i).getSumNum())));
					datas2.add(dto1);
				}
//				Collections.sort(datas1);
//				Collections.sort(datas2);
			}
		} else if (flag > 1) {
			List<SalesBoardDto> list = salesBoardDao.queryOrganSalesStatisticsWithEmp(customerId, organId, yearMonth);
			num = formatDouble(list.get(0).getSumNum() * 1.1);
			for (int i = 0; i < list.size(); i++) {
				names.add(list.get(i).getName());
				SalesSortDescDto dto = new SalesSortDescDto();
				dto.setId(list.get(i).getId());
				dto.setName(list.get(i).getName());
				dto.setValue(formatDouble(list.get(i).getSumNum()));
				datas1.add(dto);
				SalesSortDto dto1 = new SalesSortDto();
				dto1.setId(list.get(i).getId());
				dto1.setName(list.get(i).getName());
				dto1.setValue(ArithUtil.sub(num, formatDouble(list.get(i).getSumNum())));
				datas2.add(dto1);
			}
		}
		map.put("names", names.toArray(new String[names.size()]));
		map.put("datas1", datas1.toArray(new Object[datas1.size()]));
		map.put("datas2", datas2.toArray(new Object[datas2.size()]));
		return map;
	}

	/**
	 * 销售排名榜
	 */
	@Override
	public Map<String, Object> querySalesRanking(String customerId, String organId, String date) {
		Map<String, Object> map = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (date != null && !"".equals(date)) {
			yearMonth = date.replaceAll("-", "");
		}
		int count = salesBoardDao.querySalesRankingCount(customerId, organId, yearMonth);
		List<SalesBoardDto> list = salesBoardDao.querySalesRanking(customerId, organId, yearMonth);
		if (list != null && list.size() > 0) {
			for (SalesBoardDto l : list) {
				l.setSumNum(formatDouble(l.getSumNum()));
			}
		}
		map.put("count", count);
		map.put("list", list);
		return map;
	}

	/**
	 * 查询下级组织机构
	 */
	public List<SalesBoardDto> querySubOrganization(String customerId, String organId) {
		return salesBoardDao.querySubOrganization(customerId, organId);
	}

	/**
	 * 查询能力趋势
	 */
	public Map<String, Object> queryAbilityByEmpid(String customerId, String empId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> yData = CollectionKit.newLinkedList();
		List<Integer> yVal = CollectionKit.newLinkedList();
		List<SalesBoardDto> abilityList = salesBoardDao.queryAbilityInfo(customerId);
		if (abilityList != null && abilityList.size() > 0) {
			int num = 1;
			for (SalesBoardDto l : abilityList) {
				yData.add(l.getName());
				yVal.add(num);
				num++;
			}
//            for (int i = abilityList.size() - 1; i >= 0; i--) {
//                yData.add(abilityList.get(i).getName());
//                yVal.add(num);
//                num++;
//            }
		}
		List<SalesBoardDto> list = salesBoardDao.queryAbilityByEmpid(customerId, empId);
		map.put("yData", yData.toArray(new String[yData.size()]));
		map.put("yVal", yVal.toArray(new Integer[yVal.size()]));
		map.put("list", list);
		return map;
	}

	/**
	 * 查询排名趋势
	 */
	public Map<String, Object> queryRankByEmpid(String customerId, String organId, String empId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> yData = CollectionKit.newLinkedList();
		List<Integer> yVal = CollectionKit.newLinkedList();
		List<SalesBoardDto> abilityList = salesBoardDao.queryRankChartInfo(customerId);
		int num = 1;
		if (abilityList != null && abilityList.size() > 0) {
			for (SalesBoardDto l : abilityList) {
				yData.add(l.getName());
				yVal.add(num);
				num++;
			}
		}
		List<SalesBoardDto> list = salesBoardDao.queryRankByEmpid(customerId, organId, empId);
		map.put("yData", yData.toArray(new String[yData.size()]));
		map.put("yVal", yVal.toArray(new Integer[yVal.size()]));
		map.put("list", list);
		return map;
	}

	/**
	 * 查询团队时间
	 */
	public List<SalesBoardDto> queryTeamTime(String customerId) {
		return salesBoardDao.queryTeamTime(customerId);
	}

	/**
	 * 查询组织销售额
	 */
	public List<SalesBoardDto> querySalesByOrgan(String customerId, String organId, String now, Integer salesType) {
		Map<String, Object> map = CollectionKit.newMap();
		String beginTime = getLastYear(now);
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("beginTime", beginTime);
		map.put("endTime", now);
		map.put("salesType", salesType);
		return salesBoardDao.querySalesByOrgan(map);
	}

	/**
	 * 查询销售人员异动情况
	 */
	public PaginationDto<SalesBoardDto> queryEmpChangeInfo(PaginationDto<SalesBoardDto> dto, String customerId,
			String organId, String userName, String time) {
		int offset = dto.getOffset();
		int limit = dto.getLimit();
		List<Integer> putList = CollectionKit.newLinkedList();
		List<Integer> inputList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_INPUT);
		List<Integer> outputList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_OUTPUT);
		putList.addAll(inputList);
		putList.addAll(outputList);
		String beginTime = getLastYear(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		try {
			beginTime = df.format(getBeforeMonth(df.parse(time.substring(0, 7)), -6)) + time.substring(7, 10);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("userName", userName);
		map.put("beginTime", beginTime);
		map.put("endTime", time);
		map.put("putList", putList);
		map.put("offset", offset);
		map.put("limit", limit);
		List<SalesBoardDto> list = salesBoardDao.queryEmpChangeInfo(map);
		int count = salesBoardDao.queryEmpChangeInfoCount(map);
		dto.setRecords(count);
		dto.setRows(list);
		return dto;
	}

	public List<Object> querySalesByEmpid(String customerId, String organId, String now, Integer salesType,
			String[] empArr, String[] changeTypeArr) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		Map<Object, Object> map = CollectionKit.newMap();
		String time = now.substring(0, 10);
		String beginTime = now.substring(0, 7);
//		String beginTime = getLastYear(now);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		try {
			beginTime = df.format(getBeforeMonth(df.parse(beginTime), -11)) + "-01";
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		map.put("customerId", customerId);
		map.put("organId", organId);
		map.put("salesType", salesType);
		map.put("beginTime", beginTime);
		map.put("endTime", time);
		map.put("subOrganIdList", subOrganIdList);
		List<Object> li = CollectionKit.newList();
		int num = 0;
		for (int i = 0; i < empArr.length; i++) {
			map.put("empId", empArr[i]);
			List<SalesBoardDto> list = salesBoardDao.querySalesByEmpid(map);
			if(list != null && list.size() > 0){
				li.add(num, list);
				num++;
			}
		}
		return li;
	}

	/**
	 * 人员变动-销售额
	 */
	@Override
	public Map<String, Object> queryChangeByMonth(String customerId, String organId, String time, Integer salesType) {
		Map<String, Object> map = CollectionKit.newMap();
		String day = time.substring(0, 10);
		String begin = getLastYear(time).substring(0, 4) + getLastYear(time).substring(5, 7);
		String beginTime = DateUtil.getBeforeYear("yyyyMM", 1);
		String endTime = DateUtil.getDBTime("yyyyMM");
		Map<Integer, Integer> mapTime = getMapTime(begin, endTime);
		LinkedList<SalesBoardDto> list = salesBoardDao.queryChangeByMonth(customerId, organId, beginTime, endTime, salesType);
		List<Integer> inputList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_INPUT);
		List<Integer> outputList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_OUTPUT);
		List<SalesBoardDto> list1 = CollectionKit.newList();
		List<SalesBoardDto> list2 = CollectionKit.newList();
		int num = 0;
		if(list != null && list.size() > 0){
			if(!beginTime.equals(list.get(0).getYearMonth() + "")){
				SalesBoardDto dto = new SalesBoardDto();
				dto.setYearMonth(Integer.parseInt(beginTime));
				dto.setSales(0d);
				list.addFirst(dto);
			}
			Map<String, Object> pMap = CollectionKit.newMap();
			pMap.put("customerId", customerId);
			pMap.put("organId", organId);
			pMap.put("day", day);
			pMap.put("minYearMonth", beginTime);
			pMap.put("maxYearMonth", endTime);
			pMap.put("inputList", inputList);
			pMap.put("outputList", outputList);
			List<SalesBoardDto> personCountList = salesBoardDao.queryPersonChangeCount(pMap);
			Map<String, Integer> personMap = CollectionKit.newMap();
			if(personCountList != null && personCountList.size() > 0){
				for(SalesBoardDto l : personCountList){
					personMap.put(l.getDate(), l.getConNum());
				}
			}
			for (int i = 0; i < list.size(); i++) {
				SalesBoardDto d = list.get(i);
				double sales = 0D;
				double nextSales = 0D;
				if (i < list.size() - 1) {
					sales = d.getSales();
					nextSales = list.get(i + 1).getSales();
					for (int j = 0; j < 5; j++) {
						int inputCount = 0, outputCount = 0;
						if (num % 5 != 0){
							if(personMap != null && personMap.size() > 0){
								inputCount = personMap.get(mapTime.get(num) + "i") == null ? 0 : personMap.get(mapTime.get(num) + "i");
								outputCount = personMap.get(mapTime.get(num) + "o") == null ? 0 : personMap.get(mapTime.get(num) + "o");
							}
						}
						switch (j) {
						case 1:
							SalesBoardDto dto1 = new SalesBoardDto();
							dto1.setConNum(num);
							if (inputCount > 0) {
								dto1.setSales(sales + (nextSales - sales) / 5);
							}
							list1.add(dto1);
							num++;
							break;
						case 2:
							SalesBoardDto dto2 = new SalesBoardDto();
							dto2.setConNum(num);
							if (outputCount > 0) {
								dto2.setSales(sales + (nextSales - sales) / 5 * 2);
							}
							list2.add(dto2);
							num++;
							break;
						case 3:
							SalesBoardDto dto3 = new SalesBoardDto();
							dto3.setConNum(num);
							if (inputCount > 0) {
								dto3.setSales(sales + (nextSales - sales) / 5 * 3);
							}
							list1.add(dto3);
							num++;
							break;
						case 4:
							SalesBoardDto dto4 = new SalesBoardDto();
							dto4.setConNum(num);
							if (outputCount > 0) {
								dto4.setSales(sales + (nextSales - sales) / 5 * 4);
							}
							list2.add(dto4);
							num++;
							break;
						default:
							num++;
							break;
						}
					}
				}
			}
		}
		map.put("list", list);
		map.put("list1", list1);
		map.put("list2", list2);
		return map;
	}
	
	/**
	 * 人员变动-异动情况
	 */
	public List<SalesBoardDto> queryChangeInfo(String customerId, String organId, String time, 
			Integer flag, String index, Integer salesType) {
		String beginTime = getLastYear(time).substring(0, 4) + getLastYear(time).substring(5, 7);
		String endTime = time.substring(0, 4) + time.substring(5, 7);
		String maxTime = time.substring(0, 10);
		Map<Integer, Integer> mapTime = getMapTime(beginTime, endTime);
		List<Integer> inputList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_INPUT);
		List<Integer> outputList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.XSKB_OUTPUT);
		List<Integer> putList = CollectionKit.newLinkedList();
		String inputOutput = "";
		if("2".equals(index)){
			putList = inputList;
			inputOutput = "input";
		} else {
			putList = outputList;
			inputOutput = "output";
		}
		Integer yearMonths = mapTime.get(flag);
		String yearMonth = null;
		if(yearMonths != null){
			yearMonth = yearMonths.toString().substring(0, 6);
		}
		Map<String, Object> pMap = CollectionKit.newMap();
		pMap.put("customerId", customerId);
		pMap.put("organId", organId);
		pMap.put("yearMonth", yearMonth);
		pMap.put("time", mapTime.get(flag));
		pMap.put("maxTime", maxTime);
		pMap.put("inputOutput", inputOutput);
		pMap.put("putList", putList);
		pMap.put("salesType", salesType);
		return salesBoardDao.queryChangeInfo(pMap);
	}

	public Map<Integer, Integer> getMapTime(String beginTime, String endTime) {
		Map<Integer, Integer> map = CollectionKit.newMap();
		int begin = Integer.parseInt(beginTime);
		int end = Integer.parseInt(endTime);
		int temp = begin;
		int num = 0;
		while (temp <= end) {
			for (int j = 0; j < 5; j++) {
				switch (j) {
				case 1:
					map.put(num, temp * 10);
					num++;
					break;
				case 2:
					map.put(num, temp * 10);
					num++;
					break;
				case 3:
					map.put(num, temp * 10 + 1);
					num++;
					break;
				case 4:
					map.put(num, temp * 10 + 1);
					num++;
					break;
				default:
					num++;
					break;
				}
			}
			if (temp % 100 < 12) {
				temp++;
			} else if (temp % 100 == 12) {
				temp = temp + 100 - 11;
			}
		}
		return map;
	}

	/**
	 * 获取一年前的时间
	 */
	public String getLastYear(String time) {
		String year = time.substring(0, 4);
		String month = time.substring(5, 7);
		String retTime = time;
		if (Integer.parseInt(month) == 12) {
			retTime = year + "-" + "01-01";
		} else {
			retTime = Integer.parseInt(year) - 1 + "-" + (Integer.parseInt(month) + 1) + "-01";
		}
		return retTime;
	}

	/**
	 * 销售排名地图-团队PK
	 */

	@Override
	public Map<String, Object> querySalesRankMapTeamPK(String customerId, String organId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<SalesBoardDto> list = salesBoardDao.queryNextOrganInfo(customerId, organId);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		List<SalesSortDescDto> finalList = CollectionKit.newList();
		if (list != null && list.size() > 0) {
			for (SalesBoardDto l : list) {
				SalesSortDescDto dto = new SalesSortDescDto();
				dto.setOrganId(l.getOrganId());
				dto.setOrganName(l.getOrganName());
				int conNum = salesBoardDao.querySalesRankMapTeamPK(customerId, l.getOrganId(), yearMonth);
				dto.setConNum(conNum);
				finalList.add(dto);
			}
			Collections.sort(finalList);
		}
		map.put("list", finalList);
		return map;
	}

	/**
	 * 销售排名地图-团队总览chart
	 */
	@Override
	public List<SalesMapsSimpleCountDto> queryTeamViewChart(String customerId, String organId, String date) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (date != null && !"".equals(date)) {
			yearMonth = date.replaceAll("-", "");
		}
		return salesBoardDao.queryTeamViewChart(customerId, organId, yearMonth, subOrganIdList);
	}
	
	/**
	 * 销售排名地图-团队总览全屏chart
	 */
	@Override
	public List<SalesBoardDto> queryTeamViewFullChart(String customerId, String organId, String date, String rows) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (date != null && !"".equals(date)) {
			yearMonth = date.replaceAll("-", "");
		}
		Integer row = null;
		if (rows != null && !"".equals(rows)) {
			row = Integer.parseInt(rows);
		}
		return salesBoardDao.queryTeamViewFullChart(customerId, organId, yearMonth, row, subOrganIdList);
	}

	/**
	 * 销售排名地图-团队PKchart
	 */
	@Override
	public List<SalesMapsPointDto> queryTeamPKChart(SalesBoardQueryDto dto) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (dto.getYearMonth() != null && !"".equals(dto.getYearMonth())) {
			yearMonth = dto.getYearMonth().replaceAll("-", "");
		}
		Integer row = null;
		if (dto.getRows() != null) {
			row = dto.getRows();
		}
		dto.setYearMonth(yearMonth);
		dto.setRows(row);
		dto.setSubOrganIdList(subOrganIdList);
		dto.setPerformanceList(CollectionKit.strToList(dto.getPerformanceStr(), ","));
		dto.setAgeList(dto.getAgeIntervals());
		dto.setSexList(CollectionKit.strToList(dto.getSexStr(), ","));
		dto.setEduList(CollectionKit.strToList(dto.getEduStr(), ","));
		return salesBoardDao.queryTeamPKChart(dto);
	}
	
	/**
	 * 销售排名地图-团队PK获取员工点信息
	 */
	@Override
	public List<SalesMapsPointDto> queryTeamEmpPoint(SalesBoardQueryDto dto) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (dto.getYearMonth() != null && !"".equals(dto.getYearMonth())) {
			yearMonth = dto.getYearMonth().replaceAll("-", "");
		}
		dto.setYearMonth(yearMonth);
		dto.setSubOrganIdList(subOrganIdList);
		dto.setPerformanceList(CollectionKit.strToList(dto.getPerformanceStr(), ","));
		dto.setAgeList(dto.getAgeIntervals());
		dto.setSexList(CollectionKit.strToList(dto.getSexStr(), ","));
		dto.setEduList(CollectionKit.strToList(dto.getEduStr(), ","));
		return salesBoardDao.queryTeamEmpPoint(dto);
	}

	/**
	 * 销售排名地图-获取x轴y轴信息
	 */
	@Override
	public Map<String, Object> getMapsBaseInfo(String customerId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<SalesBoardDto> abilitys = salesBoardDao.queryAbilityInfo(customerId);
		List<SalesBoardDto> perfs = salesBoardDao.queryRankChartInfo(customerId);
		List<String> aList = CollectionKit.newLinkedList();
		int xNum = 1, yNum = 1;
		if (abilitys != null && abilitys.size() > 0) {
			yNum = abilitys.size();
//			Collections.sort(abilitys);
			for (SalesBoardDto a : abilitys) {
				aList.add(a.getName());
			}
		}
		if (perfs != null && perfs.size() > 0) {
			xNum = perfs.size();
		}
		List<String> fullList = CollectionKit.newLinkedList();
		List<String> yelList = CollectionKit.newLinkedList();
		for (int i = 0; i < xNum; i++) {
			for (int j = 0; j < yNum; j++) {
				String s = (i + 1) + "," + (j + 1);
				fullList.add(s);
			}
		}
		String color = PropertiesUtil.getProperty(ConfigKeyUtil.XSKB_PZ).trim();
		List<SalesBoardDto> configList = salesBoardDao.queryMapsConfigInfo(customerId, color);
		List<SalesMapsConfigDto> configDtos = CollectionKit.newList();
		if (configList != null && configList.size() > 0) {
			for (SalesBoardDto l : configList) {
				String configVal = l.getConfigVal();
				yelList.add(configVal);
				if (configVal.indexOf(",") != -1) {
					String[] vals = configVal.split(",");
					if (xNum >= Integer.parseInt(vals[0]) && yNum >= Integer.parseInt(vals[1])) {
						List<SalesMapsConfigAxisDto> configAxis = CollectionKit.newList();
						SalesMapsConfigAxisDto axisDto = new SalesMapsConfigAxisDto();
						axisDto.setX(vals[0]);
						axisDto.setY(vals[1]);
						configAxis.add(axisDto);
						SalesMapsConfigDto configDto = new SalesMapsConfigDto();
						configDto.setName(aList.get(Integer.parseInt(vals[1]) - 1));
						configDto.setColor("#AFEEEE");
						configDto.setValues(configAxis);
						configDtos.add(configDto);
					}
				}
			}
		}
		for (String y : yelList) {
			if (fullList.contains(y)) {
				fullList.remove(y);
			}
		}
		for (String f : fullList) {
			if (f.indexOf(",") != -1) {
				String[] vals = f.split(",");
				List<SalesMapsConfigAxisDto> noConfigAxis = CollectionKit.newList();
				SalesMapsConfigAxisDto axisDto = new SalesMapsConfigAxisDto();
				axisDto.setX(vals[0]);
				axisDto.setY(vals[1]);
				noConfigAxis.add(axisDto);
				if (yNum >= Integer.parseInt(vals[1])) {
					SalesMapsConfigDto configDto2 = new SalesMapsConfigDto();
					configDto2.setName(aList.get(Integer.parseInt(vals[1]) - 1));
					configDto2.setColor("#FFEBCD");
					configDto2.setValues(noConfigAxis);
					configDtos.add(configDto2);
				}
			}
		}
		map.put("xAxisTitle", "销售排名");
		map.put("xAxisData", perfs);
		map.put("yAxisTitle", "能力");
		map.put("yAxisData", abilitys);
		map.put("zAxisData", configDtos);
		return map;
	}

	/**
	 * 销售排名地图-列表
	 */
	@Override
	public Map<String, Object> queryMapsGrid(SalesBoardQueryDto dto) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		if (dto.getYearMonth() != null && !"".equals(dto.getYearMonth())) {
			yearMonth = dto.getYearMonth().replaceAll("-", "");
		}
		dto.setYearMonth(yearMonth);
		String color = PropertiesUtil.getProperty(ConfigKeyUtil.XSKB_PZ).trim();
		List<SalesBoardDto> configList = salesBoardDao.queryMapsConfigInfo(dto.getCustomerId(), color);
		List<SalesBoardDto> abilityList = salesBoardDao.queryAbilityInfo(dto.getCustomerId());
		List<SalesBoardDto> rankList = salesBoardDao.queryRankChartInfo(dto.getCustomerId());
		List<String> highList = CollectionKit.newLinkedList();
		List<String> lowList = CollectionKit.newLinkedList();
		List<String> fullList = CollectionKit.newLinkedList();
		List<String> normalList = CollectionKit.newLinkedList();
		Map<String, String> parMap = CollectionKit.newMap();
		int normalNum = 0, highNum = 0, lowNum = 0;
		double normalPer = 0d, highPer = 0d, lowPer = 0d;
		if (abilityList != null && abilityList.size() > 0 && rankList != null && rankList.size() > 0) {
			for (int i = abilityList.size() - 1; i >= 0; i--) {
				for (int j = 0; j < rankList.size(); j++) {
					String key = (j + 1) + "," + (i + 1);
					String value = rankList.get(j).getId() + abilityList.get(i).getId();
					parMap.put(key, value);
					fullList.add(key);
				}
			}
		}
		if (configList != null && configList.size() > 0) {
			for (SalesBoardDto c : configList) {
				String conVal = c.getConfigVal();
				normalList.add(conVal);
			}
		}
		for (String n : normalList) {
			if (fullList.contains(n)) {
				fullList.remove(n);
			}
		}
		for (String f : fullList) {
			if (f.indexOf(",") != -1) {
				String[] vals = f.split(",");
				int xVal = Integer.parseInt(vals[0]);
				int yVal = Integer.parseInt(vals[1]);
				if(xVal < yVal){
					highList.add(parMap.get(f));
				} else {
					lowList.add(parMap.get(f));
				}
			}
		}
		
		if (dto.getFlag() != null && !"".equals(dto.getFlag())) {
			// 团队总览
			int count = salesBoardDao.queryMapsGridCount(dto);
			dto.setTextList(highList);
			highNum = salesBoardDao.queryMapsGridCountByChart(dto);
			dto.setTextList(lowList);
			lowNum = salesBoardDao.queryMapsGridCountByChart(dto);
			if (count > 0) {
				normalNum = count - highNum - lowNum;
				normalPer = (double)normalNum * 100 / count;
				highPer = (double)highNum * 100 / count;
				lowPer = (double)lowNum * 100 / count;
			}
		} else {
			// 团队pk
			Map<String, Object> pMap = CollectionKit.newMap();
			pMap.put("customerId", dto.getCustomerId());
			pMap.put("organId", dto.getOrganId());
			pMap.put("subOrganIdList", subOrganIdList);
			pMap.put("yearMonth", yearMonth);
			pMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
			pMap.put("ageList", dto.getAgeIntervals());
			pMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
			pMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));
			int count = salesBoardDao.queryMapsPKGridCount(pMap);
			pMap.put("textList", highList);
			highNum = salesBoardDao.queryMapsPKGridCountByChart(pMap);
			pMap.put("textList", lowList);
			lowNum = salesBoardDao.queryMapsPKGridCountByChart(pMap);
			if (count > 0) {
				normalNum = count - highNum - lowNum;
				normalPer = (double)normalNum * 100 / count;
				highPer = (double)highNum * 100 / count;
				lowPer = (double)lowNum * 100 / count;
			}
		}
		map.put("normalNum", normalNum);
		map.put("highNum", highNum);
		map.put("lowNum", lowNum);
		map.put("normalPer", normalPer);
		map.put("highPer", highPer);
		map.put("lowPer", lowPer);
		return map;
	}

	/**
	 * double转换为整数
	 */
	public int formatDoubleToInteger(double d) {
		DecimalFormat df = new DecimalFormat("#");
		return Integer.parseInt(df.format(d));
	}

	/**
	 * double保留两位小数
	 */
	public double formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(d));
	}

	/**
	 * 获取几月日期
	 * 
	 * @param month
	 *            设置开始日期
	 * @param num
	 *            取相对开始日期的第几月日期
	 */
	public Date getBeforeMonth(Date month, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(month);
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	/**
	 * 团队PK获取员工数量
	 * */
	@Override
	public int getTeamSettingEmpCount(SalesBoardQueryDto dto) {
		Map<String, Object> parMap = CollectionKit.newMap();
		String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
		parMap.put("customerId", dto.getCustomerId());
		parMap.put("organId", dto.getOrganId());
		parMap.put("yearMonth", yearMonth);
		parMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
		parMap.put("ageList", dto.getAgeIntervals());
		parMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
		parMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));
		return salesBoardDao.getTeamSettingEmpCount(parMap);
	}

	/**
	 * 根据id，获取管理者及管理者下属销售人员销售总额、销售利润、回款总额
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * */
	@Override
	public List<SalesBoardDto> querySalesMoneyAndProfitAndReturnAmount(String customerId, String empId) {
		String curDay = DateUtil.getDBNow().substring(0, 10);
		String yearMonth = curDay.substring(0, 7);
		String day = curDay.substring(7, 10);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String befMinDay = "", befMaxDay = "";
		try {
			String befYearMonth = df.format(getBeforeMonth(df.parse(yearMonth), -1));
			befMinDay = befYearMonth + "-01";
			befMaxDay = befYearMonth + day;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		String curYearMonth = yearMonth.replaceAll("-", "");
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("empId", empId);
		map.put("curYearMonth", curYearMonth);
		map.put("befMinDay", befMinDay);
		map.put("befMaxDay", befMaxDay);
		return salesBoardDao.querySalesMoneyAndProfitAndReturnAmount(map);
	}

	/**
	 * 根据id，获取客户购买总额、销售利润、回款总额
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param clientId 客户Id
	 * */
	@Override
	public List<SalesBoardDto> queryClientSalesMoneyAndProfitAndReturnAmount(String customerId, String empId,
			String clientId) {
		String curDay = DateUtil.getDBNow().substring(0, 10);
		String yearMonth = curDay.substring(0, 7);
		String day = curDay.substring(7, 10);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String befMinDay = "", befMaxDay = "";
		try {
			String befYearMonth = df.format(getBeforeMonth(df.parse(yearMonth), -1));
			befMinDay = befYearMonth + "-01";
			befMaxDay = befYearMonth + day;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		String curMinDay = yearMonth + "-01";
		Map<String, Object> map = CollectionKit.newMap();
		map.put("customerId", customerId);
		map.put("empId", empId);
		map.put("clientId", clientId);
		map.put("curMinDay", curMinDay);
		map.put("curMaxDay", curDay);
		map.put("befMinDay", befMinDay);
		map.put("befMaxDay", befMaxDay);
		return salesBoardDao.queryClientSalesMoneyAndProfitAndReturnAmount(map);
	}
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param row 最近几个月
	 * */
	@Override
	public Map<String, Object> querySalesMoneyAndRing(String customerId, String empId, Integer row) {
		Map<String, Object> map = CollectionKit.newMap();
		int newRow = 7;
		if(row != null && row > 0 ){
			newRow = row + 1;
		}
		List<SalesBoardDto> list = salesBoardDao.querySalesMoney(customerId, empId, newRow);
		List<String> names = CollectionKit.newLinkedList();
		List<Double> seriesDatas = CollectionKit.newLinkedList();
		List<Object> per = CollectionKit.newLinkedList();
		if(list != null && list.size() > 0){
			for(int i = 1; i < list.size(); i++){
				names.add(list.get(i).getDate());
				seriesDatas.add(list.get(i).getMoneyNum());
				per.add((list.get(i).getMoneyNum() - list.get(i - 1).getMoneyNum()) / list.get(i - 1).getMoneyNum());
			}
			if(list.size() > row){
				String oneDate = list.get(1).getDate();
				String befDate = null;
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
				try {
					befDate = df.format(getBeforeMonth(df.parse(oneDate), -1));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				if(!oneDate.equals(befDate)){
					per.set(0, "-");
				}
			} else {
				names.add(0, list.get(0).getDate());
				seriesDatas.add(0, list.get(0).getMoneyNum());
				per.add(0, "-");
			}
		}
		map.put("xAxisData", names);
		map.put("seriesData", seriesDatas);
		map.put("ringsData", per);
		return map;
	}
	
	/**
	 * 根据id，获取销售客户销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param clientId 销售客户id
	 * @param row 最近几个月
	 * */
	@Override
	public Map<String, Object> queryClientSalesMoneyAndRing(String customerId, String empId, String clientId, Integer row) {
		Map<String, Object> map = CollectionKit.newMap();
		int newRow = 7;
		if(row != null && row > 0 ){
			newRow = row + 1;
		}
		List<SalesBoardDto> list = salesBoardDao.queryClientSalesMoney(customerId, empId, clientId, newRow);
		List<String> names = CollectionKit.newLinkedList();
		List<Double> seriesDatas = CollectionKit.newLinkedList();
		List<Object> per = CollectionKit.newLinkedList();
		if(list != null && list.size() > 0){
			for(int i = 1; i < list.size(); i++){
				names.add(list.get(i).getDate());
				seriesDatas.add(list.get(i).getMoneyNum());
				per.add((list.get(i).getMoneyNum() - list.get(i - 1).getMoneyNum()) / list.get(i - 1).getMoneyNum());
			}
			if(list.size() > row){
				String oneDate = list.get(1).getDate();
				String befDate = null;
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
				try {
					befDate = df.format(getBeforeMonth(df.parse(oneDate), -1));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				if(!oneDate.equals(befDate)){
					per.set(0, "-");
				}
			} else {
				names.add(0, list.get(0).getDate());
				seriesDatas.add(0, list.get(0).getMoneyNum());
				per.add(0, "-");
			}
		}
		map.put("xAxisData", names);
		map.put("seriesData", seriesDatas);
		map.put("ringsData", per);
		return map;
	}
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员回款额/环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param row 最近几个月
	 * */
	@Override
	public Map<String, Object> querySalesReturnAmountAndRing(String customerId, String empId, Integer row) {
		Map<String, Object> map = CollectionKit.newMap();
		int newRow = 7;
		if(row != null && row > 0 ){
			newRow = row + 1;
		}
		List<SalesBoardDto> list = salesBoardDao.querySalesReturnAmount(customerId, empId, newRow);
		List<String> names = CollectionKit.newLinkedList();
		List<Double> seriesDatas = CollectionKit.newLinkedList();
		List<Object> per = CollectionKit.newLinkedList();
		if(list != null && list.size() > 0){
			for(int i = 1; i < list.size(); i++){
				names.add(list.get(i).getDate());
				seriesDatas.add(list.get(i).getReturnAmount());
				per.add((list.get(i).getReturnAmount() - list.get(i - 1).getReturnAmount()) / list.get(i - 1).getReturnAmount());
			}
			if(list.size() > row){
				String oneDate = list.get(1).getDate();
				String befDate = null;
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
				try {
					befDate = df.format(getBeforeMonth(df.parse(oneDate), -1));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				if(!oneDate.equals(befDate)){
					per.set(0, "-");
				}
			} else {
				names.add(0, list.get(0).getDate());
				seriesDatas.add(0, list.get(0).getReturnAmount());
				per.add(0, "-");
			}
		}
		map.put("xAxisData", names);
		map.put("seriesData", seriesDatas);
		map.put("ringsData", per);
		return map;
	}
	
	/**
	 * 根据id，获取销售人员/客户回款额/环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param clientId 销售客户id
	 * @param row 最近几个月
	 * */
	@Override
	public Map<String, Object> queryClientReturnAmountAndRing(String customerId, String empId, String clientId, Integer row) {
		Map<String, Object> map = CollectionKit.newMap();
		int newRow = 7;
		if(row != null && row > 0 ){
			newRow = row + 1;
		}
		List<SalesBoardDto> list = salesBoardDao.queryClientReturnAmount(customerId, empId, clientId, newRow);
		List<String> names = CollectionKit.newLinkedList();
		List<Double> seriesDatas = CollectionKit.newLinkedList();
		List<Object> per = CollectionKit.newLinkedList();
		if(list != null && list.size() > 0){
			for(int i = 1; i < list.size(); i++){
				names.add(list.get(i).getDate());
				seriesDatas.add(list.get(i).getReturnAmount());
				per.add((list.get(i).getReturnAmount() - list.get(i - 1).getReturnAmount()) / list.get(i - 1).getReturnAmount());
			}
			if(list.size() > row){
				String oneDate = list.get(1).getDate();
				String befDate = null;
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
				try {
					befDate = df.format(getBeforeMonth(df.parse(oneDate), -1));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				if(!oneDate.equals(befDate)){
					per.set(0, "-");
				}
			} else {
				names.add(0, list.get(0).getDate());
				seriesDatas.add(0, list.get(0).getReturnAmount());
				per.add(0, "-");
			}
		}
		map.put("xAxisData", names);
		map.put("seriesData", seriesDatas);
		map.put("ringsData", per);
		return map;
	}
	
}
