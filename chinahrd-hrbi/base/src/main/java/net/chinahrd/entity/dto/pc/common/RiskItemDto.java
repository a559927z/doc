package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

public class RiskItemDto implements Serializable {

	private static final long serialVersionUID = -6269864225690887197L;
	private String id;
	private String customerId;
	private String riskId;
	private String dimissionId;
	private int riskFlag;
	
	
	
	public RiskItemDto() {
		
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getRiskId() {
		return riskId;
	}


	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}


	public String getDimissionId() {
		return dimissionId;
	}


	public void setDimissionId(String dimissionId) {
		this.dimissionId = dimissionId;
	}


	public int getRiskFlag() {
		return riskFlag;
	}


	public void setRiskFlag(int riskFlag) {
		this.riskFlag = riskFlag;
	}
	
	
}
