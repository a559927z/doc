package net.chinahrd.entity.dto.app.performance;

import java.io.Serializable;

/**
 * 个人绩效详情
 * @author guanjian
 *
 */
public class PreDetailDto implements Serializable {

	/**  */
	private static final long serialVersionUID = 6880134883890345766L;

	private String empId;
	
	private String userNameCh;
	private String organizationName;
	
	private String positionName;
	private String sequenceName;
	private String abilityName;
	private String rankName;
	private String performanceName;
	private String performanceName1;
	private String performanceName2;
	private String performanceName3;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getUserNameCh() {
		return userNameCh;
	}
	public void setUserNameCh(String userNameCh) {
		this.userNameCh = userNameCh;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public String getAbilityName() {
		return abilityName;
	}
	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}
	public String getPerformanceName1() {
		return performanceName1;
	}
	public void setPerformanceName1(String performanceName1) {
		this.performanceName1 = performanceName1;
	}
	public String getPerformanceName2() {
		return performanceName2;
	}
	public void setPerformanceName2(String performanceName2) {
		this.performanceName2 = performanceName2;
	}
	public String getPerformanceName3() {
		return performanceName3;
	}
	public void setPerformanceName3(String performanceName3) {
		this.performanceName3 = performanceName3;
	}
	public String getPerformanceName() {
		return performanceName;
	}
	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	
}
