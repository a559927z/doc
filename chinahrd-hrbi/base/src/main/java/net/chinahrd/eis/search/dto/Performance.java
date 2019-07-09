package net.chinahrd.eis.search.dto;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Performance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6298293583115860634L;
	/** key */
	@Field(type = FieldType.String, index=FieldIndex.no)
	private String performanceChangeId;
	@Field(type = FieldType.String, index=FieldIndex.analyzed)
	private String empId;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	@Field(type = FieldType.Integer, index=FieldIndex.not_analyzed)
	private int yearMonth;
	@Field(type = FieldType.Integer, index=FieldIndex.no)
	private int perfType;
	@Field(type = FieldType.String, index=FieldIndex.not_analyzed)
	private String performanceId;
	@Field(type = FieldType.String, index=FieldIndex.no)
	private String performanceName;
	
	public String getPerformanceChangeId() {
		return performanceChangeId;
	}
	public void setPerformanceChangeId(String performanceChangeId) {
		this.performanceChangeId = performanceChangeId;
	}
	public int getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}
	public int getPerfType() {
		return perfType;
	}
	public void setPerfType(int perfType) {
		this.perfType = perfType;
	}
	public String getPerformanceId() {
		return performanceId;
	}
	public void setPerformanceId(String performanceId) {
		this.performanceId = performanceId;
	}
	public String getPerformanceName() {
		return performanceName;
	}
	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}

}
