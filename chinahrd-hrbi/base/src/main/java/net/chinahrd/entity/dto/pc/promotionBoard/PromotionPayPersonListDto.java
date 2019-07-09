package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PromotionPayPersonListDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private String empId;
    private String userName;
    private double salary;
}
