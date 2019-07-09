package net.chinahrd.entity.dto.app.keyTalents;


import java.io.Serializable;
import java.util.Date;

/**
 * 关键人才库  页面卡片 数据模板
 * Created by htpeng on 16/01/21 0021.
 */
public class KeyTalentsCardDto implements Serializable {
    private static final long serialVersionUID = 5203404039451876992L;
    private String keyTalentId;                       //关键人才ID
    private String createEmpId;                       //创建人Id
    private String focusesId;                       //关注Id
    private String empId;                       //员工ID
    private String imgPath;                     //头像
    private String empKey;                      //员工编码
    private String userName;                    //中文名
    private String positionName;                //现岗位
    private String organizationName;                //现部门
    private String keyTalentTypeName;                //关键人才类型
    private int riskFlag;
    private String encourageContent;         //激励要术类容
    
    private String advantageTag;			//优势标签
    private String inferiorityTag;			//劣势标签
    private String logContent;				//日志
    
    
    private int sychron;   //是否同步
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpKey() {
        return empKey;
    }

    public void setEmpKey(String empKey) {
        this.empKey = empKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getKeyTalentTypeName() {
		return keyTalentTypeName;
	}

	public void setKeyTalentTypeName(String keyTalentTypeName) {
		this.keyTalentTypeName = keyTalentTypeName;
	}

	public int getRiskFlag() {
		return riskFlag;
	}

	public void setRiskFlag(int riskFlag) {
		this.riskFlag = riskFlag;
	}

	
	public String getEncourageContent() {
		return encourageContent;
	}

	public void setEncourageContent(String encourageContent) {
		this.encourageContent = encourageContent;
	}



	public String getKeyTalentId() {
		return keyTalentId;
	}

	public void setKeyTalentId(String keyTalentId) {
		this.keyTalentId = keyTalentId;
	}

	public String getCreateEmpId() {
		return createEmpId;
	}

	public void setCreateEmpId(String createEmpId) {
		this.createEmpId = createEmpId;
	}

	public String getFocusesId() {
		return focusesId;
	}

	public void setFocusesId(String focusesId) {
		this.focusesId = focusesId;
	}

	public int getSychron() {
		return sychron;
	}

	public void setSychron(int sychron) {
		this.sychron = sychron;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAdvantageTag() {
		return advantageTag;
	}

	public void setAdvantageTag(String advantageTag) {
		this.advantageTag = advantageTag;
	}

	public String getInferiorityTag() {
		return inferiorityTag;
	}

	public void setInferiorityTag(String inferiorityTag) {
		this.inferiorityTag = inferiorityTag;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	
    
}
