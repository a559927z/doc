package net.chinahrd.entity.dto.pc.history;

import java.io.Serializable;

public class HistoryEmpCountDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String  organizationId ;
	private String  fullPath       ;
	private Integer type            ;
	private Integer empCount       ;
	private Integer empCountSum   ;
	private String  day             ;
	private String  note            ;
	private String  year            ;
	public HistoryEmpCountDto(){}
	public HistoryEmpCountDto(String orgId, String fullPath, Integer type, 
			Integer empC, Integer empCSum, String day, String note, String year){
		this.organizationId = orgId;
		this.fullPath = fullPath;
		this.type = type;
		this.empCount = empC;
		this.empCountSum = empCSum;
		this.day = day;
		this.note = note;
		this.year = year;
	}
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getEmpCount() {
		return empCount;
	}
	public void setEmpCount(Integer empCount) {
		this.empCount = empCount;
	}
	public Integer getEmpCountSum() {
		return empCountSum;
	}
	public void setEmpCountSum(Integer empCountSum) {
		this.empCountSum = empCountSum;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
