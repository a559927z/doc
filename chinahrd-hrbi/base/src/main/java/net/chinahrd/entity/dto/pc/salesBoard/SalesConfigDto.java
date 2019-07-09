package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesConfigDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -621179934469906489L;
	private String empId;// 员工id
	private String customerId;// 客户id
	private String region;// 区域
	private String yellowRange;// 黄色预警范围
	private String redRange;// 红色预警范围
	private String note;// 备注

	private String regions;// 区域名称
	private String names;// 名称
	private String yellows;// 黄色
	private String reds;// 红色

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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getYellowRange() {
		return yellowRange;
	}

	public void setYellowRange(String yellowRange) {
		this.yellowRange = yellowRange;
	}

	public String getRedRange() {
		return redRange;
	}

	public void setRedRange(String redRange) {
		this.redRange = redRange;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRegions() {
		return regions;
	}

	public void setRegions(String regions) {
		this.regions = regions;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getYellows() {
		return yellows;
	}

	public void setYellows(String yellows) {
		this.yellows = yellows;
	}

	public String getReds() {
		return reds;
	}

	public void setReds(String reds) {
		this.reds = reds;
	}

}
