package net.chinahrd.entity.dto.pc.laborEfficiency;

import java.io.Serializable;

/**
 * 劳动力效能
 * 
 * @author xwli and lma 2016-06-13
 */
public class LaborEfficiencyDto implements Serializable ,Comparable<LaborEfficiencyDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887912303933446422L;
	private String id;
	private String empId; // 员工id
	private String userName;// 员工名称
	private Double conNum;// 数
	private String imgPath;// 图片路径
	private Double hourCount;// 小时数
	private String date;// 日期
	private String checkWorkTypeId;// 考勤类型id
	private String checkWorkTypeName;// 考勤类型名称
	private String curtName;// 考勤类型type

	private String organId;
	private String parentId;
	private String organName;
	private String empName;
	private int hasChildren;
	private Double actualAttendance; // 实际出勤
	private Double beInAttendance; // 应出勤
	private Double attendanceRate; // 出勤率
	private Double rate;
	private int yearMonth;
	private String userKey;
	private Double overTime;
	private String other;
	private int day;
	private Double avgNum;// 人均时长
	
	private String locationError;
	private String errorMsg;
	private String customerId;
	private int count;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Double getActualAttendance() {
		return actualAttendance;
	}

	public void setActualAttendance(Double actualAttendance) {
		this.actualAttendance = actualAttendance;
	}

	public Double getBeInAttendance() {
		return beInAttendance;
	}

	public void setBeInAttendance(Double beInAttendance) {
		this.beInAttendance = beInAttendance;
	}

	public Double getAttendanceRate() {
		return attendanceRate;
	}

	public void setAttendanceRate(Double attendanceRate) {
		this.attendanceRate = attendanceRate;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getConNum() {
		return conNum;
	}

	public void setConNum(Double conNum) {
		this.conNum = conNum;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Double getHourCount() {
		return hourCount;
	}

	public void setHourCount(Double hourCount) {
		this.hourCount = hourCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCheckWorkTypeName() {
		return checkWorkTypeName;
	}

	public void setCheckWorkTypeName(String checkWorkTypeName) {
		this.checkWorkTypeName = checkWorkTypeName;
	}

	public String getCurtName() {
		return curtName;
	}

	public void setCurtName(String curtName) {
		this.curtName = curtName;
	}

	public Double getAvgNum() {
		return avgNum;
	}

	public void setAvgNum(Double avgNum) {
		this.avgNum = avgNum;
	}

	public String getCheckWorkTypeId() {
		return checkWorkTypeId;
	}

	public void setCheckWorkTypeId(String checkWorkTypeId) {
		this.checkWorkTypeId = checkWorkTypeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Double getOverTime() {
		return overTime;
	}

	public void setOverTime(Double overTime) {
		this.overTime = overTime;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getLocationError() {
		return locationError;
	}

	public void setLocationError(String locationError) {
		this.locationError = locationError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int compareTo(LaborEfficiencyDto o) {
		return this.getAttendanceRate()>o.getAttendanceRate()?1:this.getAttendanceRate()==o.getAttendanceRate()?0:-1;
	}
}
