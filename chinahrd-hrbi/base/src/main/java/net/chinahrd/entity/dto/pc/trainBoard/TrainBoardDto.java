package net.chinahrd.entity.dto.pc.trainBoard;

import java.io.Serializable;


/**
 * 培训看板dto
 * 
 * @author qpzhu by 2016-03-29
 */
public class TrainBoardDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1700858290607187325L;
	
	private String year;//培训年度
	
	private String yearMonth;//培训年月
	
	private String quarter;

	private double cost;//培训费用
	
	private double budgetCost;//培训预算
	
	private double budgetRate;//预算费用率
	
	private double completeRate;//完成率
	
	private double avgCost;//人均费用
	
	private String organizationId;//组织编号
	
	private String organizationName;//组织名称
	
	private double hours;//学时
	
	private Integer lecturerNum;//讲师数
	
	private double soure;//满意度分数
	
	private String courseTypeKey;//培训类型Key
	
	private String courseTypeName;//培训类型
	
	private Integer frequency;//次数
	
	private double coverageRate;//覆盖率

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getBudgetCost() {
		return budgetCost;
	}

	public void setBudgetCost(double budgetCost) {
		this.budgetCost = budgetCost;
	}

	public double getBudgetRate() {
		return budgetRate;
	}

	public void setBudgetRate(double budgetRate) {
		this.budgetRate = budgetRate;
	}

	public double getCompleteRate() {
		return completeRate;
	}

	public void setCompleteRate(double completeRate) {
		this.completeRate = completeRate;
	}

	public double getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public Integer getLecturerNum() {
		return lecturerNum;
	}

	public void setLecturerNum(Integer lecturerNum) {
		this.lecturerNum = lecturerNum;
	}

	public double getSoure() {
		return soure;
	}

	public void setSoure(double soure) {
		this.soure = soure;
	}

	public String getCourseTypeKey() {
		return courseTypeKey;
	}

	public void setCourseTypeKey(String courseTypeKey) {
		this.courseTypeKey = courseTypeKey;
	}

	public String getCourseTypeName() {
		return courseTypeName;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public double getCoverageRate() {
		return coverageRate;
	}

	public void setCoverageRate(double coverageRate) {
		this.coverageRate = coverageRate;
	}

	

	
}
