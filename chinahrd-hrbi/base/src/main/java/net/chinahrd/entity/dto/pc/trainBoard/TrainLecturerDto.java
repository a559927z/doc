package net.chinahrd.entity.dto.pc.trainBoard;

import java.io.Serializable;


/**
 * 培训讲师Dto
 * 
 * @author qpzhu by 2016-03-30
 */
public class TrainLecturerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2703750092407880043L;

	private String lecturerName;
	
	private String level;
	
	private String organizationName;
	
	private String abilityLvName;
	
	private Integer courseNumber;
	
	private Integer teachingNumber;

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAbilityLvName() {
		return abilityLvName;
	}

	public void setAbilityLvName(String abilityLvName) {
		this.abilityLvName = abilityLvName;
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Integer getTeachingNumber() {
		return teachingNumber;
	}

	public void setTeachingNumber(Integer teachingNumber) {
		this.teachingNumber = teachingNumber;
	}
	
}
