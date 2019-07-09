package net.chinahrd.entity.dto.pc.salaryBoard;

import java.io.Serializable;


/**
 * 福利 dto
 * 
 * @author qpzhu by 2016-04-08
 */
public class SalaryWelfareDto implements Serializable ,Comparable<SalaryWelfareDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7053223378273632629L;
	
	private String organizationName;//组织名称
	
	private double sumWelfare;//福利总额
	
	private double sumPay;//薪酬总额
	
	private double welfare;//福利费用
	
	private String yearMonth;//年月
	
	private String year;//年度
	
	private double coverageRate;// 覆盖率
	
	private String welfareKey;//福利key
	
	private String welfareName;//福利名称
	
	private String empName;//员工姓名
	
	private String payDate;//缴纳时间
	
	private double payAmount;//缴纳金额
	
	private String grantDate;//发放时间
	
	private double grantAmount;//发放金额
	
	private String holidayTime;//假期时间
	
	private double welfareShare;//福利占薪酬比
	
	private double avgWelfare;//平均福利
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public double getSumWelfare() {
		return sumWelfare;
	}

	public void setSumWelfare(double sumWelfare) {
		this.sumWelfare = sumWelfare;
	}

	public double getSumPay() {
		return sumPay;
	}

	public void setSumPay(double sumPay) {
		this.sumPay = sumPay;
	}

	public double getWelfare() {
		return welfare;
	}

	public void setWelfare(double welfare) {
		this.welfare = welfare;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth.substring(2, 4)+"/"+yearMonth.substring(4, 6);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getCoverageRate() {
		return coverageRate;
	}

	public void setCoverageRate(double coverageRate) {
		this.coverageRate = coverageRate;
	}

	public String getWelfareName() {
		return welfareName;
	}

	public void setWelfareName(String welfareName) {
		this.welfareName = welfareName;
	}

	public String getWelfareKey() {
		return welfareKey;
	}

	public void setWelfareKey(String welfareKey) {
		this.welfareKey = welfareKey;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(String grantDate) {
		this.grantDate = grantDate;
	}

	public double getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(double grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getHolidayTime() {
		return holidayTime;
	}

	public void setHolidayTime(String holidayTime) {
		this.holidayTime = holidayTime;
	}

	public double getWelfareShare() {
		return welfareShare;
	}

	public void setWelfareShare(double welfareShare) {
		this.welfareShare = welfareShare;
	}

	public double getAvgWelfare() {
		return avgWelfare;
	}

	public void setAvgWelfare(double avgWelfare) {
		this.avgWelfare = avgWelfare;
	}

	@Override
	public int compareTo(SalaryWelfareDto o) {
		
		return this.welfare>o.getWelfare()?1:this.welfare==o.getWelfare()?0:-1;
	}
	
}
