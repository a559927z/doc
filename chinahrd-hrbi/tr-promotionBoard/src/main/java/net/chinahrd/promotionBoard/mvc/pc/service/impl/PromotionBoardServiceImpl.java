package net.chinahrd.promotionBoard.mvc.pc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PerfChangeAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionBoardDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionDateDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionElementSchemeDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionForewarningDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPayDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPayListDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPayPersonListDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPaySelectDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionReqDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionSpeedDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionStatusDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionTotalDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionTrackDto;
import net.chinahrd.promotionBoard.mvc.pc.dao.PromotionBoardDao;
import net.chinahrd.promotionBoard.mvc.pc.service.PromotionBoardService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

/**
 * 晋级看板service
 */
@Service("promotionBoardService")
public class PromotionBoardServiceImpl implements PromotionBoardService {

	/**
	 * 晋级总数
	 */
	private double conditionProp = 70;

	@Autowired
	PromotionBoardDao promotionBoardDao;

	@Override
	public PromotionDateDto getDate(String customerId) {
		PromotionDateDto dto = promotionBoardDao.getTrendDate(customerId);
		dto.setConditionProp(conditionProp);
		return dto;
	}

	private String getYearName(Date d) {
		return new SimpleDateFormat("yyyy").format(d) + "年"
				+ (Integer.parseInt(new SimpleDateFormat("MM").format(d)) > 6 ? "下半年" : "上半年");
	}

	// 符合条件
	@Override
	public PromotionForewarningDto getEligibilityApplication(String customerId, String organId, Integer status) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		List<PromotionTotalDto> list = promotionBoardDao.getEligibilityApplication(customerId, organId, status,
				subOrganIdList);
		PromotionForewarningDto dto = new PromotionForewarningDto();
		dto.setNumber(list.size());
		dto.setYear(list.size() > 0 ? getYearName(list.get(0).getTotalDate()) : "");
		List<String> names = new ArrayList<>();
		for (Integer i = 0; i < list.size(); i++) {
			if (i > 10) {
				break;
			}
			names.add(list.get(i).getUserName());
		}
		dto.setList(names);
		return dto;
	}

	// 符合条件 列表
	@Override
	public PaginationDto<PromotionBoardDto> getEligibilityApplicationList(String customerId, String organId,
			Integer page, Integer row, Integer status) {
		PaginationDto<PromotionBoardDto> dto = new PaginationDto<PromotionBoardDto>(page, row);
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		Integer total = promotionBoardDao.getEligibilityApplicationListCount(customerId, organId, status,
				subOrganIdList);
		List<PromotionBoardDto> list = promotionBoardDao.getEligibilityApplicationList(customerId, organId, status,
				dto.getOffset(), dto.getLimit(), subOrganIdList);
		dto.setRows(list);
		dto.setRecords(total);
		return dto;
	}

	// 部分符合条件
	@Override
	public PromotionForewarningDto getSomeEligibility(String customerId, String organId) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		PromotionTotalDto model = promotionBoardDao.getSomeEligibility(customerId, organId, conditionProp,
				subOrganIdList);
		PromotionForewarningDto dto = new PromotionForewarningDto();
		dto.setNumber(model.getTotal());
		dto.setYear(getYearName(model.getTotalDate()));
		return dto;
	}

	private List<String> getTrendTime(String beginDate, String endDate) {
		List<String> list = new ArrayList<String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.strToDate(beginDate));
		int y1 = calendar.get(Calendar.YEAR);
		int m1 = calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(DateUtil.strToDate(endDate));
		int y2 = calendar.get(Calendar.YEAR);
		int m2 = calendar.get(Calendar.MONTH) + 1;
		if (m1 == 6) {
			list.add((y1 - 1) + "-12-31");
		} else {
			list.add(y1 + "-06-30");
		}
		list.add(beginDate);
		if (beginDate.equals(endDate)) {
			return list;
		}
		if (y2 > y1) {
			if (m1 == 6) {
				list.add(y1 + "-12-31");
			}
			for (int i = y1 + 1; i < y2; i++) {
				list.add(i + "-06-30");
				list.add(i + "-12-30");
			}
			if (m2 == 12) {
				list.add(y2 + "-06-30");
			}
		}

		list.add(endDate);
		return list;
	}

	// 趋势分析
	@Override
	public List<PromotionSpeedDto> getTrendAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds) {
		List<PromotionSpeedDto> psd = new LinkedList<>();
		double prevValue = 0;
		List<String> list = getTrendTime(beginDate, endDate);
		PromotionSpeedDto pdto = promotionBoardDao.getTrendAnalysis(customerId, organId, list.get(0), populationIds);
		prevValue = pdto.getTotal() == 0 ? 0 : pdto.getRank() / pdto.getTotal();
		list.remove(0);
		for (String time : list) {
			PromotionSpeedDto dto = promotionBoardDao.getTrendAnalysis(customerId, organId, time, populationIds);
			dto.setName(DateUtil.getYearMonthTohHalf(time));
			psd.add(dto);
		}
		for (PromotionSpeedDto ps : psd) {
			ps.setAvg(ps.getTotal() == 0 ? 0 : ps.getRank() / ps.getTotal());
			String increase = "0";
			if (prevValue == 0) {
				increase = "-";
				// if(ps.getAvg()==0){
				// increase="0";
				// }else{
				// increase="-";
				// }
			} else {
				increase = "" + ((ps.getAvg() - prevValue) / prevValue);
				// if(ps.getAvg()==0){
				// increase="0";
				// }else{
				// increase=""+((ps.getAvg()-prevValue)/prevValue);
				// }
			}
			ps.setIncrease(increase);
			prevValue = ps.getAvg();
		}
		return psd;
	}

	// 下级组织分析
	@Override
	public Map<String, Object> getOrgAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds) {
		String trendDate = getLastYMD(beginDate, endDate);
		Integer min = getMinValByBetweenDate(beginDate);
		Integer max = getMaxValByBetweenDate(endDate);
		int siLing = max - min;
		Map<String, Object> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", trendDate);
		paramMap.put("populationIds", populationIds);
		paramMap.put("siLing", siLing * 0.5);
		List<PromotionSpeedDto> dto = promotionBoardDao.getOrgAnalysis(paramMap);
		double total = 0;
		double rank = 0;
		for (PromotionSpeedDto ps : dto) {
			ps.setAvg(ps.getRank() / ps.getTotal());
			total += ps.getTotal();
			rank += ps.getRank();
		}
		Map<String, Object> obj = new HashMap<>();
		if (total == 0)
			obj.put("avg", 0);
		else
			obj.put("avg", ArithUtil.div(rank, total, 4));
		obj.put("list", dto);
		return obj;
	}

	// 下级组织分析，个人晋级速度
	@Override
	public PaginationDto<PromotionTrackDto> getOrgAnalysisPerJinsuList(String customerId, String organId, Integer page,
			Integer row) {
		PaginationDto<PromotionTrackDto> dto = new PaginationDto<PromotionTrackDto>(page, row);
		Integer total = promotionBoardDao.getOrgAnalysisPerJinsuCount(customerId, organId);
		List<PromotionTrackDto> list = promotionBoardDao.getOrgAnalysisPerJinsuList(customerId, organId,
				dto.getOffset(), dto.getLimit());
		dto.setRows(list);
		dto.setRecords(total);
		return dto;
	}

	// 序列统计
	@Override
	public Map<String, Object> getSequenceAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds) {
		String trendDate = getLastYMD(beginDate, endDate);
		Integer min = getMinValByBetweenDate(beginDate);
		Integer max = getMaxValByBetweenDate(endDate);
		int siLing = max - min;
		Map<String, Object> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", trendDate);
		paramMap.put("populationIds", populationIds);
		paramMap.put("siLing", siLing * 0.5);
		List<PromotionSpeedDto> dto = promotionBoardDao.getSequenceAnalysis(paramMap);
		double total = 0;
		double rank = 0;
		for (PromotionSpeedDto ps : dto) {
			// ps.setAvg(ps.getRank() / ps.getTotal());
			total += ps.getTotal();
			rank += ps.getRank();
		}
		Map<String, Object> obj = new HashMap<>();
		obj.put("avg", ArithUtil.div(rank, total, 4));
		obj.put("list", dto);
		return obj;
	}

	// 关键人才
	@Override
	public Map<String, Object> getKeyTalentAnalysis(String customerId, String organId, String beginDate, String endDate,
			List<String> populationIds) {
		String trendDate = getLastYMD(beginDate, endDate);
		Integer min = getMinValByBetweenDate(beginDate);
		Integer max = getMaxValByBetweenDate(endDate);
		int siLing = max - min;
		Map<String, Object> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", trendDate);
		paramMap.put("populationIds", populationIds);
		paramMap.put("siLing", siLing * 0.5);
		List<PromotionSpeedDto> dto = promotionBoardDao.getKeyTalentAnalysis(paramMap);
		double total = 0;
		double rank = 0;
		for (PromotionSpeedDto ps : dto) {
			// ps.setAvg(ps.getRank() / ps.getTotal());
			total += ps.getTotal();
			rank += ps.getRank();
		}
		Map<String, Object> obj = new HashMap<>();
		obj.put("avg", ArithUtil.div(rank, total, 4));
		obj.put("list", dto);
		return obj;
	}

	// 绩效
	@Override
	public Map<String, Object> getPerformanceAnalysis(String customerId, String organId, String beginDate,
			String endDate, List<String> populationIds) {
		String trendDate = getLastYMD(beginDate, endDate);
		Integer min = getMinValByBetweenDate(beginDate);
		Integer max = getMaxValByBetweenDate(endDate);
		int siLing = max - min;
		Map<String, Object> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", customerId);
		paramMap.put("organId", organId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", trendDate);
		paramMap.put("populationIds", populationIds);
		paramMap.put("siLing", siLing * 0.5);
		List<PromotionSpeedDto> dto = promotionBoardDao.getPerformanceAnalysis(paramMap);
		double total = 0;
		double rank = 0;
		for (PromotionSpeedDto ps : dto) {
			// ps.setAvg(ps.getRank() / ps.getTotal());
			total += ps.getTotal();
			rank += ps.getRank();
		}
		Map<String, Object> obj = new HashMap<>();
		obj.put("avg", ArithUtil.div(rank, total, 4));
		obj.put("list", dto);
		return obj;
	}

	// 晋级轨迹(图)
	@Override
	public List<PromotionTrackDto> getTrackChart(String customerId, String organId, List<String> empIds) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		List<PromotionTrackDto> dto = promotionBoardDao.getTrackChart(customerId, organId, empIds, subOrganIdList);

		return dto;
	}

	// 晋级轨迹(搜索下拉)
	@Override
	public Map<String, Object> getTrackSelect(String customerId, String organId, String key, Integer page,
			Integer rows) {
		PaginationDto<PromotionTrackDto> dto = new PaginationDto<>(page, rows);
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		List<PromotionTrackDto> list = promotionBoardDao.getTrackSelect(customerId, organId, key, dto.getOffset(),
				dto.getLimit() + 1, subOrganIdList);
		List<PromotionTrackDto> pt = new LinkedList<>();
		if (null != list && list.size() > 0) {
			for (Integer i = 0; i < list.size(); i++) {
				pt.add(list.get(i));
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("list", pt);
		map.put("more", list.size() > dto.getLimit() ? 1 : 0);
		return map;
	}

	// 晋级轨迹(列表)
	@Override
	public PaginationDto<PromotionTrackDto> getTrackList(String customerId, String organId, String empId, Integer page,
			Integer rows) {
		PaginationDto<PromotionTrackDto> dto = new PaginationDto<>(page, rows);
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		Integer total = promotionBoardDao.getTrackListCount(customerId, organId, empId, subOrganIdList);
		List<PromotionTrackDto> list = promotionBoardDao.getTrackList(customerId, organId, empId, dto.getOffset(),
				dto.getLimit(), subOrganIdList);
		dto.setRecords(total);
		dto.setRows(list);
		return dto;
	}

	// 人员晋级状态(列表)
	@Override
	public PaginationDto<PromotionStatusDto> getStatusList(String customerId, String organId, String empId,
			String rankName, String rankNameNext, Integer condition, double conditionBegin, double conditionEnd,
			Integer page, Integer rows, boolean isAll) {
		PaginationDto<PromotionStatusDto> dto = new PaginationDto<>(page, rows);
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		Integer total = promotionBoardDao.getStatusListCount(customerId, organId, empId, rankName, rankNameNext,
				condition, conditionBegin, conditionEnd, dto.getOffset(), dto.getLimit(), subOrganIdList, isAll);
		List<PromotionStatusDto> list = promotionBoardDao.getStatusList(customerId, organId, empId, rankName,
				rankNameNext, condition, conditionBegin, conditionEnd, dto.getOffset(), dto.getLimit(), subOrganIdList,
				isAll);
		dto.setRecords(total);
		if (total == 0) {
			dto.setRows(list);
			return dto;
		}
		List<String> empIds = new ArrayList<>();
		for (PromotionStatusDto m : list) {
			empIds.add(m.getEmpId());
		}
		List<PromotionReqDto> listReq = promotionBoardDao.getStatusReqList(customerId, empIds);
		for (PromotionStatusDto m : list) {
			List<PromotionReqDto> prd = new LinkedList<>();
			for (PromotionReqDto r : listReq) {
				if (m.getEmpId().equals(r.getEmpId())) {
					prd.add(r);
				}
			}
			m.setListReq(prd);
		}
		dto.setRows(list);
		return dto;
	}

	// 晋级薪酬模拟器（筛选显示的职级）
	public List<PromotionPaySelectDto> getPromotionSelectRank(String customerId, String organId) {
		List<PromotionPayDto> dto = promotionBoardDao.getPromotionSelectRank(customerId, organId, conditionProp);
		List<String> keys = new LinkedList<>();
		for (PromotionPayDto pp : dto) {
			if (!keys.contains(pp.getRankName())) {
				keys.add(pp.getRankName());
			}
		}
		List<PromotionPaySelectDto> list = new LinkedList<>();
		for (String key : keys) {
			PromotionPaySelectDto pps = new PromotionPaySelectDto();
			pps.setRank(key);
			pps.setMatch(0);
			pps.setMismatch(0);
			for (PromotionPayDto pp : dto) {
				if (pp.getRankName().equals(key)) {
					if (pp.getMatchCondition() == 0) {
						pps.setMismatch(pp.getMatchCount() + pps.getMismatch());
					} else if (pp.getMatchCondition() == 1) {
						pps.setMatch(pp.getMatchCount() + pps.getMatch());
					}
				}
			}
			list.add(pps);
		}
		return list;
	}

	// 晋级薪酬模拟器（列表）
	public List<PromotionPayListDto> getPromotionRankList(String customerId, String organId, List<String> ranks) {
		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		List<PromotionPayDto> currentRank = promotionBoardDao.getPromotionCurrentRankList(customerId, organId,
				conditionProp, ranks, subOrganIdList);
		/*
		 * List<PromotionPayDto> nextRank =
		 * promotionBoardDao.getPromotionNextRankList(customerId, organId,
		 * ranks); List<String> empIds = new ArrayList<>(); for (PromotionPayDto
		 * p:currentRank){ empIds.add(p.getEmpId()); }
		 */
		List<PromotionPayDto> pay = promotionBoardDao.getPromotionRankSalaryList(customerId, ranks, subOrganIdList);

		List<PromotionPayListDto> dto = new LinkedList<>();
		for (String rank : ranks) {
			PromotionPayListDto d = new PromotionPayListDto();
			d.setRankName(rank);
			// 晋级后平均薪酬
			double total = 0;
			/*
			 * for (PromotionPayDto pp : nextRank){
			 * if(pp.getRankName().equals(rank)){
			 */
			for (PromotionPayDto p : pay) {
				if (p.getRankName().equals(rank)) {
					total += p.getPay();
				}
			}
			/*
			 * } }
			 */
			d.setNextRankSalary(total);

			// 符合条件人员
			List<PromotionPayPersonListDto> match = new LinkedList<>();
			for (PromotionPayDto pp : currentRank) {
				if (pp.getRankNameAf().equals(rank) && pp.getConditionProp() == 100) {
					PromotionPayPersonListDto person = new PromotionPayPersonListDto();
					person.setEmpId(pp.getEmpId());
					person.setUserName(pp.getUserName());
					/*
					 * for (PromotionPayDto p:pay){
					 * if(p.getEmpId().equals(pp.getEmpId())){
					 */
					person.setSalary(pp.getPay());
					/*
					 * break; } }
					 */
					match.add(person);
				}
			}
			d.setMatch(match);

			// 部分符合条件人员
			List<PromotionPayPersonListDto> mismatch = new LinkedList<>();
			for (PromotionPayDto pp : currentRank) {
				if (pp.getRankNameAf().equals(rank) && pp.getConditionProp() >= conditionProp
						&& pp.getConditionProp() < 100) {
					PromotionPayPersonListDto person = new PromotionPayPersonListDto();
					person.setEmpId(pp.getEmpId());
					person.setUserName(pp.getUserName());
					person.setSalary(pp.getPay());
					mismatch.add(person);
				}
			}
			d.setMismatch(mismatch);

			dto.add(d);
		}

		return dto;
	}

	// 晋级薪酬模拟器(添加下拉)
	public Map<String, Object> getPromotionAddPersonList(String customerId, String organId, String rank, Integer page,
			Integer rows, String key) {
		PaginationDto<PromotionPayDto> dtoPage = new PaginationDto<>(page, rows);
		// List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
		List<String> subOrganIdList = new ArrayList<String>();
		for (OrganDto organDto : EisWebContext.getCurrentUser().getOrganPermit()) {
			subOrganIdList.add(organDto.getOrganizationId());
		}
		List<PromotionPayDto> list = promotionBoardDao.getPromotionRankSalaryListByEmpid(customerId,
				dtoPage.getOffset(), dtoPage.getLimit() + 1, key, subOrganIdList);
		// List<String> empIds = new ArrayList<>();
		// for (PromotionPayDto p:list){
		// empIds.add(p.getEmpId());
		// }
		// List<PromotionPayDto> pay =
		// promotionBoardDao.getPromotionRankSalaryListByEmpid(customerId,
		// empIds,subOrganIdList);
		// for (PromotionPayDto pp:list){
		// for (PromotionPayDto p:pay){
		// if(pp.getEmpId().equals(p.getEmpId())){
		// pp.setPay(p.getPay());
		// }
		// }
		// }
		// List<PromotionPayDto> dto = new LinkedList<>();
		// for (Integer i=0; i<list.size(); i++){
		// if (i<rows){
		// dto.add(list.get(i));
		// }
		// }
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("more", (null != list && list.size() > 0) ? 1 : 0);
		return map;
	}

	// 獲取最後一次時間
	private String getLastYMD(String beginDate, String endDate) {
		// Date bDate = DateUtil.strToDate(beginDate);
		// Integer min=getMinValByBetweenDate(beginDate);
		// Integer max=getMaxValByBetweenDate(endDate);
		// int newYearMonth = 1;
		// int newYearMonth2 = 1;
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(bDate);
		// newYearMonth = calendar.get(Calendar.MONTH) + 1;
		// String trendDate =null;
		// for (Integer i = min; i <= max; i++) {
		// if (i == min) {
		// }else{
		// if (newYearMonth > 6) {
		// newYearMonth2 = 1;
		// newYearMonth = 1;
		// } else {
		// newYearMonth2 = 7;
		// newYearMonth = 7;
		// }
		// if (i.equals(max)) {
		// trendDate = String.valueOf((i + 1) / 2) + "-" + "0"
		// + newYearMonth2 + "-" + "01";
		// }
		// }
		//
		// }
		// return trendDate;
		return endDate;
	}

	private int getMinValByBetweenDate(String strDate) {
		Date date = DateUtil.strToDate(strDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int minVal = (calendar.get(Calendar.YEAR)) * 2 + ((calendar.get(Calendar.MONTH) + 1) > 6 ? 1 : 0) - 1;
		return minVal;
	}

	private int getMaxValByBetweenDate(String strDate) {
		Date date = DateUtil.strToDate(strDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int MaxVal = (calendar.get(Calendar.YEAR)) * 2 + ((calendar.get(Calendar.MONTH) + 1) > 6 ? 1 : 0);
		return MaxVal;
	}

	public static void main(String[] f) {
		System.out.println(new PromotionBoardServiceImpl().getMaxValByBetweenDate("2015-12-31"));
		System.out.println(new PromotionBoardServiceImpl().getMinValByBetweenDate("2013-07-01"));
	}

	@Override
	public List<KVItemDto> getEmpSchemeRel(String date) {
		return promotionBoardDao.getEmpSchemeRel(date);
	}

	@Override
	public List<PromotionElementSchemeDto> getSchemeAll() {
		return promotionBoardDao.getSchemeAll();
	}

	@Override
	public void delEmpAnalysis(String empId) {
		promotionBoardDao.delEmpAnalysis(empId);
	}

	@Override
	public List<KVItemDto> getEmpCompanyAge() {
		return promotionBoardDao.getEmpCompanyAge();
	}

	@Override
	public List<KVItemDto> getEmpEval() {
		return promotionBoardDao.getEmpEval();
	}

	@Override
	public List<KVItemDto> getEvalAll() {
		return promotionBoardDao.getEvalAll();
	}

	@Override
	public List<KVItemDto> getEmpCertificate() {
		return promotionBoardDao.getEmpCertificate();
	}

	@Override
	public List<KVItemDto> getCertificateAll() {
		return promotionBoardDao.getCertificateAll();
	}

	@Override
	public List<KVItemDto> getEmpCertificateType() {
		return promotionBoardDao.getEmpCertificateType();
	}

	@Override
	public List<PerfChangeAnalysisDto> getEmpPerf() {
		return promotionBoardDao.getEmpPerf();
	}

	@Override
	public void batchInsertPA(List<PromotionAnalysisDto> list) {
		promotionBoardDao.batchInsertPA(list);
	}

	@Override
	public void batchUpdateCP(List<KVItemDto> list, String date) {
		promotionBoardDao.batchUpdateCP(list, date);
	}
	
	
}
