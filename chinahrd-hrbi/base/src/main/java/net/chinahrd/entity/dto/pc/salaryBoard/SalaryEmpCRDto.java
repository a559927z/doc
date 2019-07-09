package net.chinahrd.entity.dto.pc.salaryBoard;

import java.io.Serializable;


/**
 * 薪酬员工CR dto
 * 
 * @author qpzhu by 2016-04-07
 */
public class SalaryEmpCRDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7222132576196769558L;

	private String name;
	
	private String organizationName;
	
	private String abilityLvName;
	
	private String performanceName;
	
	private double cr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAbilityLvName() {
		return abilityLvName;
	}

	public void setAbilityLvName(String abilityLvName) {
		this.abilityLvName = abilityLvName;
	}

	public String getPerformanceName() {
		return performanceName;
	}

	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}

	public double getCr() {
		return cr;
	}

	public void setCr(double cr) {
		this.cr = cr;
	}
	
	
	
	
}
