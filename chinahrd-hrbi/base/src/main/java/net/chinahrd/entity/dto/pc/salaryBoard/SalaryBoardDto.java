package net.chinahrd.entity.dto.pc.salaryBoard;

import java.io.Serializable;


/**
 * 薪酬总览 dto
 * 
 * @author qpzhu by 2016-04-06
 */
public class SalaryBoardDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8342381325392216471L;
	
	private String year;//年度
	
	private String month;//月份

	private double payValue;//年度预算
	
	private double compareValue;//与去年比较
	
	private double sumPay;//累计，薪酬额
	
	private double totalCompareValue;//与去年累计比较
	
	private String yearMonth;//年月
	
	private double cost;//费用，人力成本，薪酬福利费用
	
	private String organId;//组织ID
	
	private String organizationName;//组织名称
	
	private double avgPay;//平均薪酬
	
	private double kpi;//kpi值
	
	private double salesAmount;//销售额，营业收入
	
	private double expenditure;//营业支出
	
	private double gainAmount;//营业利润
	
	private double rateReturn;//回报率
	
	private String positionName;//岗位名称
	
	private String jobTitleName;//职衔，岗位类别
	
	private double difference;//岗位薪酬差异度
	
	private String empName;//员工姓名
	
	private double crValue;//cr值
	
	private double sumSalary;//工资总额
	
	private double sumWelfare;//福利总额
	
	private double quantileValue;//分位绝对值
	
	private double bitValue;//分位值
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getSumPay() {
		return sumPay;
	}

	public void setSumPay(double sumPay) {
		this.sumPay = sumPay;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth.substring(2, 4)+"/"+yearMonth.substring(4, 6);
	}

	public double getPayValue() {
		return payValue;
	}

	public void setPayValue(double payValue) {
		this.payValue = payValue;
	}

	public double getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(double compareValue) {
		this.compareValue = compareValue;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public double getAvgPay() {
		return avgPay;
	}

	public void setAvgPay(double avgPay) {
		this.avgPay = avgPay;
	}

	public double getKpi() {
		return kpi;
	}

	public void setKpi(double kpi) {
		this.kpi = kpi;
	}

	public double getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(double salesAmount) {
		this.salesAmount = salesAmount;
	}

	public double getGainAmount() {
		return gainAmount;
	}

	public void setGainAmount(double gainAmount) {
		this.gainAmount = gainAmount;
	}

	public double getRateReturn() {
		return rateReturn;
	}

	public void setRateReturn(double rateReturn) {
		this.rateReturn = rateReturn;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public double getDifference() {
		return difference;
	}

	public void setDifference(double difference) {
		this.difference = difference;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getCrValue() {
		return crValue;
	}

	public void setCrValue(double crValue) {
		this.crValue = crValue;
	}

	public double getSumSalary() {
		return sumSalary;
	}

	public void setSumSalary(double sumSalary) {
		this.sumSalary = sumSalary;
	}

	public double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}

	public double getTotalCompareValue() {
		return totalCompareValue;
	}

	public void setTotalCompareValue(double totalCompareValue) {
		this.totalCompareValue = totalCompareValue;
	}

	public double getQuantileValue() {
		return quantileValue;
	}

	public void setQuantileValue(double quantileValue) {
		this.quantileValue = quantileValue;
	}

	public double getBitValue() {
		return bitValue;
	}

	public void setBitValue(double bitValue) {
		this.bitValue = bitValue;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getSumWelfare() {
		return sumWelfare;
	}

	public void setSumWelfare(double sumWelfare) {
		this.sumWelfare = sumWelfare;
	}
	
}
