package net.chinahrd.entity.dto.app.laborEfficiency;

import java.io.Serializable;

/**
 * 劳动力效能
 * @author htpeng
 *2016年8月19日下午2:03:50
 */
public class LaborEfficiencyDto implements Serializable {

	private static final long serialVersionUID = -3110509706164227452L;
	private String organId;
	private String organName;
	private String typeName;
	private String empId;
	private String empName;
	private Double num;// 小时数
	private String rate;// 劳动力效能趋势   -表示无效
	private Double totalNum;// 总小时
	private Double avgNum;// 平均小时
	private String avgRate;// 平均趋势
	private String totalRate;// 总趋势
	private Double actualAttendance;// 
	private Double beInAttendance;// 
	private Double attendanceRate;//
	private String curtName;// 考勤类型type
	private String checkWorkTypeId;// 考勤类型id
	private String checkWorkTypeName;// 考勤类型名称
	/**
	 * @return the checkWorkTypeId
	 */
	public String getCheckWorkTypeId() {
		return checkWorkTypeId;
	}
	/**
	 * @param checkWorkTypeId the checkWorkTypeId to set
	 */
	public void setCheckWorkTypeId(String checkWorkTypeId) {
		this.checkWorkTypeId = checkWorkTypeId;
	}
	/**
	 * @return the checkWorkTypeName
	 */
	public String getCheckWorkTypeName() {
		return checkWorkTypeName;
	}
	/**
	 * @param checkWorkTypeName the checkWorkTypeName to set
	 */
	public void setCheckWorkTypeName(String checkWorkTypeName) {
		this.checkWorkTypeName = checkWorkTypeName;
	}
	/**
	 * @return the curtName
	 */
	public String getCurtName() {
		return curtName;
	}
	/**
	 * @param curtName the curtName to set
	 */
	public void setCurtName(String curtName) {
		this.curtName = curtName;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public Double getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
	public Double getAvgNum() {
		return avgNum;
	}
	public void setAvgNum(Double avgNum) {
		this.avgNum = avgNum;
	}
	public String getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(String avgRate) {
		this.avgRate = avgRate;
	}
	public String getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
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
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	
	
}
