package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * 方案
 * 
 * @author jxzhang on 2017年5月24日
 * @Verdion 1.0 版本
 */
public class PromotionElementSchemeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2192520834841867257L;

	private String schemeId;
	private String customerId;
	private String schemeName;
	private Integer companyAge;
	private String curtNamePer;
	private Integer curtNameEval;
	private String certificateId;
	private String certificateTypeId;
	private String createUserId;
	private String modifyUserId;
	private String createTime;
	private String modifyTime;
	private String startDate;
	private String invalidDate;

	public PromotionElementSchemeDto() {
		super();
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Integer getCompanyAge() {
		return companyAge;
	}

	public void setCompanyAge(Integer companyAge) {
		this.companyAge = companyAge;
	}

	public String getCurtNamePer() {
		return curtNamePer;
	}

	public void setCurtNamePer(String curtNamePer) {
		this.curtNamePer = curtNamePer;
	}

	public Integer getCurtNameEval() {
		return curtNameEval;
	}

	public void setCurtNameEval(Integer curtNameEval) {
		this.curtNameEval = curtNameEval;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public String getCertificateTypeId() {
		return certificateTypeId;
	}

	public void setCertificateTypeId(String certificateTypeId) {
		this.certificateTypeId = certificateTypeId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

}
