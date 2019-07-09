package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

public class RiskDto implements Serializable {

	private static final long serialVersionUID = -6269864225690887197L;
	private String id;                            //
	private String riskId;                        //
	private String customerId;                    //
	private String empId;                         //
	private String riskDate;                      //
	private String note;                          //
	private int riskFlag;                         //
	
	private int last;                             // 是否最新数据   1--最新   0--历史
	
	public int getLast() {
		return last;
	}


	public void setLast(int last) {
		this.last = last;
	}


	public RiskDto() {
		
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


	public String getRiskDate() {
		return riskDate;
	}


	public void setRiskDate(String riskDate) {
		this.riskDate = riskDate;
	}


	public int getRiskFlag() {
		return riskFlag;
	}


	public void setRiskFlag(int riskFlag) {
		this.riskFlag = riskFlag;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getRiskId() {
		return riskId;
	}


	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}  
	
	
}
