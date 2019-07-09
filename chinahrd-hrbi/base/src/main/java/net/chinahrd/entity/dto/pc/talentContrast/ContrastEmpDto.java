package net.chinahrd.entity.dto.pc.talentContrast;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 对比人员基本信息dto
 * Created by wqcai on 15/10/21 0021.
 */
public class ContrastEmpDto implements Serializable {
    private static final long serialVersionUID = 5203404039451876992L;

    private String empId;                       //员工ID
    private String imgPath;                     //头像
    private String empKey;                      //员工编码
    private String userName;                    //中文名
    private String organName;                   //机构名称
    private String sex;                         //性别
    private Integer age;                        //年龄
    private String marryStatus;                 //婚姻状况  0：未婚    1：已婚
    private String degree;                      //学历
    private String organParentName;             //单位/（分）公司
    private Timestamp entryDate;                //入职时间
    private String sequenceName;                //职位主序列
    private String sequenceSubName;             //职位子序列
    private String abilityName;                 //能力层级
    private String rankName;                    //职级
    private String positionName;                //现岗位
    private String organizationId;
    

    public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpKey() {
        return empKey;
    }

    public void setEmpKey(String empKey) {
        this.empKey = empKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getOrganParentName() {
        return organParentName;
    }

    public void setOrganParentName(String organParentName) {
        this.organParentName = organParentName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getSequenceSubName() {
        return sequenceSubName;
    }

    public void setSequenceSubName(String sequenceSubName) {
        this.sequenceSubName = sequenceSubName;
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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
