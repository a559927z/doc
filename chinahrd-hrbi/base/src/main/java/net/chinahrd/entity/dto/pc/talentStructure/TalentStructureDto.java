package net.chinahrd.entity.dto.pc.talentStructure;


import net.chinahrd.entity.dto.pc.common.EmpDto;

import java.io.Serializable;

/**
 * 人力结构dto
 * 
 * @author jxzhang by 2016-02-22
 */
public class TalentStructureDto extends EmpDto implements Serializable {

	private static final long serialVersionUID = -6984106987778565869L;

	private boolean isHasBudgetPer;
	/** 编制数 */
	private Double number;
	/** 在岗人数 */
	private Double empCount;
	/** 可用编制数 */
	private Double usableEmpCount;
	/** 编制使用率 */
	private Double budgetPer;
	/** 编制正常值 */
	private Double normal;
	/** 编制风险值 */
	private Double risk;
	/** 能力层级短名称 */
	private String abCurtName;

	/** 父机构id */
	private String organizationPId;
	/**
	 * 最高的能力层级
	 */
	private Integer maxAbLevel;
	/**
	 * 工作地点
	 */
	private String workPlace;
	/**
	 * 学历
	 */
	private String degree;
	
	private String fullPath;


	
	
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public Integer getMaxAbLevel() {
		return maxAbLevel;
	}
	public void setMaxAbLevel(Integer maxAbLevel) {
		this.maxAbLevel = maxAbLevel;
	}
	public boolean isHasBudgetPer() {
		return isHasBudgetPer;
	}
	public void setHasBudgetPer(boolean isHasBudgetPer) {
		this.isHasBudgetPer = isHasBudgetPer;
	}
	public String getOrganizationPId() {
		return organizationPId;
	}
	public void setOrganizationPId(String organizationPId) {
		this.organizationPId = organizationPId;
	}
	public String getAbCurtName() {
		return abCurtName;
	}
	public void setAbCurtName(String abCurtName) {
		this.abCurtName = abCurtName;
	}
	public Double getNormal() {
		return normal;
	}
	public void setNormal(Double normal) {
		this.normal = normal;
	}
	public Double getRisk() {
		return risk;
	}
	public void setRisk(Double risk) {
		this.risk = risk;
	}
	private String seqKey;

	public String getSeqKey() {
		return seqKey;
	}
	public void setSeqKey(String seqKey) {
		this.seqKey = seqKey;
	}
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	public Double getEmpCount() {
		return empCount;
	}
	public void setEmpCount(Double empCount) {
		this.empCount = empCount;
	}
	public Double getUsableEmpCount() {
		return usableEmpCount;
	}
	public void setUsableEmpCount(Double usableEmpCount) {
		this.usableEmpCount = usableEmpCount;
	}
	public Double getBudgetPer() {
		return budgetPer;
	}
	public void setBudgetPer(Double budgetPer) {
		this.budgetPer = budgetPer;
	}

}
