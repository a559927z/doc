package net.chinahrd.entity.dto.pc.productivity;

import net.chinahrd.utils.ArithUtil;

import java.io.Serializable;

/**
 * 人均效益Dto
 * Created by wqcai on 16/5/30.
 */
public class AvgBenefitTrendDto implements Serializable {

    private static final long serialVersionUID = -2257204712692535804L;

    /* 人力成本 */
    private Double cost;
    /* 人均效益 */
    private Double benefit;
    /* 年月 */
    private int yearMonth;
    /* 环比（上一个月）人均效益 */
    private Double chainBenefit;
    /* 环比率 */
    private Double chainRate;
    /* 同比（上一年同月）人均效益 */
    private Double yoyBenefit;
    /* 同比率 */
    private Double yoyRate;

    public Double getBenefit() {
        return benefit;
    }

    public void setBenefit(Double benefit) {
        this.benefit = benefit;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public int getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(int yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getChainBenefit() {
        return chainBenefit;
    }

    public void setChainBenefit(Double chainBenefit) {
        this.chainBenefit = chainBenefit;
    }

    public Double getChainRate() {
        return chainBenefit != null && chainBenefit > 0 ? ArithUtil.div(ArithUtil.sub(benefit, chainBenefit), chainBenefit, 4) : (chainRate != null ? chainRate : 0);
    }

    public void setChainRate(Double chainRate) {
        this.chainRate = chainRate;
    }

    public Double getYoyBenefit() {
        return yoyBenefit;
    }

    public void setYoyBenefit(Double yoyBenefit) {
        this.yoyBenefit = yoyBenefit;
    }

    public Double getYoyRate() {
        return yoyBenefit != null && yoyBenefit > 0 ? ArithUtil.div(ArithUtil.sub(benefit, yoyBenefit), yoyBenefit, 4) : (yoyRate != null ? yoyRate : 0);
    }

    public void setYoyRate(Double yoyRate) {
        this.yoyRate = yoyRate;
    }
}
