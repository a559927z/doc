package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;

import net.chinahrd.entity.dto.pc.admin.UserDto;

/**
 * 插入员工个人信息
 * @author zhiwei
 * @time 2016年12月9日下午2:39:20
 * @version 20162016年12月13日
 */
public class DimEmp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  **/
	private String vDimEmpId;
	private String empId;
	private String customerId;
	/**  **/
	private String empKey;
	private String userName;
	private String userNameCh;
	private String empHfId;
	private String empHfKey;
	
	private String empHfName;
	
	private String email;
	private String passtimeOrFulltime;
	private String age;
	private String companyAge;
	private String isKeyTalent;
	private String sex;
	private String nation;
	private String degreeId;
	private String degree;
	private String nativePlace;
	private String country;
	private String birthPlace;
	private String birthDate;
	private String nationalId;
	private String nationalType;
	private String marryStatus;
	private String pattyName;
	private String organizationParentId;
	private String organizationParentName;
	
	private String organId;
	private String organName;
	private String positionId;
	private String positionName;
	private String mainseqId;
	private String mainseqName;
	private String subseqId;
	private String subseqName;
	private String abilityId;
	private String abilityName;
	private String jobTitleId;
	private String jobTitleName;
	private String abilitylvId;
	private String abilitylvName;
	private String rankName;
	private String telPhone;
	private String qq;
	private String wxCode;
	private String address;
	private String contractUnit;
	private String contract;
	private String workPlaceId;
	private String workPlaceName;
	private String regularDate;
	private String runOffDate;
	private String applyChannel;
	private String refreshDate;
	private String cId;
	private String entryDate;
	private String remark;
	
	private String isRegular;
	
	/**
	 * dim_user
	 */
	private UserDto user;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameCh() {
		return userNameCh;
	}

	public void setUserNameCh(String userNameCh) {
		this.userNameCh = userNameCh;
	}

	public String getEmpHfId() {
		return empHfId;
	}

	public void setEmpHfId(String empHfId) {
		this.empHfId = empHfId;
	}

	public String getEmpHfKey() {
		return empHfKey;
	}

	public void setEmpHfKey(String empHfKey) {
		this.empHfKey = empHfKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasstimeOrFulltime() {
		return passtimeOrFulltime;
	}

	public void setPasstimeOrFulltime(String passtimeOrFulltime) {
		this.passtimeOrFulltime = passtimeOrFulltime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(String degreeId) {
		this.degreeId = degreeId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getNationalType() {
		return nationalType;
	}

	public void setNationalType(String nationalType) {
		this.nationalType = nationalType;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}

	public String getPattyName() {
		return pattyName;
	}

	public void setPattyName(String pattyName) {
		this.pattyName = pattyName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getMainseqId() {
		return mainseqId;
	}

	public void setMainseqId(String mainseqId) {
		this.mainseqId = mainseqId;
	}

	public String getMainseqName() {
		return mainseqName;
	}

	public void setMainseqName(String mainseqName) {
		this.mainseqName = mainseqName;
	}

	public String getSubseqId() {
		return subseqId;
	}

	public void setSubseqId(String subseqId) {
		this.subseqId = subseqId;
	}

	public String getSubseqName() {
		return subseqName;
	}

	public void setSubseqName(String subseqName) {
		this.subseqName = subseqName;
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

	public String getAbilitylvId() {
		return abilitylvId;
	}

	public void setAbilitylvId(String abilitylvId) {
		this.abilitylvId = abilitylvId;
	}

	public String getAbilitylvName() {
		return abilitylvName;
	}

	public void setAbilitylvName(String abilitylvName) {
		this.abilitylvName = abilitylvName;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getContractUnit() {
		return contractUnit;
	}

	public void setContractUnit(String contractUnit) {
		this.contractUnit = contractUnit;
	}

	public String getWorkPlaceId() {
		return workPlaceId;
	}

	public void setWorkPlaceId(String workPlaceId) {
		this.workPlaceId = workPlaceId;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}

	public String getRegularDate() {
		return regularDate;
	}

	public void setRegularDate(String regularDate) {
		this.regularDate = regularDate;
	}

	public String getRunOffDate() {
		return runOffDate;
	}

	public void setRunOffDate(String runOffDate) {
		this.runOffDate = runOffDate;
	}

	public String getApplyChannel() {
		return applyChannel;
	}

	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getEmpKey() {
		return empKey;
	}

	public void setEmpKey(String empKey) {
		this.empKey = empKey;
	}

	public String getvDimEmpId() {
		return vDimEmpId;
	}

	public void setvDimEmpId(String vDimEmpId) {
		this.vDimEmpId = vDimEmpId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCompanyAge() {
		return companyAge;
	}

	public void setCompanyAge(String companyAge) {
		this.companyAge = companyAge;
	}

	public String getIsKeyTalent() {
		return isKeyTalent;
	}

	public void setIsKeyTalent(String isKeyTalent) {
		this.isKeyTalent = isKeyTalent;
	}

	public String getOrganizationParentId() {
		return organizationParentId;
	}

	public void setOrganizationParentId(String organizationParentId) {
		this.organizationParentId = organizationParentId;
	}

	public String getOrganizationParentName() {
		return organizationParentName;
	}

	public void setOrganizationParentName(String organizationParentName) {
		this.organizationParentName = organizationParentName;
	}

	public String getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(String refreshDate) {
		this.refreshDate = refreshDate;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsRegular() {
		return isRegular;
	}

	public void setIsRegular(String isRegular) {
		this.isRegular = isRegular;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getEmpHfName() {
		return empHfName;
	}

	public void setEmpHfName(String empHfName) {
		this.empHfName = empHfName;
	}
	

}
