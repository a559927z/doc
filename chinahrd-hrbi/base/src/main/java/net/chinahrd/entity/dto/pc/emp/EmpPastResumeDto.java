package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 过往履历dto
 */
public class EmpPastResumeDto implements Serializable {
    private static final long serialVersionUID = 8965256342008659859L;
    private String id;
    private String empId;
    /**
     * 就职单位
     */
    private String workUnit;
    /**
     * 入职时间
     */
    private Timestamp entryDate;
    /**
     * 离职时间
     */
    private Timestamp runOffDate;
    /**
     * 部门
     */
    private String departmentName;
    /**
     * 岗位
     */
    private String positionName;
    /**
     * 奖惩名称
     */
    private String bonusPenaltyName;
    /**
     * 证明人
     */
    private String witnessName;
    /**
     * 证明人联系方式
     */
    private String witnessContactInfo;
    /**
     * 变动原因
     */
    private String changeReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getRunOffDate() {
        return runOffDate;
    }

    public void setRunOffDate(Timestamp runOffDate) {
        this.runOffDate = runOffDate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getBonusPenaltyName() {
        return bonusPenaltyName;
    }

    public void setBonusPenaltyName(String bonusPenaltyName) {
        this.bonusPenaltyName = bonusPenaltyName;
    }

    public String getWitnessName() {
        return witnessName;
    }

    public void setWitnessName(String witnessName) {
        this.witnessName = witnessName;
    }

    public String getWitnessContactInfo() {
        return witnessContactInfo;
    }

    public void setWitnessContactInfo(String witnessContactInfo) {
        this.witnessContactInfo = witnessContactInfo;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

}
