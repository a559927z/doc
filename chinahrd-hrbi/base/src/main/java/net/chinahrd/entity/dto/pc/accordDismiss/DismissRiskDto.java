package net.chinahrd.entity.dto.pc.accordDismiss;

import java.io.Serializable;
import java.util.Date;

import net.chinahrd.entity.dto.pc.common.EmpDto;

/**
 * 人员流失风险dto
 */
public class DismissRiskDto extends EmpDto implements Serializable {

	private static final long serialVersionUID = 6329903626049403450L;

	/** 关键人才类型id */
	private String keyTalentTypeId;
	/** 关键人才类型名字 */
	private String keyTalentTypeName;
	/** 离职风险id */
	private String separationRiskId;
	/** 离职风险名字 */
	private String separationRiskName;
	/** 直属上级员工ID */
	private String parentEmpId;
	/** 是否有过预警 */
	private Integer isWarn;

	private Date runOffDate;

	public DismissRiskDto() {
	}


	public Date getRunOffDate() {
		return runOffDate;
	}

	public void setRunOffDate(Date runOffDate) {
		this.runOffDate = runOffDate;
	}

	public String getKeyTalentTypeId() {
		return keyTalentTypeId;
	}

	public void setKeyTalentTypeId(String keyTalentTypeId) {
		this.keyTalentTypeId = keyTalentTypeId;
	}

	public String getKeyTalentTypeName() {
		return keyTalentTypeName;
	}

	public void setKeyTalentTypeName(String keyTalentTypeName) {
		this.keyTalentTypeName = keyTalentTypeName;
	}

	public String getSeparationRiskId() {
		return separationRiskId;
	}

	public void setSeparationRiskId(String separationRiskId) {
		this.separationRiskId = separationRiskId;
	}

	public String getSeparationRiskName() {
		return separationRiskName;
	}

	public void setSeparationRiskName(String separationRiskName) {
		this.separationRiskName = separationRiskName;
	}

	public String getParentEmpId() {
		return parentEmpId;
	}

	public void setParentEmpId(String parentEmpId) {
		this.parentEmpId = parentEmpId;
	}

	public Integer getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}

}
