package net.chinahrd.entity.dto.pc.emp;


import java.io.Serializable;

/**
 * 关键人才库  页面卡片 数据模板
 * Created by htpeng on 16/01/21 0021.
 */
public class FocusesDto implements Serializable {
    private static final long serialVersionUID = 5203404039451876992L;
    private String keyTalentFocusesId;                       //关键人才ID
    private String customerId;                       //客户Id
    private String empId;                       //添加人员Id
    private String keyTalentId;                       //关键人才Id
    private String createTime;                     //创建时间
	public String getKeyTalentFocusesId() {
		return keyTalentFocusesId;
	}
	public void setKeyTalentFocusesId(String keyTalentFocusesId) {
		this.keyTalentFocusesId = keyTalentFocusesId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getKeyTalentId() {
		return keyTalentId;
	}
	public void setKeyTalentId(String keyTalentId) {
		this.keyTalentId = keyTalentId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    
 


    
}
