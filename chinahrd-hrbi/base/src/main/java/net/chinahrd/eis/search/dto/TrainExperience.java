package net.chinahrd.eis.search.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class TrainExperience implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6363401182604366694L;

	/** key */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String trainExperienceId;
	@Field(type = FieldType.String, index=FieldIndex.analyzed)
	private String empId;
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String courseName;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String result;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String trainUnit;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String gainCertificate;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String lecturerName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String note;

	@Field(type = FieldType.Double, index = FieldIndex.no)
	private Double trainTime;

	@Field(type = FieldType.Date, index = FieldIndex.no)
	private Date startDate;

	@Field(type = FieldType.Date, index = FieldIndex.no)
	private Date finishDate;

	public String getTrainExperienceId() {
		return trainExperienceId;
	}

	public void setTrainExperienceId(String trainExperienceId) {
		this.trainExperienceId = trainExperienceId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTrainUnit() {
		return trainUnit;
	}

	public void setTrainUnit(String trainUnit) {
		this.trainUnit = trainUnit;
	}

	public String getGainCertificate() {
		return gainCertificate;
	}

	public void setGainCertificate(String gainCertificate) {
		this.gainCertificate = gainCertificate;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Double trainTime) {
		this.trainTime = trainTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

}
