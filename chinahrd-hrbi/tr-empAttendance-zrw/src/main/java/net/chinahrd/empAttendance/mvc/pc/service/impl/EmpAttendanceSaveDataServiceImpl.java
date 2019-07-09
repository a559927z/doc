package net.chinahrd.empAttendance.mvc.pc.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.empAttendance.mvc.pc.dao.EmpAttendanceDao;
import net.chinahrd.empAttendance.mvc.pc.service.EmpAttendanceSaveDataService;
import net.chinahrd.entity.enums.IdCodeEnum;
import net.chinahrd.utils.CollectionKit;

/**
 * 
 * @author jxzhang on 2016年11月21日
 * @modify xwli on 2017年2月14日
 * @Verdion 1.0 版本
 */
@Service("empAttendanceSaveDataService")
public class EmpAttendanceSaveDataServiceImpl implements EmpAttendanceSaveDataService {

	@Autowired
	private EmpAttendanceDao empAttendanceDao;

	private final String NORMAL = IdCodeEnum.CHECKWORK_NORMAL_ZRW.getStatus(); // 正常出勤
	private final String OT = IdCodeEnum.CHECKWORK_OT_ZRW.getStatus(); // 加班
	private final String ANNUAL = IdCodeEnum.CHECKWORK_ANNUAL_ZRW.getStatus(); // 年假
	private final String CANLEAVE = IdCodeEnum.CHECKWORK_CANLEAVE_ZRW.getStatus(); // 调休

	@Transactional(rollbackFor = Exception.class)
	public void saveLaborEfficiencyData(Map<String, Object> param) {

		String customerId = (String) param.get("customerId");
		String p_emp_attendance_id = (String) param.get("empAttendanceId");
		String day = (String) param.get("day");
		String empKey = (String) param.get("empKey");
		String empId = (String) param.get("empId");
		String userName = (String) param.get("userName");
		String p_checkwork_type_id = (String) param.get("p_checkworkTypeId");
		String p_hour = (String) param.get("p_hour");
		String typeIds = (String) param.get("typeIds");
		String hour = (String) param.get("hour");
		String organId = (String) param.get("organId");
		int yearMonth = Integer.parseInt((String) param.get("yearMonth"));

		try {
			String[] checkworkTypeIdArr = p_checkwork_type_id.split(","); // 二次调整的checkworktypeid
			String[] p_hourValArr = p_hour.split(","); // 二次调整的计算结果
			String[] typeIdArr = typeIds.split(","); // 页面传过来的数据checkworktypeid
			String[] hourValArr = hour.split(","); // 页面传过来的数据hour

			empAttendanceDao.deleteEmpAttendanceDay(customerId, empId, day);
			empAttendanceDao.deleteEmpOvertimeDay(customerId, empId, day);
			empAttendanceDao.deleteEmpOtherDay(customerId, empId, day);

			int hourLength = hourValArr.length;
			for (int i = 0; i < hourLength; i++) {
				String typeId = typeIdArr[i];
				double hourVal = Double.parseDouble(hourValArr[i]);
				Map<String, Object> map = CollectionKit.newMap();
				map.put("id", p_emp_attendance_id);
				map.put("customerId", customerId);
				map.put("empKey", empKey);
				map.put("empId", empId);
				map.put("userName", userName);
				map.put("hourCount", hourVal);
				map.put("theoryHour", 7.5);
				map.put("organId", organId);
				map.put("checkworkTypeId", typeId);
				map.put("day", day);
				map.put("yearMonth", yearMonth);
				if (typeId.equals(NORMAL)) {
					// 正常考勤信息
					empAttendanceDao.insertEmpAttendanceDay(map);
				} else if (typeId.equals(OT)) {
					// 补丁 zrw不对加班处理 by jxzhang on 20171228
					// 加班信息
					// empAttendanceDao.insertEmpOvertimeDay(map);
				} else {
					// 其它考勤信息
					empAttendanceDao.insertEmpOtherDay(map);
				}
			}
			int p_hourLength = p_hourValArr.length;
			for (int i = 0; i < p_hourLength; i++) {
				String checkworkTypeId = checkworkTypeIdArr[i];
				double p_hourVal = Double.parseDouble(p_hourValArr[i]);
				if (checkworkTypeId.equals(OT)) {
					// 补丁 zrw不对加班处理 by jxzhang on 20171228
					// /** 更新可调休时间 */
					// Map<String, Object> vacation = empAttendanceDao.findEmpVacation(customerId,
					// empId);
					// double canLeave = Double.parseDouble((String) vacation.get("can_leave"));
					// double historyHour = Double.parseDouble((String)
					// vacation.get("history_hour"));
					// p_hourVal += historyHour;
					// double zheng = 0d;
					// double yu = 0d;
					// if(p_hourVal >= 0) {
					// if(p_hourVal >= 7.5) {
					// zheng = 1d;
					// yu = p_hourVal - 7.5;
					// if(yu >= 4) {
					// zheng += 0.5d;
					// yu = yu - 4;
					// }
					// } else if(p_hourVal < 7.5 && p_hourVal >= 4) {
					// zheng = 0.5d;
					// yu = p_hourVal - 4;
					// } else {
					// yu = p_hourVal;
					// }
					// } else {
					// if(p_hourVal < -7.5) {
					// zheng = -1.5d;
					// yu = 11.5 + p_hourVal;
					// } else if(p_hourVal >= -7.5 && p_hourVal < -4) {
					// zheng = 1d;
					// yu = 7.5 + p_hourVal;
					// } else {
					// zheng = 0.5d;
					// yu = 4 + p_hourVal;
					// }
					// }
					// canLeave += zheng;
					// empAttendanceDao.updateEmpVacation(customerId, empId,
					// String.valueOf(canLeave), String.valueOf(yu));
				} else if (checkworkTypeId.equals(ANNUAL)) {
					/** 考勤类型为年假需要更新年假时间 */
					Map<String, Object> vacation = empAttendanceDao.findEmpVacation(customerId, empId);
					double annualVal = Double.parseDouble((String) vacation.get("annual"));
					if (p_hourVal == 4) {
						annualVal = annualVal - 0.5;
					} else if (p_hourVal == 7.5) {
						annualVal = annualVal - 1;
					} else if (p_hourVal == -4) {
						annualVal = annualVal + 0.5;
					} else if (p_hourVal == -7.5) {
						annualVal = annualVal + 1;
					}
					empAttendanceDao.updateEmpAnnulVacation(customerId, empId, String.valueOf(annualVal));
				} else if (checkworkTypeId.equals(CANLEAVE)) {
					/** 考勤类型为调休需要更新可调休时间 */
					Map<String, Object> vacation = empAttendanceDao.findEmpVacation(customerId, empId);
					double canLeaveVal = Double.parseDouble((String) vacation.get("can_leave"));
					if (p_hourVal == 7.5) {
						canLeaveVal -= 1;
					} else if (p_hourVal == 4) {
						canLeaveVal -= 0.5;
					} else if (p_hourVal == -7.5) {
						canLeaveVal += 1;
					} else if (p_hourVal == -4) {
						canLeaveVal += 0.5;
					}
					empAttendanceDao.updateEmpLeaveVacation(customerId, empId, String.valueOf(canLeaveVal));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
