package net.chinahrd.entity.dto.pc.accordDismiss;

import java.io.Serializable;

/**
 * 流失对比dto
 */
public class DismissContrastDto  implements Serializable {
	
	private static final long serialVersionUID = 5171829312087596325L;
	
	/**机构*/
    private String organizationId;
    /**机构名称 */
    private String organizationName;
    /**月初/季初 人数 */
    private int beginSum;
	/** 月末/季末人数 */
	private int endSum;
	/** 流失人数 */
	private int runOffCount;
	/** 年月 */
	private int yearMonth;
	/** 流失率 */
	private Double dismissRate;
	/** 时间  */
	private String days;
	/** 总计  */
	private int total;

    public DismissContrastDto() {
    }

	public DismissContrastDto(String organizationId, String organizationName) {
		this.organizationId = organizationId;
		this.organizationName = organizationName;
	}
	

	public DismissContrastDto(String organizationId, String organizationName,
			int beginSum, int endSum, int runOffCount) {
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.beginSum = beginSum;
		this.endSum = endSum;
		this.runOffCount = runOffCount;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getBeginSum() {
		return beginSum;
	}

	public void setBeginSum(int beginSum) {
		this.beginSum = beginSum;
	}

	public int getEndSum() {
		return endSum;
	}

	public void setEndSum(int endSum) {
		this.endSum = endSum;
	}

	public int getRunOffCount() {
		return runOffCount;
	}

	public void setRunOffCount(int runOffCount) {
		this.runOffCount = runOffCount;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Double getDismissRate() {
		return dismissRate;
	}

	public void setDismissRate(Double dismissRate) {
		this.dismissRate = dismissRate;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
