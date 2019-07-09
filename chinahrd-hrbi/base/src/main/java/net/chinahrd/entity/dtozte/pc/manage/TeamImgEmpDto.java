package net.chinahrd.entity.dtozte.pc.manage;

import java.util.Date;

import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;

public class TeamImgEmpDto extends EmpBaseInfoDto {

	private static final long serialVersionUID = -8984144501948535844L;

	/** 性别 */
	private String sex;
	/** 生日 */
	private Date birthDate;
	/** 是否已婚 */
	private Integer marryStatus;
	/** 能力层级 */
	private String ability;
	/** 年龄 */
	private Integer age;
	/** 司龄 */
	private Integer companyAge;
	/** 血型 */
	private String blood;
	/** 员工性格 */
	private Integer personalityType;
	/** 工作地点 */
	private String workPlace;
	private String curtName;  //职级

	public String getAbilityLvName() {
		return abilityLvName;
	}

	public void setAbilityLvName(String abilityLvName) {
		this.abilityLvName = abilityLvName;
	}

	//职级
	private String abilityLvName;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(Integer marryStatus) {
		this.marryStatus = marryStatus;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCompanyAge() {
		return companyAge;
	}

	public void setCompanyAge(Integer companyAge) {
		this.companyAge = companyAge;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public Integer getPersonalityType() {
		return personalityType;
	}

	public void setPersonalityType(Integer personalityType) {
		this.personalityType = personalityType;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getCurtName() {
		return curtName;
	}

	public void setCurtName(String curtName) {
		this.curtName = curtName;
	}


	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}
}
