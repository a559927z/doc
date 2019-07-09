package net.chinahrd.entity.dto.pc.emp;


import java.io.Serializable;

/**
 * 标签数据模板
 * Created by htpeng on 16/01/21 0021.
 */
public class KeyTalentTagDto implements Serializable {

    private static final long serialVersionUID = 5203404039451876992L;
    private String tagId;                       //标签ID
    private String historyTagId;                       //历史标签ID
    
    private String customerId;                       //客户Id
    private String keyTalentId;                       //关键人才Id
    private int type;                       //类型  0-自动  1-优势  2-劣势
    private String createEmpId;                       //创建人id
    private String createEmpName;                       //创建人name
    private String content;                       //内容
    private int actionType;              //类型    1-新增  2-删除
    private String createTime;              //创建时间
    
    
	public String getHistoryTagId() {
		return historyTagId;
	}
	public void setHistoryTagId(String historyTagId) {
		this.historyTagId = historyTagId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreateEmpId() {
		return createEmpId;
	}
	public void setCreateEmpId(String createEmpId) {
		this.createEmpId = createEmpId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateEmpName() {
		return createEmpName;
	}
	public void setCreateEmpName(String createEmpName) {
		this.createEmpName = createEmpName;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
