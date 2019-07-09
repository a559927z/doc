package net.chinahrd.entity.dto.pc;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import net.chinahrd.entity.enums.TipsEnum;

/**
 * 员工详细基础dto
 * Created by wqcai on 15/10/12 0012.
 */
public class EmpDetailDto implements Serializable {
    private static final long serialVersionUID = 491630470398542343L;
    private String empId;                       //员工ID
    private String empKey;                      //员工编号
    private String userName;                    //员工姓名
    private String imgPath;                     //员工头像
    private String organName;                   //部门
    private String positionName;                //岗位
    private String empHfName;                   //直属上级
    private String telPhone;                    //手机
    private String email;                       //邮箱
    private String qq;                          //QQ
    private String wxCode;                      //微信
    private String sex;                         //性别
    private Timestamp birthDate;                //出生日期
    private String degree;                      //学历
    private String marryStatus;                 //婚姻状况  0：未婚    1：已婚
    private String nativePlace;                 //籍贯
    private String nation;                      //民族
    private String pattyName;                   //政治面貌
    private String nationalType;                //证件类型
    private String nationalId;                  //证件号码
    private String birthPlace;                  //户口所在地
    private String address;                     //常住址,现住址
    private String contactPersonName;           //紧急联系人
    private String contactTelPhone;             //联系方式
    private String contactCall;                 //关系
    private String contractUnit;                //合同单位
    private String workPlace;                   //工作地点
    private String passtimeOrFulltime;          //员工性质
    private String contract;          			//合同性质
    private String sequenceName;                //职位主序列
    private String sequenceSubName;             //职位子序列
    private String abilityName;                 //能力层级
    private String rankName;                    //职级
    private String jobTitleName;  //职衔                  //职级
    private Timestamp entryDate;                //入职时间
    private Timestamp regularDate;              //转正时间
    private Timestamp runOffDate;               //离职时间
    private String applyType;                   //应聘类型
    private String applyChannel;                //应聘渠道
    private String speciality;                  //应聘渠道
//    private String empStatus;                  	//员工状态

    
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getEmpHfName() {
        return empHfName;
    }

    public void setEmpHfName(String empHfName) {
        this.empHfName = empHfName;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPattyName() {
        return pattyName;
    }

    public void setPattyName(String pattyName) {
        this.pattyName = pattyName;
    }

    public String getNationalType() {
        return nationalType;
    }

    public void setNationalType(String nationalType) {
        this.nationalType = nationalType;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactTelPhone() {
        return contactTelPhone;
    }

    public void setContactTelPhone(String contactTelPhone) {
        this.contactTelPhone = contactTelPhone;
    }

    public String getContractUnit() {
        return contractUnit;
    }

    public void setContractUnit(String contractUnit) {
        this.contractUnit = contractUnit;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public Timestamp getRegularDate() {
        return regularDate;
    }

    public void setRegularDate(Timestamp regularDate) {
        this.regularDate = regularDate;
    }

    public Timestamp getRunOffDate() {
        return runOffDate;
    }

    public void setRunOffDate(Timestamp runOffDate) {
        this.runOffDate = runOffDate;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyChannel() {
        return applyChannel;
    }

    public void setApplyChannel(String applyChannel) {
        this.applyChannel = applyChannel;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

    public String getContactCall() {
        return contactCall;
    }

    public void setContactCall(String contactCall) {
        this.contactCall = contactCall;
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

	public String getPasstimeOrFulltime() {
		return passtimeOrFulltime == null ? null :
			TipsEnum.getDescByInfo(passtimeOrFulltime, 12);
	}

    public void setPasstimeOrFulltime(String passtimeOrFulltime) {
        this.passtimeOrFulltime = passtimeOrFulltime;
    }

//	public String getEmpStatus() {
//		return empStatus == null ? "离职" : EmpStatusEnum.getDesc(Integer.valueOf(empStatus));
//	}
//
//	public void setEmpStatus(String empStatus) {
//		this.empStatus = empStatus;
//	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}
	
}
