package net.chinahrd.entity.dtozte.pc.probenefit;

import java.io.Serializable;

/***
 * 人均效益dto
 */
public class AvgBenefitDto implements Serializable {

    private static final long serialVersionUID = -7031519179458114700L;
    /* 机构ID */
    private String organId;
    /* 年月 */
    private Integer yearMonth;
    /* 人均效益（收入） */
    private Double income;
    /* 人均效益（毛利） */
    private Double gain;
    /* 上一月人均效益（收入） */
    private Double prevIncome;
    /* 上一月人均效益（毛利） */
    private Double prevGain;
    /* 与上个月相比的幅度（收入） */
    private Double incomeChain;
    /* 与上个月相比的幅度（毛利） */
    private Double gainChain;

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public Double getGainChain() {
        return gainChain;
    }

    public void setGainChain(Double gainChain) {
        this.gainChain = gainChain;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getIncomeChain() {
        return incomeChain;
    }

    public void setIncomeChain(Double incomeChain) {
        this.incomeChain = incomeChain;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Double getPrevGain() {
        return prevGain;
    }

    public void setPrevGain(Double prevGain) {
        this.prevGain = prevGain;
    }

    public Double getPrevIncome() {
        return prevIncome;
    }

    public void setPrevIncome(Double prevIncome) {
        this.prevIncome = prevIncome;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }
}
