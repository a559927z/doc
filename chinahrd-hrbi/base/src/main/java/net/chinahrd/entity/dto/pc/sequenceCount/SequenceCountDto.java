package net.chinahrd.entity.dto.pc.sequenceCount;

import java.io.Serializable;

/**
 * 职位序列统计dto
 * Created by wqcai on 15/11/03 0021.
 */
public class SequenceCountDto implements Serializable {
    private static final long serialVersionUID = 3676739173446174649L;

    private String sequenceId;                  //序列ID
    private String sequenceSubId;               //子序列ID
    private String abilityId;                   //职位层级ID
    private String abilityPrefix;               //职位层级前缀
    private String abilityName;                 //职位层级名称
    private Integer abilityShowIndex;            //职位层级索引
    private String jobTitleId;                  //职衔ID
    private String jobTitleName;                //职衔名称
    private Integer jobTitleShowIndex;          //职衔索引
    private String abilityLvId;                 //职级ID
    private String abilityLvPrefix;             //职级前缀
    private Integer abilityLvShowIndex;          //职级索引
    private Integer empCount;                    //在职人数

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getSequenceSubId() {
        return sequenceSubId;
    }

    public void setSequenceSubId(String sequenceSubId) {
        this.sequenceSubId = sequenceSubId;
    }

    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getAbilityLvId() {
        return abilityLvId;
    }

    public void setAbilityLvId(String abilityLvId) {
        this.abilityLvId = abilityLvId;
    }

    public Integer getEmpCount() {
        return empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }

    public String getAbilityPrefix() {
        return abilityPrefix;
    }

    public void setAbilityPrefix(String abilityPrefix) {
        this.abilityPrefix = abilityPrefix;
    }

    public String getAbilityLvPrefix() {
        return abilityLvPrefix;
    }

    public void setAbilityLvPrefix(String abilityLvPrefix) {
        this.abilityLvPrefix = abilityLvPrefix;
    }

    public Integer getAbilityShowIndex() {
        return abilityShowIndex;
    }

    public void setAbilityShowIndex(Integer abilityShowIndex) {
        this.abilityShowIndex = abilityShowIndex;
    }

    public Integer getJobTitleShowIndex() {
        return jobTitleShowIndex;
    }

    public void setJobTitleShowIndex(Integer jobTitleShowIndex) {
        this.jobTitleShowIndex = jobTitleShowIndex;
    }

    public Integer getAbilityLvShowIndex() {
        return abilityLvShowIndex;
    }

    public void setAbilityLvShowIndex(Integer abilityLvShowIndex) {
        this.abilityLvShowIndex = abilityLvShowIndex;
    }
}
