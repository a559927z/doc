package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;

/**
 * 月报培训概况dto
 * Created by wqcai on 16/08/30 030.
 */
public class MonthReportTrainGeneralDto implements Serializable {

    private static final long serialVersionUID = 1244586853095293460L;
    private String organId;             //机构ID
    private String organName;           //机构名称
    private Double budgetValue;         //预算费用
    private Double outlays;             //已花费用
    private Double coverageRate;        //培训覆盖率
    private Integer frequency;          //培训人次
    private Integer lecturerNum;        //讲师数
    private Double hours;               //人均学时

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Double getBudgetValue() {
        return budgetValue;
    }

    public void setBudgetValue(Double budgetValue) {
        this.budgetValue = budgetValue;
    }

    public Double getOutlays() {
        return outlays;
    }

    public void setOutlays(Double outlays) {
        this.outlays = outlays;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getLecturerNum() {
        return lecturerNum;
    }

    public void setLecturerNum(Integer lecturerNum) {
        this.lecturerNum = lecturerNum;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Double getCoverageRate() {
        return coverageRate;
    }

    public void setCoverageRate(Double coverageRate) {
        this.coverageRate = coverageRate;
    }
}
