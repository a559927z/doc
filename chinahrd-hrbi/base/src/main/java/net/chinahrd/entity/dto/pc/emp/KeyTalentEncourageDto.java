package net.chinahrd.entity.dto.pc.emp;


import java.io.Serializable;

/**
 * 关键人才库核心激励要素dto
 * Created by htpeng on 16/01/21 0021.
 */
public class KeyTalentEncourageDto implements Serializable {
    private static final long serialVersionUID = 5203404039451876992L;
    
    private String keyTalentEncourageId;                       //关键人才要素ID
    private String encourageId;                       //关键人才要素维度ID
    private String keyTalentId;                       //关键人才ID
    private String customerId;                       //客户Id
    private String createEmpId;                //添加人员Id
    private String createTime;                     //创建时间
//    private String content;                     //
    
	public String getKeyTalentId() {
		return keyTalentId;
	}
	public void setKeyTalentId(String keyTalentId) {
		this.keyTalentId = keyTalentId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCreateEmpId() {
		return createEmpId;
	}
	public void setCreateEmpId(String createEmpId) {
		this.createEmpId = createEmpId;
	}

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getKeyTalentEncourageId() {
		return keyTalentEncourageId;
	}
	public void setKeyTalentEncourageId(String keyTalentEncourageId) {
		this.keyTalentEncourageId = keyTalentEncourageId;
	}
	public String getEncourageId() {
		return encourageId;
	}
	public void setEncourageId(String encourageId) {
		this.encourageId = encourageId;
	}
	
	
}
