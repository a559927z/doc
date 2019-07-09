package net.chinahrd.entity.dto.pc.salaryBoard;

import java.io.Serializable;


/**
 * 工资 dto
 * 
 * @author qpzhu by 2016-04-07
 */
public class SalaryWageDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7831788761678781115L;
	
	private double sumSalary;//工资总额
	
	private double sumPay;//薪酬总额
	
	private double wageShare;//占比
	
	private double avgSalary;//平均工资
	
	private String structureName;//工资结构名称
	
	private double salaryValue;//工资

	private double fixed;//固定占比
	
	private double flot;//浮动占比
	
	private String sequenceId;//职位序列
	
	private String sequenceName;//职位序列
	
	private String abilityName;//职位层级
	
	private String organizationId;//组织名称
	
	private String organizationName;//组织名称
	
	private double fixedSalary;//固定工资
	
	private double flotSalary;//浮动工资
	
	private String year;//年
	
	private String yearMonth;//年月
	
	private double bonus;//年终奖
	
	private double profit;//利润
	
	private double bonusProportion;//年终奖占利润比
	
	private double cost;//费用
	
	public double getSumSalary() {
		return sumSalary;
	}

	public void setSumSalary(double sumSalary) {
		this.sumSalary = sumSalary;
	}

	public double getSumPay() {
		return sumPay;
	}

	public void setSumPay(double sumPay) {
		this.sumPay = sumPay;
	}

	public double getWageShare() {
		return wageShare;
	}

	public void setWageShare(double wageShare) {
		this.wageShare = wageShare;
	}

	public double getAvgSalary() {
		return avgSalary;
	}

	public void setAvgSalary(double avgSalary) {
		this.avgSalary = avgSalary;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public double getSalaryValue() {
		return salaryValue;
	}

	public void setSalaryValue(double salaryValue) {
		this.salaryValue = salaryValue;
	}

	public double getFixed() {
		return fixed;
	}

	public void setFixed(double fixed) {
		this.fixed = fixed;
	}

	public double getFlot() {
		return flot;
	}

	public void setFlot(double flot) {
		this.flot = flot;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public double getFixedSalary() {
		return fixedSalary;
	}

	public void setFixedSalary(double fixedSalary) {
		this.fixedSalary = fixedSalary;
	}

	public double getFlotSalary() {
		return flotSalary;
	}

	public void setFlotSalary(double flotSalary) {
		this.flotSalary = flotSalary;
	}

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
		this.yearMonth = yearMonth.substring(2, 4)+"/"+yearMonth.substring(4, 6);
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getBonusProportion() {
		return bonusProportion;
	}

	public void setBonusProportion(double bonusProportion) {
		this.bonusProportion = bonusProportion;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	
}
