package net.chinahrd.entity.dto.pc.benefit;

import net.chinahrd.utils.ArithUtil;

import java.io.Serializable;

/***
 * 万元薪资、人力资金执行率 dto
 */
public class PerBenefitResultDto implements Serializable {

    private static final long serialVersionUID = 590332835821841167L;
    /* 机构ID */
    private String organId;
    /* 机构名称 */
    private String organName;
    /* 年度 */
    private Integer year;
    /* 预算成本 */
    private Double budgetValue;
    /* 人力成本 */
    private Double cost;
    /* 营业收入 */
    private Double salesAmount;
    /* 营业支出 */
    private Double expenditure;
    /* 营业利润 */
    private Double gainAmount;
    /* 万元薪资（收入） */
    private Double payIncome;
    /* 万元薪资（毛利） */
    private Double payGain;
    /* 人力成执行率 */
    private Double executeRate;

    public Double getBudgetValue() {
        return budgetValue;
    }

    public void setBudgetValue(Double budgetValue) {
        this.budgetValue = budgetValue;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    public Double getGainAmount() {
        return gainAmount;
    }

    public void setGainAmount(Double gainAmount) {
        this.gainAmount = gainAmount;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPayGain() {
        return null != gainAmount && (null != cost && cost > 0d) ? ArithUtil.div(gainAmount, cost, 4) : (payGain != null ? payGain : 0);
    }

    public void setPayGain(Double payGain) {
        this.payGain = payGain;
    }

    public Double getPayIncome() {
        return null != salesAmount && (null != cost && cost > 0d) ? ArithUtil.div(salesAmount, cost, 4) : (payIncome != null ? payIncome : 0);
    }

    public void setPayIncome(Double payIncome) {
        this.payIncome = payIncome;
    }

    public Double getExecuteRate() {
        return null != cost && (null != budgetValue && budgetValue > 0d) ? ArithUtil.div(cost, budgetValue, 4) : (executeRate != null ? executeRate : 0);
    }

    public void setExecuteRate(Double executeRate) {
        this.executeRate = executeRate;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
