package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.pc.common.RiskDto;

public class OpearRiskDto implements Serializable {

	private static final long serialVersionUID = -6269864225690887197L;
	private String empId;
	private String note;
	private List<RiskDto> risks;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public OpearRiskDto() {
		
	}
	
	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public List<RiskDto> getRisks() {
		return risks;
	}


	public void setRisks(List<RiskDto> risks) {
		this.risks = risks;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	
	
}
