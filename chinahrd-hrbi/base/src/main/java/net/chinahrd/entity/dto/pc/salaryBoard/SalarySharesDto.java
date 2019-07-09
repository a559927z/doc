package net.chinahrd.entity.dto.pc.salaryBoard;

import java.io.Serializable;


/**
 * 持股 dto
 * 
 * @author qpzhu by 2016-04-08
 */
public class SalarySharesDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8610662594264628404L;
	
	private String year;//年度
	
	private String organizationName;//组织机构

	private double sumShares;//持股数量
	
	private int empSharesCount;//持股员工数
	
	private double sharesCover;//持股覆盖率
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public double getSumShares() {
		return sumShares;
	}

	public void setSumShares(double sumShares) {
		this.sumShares = sumShares;
	}

	public void setSumShares(int sumShares) {
		this.sumShares = sumShares;
	}

	public int getEmpSharesCount() {
		return empSharesCount;
	}

	public void setEmpSharesCount(int empSharesCount) {
		this.empSharesCount = empSharesCount;
	}

	public double getSharesCover() {
		return sharesCover;
	}

	public void setSharesCover(double sharesCover) {
		this.sharesCover = sharesCover;
	}
	
	
}
