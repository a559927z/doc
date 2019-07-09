package net.chinahrd.entity.dto.pc.monthReport;

import net.chinahrd.utils.ArithUtil;

import java.io.Serializable;

/**
 * 月报销售统计dto
 * Created by wqcai on 16/08/30 030.
 */
public class MonthReportSalesCountDto implements Serializable {
    private static final long serialVersionUID = -5135562534710225142L;
    /* 商品ID/机构ID */
    private String itemId;
    /* 商品名称/机构名称 */
    private String itemName;
    /* 年月 */
    private Integer yearMonth;
    /* 人员数量 */
    private Integer empNumber;
    /* 销售数量 */
    private Integer salesNumber;
    /* 人均销量 */
    private Integer avgNumber;
    /* 销售额 */
    private Double salesMoney;
    /* 销售目标额 */
    private Double salesTarget;
    /* 回款额 */
    private Double returnAmount;
    /* 已回款额 */
    private Double payment;
    /* 达标率 */
    private Double standardsRate;
    /* 回款率 */
    private Double returnAmountRate;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(Integer empNumber) {
        this.empNumber = empNumber;
    }

    public Integer getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(Integer salesNumber) {
        this.salesNumber = salesNumber;
    }

    public Integer getAvgNumber() {
        return avgNumber == null ? getSalesAgeNumber() : avgNumber;
    }

    public void setAvgNumber(Integer avgNumber) {
        this.avgNumber = avgNumber;
    }

    public Double getSalesMoney() {
        return salesMoney;
    }

    public void setSalesMoney(Double salesMoney) {
        this.salesMoney = salesMoney;
    }

    public Double getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(Double salesTarget) {
        this.salesTarget = salesTarget;
    }

    public Double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getStandardsRate() {
        return standardsRate == null ? getSalesStandardsRate() : standardsRate;
    }

    public void setStandardsRate(Double standardsRate) {
        this.standardsRate = standardsRate;
    }

    public Double getReturnAmountRate() {
        return returnAmountRate == null ? getSalesReturnAmountRate() : returnAmountRate;
    }

    public void setReturnAmountRate(Double returnAmountRate) {
        this.returnAmountRate = returnAmountRate;
    }

    private Double getSalesStandardsRate() {
        if (salesMoney == null || salesTarget == null || salesTarget.doubleValue() == 0d) return 0d;
        return ArithUtil.div(salesMoney, salesTarget, 4);
    }

    private Double getSalesReturnAmountRate() {
        if (payment == null || returnAmount == null || returnAmount.doubleValue() == 0d) return 0d;
        return ArithUtil.div(payment, returnAmount, 4);
    }

    private Integer getSalesAgeNumber() {
        if (salesNumber == null || empNumber == null || empNumber.doubleValue() == 0) return 0;
        return (int) ArithUtil.div(salesNumber.doubleValue(), empNumber.doubleValue(), 0);
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }
}
