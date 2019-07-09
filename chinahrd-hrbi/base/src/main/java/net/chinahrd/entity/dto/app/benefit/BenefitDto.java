package net.chinahrd.entity.dto.app.benefit;

import java.io.Serializable;

/**
 * 人均效益dto
 */
public class BenefitDto implements Serializable,Comparable<BenefitDto>{
	private static final long serialVersionUID = 182977095556542956L;
	
	private String id;
	private String organizationId;
	private String organizationName;
	/** 行业均值 */
	private Double avgValue;
	/** 年月 */
	private Integer yearMonth;
	/** 利润 */
	private Double profit;
	/** 等值全员工数*/
	private Double eqEmpNum;
	/** 人均效益的值 */
	private Double benefitValue;
	/** 人均效益的值 of echar */
	private Double value;
	/** 环比的值 */
	private Double changeValue;
	/** 销售总额 */
	private Double salesAmount;
	/** 是否当前节点**/
	private boolean isSelfNode;
	/** 全职员工数**/
	private double fulltimeSum;
	/** 兼职员工数**/
	private Double passtimeSum;
	/** 加班工数**/
	private Double overtimeSum;
	/** 幅度**/
	private Double rangePer;
	/**目标人均效益的值**/
	private Double targetValue;

	public Double getRangePer() {
		return rangePer;
	}
	public void setRangePer(Double rangePer) {
		this.rangePer = rangePer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}
	public Double getChangeValue() {
		return changeValue;
	}
	public void setChangeValue(Double changeValue) {
		this.changeValue = changeValue;
	}
	public Double getBenefitValue() {
		return benefitValue;
	}
	public void setBenefitValue(Double benefitValue) {
		this.benefitValue = benefitValue;
	}
	public Double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Double getEqEmpNum() {
		return eqEmpNum;
	}
	public void setEqEmpNum(Double eqEmpNum) {
		this.eqEmpNum = eqEmpNum;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public boolean getIsSelfNode() {
		return isSelfNode;
	}
	public void setIsSelfNode(boolean isSelfNode) {
		this.isSelfNode = isSelfNode;
	}
	public Double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}
	public Double getFulltimeSum() {
		return fulltimeSum;
	}
	public void setFulltimeSum(int fulltimeSum) {
		this.fulltimeSum = fulltimeSum;
	}
	public Double getPasstimeSum() {
		return passtimeSum;
	}
	public void setPasstimeSum(Double passtimeSum) {
		this.passtimeSum = passtimeSum;
	}
	public Double getOvertimeSum() { return overtimeSum; }
	public void setOvertimeSum(double overtimeSum) { this.overtimeSum = overtimeSum; }
	public void setSelfNode(boolean isSelfNode) {
		this.isSelfNode = isSelfNode;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(Double targetValue) {
		this.targetValue = targetValue;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BenefitDto o) {
		// TODO Auto-generated method stub
		return this.getBenefitValue()>o.getBenefitValue()?1:this.getBenefitValue()==o.getBenefitValue()?0:-1;
	}
	
}
