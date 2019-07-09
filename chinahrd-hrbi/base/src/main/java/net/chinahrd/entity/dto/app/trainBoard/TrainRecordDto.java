package net.chinahrd.entity.dto.app.trainBoard;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 培训记录Dto
 * 
 * @author qpzhu by 2016-03-30
 */
public class TrainRecordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2321213362882483008L;

	private String userName;
	private String typeName;
	private String courseName;
	private Timestamp startDate;
	private Timestamp finishDate;
	private Double trainTime;
	private String status;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Double getTrainTime() {
		return trainTime;
	}
	public void setTrainTime(Double trainTime) {
		this.trainTime = trainTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Timestamp getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
	}
}
