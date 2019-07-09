package net.chinahrd.entity.dtozrw.pc.empAttendance;

import java.io.Serializable;

public class EmpAttendanceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5353835241577095880L;

	private String id;
	private String empId;
	private String userName;
	private String organId;
	private String organName;
	private String positionName;
	private String workPlace;
	private String entryYear;
	private String entryMonth;
	private String entryDay;
	private String entryDate;
	private int incumbencyTime; // 在职时间
	private float annual; // 年假
	private float total; // 可用调休
	private String typeId; // 出勤类型ID
	private String typeName; // 出勤类型
	private int num;
	private int yearMonth;

	private String clockInAm; // 上午上班时间
	private String clockOutAm; // 上午下班时间
	private String clockInPm; // 下午上班时间
	private String clockOutPm; // 下午下班时间
	private String adjustBegin; // 调整开始时间
	private String adjustEnd; // 调整结束时间
	private String calHour; //
	private String note;
	private String reason;
	private String sex; // 性别

	private double hourCount; // 时总数
	private String days;

	private String refresh; // 最后更新时间
	
	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public double getHourCount() {
		return hourCount;
	}

	public void setHourCount(double hourCount) {
		this.hourCount = hourCount;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getEntryYear() {
		return entryYear;
	}

	public void setEntryYear(String entryYear) {
		this.entryYear = entryYear;
	}

	public String getEntryMonth() {
		return entryMonth;
	}

	public void setEntryMonth(String entryMonth) {
		this.entryMonth = entryMonth;
	}

	public String getEntryDay() {
		return entryDay;
	}

	public void setEntryDay(String entryDay) {
		this.entryDay = entryDay;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public int getIncumbencyTime() {
		return incumbencyTime;
	}

	public void setIncumbencyTime(int incumbencyTime) {
		this.incumbencyTime = incumbencyTime;
	}

	public float getAnnual() {
		return annual;
	}

	public void setAnnual(float annual) {
		this.annual = annual;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getClockInAm() {
		return clockInAm;
	}

	public void setClockInAm(String clockInAm) {
		this.clockInAm = clockInAm;
	}

	public String getClockOutAm() {
		return clockOutAm;
	}

	public void setClockOutAm(String clockOutAm) {
		this.clockOutAm = clockOutAm;
	}

	public String getClockInPm() {
		return clockInPm;
	}

	public void setClockInPm(String clockInPm) {
		this.clockInPm = clockInPm;
	}

	public String getClockOutPm() {
		return clockOutPm;
	}

	public void setClockOutPm(String clockOutPm) {
		this.clockOutPm = clockOutPm;
	}

	public String getAdjustBegin() {
		return adjustBegin;
	}

	public void setAdjustBegin(String adjustBegin) {
		this.adjustBegin = adjustBegin;
	}

	public String getAdjustEnd() {
		return adjustEnd;
	}

	public void setAdjustEnd(String adjustEnd) {
		this.adjustEnd = adjustEnd;
	}

	public String getCalHour() {
		return calHour;
	}

	public void setCalHour(String calHour) {
		this.calHour = calHour;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
