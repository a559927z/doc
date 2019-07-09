package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;

public class MonthReportConfigDto implements Serializable {

	private static final long serialVersionUID = -2761241334777856292L;
	
	private String id;
	private String empId;
	private String reportPath;
	private String reportContent;
	private Integer flag;
	private Integer showIndex;
	
	public MonthReportConfigDto() {}
	
	public MonthReportConfigDto(String empId, String reportPath, Integer flag, Integer showIndex) {
		this.empId = empId;
		this.reportPath = reportPath;
		this.flag = flag;
		this.showIndex = showIndex;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	
}
