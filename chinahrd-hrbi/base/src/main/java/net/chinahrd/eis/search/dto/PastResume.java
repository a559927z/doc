package net.chinahrd.eis.search.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class PastResume implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5805089345695306835L;
	/** key */
	@Field(type = FieldType.String, index=FieldIndex.no)
	private String empPastResumeId;
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Field(type = FieldType.String, index=FieldIndex.analyzed)
	private String empId;
	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String workUnit;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String departmentName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String positionName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String bonusPenaltyName;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String witnessName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String changeReason;

	@Field(type = FieldType.Date, index = FieldIndex.no)
	private Date entryDate;

	@Field(type = FieldType.Date, index = FieldIndex.no)
	private Date runOffDate;
	

	public String getEmpPastResumeId() {
		return empPastResumeId;
	}

	public void setEmpPastResumeId(String empPastResumeId) {
		this.empPastResumeId = empPastResumeId;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getBonusPenaltyName() {
		return bonusPenaltyName;
	}

	public void setBonusPenaltyName(String bonusPenaltyName) {
		this.bonusPenaltyName = bonusPenaltyName;
	}

	public String getWitnessName() {
		return witnessName;
	}

	public void setWitnessName(String witnessName) {
		this.witnessName = witnessName;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getRunOffDate() {
		return runOffDate;
	}

	public void setRunOffDate(Date runOffDate) {
		this.runOffDate = runOffDate;
	}

}
