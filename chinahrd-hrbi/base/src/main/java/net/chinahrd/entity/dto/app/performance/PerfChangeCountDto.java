package net.chinahrd.entity.dto.app.performance;

import java.io.Serializable;

/**
 * 员工绩效 员工数量 dto
 * @author htpeng
 *2016年6月6日下午12:05:21
 */
public class PerfChangeCountDto implements Serializable {

    private static final long serialVersionUID = -8982011075350343543L;
	
    private int total;   
    private int managerCount;
    private int empCount;
    private String organId;
    private String organName;
    private Double count;
    private Double perCount;
    private Double rate;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getManagerCount() {
		return managerCount;
	}
	public void setManagerCount(int managerCount) {
		this.managerCount = managerCount;
	}
	public int getEmpCount() {
		return empCount;
	}
	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}
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
	public Double getPerCount() {
		return perCount;
	}
	public void setPerCount(Double perCount) {
		this.perCount = perCount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
   
}
