package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

public class RiskTreeDto implements Serializable {

	private static final long serialVersionUID = -6269864225690887197L;
	private String id;
	private String name;
	private String pid;
	private String empId;
	private int riskFlag;
	
	/**
	 * 顶级节点的id（对应dimiss_risk 的dimiss_risk_id）
	 */
	private String topRiskId;
	/**
	 * 顶级节点的风险（用于保存数据库查询结果）
	 */
	private int topRiskFlag;
	/**
	 * 备注（用于保存数据库查询结果）
	 */
	private String note;
	
	
	public RiskTreeDto() {
		
	}
	
	public RiskTreeDto(String id, int riskFlag,String topRiskId, String note) {
		this.id = id;
		this.riskFlag = riskFlag;
		this.topRiskId = topRiskId;
		this.note = note;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public int getRiskFlag() {
		return riskFlag;
	}
	public void setRiskFlag(int riskFlag) {
		this.riskFlag = riskFlag;
	}
	public int getTopRiskFlag() {
		return topRiskFlag;
	}
	public void setTopRiskFlag(int topRiskFlag) {
		this.topRiskFlag = topRiskFlag;
	}
	public String getTopRiskId() {
		return topRiskId;
	}

	public void setTopRiskId(String topRiskId) {
		this.topRiskId = topRiskId;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}  
}
