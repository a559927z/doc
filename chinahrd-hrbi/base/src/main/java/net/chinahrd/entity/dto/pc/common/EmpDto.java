package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工信息dto
 */
public class EmpDto implements Serializable {

	private static final long serialVersionUID = -162863916929999709L;

	/**员工id*/
	private String empId;
	/*员工编号*/
	private String empKey;
	/*员工名称*/
	private String empName;
	/**员工名字*/
    private String userNameCh;
    /**机构Id */
    private String organizationId;
    /**机构名称 */
    private String organizationName;
    /**序列id */
    private String sequenceId;
    /**序列名称 */
    private String sequenceName;
    /**能力层级id */
    private String abilityId;
    /**能力层级名称(职级) */
    private String abilityName;
    /**绩效id */
    private String performanceId;
    /**绩效key 1,2,3,4,5 */
    private String performanceKey;
    /**绩效名称 */
    private String performanceName;
	/** 岗位名称 */
	private String positionName;
	/** 头像*/
	private String imgPath;
	/** 职级子级 */
	private String rankName;
	/** 司龄 */
	private Integer companyAge;
	/** 最新离职风险评估时间*/
	private Date riskDate;
	/** 最新离职风险*/	/** 预警标识  1：红 2：黄 3：绿 */
	private Integer riskFlag;
	/** 风险预警备注 */
	private String note;
	

    public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getRiskFlag() {
		return riskFlag;
	}

	public void setRiskFlag(Integer riskFlag) {
		this.riskFlag = riskFlag;
	}

	public EmpDto() {
    }

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpKey() {
		return empKey;
	}

	public void setEmpKey(String empKey) {
		this.empKey = empKey;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
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

	public String getPerformanceName() {
		return performanceName;
	}

	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}

	public String getPerformanceId() {
		return performanceId;
	}

	public void setPerformanceId(String performanceId) {
		this.performanceId = performanceId;
	}

	public String getPerformanceKey() {
		return performanceKey;
	}

	public void setPerformanceKey(String performanceKey) {
		this.performanceKey = performanceKey;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Date getRiskDate() {
		return riskDate;
	}

	public void setRiskDate(Date riskDate) {
		this.riskDate = riskDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

    public Integer getCompanyAge() {
        return companyAge;
    }

    public void setCompanyAge(Integer companyAge) {
        this.companyAge = companyAge;
    }
}
