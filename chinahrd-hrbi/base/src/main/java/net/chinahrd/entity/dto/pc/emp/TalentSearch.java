package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.enums.AgeIntervalEnum;


public class TalentSearch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3926745508315108166L;

    private String keyName;
    private String[] empTypeArray;
    private List<AgeIntervalEnum> ageArray;
    private String[] sexArray;
    private String[] eduArray;
    private String organization;
    private String[] mainSequenceArray;
    private String[] subSequenceArray;
    private String[] abilityArray;
    private String[] jobTitleArray;
    private String performanceTimeOne;
    private String performanceTimeTwo;
    private String[] performanceRankArray;
    private String pastHistory;
    private String projectExperience;
    private String trainingExperience;
    private List<String> performanceCondition;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String[] getEmpTypeArray() {
        return empTypeArray;
    }

    public void setEmpTypeArray(String[] empTypeArray) {
        this.empTypeArray = empTypeArray;
    }


    public String[] getSexArray() {
        return sexArray;
    }

    public void setSexArray(String[] sexArray) {
        this.sexArray = sexArray;
    }

    public String[] getEduArray() {
        return eduArray;
    }

    public void setEduArray(String[] eduArray) {
        this.eduArray = eduArray;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String[] getMainSequenceArray() {
        return mainSequenceArray;
    }

    public void setMainSequenceArray(String[] mainSequenceArray) {
        this.mainSequenceArray = mainSequenceArray;
    }

    public String[] getSubSequenceArray() {
        return subSequenceArray;
    }

    public void setSubSequenceArray(String[] subSequenceArray) {
        this.subSequenceArray = subSequenceArray;
    }

    public String[] getAbilityArray() {
        return abilityArray;
    }

    public void setAbilityArray(String[] abilityArray) {
        this.abilityArray = abilityArray;
    }

    public String[] getJobTitleArray() {
        return jobTitleArray;
    }

    public void setJobTitleArray(String[] jobTitleArray) {
        this.jobTitleArray = jobTitleArray;
    }

    public String getPerformanceTimeOne() {
        return performanceTimeOne;
    }

    public void setPerformanceTimeOne(String performanceTimeOne) {
        this.performanceTimeOne = performanceTimeOne;
    }

    public String getPerformanceTimeTwo() {
        return performanceTimeTwo;
    }

    public void setPerformanceTimeTwo(String performanceTimeTwo) {
        this.performanceTimeTwo = performanceTimeTwo;
    }

    public String[] getPerformanceRankArray() {
        return performanceRankArray;
    }

    public void setPerformanceRankArray(String[] performanceRankArray) {
        this.performanceRankArray = performanceRankArray;
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory;
    }

    public String getProjectExperience() {
        return projectExperience;
    }

    public void setProjectExperience(String projectExperience) {
        this.projectExperience = projectExperience;
    }

    public String getTrainingExperience() {
        return trainingExperience;
    }

    public void setTrainingExperience(String trainingExperience) {
        this.trainingExperience = trainingExperience;
    }

    public List<String> getPerformanceCondition() {
        return performanceCondition;
    }

    public void setPerformanceCondition(List<String> performanceCondition) {
        this.performanceCondition = performanceCondition;
    }

    public List<AgeIntervalEnum> getAgeArray() {
        return ageArray;
    }

    public void setAgeArray(List<AgeIntervalEnum> ageArray) {
        this.ageArray = ageArray;
    }
}
