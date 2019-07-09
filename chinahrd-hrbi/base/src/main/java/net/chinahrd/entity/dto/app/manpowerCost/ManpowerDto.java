package net.chinahrd.entity.dto.app.manpowerCost;

/**
 * 人力成本  人均成本  
 * @author htpeng
 *2015年12月30日上午11:19:09
 */
public class ManpowerDto {
	private double cost;      //人力成本
	private double costAvg;      //平均人力成本
	private double gainAmount;      //营业利润
	private double salesAmount;      //销售总额
	private int yearMonth;
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}
	public double getCostAvg() {
		return costAvg;
	}
	public void setCostAvg(double costAvg) {
		this.costAvg = costAvg;
	}
	public double getGainAmount() {
		return gainAmount;
	}
	public void setGainAmount(double gainAmount) {
		this.gainAmount = gainAmount;
	}
	public double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(double salesAmount) {
		this.salesAmount = salesAmount;
	}

}
