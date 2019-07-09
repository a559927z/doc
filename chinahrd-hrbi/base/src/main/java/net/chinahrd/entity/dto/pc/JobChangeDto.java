package net.chinahrd.entity.dto.pc;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 员工工作异常dto
 * Created by wqcai on 15/10/13 0013.
 */
public class JobChangeDto implements Serializable {
    private static final long serialVersionUID = -1308229008179630514L;

    private String jobChangeId;         //主键ID
    private String empId;               //员工ID
    private String organName;           //机构/部门
    private String positionName;        //岗位
    private String sequenceName;        //主序列
    private String subSequenceName;     //子序列
    private String abilityName;         //能力层级
    private String abilityLvName;       //职级
    private String rankName;            //职级展示
    private String changeType;         //异动类型
    private Timestamp changeDate;       //异动日期

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }


    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getAbilityLvName() {
        return abilityLvName;
    }

    public void setAbilityLvName(String abilityLvName) {
        this.abilityLvName = abilityLvName;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getSubSequenceName() {
        return subSequenceName;
    }

    public void setSubSequenceName(String subSequenceName) {
        this.subSequenceName = subSequenceName;
    }

    public String getJobChangeId() {
        return jobChangeId;
    }

    public void setJobChangeId(String jobChangeId) {
        this.jobChangeId = jobChangeId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getChangeType() {
        return changeType;
    }
}
