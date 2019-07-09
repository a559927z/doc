package net.chinahrd.entity.dto.pc.manpowerCost;

import java.io.Serializable;

/**
 * 人力成本 上月 本月  上年 本年模本
 * @author htpeng
 *2015年12月30日上午11:19:09
 */
public class ManpowerCompareDto implements Serializable{
	private static final long serialVersionUID = 4168385763809429269L;
	private double cost;            //人力成本
	private double costAvg;         //平均成本
	private double total;            //总成本
	private int yearMonth;
	private int type;   //1本年 或上月   0上上月或上年
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getCostAvg() {
		return costAvg;
	}
	public void setCostAvg(double costAvg) {
		this.costAvg = costAvg;
	}
	public int getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

}
