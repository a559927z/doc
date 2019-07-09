package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;

/**
 * 月报人员分布统计dto
 * Created by wqcai on 16/08/30 030.
 */
public class MonthReportEmpCountDto implements Serializable {
    private static final long serialVersionUID = -4619302580323008823L;
    private String abilityName;         //职级名称（能力层级）
    private String sequenceId;          //序列ID
    private String sequenceName;        //序列名称
    private Integer empNumber;          //员工数量

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Integer getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(Integer empNumber) {
        this.empNumber = empNumber;
    }
}
