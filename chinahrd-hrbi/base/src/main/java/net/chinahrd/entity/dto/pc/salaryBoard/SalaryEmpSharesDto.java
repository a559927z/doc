package net.chinahrd.entity.dto.pc.salaryBoard;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 员工股票 dto
 * 
 * @author qpzhu by 2016-04-08
 */
public class SalaryEmpSharesDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8610662594264628404L;

	private String empName;//员工姓名
	
	private int currentShares;//当前数量
	
	private int grantShares;//授予数量
	
	private int grantPrice;//授予价格
	
	private String holdYear;//持有年
	
	private int subtractShares;//最近减持数量
	
	private Timestamp subtractDate;//最近减持时间

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getCurrentShares() {
		return currentShares;
	}

	public void setCurrentShares(int currentShares) {
		this.currentShares = currentShares;
	}

	public int getGrantShares() {
		return grantShares;
	}

	public void setGrantShares(int grantShares) {
		this.grantShares = grantShares;
	}

	public int getGrantPrice() {
		return grantPrice;
	}

	public void setGrantPrice(int grantPrice) {
		this.grantPrice = grantPrice;
	}

	public String getHoldYear() {
		return holdYear;
	}

	public void setHoldYear(String holdYear) {
		this.holdYear = holdYear;
	}

	public int getSubtractShares() {
		return subtractShares;
	}

	public void setSubtractShares(int subtractShares) {
		this.subtractShares = subtractShares;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Timestamp getSubtractDate() {
		return subtractDate;
	}

	public void setSubtractDate(Timestamp subtractDate) {
		this.subtractDate = subtractDate;
	}

	

	
	
	
	
}
