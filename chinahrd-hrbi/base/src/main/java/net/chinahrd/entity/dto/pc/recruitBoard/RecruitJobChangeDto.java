package net.chinahrd.entity.dto.pc.recruitBoard;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 招聘看板-工作异动员工详细dto
 * Created by wqcai on 16/5/16.
 */
public class RecruitJobChangeDto implements Serializable {

    private static final long serialVersionUID = -4281103934908446989L;

    private String empId;                   //员工ID
    private String userNameCh;              //员工名称
    private String sex;                     //性别
    private String age;                     //年龄
    private String degree;                  //学历
    private String major;                   //专业
    private String organizationName;        //机构
    private String positionNameEd;          //前岗位
    private String positionName;            //现岗位
    private Timestamp changeDate;           //异动时间

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionNameEd() {
        return positionNameEd;
    }

    public void setPositionNameEd(String positionNameEd) {
        this.positionNameEd = positionNameEd;
    }

    public String getSex() {
        return sex.equals("m")?"男":(sex.equals("w")?"女":"-");
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
