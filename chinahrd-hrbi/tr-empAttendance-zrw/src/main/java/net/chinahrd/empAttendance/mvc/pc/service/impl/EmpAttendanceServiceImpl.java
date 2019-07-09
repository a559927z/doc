package net.chinahrd.empAttendance.mvc.pc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.empAttendance.mvc.pc.dao.EmpAttendanceDao;
import net.chinahrd.empAttendance.mvc.pc.service.EmpAttendanceSaveDataService;
import net.chinahrd.empAttendance.mvc.pc.service.EmpAttendanceService;
import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpAttendanceDto;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.PropertiesUtil;

/**
 * 考勤
 * 
 * @author xwli 2016-11-4
 */
@Service("empAttendanceService")
public class EmpAttendanceServiceImpl implements EmpAttendanceService {
	// todo 中人网系统默认7号为截至修改日期
	public static int ZRW_WARN_DAY = 7;
	static {
		try {
			String property = PropertiesUtil.getProperty("YGKQ.ZRW_WARN_DAY").trim();
			ZRW_WARN_DAY = Integer.parseInt(property);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private EmpAttendanceDao empAttendanceDao;

	@Autowired
	private EmpAttendanceSaveDataService empAttendanceSaveDataService;

	@Override
	public EmpAttendanceDto queryEmpInfo(String customerId, String empId) {
		return empAttendanceDao.queryEmpInfo(customerId, empId);
	}

	@Override
	public EmpAttendanceDto queryEmpVacationInfo(String customerId, String empId) {
		EmpAttendanceDto rs = empAttendanceDao.queryEmpVacationInfo(customerId, empId);
		EmpAttendanceDto dto = new EmpAttendanceDto();
		if (null == rs) {
			dto.setAnnual(0);
			dto.setTotal(0);
		}
		return rs;
	}

	/**
	 * 获取考勤时间的年月
	 * 
	 * @return
	 */
	public int getYearMonth() {
		String now = DateUtil.getDBNow();
		int day = Integer.parseInt(now.substring(8, 10));
		String month = now.substring(5, 7);
		String year = now.substring(0, 4);
		if (day <= ZRW_WARN_DAY) {
			if ("01".equals(month)) {
				month = "12";
				year = (Integer.parseInt(year) - 1) + "";
			} else {
				month = (Integer.parseInt(month) - 1) < 10 ? "0" + (Integer.parseInt(month) - 1)
						: (Integer.parseInt(month) - 1) + "";
			}
		}
		int yearMonth = Integer.parseInt(year + month);
		return yearMonth;
	}

	@Override
	public Map<String, Object> queryAttendanceRecord(String customerId, String empId) {
		Map<String, Object> map = CollectionKit.newMap();
		List<String> theory = CollectionKit.newList();
		List<EmpAttendanceDto> cal = CollectionKit.newList();
		List<EmpAttendanceDto> checkType = CollectionKit.newList();
		String exceptionId = "CHECKWORK_TYPE_E";
		String exceptionName = "异常";
		// 添加异常类型
		EmpAttendanceDto dt = new EmpAttendanceDto();
		dt.setTypeId(exceptionId);
		dt.setTypeName(exceptionName);
		checkType.add(dt);
		List<EmpAttendanceDto> query = CollectionKit.newList();
		checkType.addAll(empAttendanceDao.queryCheckType());

		int yearMonth = getYearMonth();
		List<EmpAttendanceDto> result = empAttendanceDao.queryAttendanceRecord(customerId, empId, yearMonth);
		String endDay = DateUtil.getBeforeDay("yyyy-MM-dd");
		int day = Integer.parseInt(DateUtil.getDBTime("dd"));
		if (day <= ZRW_WARN_DAY) {
			theory = empAttendanceDao.queryAttendanceTheory(yearMonth, null, null);
		} else {
			String beginDay = endDay.substring(0, 8) + "01";
			theory = empAttendanceDao.queryAttendanceTheory(yearMonth, beginDay, endDay);
		}
		int theoryNum = 0; // 应出勤和考勤记录对比差
		for (String th : theory) {
			boolean flag = true;
			for (EmpAttendanceDto dto : result) {
				if (th.equals(dto.getEntryDay())) {
					flag = false;
				}
			}
			if (flag) {
				theoryNum++;
			}
		}

		String now = DateUtil.getDBTime("yyyy-MM-dd");
		for (EmpAttendanceDto type : checkType) {
			for (EmpAttendanceDto dto : result) {
				if (!now.equals(dto.getEntryDay())) {
					if (dto.getCalHour() != null && !"".equals(dto.getCalHour())) {
						String[] hourArr = dto.getCalHour().split(",");
						String[] typeIds = dto.getTypeId().split(",");
						for (int i = 0; i < typeIds.length; i++) {
							if (typeIds[i].equals(type.getTypeId())) {
								EmpAttendanceDto d = new EmpAttendanceDto();
								d.setTypeId(type.getTypeId());
								d.setTypeName(type.getTypeName());
								d.setTotal(Float.parseFloat(hourArr[i]));
								cal.add(d);
							}
						}
					} else {
						if (type.getTypeId().equals(dto.getTypeId())) {
							String clockInAm = dto.getClockInAm();
							String clockOutPm = dto.getClockOutPm();
							if (clockInAm == null || clockOutPm == null) {
								String adjustbegin = dto.getAdjustBegin();
								String adjustend = dto.getAdjustEnd();
								if (adjustbegin != null || adjustend != null) {
									EmpAttendanceDto d = new EmpAttendanceDto();
									d.setTypeId(type.getTypeId());
									d.setTypeName(type.getTypeName());
									d.setTotal(7.5f);
									cal.add(d);
								} else {
									EmpAttendanceDto d = new EmpAttendanceDto();
									d.setTypeId(exceptionId);
									d.setTypeName(exceptionName);
									d.setTotal(7.5f);
									cal.add(d);
								}
							} else {
								int inHour = Integer.parseInt(clockInAm.substring(11, 13));
								int inMinite = Integer.parseInt(clockInAm.substring(14, 16));
								int outHour = Integer.parseInt(clockOutPm.substring(11, 13));
								if (inHour > 10 || (inHour == 10 && inMinite > 0) || outHour < 18) {
									EmpAttendanceDto d = new EmpAttendanceDto();
									d.setTypeId(exceptionId);
									d.setTypeName(exceptionName);
									d.setTotal(7.5f);
									cal.add(d);
								} else {
									EmpAttendanceDto d = new EmpAttendanceDto();
									d.setTypeId(type.getTypeId());
									d.setTypeName(type.getTypeName());
									d.setTotal(7.5f);
									cal.add(d);
								}
							}
						}
					}
				}
			}
		}
		// 添加整天无打卡记录
		if (theoryNum > 0) {
			EmpAttendanceDto calDto = new EmpAttendanceDto();
			calDto.setTypeId(exceptionId);
			calDto.setTypeName(exceptionName);
			calDto.setTotal(theoryNum * 7.5f);
			cal.add(calDto);
		}

		// 统计考勤记录数据
		for (EmpAttendanceDto type : checkType) {
			float num = 0f;
			for (EmpAttendanceDto dto : cal) {
				if (type.getTypeId().equals(dto.getTypeId())) {
					num += dto.getTotal();
				}
			}
			if (num != 0) {
				EmpAttendanceDto d = new EmpAttendanceDto();
				d.setTypeId(type.getTypeId());
				d.setTypeName(type.getTypeName());
				d.setTotal(num);
				query.add(d);
			}
		}

		map.put("query", query);
		map.put("theory", theory);
		return map;
	}

	public List<EmpAttendanceDto> queryAttendance(String customerId, String empId, Integer yearMonth) {
		if (yearMonth == null || yearMonth <= 0) {
			yearMonth = getYearMonth();
		}
		List<EmpAttendanceDto> list = empAttendanceDao.queryAttendanceRecord(customerId, empId, yearMonth);
		List<EmpAttendanceDto> result = CollectionKit.newList();
		List<EmpAttendanceDto> checkType = empAttendanceDao.queryCheckType();
		String now = DateUtil.getDBTime("yyyy-MM-dd");
		for (EmpAttendanceDto dto : list) {
			if (dto.getCalHour() != null && !"".equals(dto.getCalHour())) {
				String[] typeIds = dto.getTypeId().split(",");
				if (typeIds.length > 1) {
					String names = "";
					for (int i = 0; i < typeIds.length; i++) {
						for (EmpAttendanceDto type : checkType) {
							if (typeIds[i].equals(type.getTypeId())) {
								if (i != 0) {
									if (i < typeIds.length - 1)
										names += type.getTypeName() + ",";
									else
										names += type.getTypeName();
								}
							}
						}
					}
					dto.setId("1");
					dto.setTypeName(names);
				} else {
					for (EmpAttendanceDto type : checkType) {
						if (typeIds[0].equals(type.getTypeId())) {
							dto.setId("1");
							dto.setTypeName((!"".equals(dto.getReason()) && dto.getReason() != null) ? dto.getReason()
									: type.getTypeName());
						}
					}
				}
			} else {
				String clockInAm = dto.getClockInAm();
				String clockOutPm = dto.getClockOutPm();
				if (clockInAm == null || clockOutPm == null) {
					dto.setId("2");
					dto.setTypeName("异常");
				} else {
					int inHour = Integer.parseInt(clockInAm.substring(11, 13));
					int inMinite = Integer.parseInt(clockInAm.substring(14, 16));
					int outHour = Integer.parseInt(clockOutPm.substring(11, 13));
					if (inHour > 10 || (inHour == 10 && inMinite > 0) || outHour < 18) {
						dto.setId("2");
						dto.setTypeName("异常");
					} else {
						for (EmpAttendanceDto type : checkType) {
							if (dto.getTypeId().equals(type.getTypeId())) {
								dto.setId("0");
								dto.setTypeName(type.getTypeName());
							}
						}
					}
				}
			}
			dto.setEntryDay(dto.getEntryDay());
			if (!now.equals(dto.getEntryDay()))
				result.add(dto);
		}
		List<String> theory = CollectionKit.newList();
		String endDay = DateUtil.getBeforeDay("yyyy-MM-dd");
		int nowYm = Integer.parseInt(DateUtil.getBeforeDay("yyyyMM"));
		// int day = Integer.parseInt(endDay.substring(8, 10));
		if (nowYm == yearMonth) {
			theory = empAttendanceDao.queryAttendanceTheory(yearMonth, null, endDay);
		} else {
			theory = empAttendanceDao.queryAttendanceTheory(yearMonth, null, null);
		}
		// 添加一整天没有打卡记录的信息
		for (String th : theory) {
			boolean flag = true;
			for (EmpAttendanceDto dto : result) {
				if (th.equals(dto.getEntryDay())) {
					flag = false;
				}
			}
			if (flag) {
				EmpAttendanceDto d = new EmpAttendanceDto();
				d.setId("2");
				d.setTypeName("异常");
				d.setEntryDay(th);
				result.add(d);
			}
		}
		return result;
	}

	@Override
	public EmpAttendanceDto queryAttendanceByEmpId(String customerId, String empId, String day) {
		List<EmpAttendanceDto> list = empAttendanceDao.queryAttendanceFromCard(customerId, empId, day, null);
		if (list != null && list.size() > 0) {
			EmpAttendanceDto dto = list.get(0);
			List<EmpAttendanceDto> check = empAttendanceDao.queryCheckType();
			String typeIds = dto.getTypeId();
			String[] typeArr = typeIds.split(",");
			String typeNames = "";
			for (int i = 0; i < typeArr.length; i++) {
				for (EmpAttendanceDto c : check) {
					if (typeArr[i].equals(c.getTypeId())) {
						if (i == typeArr.length - 1)
							typeNames += c.getTypeName();
						else
							typeNames += c.getTypeName() + ",";
					}
				}
			}
			dto.setTypeName(typeNames);

			return dto;
		} else
			return null;
	}

	@Override
	public Map<String, Object> queryAttendanceFromCard(String customerId, String empId, int yearMonth) {
		Map<String, Object> map = CollectionKit.newMap();
		if (yearMonth <= 0)
			yearMonth = getYearMonth();
		// 查询打卡记录
		List<EmpAttendanceDto> list = empAttendanceDao.queryAttendanceFromCard(customerId, empId, null, yearMonth);
		// 正常的打卡记录
		List<EmpAttendanceDto> normal = CollectionKit.newList();
		for (EmpAttendanceDto dto : list) {
			if (dto.getClockInAm() != null && !"".equals(dto.getClockInAm()) && dto.getClockOutPm() != null
					&& !"".equals(dto.getClockOutPm())) {
				int inHour = Integer.parseInt(dto.getClockInAm().substring(0, 2));
				int inMinite = Integer.parseInt(dto.getClockInAm().substring(3, 5));
				int outHour = Integer.parseInt(dto.getClockOutPm().substring(0, 2));
				// int outMinite = Integer.parseInt(dto.getClockOutPm().substring(3, 5));
				if ((inHour < 10 || (inHour == 10 && inMinite == 0)) && outHour >= 18) {
					normal.add(dto);
				}
			}
		}
		map.put("normal", normal);
		List<String> theory = empAttendanceDao.queryAttendanceTheory(yearMonth, null, null);
		map.put("theory", theory);
		List<String> allTheory = empAttendanceDao.queryAttendanceTheory(null, null, null);
		map.put("allTheory", allTheory);
		return map;
	}

	@Override
	public List<EmpAttendanceDto> querySoureItem() {
		return empAttendanceDao.querySoureItem();
	}

	@Override
	public List<EmpAttendanceDto> queryCheckType() {
		return empAttendanceDao.queryCheckType();
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean updateEmpAttendance(String customerId, String empId, String in, String out, String reason,
			String typeId, String note, String typeName, String time, String empKey, String userName, String day,
			String yearMonth, String hour, String organId, int type) {
		Map<String, Object> map = CollectionKit.newMap();
		map.put("empId", empId);
		map.put("reason", reason);
		map.put("typeId", typeId);
		map.put("calHour", hour);
		map.put("note", note);
		map.put("time", time);
		map.put("customerId", customerId);
		map.put("empKey", empKey);
		map.put("userName", userName);
		EmpAttendanceDto dto = empAttendanceDao.queryEmpInfo(customerId, empId);
		map.put("organId", dto.getOrganId());

		String[] hourArr = hour.split(",");
		String[] typeidArr = typeId.split(",");
		String[] typeNames = typeName.split(",");

		Map<String, Object> param = CollectionKit.newMap();
		param.put("customerId", customerId);
		param.put("empKey", empKey);
		param.put("empId", empId);
		param.put("userName", userName);
		param.put("typeIds", typeId);
		param.put("hour", hour);
		param.put("organId", organId);
		param.put("yearMonth", yearMonth);

		if (type == 1) { // 具体天调整
			map.put("in", in);
			map.put("out", out);
			map.put("day", day);
			int ym = Integer.parseInt(day.substring(0, 4) + day.substring(5, 7));
			map.put("yearMonth", ym);
			// 查询是否存在当天的考勤记录
			List<EmpAttendanceDto> list = empAttendanceDao.queryAttendanceByEmpId(customerId, empId, day);

			param.put("day", day);

			if (list != null && list.size() > 0) { // 存在更新
				String hisHour = list.get(0).getCalHour();
				String hisTypeid = list.get(0).getTypeId();
				String newHour = hour;
				String tempTypeid = "";
				if (hisHour != null && !"".equals(hisHour)) {
					String[] hisHourarr = hisHour.split(",");
					String[] hisTypeidarr = hisTypeid.split(",");
					String temp = "";
					for (int j = 0; j < hisHourarr.length; j++) {
						boolean f = true;
						for (int i = 0; i < hourArr.length; i++) {
							if (typeidArr[i].equals(hisTypeidarr[j])) {
								f = false;
							}
						}
						if (f) {
							tempTypeid += hisTypeidarr[j] + ",";
							temp += "-" + hisHourarr[j] + ",";
						}
					}
					for (int i = 0; i < hourArr.length; i++) {
						double count = Double.parseDouble(hourArr[i]);
						for (int j = 0; j < hisHourarr.length; j++) {
							double tm = Double.parseDouble(hisHourarr[j]);
							if (typeidArr[i].equals(hisTypeidarr[j]) && "加班".equals(typeNames[i])) {
								count = count - tm;
							}
							if (typeidArr[i].equals(hisTypeidarr[j]) && "请假".equals(typeNames[i])) {
								if (count == tm) {
									count = 0d;
								} else if (count > tm) {
									count = 4d;
								} else if (count < tm) {
									count = -4d;
								}
							}
						}
						if (i != hourArr.length - 1)
							temp += count + ",";
						else
							temp += count;
					}
					newHour = temp;
				}

				param.put("empAttendanceId", list.get(0).getId());
				param.put("p_checkworkTypeId", tempTypeid + typeId);
				param.put("p_hour", newHour);
				try {
					empAttendanceDao.updateEmpAttendance(map);
					// empAttendanceDao.callProLaoDongLiXiaoNeng(param); //调用存储过程生产劳动力效能数据
					// 调整年假或者可调休时间
					empAttendanceSaveDataService.saveLaborEfficiencyData(param);
				} catch (Exception e) {
					empAttendanceDao.insertDebug(e.toString());
					e.printStackTrace();
					return false;
				}
			} else { // 不存在新增
				String emp_attendance_id = Identities.uuid2();
				map.put("id", emp_attendance_id);
				param.put("empAttendanceId", emp_attendance_id);
				param.put("p_checkworkTypeId", typeId);
				param.put("p_hour", hour);
				try {
					empAttendanceDao.addEmpAttendance(map);
					// empAttendanceDao.callProLaoDongLiXiaoNeng(param); //调用存储过程生产劳动力效能数据
					// 调整年假或者可调休时间
					empAttendanceSaveDataService.saveLaborEfficiencyData(param);
				} catch (Exception e) {
					empAttendanceDao.insertDebug(e.toString());
					e.printStackTrace();
					return false;
				}
			}
		} else { // 批量调整
			int ym = Integer.parseInt(yearMonth);
			String[] days = day.split(",");

			if (days != null && days.length > 0) {
				for (int i = 0; i < days.length; i++) {
					String d = days[i];
					if (in == null || "".equals(in))
						map.put("in", in);
					else
						map.put("in", d + " " + in);
					if (out == null || "".equals(out))
						map.put("out", out);
					else
						map.put("out", d + " " + out);
					map.put("day", d);
					map.put("yearMonth", ym);
					// 查询是否存在当天的考勤记录
					List<EmpAttendanceDto> list = empAttendanceDao.queryAttendanceByEmpId(customerId, empId, d);
					param.put("day", d);
					if (list != null && list.size() > 0) { // 存在更新
						String hisHour = list.get(0).getCalHour();
						String hisTypeid = list.get(0).getTypeId();
						String newHour = hour;
						String tempTypeid = "";
						if (hisHour != null && !"".equals(hisHour)) {
							String[] hisHourarr = hisHour.split(",");
							String[] hisTypeidarr = hisTypeid.split(",");
							String temp = "";
							for (int j = 0; j < hisHourarr.length; j++) {
								boolean f = true;
								for (int k = 0; k < hourArr.length; k++) {
									if (typeidArr[k].equals(hisTypeidarr[j])) {
										f = false;
									}
								}
								if (f) {
									tempTypeid += hisTypeidarr[j] + ",";
									temp += "-" + hisHourarr[j] + ",";
								}
							}
							for (int k = 0; k < hourArr.length; k++) {
								double count = Double.parseDouble(hourArr[k]);
								for (int j = 0; j < hisHourarr.length; j++) {
									double tm = Double.parseDouble(hisHourarr[j]);
									if (typeidArr[k].equals(hisTypeidarr[j]) && "加班".equals(typeNames[k])) {
										count = count - tm;
									}
									if (typeidArr[k].equals(hisTypeidarr[j]) && "请假".equals(typeNames[k])) {
										if (count == tm) {
											count = 0d;
										} else if (count > tm) {
											count = 4d;
										} else if (count < tm) {
											count = -4d;
										}
									}
								}
								if (k != hourArr.length - 1)
									temp += count + ",";
								else
									temp += count;
							}
							newHour = temp;
						}
						param.put("empAttendanceId", list.get(0).getId());
						param.put("p_checkworkTypeId", tempTypeid + typeId);
						param.put("p_hour", newHour);
						try {
							empAttendanceDao.updateEmpAttendance(map);
							// empAttendanceDao.callProLaoDongLiXiaoNeng(param); //调用存储过程生产劳动力效能数据
							// 调整年假或者可调休时间
							empAttendanceSaveDataService.saveLaborEfficiencyData(param);
						} catch (Exception e) {
							empAttendanceDao.insertDebug(e.toString());
							e.printStackTrace();
							return false;
						}
					} else { // 不存在新增
						String emp_attendance_id = Identities.uuid2();
						map.put("id", emp_attendance_id);
						param.put("empAttendanceId", emp_attendance_id);
						param.put("p_checkworkTypeId", typeId);
						param.put("p_hour", hour);
						try {
							empAttendanceDao.addEmpAttendance(map);
							// empAttendanceDao.callProLaoDongLiXiaoNeng(param); //调用存储过程生产劳动力效能数据
							// 调整年假或者可调休时间
							empAttendanceSaveDataService.saveLaborEfficiencyData(param);
						} catch (Exception e) {
							empAttendanceDao.insertDebug(e.toString());
							e.printStackTrace();
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public int checkEmpInOrgan(String workPlaceName, String organId, String empId, String customerId) {
		List<String> organIds = CacheHelper.getSubOrganIdList(organId);
		return empAttendanceDao.checkEmpInOrgan(workPlaceName, organIds, empId, customerId);
	}

	@Override
	public List<EmpAttendanceDto> queryEmpOtherDay(String customerId, String empId, int startYm, int endYm) {
		List<EmpAttendanceDto> rs = empAttendanceDao.queryEmpOtherDay(customerId, empId, startYm, endYm);
		return rs;
	}

	@Override
	public List<String> queryEmpOtherDayYear(String customerId, String empId) {
		List<String> rs = empAttendanceDao.queryEmpOtherDayYear(customerId, empId);
		return rs;
	}

}
