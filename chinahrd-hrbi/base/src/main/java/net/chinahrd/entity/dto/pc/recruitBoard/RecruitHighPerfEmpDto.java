package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-高绩效人员信息
 * Created by wqcai on 16/5/18.
 */
public class RecruitHighPerfEmpDto implements Serializable {
    private static final long serialVersionUID = -8455346978567257419L;

    private String empId;       //员工ID
    private String sex;         //性别
    private String degreeId;    //学历ID
    private String degree;      //学历
    private String schoolType;  //学校类型

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
