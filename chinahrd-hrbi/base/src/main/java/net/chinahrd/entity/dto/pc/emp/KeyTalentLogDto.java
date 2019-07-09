package net.chinahrd.entity.dto.pc.emp;


import java.io.Serializable;

/**
 * 跟踪日志dto
 * Created by htpeng on 16/01/21 0021.
 */
public class KeyTalentLogDto implements Serializable {
    private static final long serialVersionUID = 5203404039451876992L;
    
    private String keyTalentLogId;                       //关键人才要素ID
    private String content;                       //内容
    private String keyTalentId;                       //关键人才ID
    private String customerId;                       //客户Id
    private String createEmpId;                //添加人员Id
    private String createEmpName;                //添加人员名称\
    private String imgPath;                //添加人员头像
    private String createTime;                     //创建时间
    private String refresh;                     //更新时间
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
	public String getKeyTalentLogId() {
		return keyTalentLogId;
	}
	public void setKeyTalentLogId(String keyTalentLogId) {
		this.keyTalentLogId = keyTalentLogId;
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
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getRefresh() {
		return refresh;
	}
	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}
	
	
	
}
