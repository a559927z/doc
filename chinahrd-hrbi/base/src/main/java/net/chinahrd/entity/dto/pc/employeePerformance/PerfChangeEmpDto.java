package net.chinahrd.entity.dto.pc.employeePerformance;

import java.io.Serializable;

 
/**
 * 员工绩效-员工简单DTO
 * @author guanjian
 * @author wqcai
 *
 */
public class PerfChangeEmpDto implements Serializable {
    private static final long serialVersionUID = 491630470398542343L;
    private String empId;                       //员工ID
    private String userName;                    //员工姓名
	private String imgPath;						//员工头像
	private String performanceName;				//当前绩效
	private String prevPerformanceName;			//上次绩效
    private Integer preType;					//绩效类型
    
    private Integer perKey;	//绩效编号 by jxzhang


	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getPerformanceName() {
		return performanceName;
	}

	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}

	public String getPrevPerformanceName() {
		return prevPerformanceName;
	}

	public void setPrevPerformanceName(String prevPerformanceName) {
		this.prevPerformanceName = prevPerformanceName;
	}

	public Integer getPerKey() {
		return perKey;
	}
	public void setPerKey(Integer perKey) {
		this.perKey = perKey;
	}
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
	public Integer getPreType() {
		return preType;
	}
	public void setPreType(Integer preType) {
		this.preType = preType;
	}
	
   
}
