package net.chinahrd.entity.dto.pc.accordDismiss;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员风险回顾Dto
 */
public class DismissRiskReviewDto extends EmpDto implements Serializable {
	private static final long serialVersionUID = -6985347883266934428L;
	/** 员工ID */
	private String empId;
	/** 员工姓名 */
	private String userNameCh;
	/** 组织机构名称 */
	private String organizationName;
	/** 职级 */
	private String rankName;
	/** 离职时间 */
	private Date runOffDate;
	/** 评估次数 */
	private int riskCount;
	/** 最近评估时间 */
	private Date riskDate;
	/** 预警次数 */
	private int warnCount;
	/** 最近预警时间 */
	private Date warnDate;

	/** 预警/评估次数 */
	private String warnRiskCount;
	/** 是否预警 预警时间到离职时间大于6个月为否：0， 否则小于等于6个月为1 */
	private int isWarn;


	@Override
	public String getEmpId() {
		return empId;
	}

	@Override
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Override
	public String getUserNameCh() {
		return userNameCh;
	}

	@Override
	public void setUserNameCh(String userNameCh) {
		this.userNameCh = userNameCh;
	}

	@Override
	public String getOrganizationName() {
		return organizationName;
	}

	@Override
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getRunOffDate() {
		return runOffDate;
	}

	public void setRunOffDate(Date runOffDate) {
		this.runOffDate = runOffDate;
	}

	public int getRiskCount() {
		return riskCount;
	}

	public void setRiskCount(int riskCount) {
		this.riskCount = riskCount;
	}

	public Date getRiskDate() {
		return riskDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public void setRiskDate(Date riskDate) {
		this.riskDate = riskDate;
	}

	public int getWarnCount() {
		return warnCount;
	}

	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getWarnDate() {
		return warnDate;
	}

	public void setWarnDate(Date warnDate) {
		this.warnDate = warnDate;
	}

	public String getWarnRiskCount() {
		return warnRiskCount == null ? (warnCount + "/" + riskCount) : warnRiskCount;
	}

	public void setWarnRiskCount(String warnRiskCount) {
		this.warnRiskCount = warnRiskCount;
	}

	public int getIsWarn() {
		return isWarn == 0 ? (warnDate != null && DateUtil.getBetweenDiff(warnDate, runOffDate, 2) <= 6 ? 1 : 0) : isWarn;
	}

	public void setIsWarn(int isWarn) {
		this.isWarn = isWarn;
	}
}
