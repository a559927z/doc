package net.chinahrd.entity.dto.pc;

import java.io.Serializable;

/**
 * 移动端360测评DTO
 * @author guanjian
 *
 */
public class EvalReport4MobileDto implements Serializable {
	
	private String empId; // 员工ID
	private String abilityName; // 能力维度
	private String abilityLvName; // 能力层级
	private String yearScore; // 综合评分
	
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getAbilityName() {
		return abilityName;
	}
	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}
	public String getAbilityLvName() {
		return abilityLvName;
	}
	public void setAbilityLvName(String abilityLvName) {
		this.abilityLvName = abilityLvName;
	}
	public String getYearScore() {
		return yearScore;
	}
	public void setYearScore(String yearScore) {
		this.yearScore = yearScore;
	}

	
}
