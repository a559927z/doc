package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;

/**
 * 晋级分析
 * @author xwLi
 *
 */
public class MonthReportPromotionDto implements Serializable {

	private static final long serialVersionUID = -5072758083824528983L;
	
	private String organId;
	private String organName;
	private Double count;
	private Double proCount;
	private Double proRate;
	private Double keyProRate;
	private Double proVelocity;
	
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	public Double getProCount() {
		return proCount;
	}
	public void setProCount(Double proCount) {
		this.proCount = proCount;
	}
	public Double getProRate() {
		return proRate;
	}
	public void setProRate(Double proRate) {
		this.proRate = proRate;
	}
	public Double getKeyProRate() {
		return keyProRate;
	}
	public void setKeyProRate(Double keyProRate) {
		this.keyProRate = keyProRate;
	}
	public Double getProVelocity() {
		return proVelocity;
	}
	public void setProVelocity(Double proVelocity) {
		this.proVelocity = proVelocity;
	}
	
}
