package net.chinahrd.entity.dto.app.trainBoard;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 培训类别Dto
 * 
 * @author qpzhu by 2016-03-31
 */
public class TrainTypeDto implements Serializable {

	private static final long serialVersionUID = -3245143672281973123L;

	private String courseTypeName;
	
	private String courseName;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private String status;
	
	private int trainNum;
	
	private double coverageRate;
	
	private String trainUnit;

	public String getCourseTypeName() {
		return courseTypeName;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(int trainNum) {
		this.trainNum = trainNum;
	}

	public String getTrainUnit() {
		return trainUnit;
	}

	public void setTrainUnit(String trainUnit) {
		this.trainUnit = trainUnit;
	}


	public double getCoverageRate() {
		return coverageRate;
	}

	public void setCoverageRate(double coverageRate) {
		this.coverageRate = coverageRate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
}
