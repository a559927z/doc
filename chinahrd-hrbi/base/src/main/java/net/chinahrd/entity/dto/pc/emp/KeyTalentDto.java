package net.chinahrd.entity.dto.pc.emp;


import java.io.Serializable;

/**
 * 关键人才库dto
 * Created by htpeng on 16/01/21 0021.
 */
public class KeyTalentDto implements Serializable {
    private static final long serialVersionUID = 5203404039451876992L;
    
    private String keyTalentId;                       //关键人才ID
    private String customerId;                       //客户Id
    private String keyTalentTypeId;                       //关键人才类型ID
    private String createEmpId;                //添加人员Id
    private String empId;                //被关注员工Id
    private String empName;                //被关注员工姓名
    private String note;                       //激励要素备注
    private int sychron;                       //同步数据
    private int delete;                       //删除
    private String createTime;                     //创建时间
    private String refresh;                     //刷新时间
    private String modityEncourageEmpId;                     //激励要素最近修改人
    private String modityEncourageEmpName;                     //激励要素最近修改人
    private String refreshTag1;                     //刷新时间 优势标签
    private String refreshTag2;                     //刷新时间 劣势标签
    private String refreshLog;                     //刷新时间 跟踪日志
    private String refreshEncourage;                     //刷新时间 激励要术
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
	public String getKeyTalentTypeId() {
		return keyTalentTypeId;
	}
	public void setKeyTalentTypeId(String keyTalentTypeId) {
		this.keyTalentTypeId = keyTalentTypeId;
	}
	public String getCreateEmpId() {
		return createEmpId;
	}
	public void setCreateEmpId(String createEmpId) {
		this.createEmpId = createEmpId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getSychron() {
		return sychron;
	}
	public void setSychron(int sychron) {
		this.sychron = sychron;
	}
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRefresh() {
		return refresh;
	}
	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	
	public String getRefreshTag1() {
		return refreshTag1;
	}
	public void setRefreshTag1(String refreshTag1) {
		this.refreshTag1 = refreshTag1;
	}
	public String getRefreshTag2() {
		return refreshTag2;
	}
	public void setRefreshTag2(String refreshTag2) {
		this.refreshTag2 = refreshTag2;
	}
	public String getRefreshLog() {
		return refreshLog;
	}
	public void setRefreshLog(String refreshLog) {
		this.refreshLog = refreshLog;
	}
	public String getRefreshEncourage() {
		return refreshEncourage;
	}
	public void setRefreshEncourage(String refreshEncourage) {
		this.refreshEncourage = refreshEncourage;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getModityEncourageEmpId() {
		return modityEncourageEmpId;
	}
	public void setModityEncourageEmpId(String modityEncourageEmpId) {
		this.modityEncourageEmpId = modityEncourageEmpId;
	}
	public String getModityEncourageEmpName() {
		return modityEncourageEmpName;
	}
	public void setModityEncourageEmpName(String modityEncourageEmpName) {
		this.modityEncourageEmpName = modityEncourageEmpName;
	}
    
    
}
