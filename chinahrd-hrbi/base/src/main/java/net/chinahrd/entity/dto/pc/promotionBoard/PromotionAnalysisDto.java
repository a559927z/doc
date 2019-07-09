package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * 分析
 * 
 * @author jxzhang on 2017年5月24日
 * @Verdion 1.0 版本
 */
public class PromotionAnalysisDto implements Serializable {

	private static final long serialVersionUID = 2192520834841867257L;
	private String promotionAnalysisId;
	private String empId;
	private String customerId;
	private String promotionReq;
	private String persomaStatus;
	private Integer isAccord;
	private String analysisDate;
	private String note;

	public PromotionAnalysisDto() {
		super();
	}

	public PromotionAnalysisDto(String promotionAnalysisId, String empId, String customerId, String promotionReq,
			String persomaStatus, Integer isAccord, String analysisDate, String note) {
		super();
		this.promotionAnalysisId = promotionAnalysisId;
		this.empId = empId;
		this.customerId = customerId;
		this.promotionReq = promotionReq;
		this.persomaStatus = persomaStatus;
		this.isAccord = isAccord;
		this.analysisDate = analysisDate;
		this.note = note;
	}



	public String getPromotionAnalysisId() {
		return promotionAnalysisId;
	}

	public void setPromotionAnalysisId(String promotionAnalysisId) {
		this.promotionAnalysisId = promotionAnalysisId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPromotionReq() {
		return promotionReq;
	}

	public void setPromotionReq(String promotionReq) {
		this.promotionReq = promotionReq;
	}

	public String getPersomaStatus() {
		return persomaStatus;
	}

	public void setPersomaStatus(String persomaStatus) {
		this.persomaStatus = persomaStatus;
	}

	public Integer getIsAccord() {
		return isAccord;
	}

	public void setIsAccord(Integer isAccord) {
		this.isAccord = isAccord;
	}

	public String getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(String analysisDate) {
		this.analysisDate = analysisDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
