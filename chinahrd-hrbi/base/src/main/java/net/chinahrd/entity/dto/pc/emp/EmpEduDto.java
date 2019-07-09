package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;

/**
 * 教育背景dto
 */
public class EmpEduDto  implements Serializable {
	
	private static final long serialVersionUID = -4714091706509106934L;
	
	private String id;
    /**学校名称 */
    private String schoolName;
    /**专业 */
    private String major;
	/** 学历 */
	private String degree;
	/** 学位 */
	private String academicDegree;
	/** 培养方式（全日制、其他..）*/
	private String developMode;
	/** 奖励信息 */
	private String bonus;
	/** 奖励信息 */
	private String note;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getAcademicDegree() {
		return academicDegree;
	}
	public void setAcademicDegree(String academicDegree) {
		this.academicDegree = academicDegree;
	}
	public String getDevelopMode() {
		return developMode;
	}
	public void setDevelopMode(String developMode) {
		this.developMode = developMode;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
