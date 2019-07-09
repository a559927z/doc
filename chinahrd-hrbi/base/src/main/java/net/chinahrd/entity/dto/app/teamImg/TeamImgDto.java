package net.chinahrd.entity.dto.app.teamImg;

import java.util.Date;

public class TeamImgDto  {

	/** 性别 */
	private String sex;
	/** 生日 */
	private Date birthDate;
	/** 是否已婚 */
	private Integer marryStatus;
	/** 年龄 */
	private Integer age;
	/** 血型 */
	private String blood;
	/** 员工性格 */
	private Integer personalityType;

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

}
