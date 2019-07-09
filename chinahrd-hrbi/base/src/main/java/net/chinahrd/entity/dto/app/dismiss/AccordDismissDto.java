package net.chinahrd.entity.dto.app.dismiss;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.enums.TipsEnum;

/**
 * 主动流率dto
 */
public class AccordDismissDto extends EmpDto implements Serializable {

	private static final long serialVersionUID = 6329903626049403450L;

	/** 员工id */
	private String empId;
	/** 员工名字 */
	private String userNameCh;
	/** 关键人才类型id */
	private String keyTalentTypeId;
	/** 关键人才类型名字 */
	private String keyTalentTypeName;
	private int isKeyTalent;
	/** 离职风险id */
	private String separationRiskId;
	/** 离职风险名字 */
	private String separationRiskName;
	/** 机构名称 */
	private String organizationName;
	/** 直属上级员工ID */
	private String parentEmpId;
	/** 岗位名称 */
	private String positionName;
	/** 是否有过预警 */
	private Integer isWarn;
	/** 流失类型 */
	private Integer roType;
	private String roTypeToString;
	/** 流失时间 */
	private Date roDate;
	private String roDateToString;
	private Integer roDateToInteger;	//yyyyMM
	/** 去向 */
	private String whereAbouts;

	public AccordDismissDto() {
	}

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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getRoType() {
		return roType == null ? 3 : roType;
	}

	public void setRoType(Integer roType) {
		this.roType = roType;
	}

	public Date getRoDate() {
		DateTime.now();
		return roDate;
	}

	public void setRoDate(Date roDate) {
		this.roDate = roDate;
	}

	public String getWhereAbouts() {
		return whereAbouts;
	}

	public void setWhereAbouts(String whereAbouts) {
		this.whereAbouts = whereAbouts;
	}

	public String getRoDateToString() {
		DateTime dt = new DateTime(this.roDate);
		return dt.toString("yyyy年MM月dd日", Locale.CHINESE);
	}
	
	public Integer getRoDateToInteger() {
		DateTime dt = new DateTime(this.roDate);
		return Integer.parseInt(dt.toString("yyyyMM", Locale.CHINESE));
	}

	public String getRoTypeToString() {
		return TipsEnum.getDescByCode(roType, 11);
	}

	public int getIsKeyTalent() {
		return isKeyTalent;
	}

	public void setIsKeyTalent(int isKeyTalent) {
		this.isKeyTalent = isKeyTalent;
	}

}
