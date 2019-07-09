package net.chinahrd.entity.dto.pc.benefit;

import java.io.Serializable;

/**
 * 人均效益及销售利润等相关dto
 * Created by wqcai on 16/6/16.
 */
public class PerBenefitAmountDto implements Serializable{

	private static final long serialVersionUID = 5825839396593105075L;

	private Integer yearMonth;		//年月
	private Double gainAmount;		//利润总额
	private Double salesAmount;		//销售总额
	private Double fteValue;		//等效全职员工数
	private Double benefit;			//人均效益(利润总额/等效全职员工数)
	private Double yoyGainAmount;		//同比利润总额
	private Double yoySalesAmount;		//同比销售总额
	private Double yoyFteValue;		//同比等效全职员工数
	private Double yoyBenefit;			//同比人均效益(利润总额/等效全职员工数)

	public Double getBenefit() {
		return benefit;
	}

	public void setBenefit(Double benefit) {
		this.benefit = benefit;
	}

	public Double getFteValue() {
		return fteValue;
	}

	public void setFteValue(Double fteValue) {
		this.fteValue = fteValue;
	}

	public Double getGainAmount() {
		return gainAmount;
	}

	public void setGainAmount(Double gainAmount) {
		this.gainAmount = gainAmount;
	}

	public Double getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Double getYoyBenefit() {
		return yoyBenefit;
	}

	public void setYoyBenefit(Double yoyBenefit) {
		this.yoyBenefit = yoyBenefit;
	}

	public Double getYoyFteValue() {
		return yoyFteValue;
	}

	public void setYoyFteValue(Double yoyFteValue) {
		this.yoyFteValue = yoyFteValue;
	}

	public Double getYoyGainAmount() {
		return yoyGainAmount;
	}

	public void setYoyGainAmount(Double yoyGainAmount) {
		this.yoyGainAmount = yoyGainAmount;
	}

	public Double getYoySalesAmount() {
		return yoySalesAmount;
	}

	public void setYoySalesAmount(Double yoySalesAmount) {
		this.yoySalesAmount = yoySalesAmount;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}
}
