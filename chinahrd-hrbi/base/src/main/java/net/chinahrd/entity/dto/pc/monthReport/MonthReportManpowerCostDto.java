package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;

public class MonthReportManpowerCostDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2438729055008253554L;
	
    private String organName;           //所属机构名称
    private Double sumYearBudget;        //年度预算
    private Double sumYearCost;           //年度费用
    private Double yearRate;			//年度成本率
    private Double monthCost;			//月度费用
    private Double monthAvg;			//月度人均成本
    private Double sameRatio;			//同比
    private Double basicRatio;			//环比
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public Double getSumYearBudget() {
		return sumYearBudget;
	}
	public void setSumYearBudget(Double sumYearBudget) {
		this.sumYearBudget = sumYearBudget;
	}
	public Double getSumYearCost() {
		return sumYearCost;
	}
	public void setSumYearCost(Double sumYearCost) {
		this.sumYearCost = sumYearCost;
	}
	public Double getYearRate() {
		return yearRate;
	}
	public void setYearRate(Double yearRate) {
		this.yearRate = yearRate;
	}
	public Double getMonthCost() {
		return monthCost;
	}
	public void setMonthCost(Double monthCost) {
		this.monthCost = monthCost;
	}
	public Double getMonthAvg() {
		return monthAvg;
	}
	public void setMonthAvg(Double monthAvg) {
		this.monthAvg = monthAvg;
	}
	public Double getSameRatio() {
		return sameRatio;
	}
	public void setSameRatio(Double sameRatio) {
		this.sameRatio = sameRatio;
	}
	public Double getBasicRatio() {
		return basicRatio;
	}
	public void setBasicRatio(Double basicRatio) {
		this.basicRatio = basicRatio;
	}

}
