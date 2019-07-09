package net.chinahrd.entity.dto.pc.manage;

import java.io.Serializable;

/**
 * Created by dcli on 2015/12/3.
 */
public class PerformanceEmpDto implements Serializable{
    private static final long serialVersionUID = -2207647958238863183L;
	private String EmpId;
    private String Name;
    private String AssessParentName;
    private String AssessName;
    private double Weight;
    private double Progress;
    private String IDP;
    private String IDPTotal;

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getAssessParentName() {
        return AssessParentName;
    }

    public void setAssessParentName(String assessParentName) {
        AssessParentName = assessParentName;
    }

    public String getAssessName() {
        return AssessName;
    }

    public void setAssessName(String assessName) {
        AssessName = assessName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public double getProgress() {
        return Progress;
    }

    public void setProgress(double progress) {
        Progress = progress;
    }

    public String getIDP() {
        return IDP;
    }

    public void setIDP(String IDP) {
        this.IDP = IDP;
    }

    public String getIDPTotal() {
        return IDPTotal;
    }

    public void setIDPTotal(String IDPTotal) {
        this.IDPTotal = IDPTotal;
    }
}
