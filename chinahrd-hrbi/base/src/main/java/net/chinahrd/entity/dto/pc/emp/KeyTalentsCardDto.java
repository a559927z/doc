package net.chinahrd.entity.dto.pc.emp;


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
    private String keyTalentTypeName;                //关键人才类型
    private int riskFlag;
    private int advantageTagCount;   //优势标签数量
    private String advantageTagContent; //优势标签内容
    private Date advantageTagLastDate;   //优势标签最新更新时间
    private int inferiorityTagCount;   //劣势标签数量
    private String inferiorityTagContent;	//劣势标签内容
    private Date inferiorityTagLastDate;   //劣势标签最新更新时间
    private int logCount;          //跟踪日志数量
    private String logContent;		//跟踪日志内容
    private Date logLastDate;   //跟踪日志最新更新时间
    private int encourageCount;                 //激励要数 数量
    private Date encourageLastDate;   //跟踪日志最新更新时间
    private String encourageContent;         //显示类容
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

	public int getAdvantageTagCount() {
		return advantageTagCount;
	}

	public void setAdvantageTagCount(int advantageTagCount) {
		this.advantageTagCount = advantageTagCount;
	}

	public int getInferiorityTagCount() {
		return inferiorityTagCount;
	}

	public void setInferiorityTagCount(int inferiorityTagCount) {
		this.inferiorityTagCount = inferiorityTagCount;
	}

	public int getLogCount() {
		return logCount;
	}

	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}

	public int getEncourageCount() {
		return encourageCount;
	}

	public void setEncourageCount(int encourageCount) {
		this.encourageCount = encourageCount;
	}

	public String getEncourageContent() {
		return encourageContent;
	}

	public void setEncourageContent(String encourageContent) {
		this.encourageContent = encourageContent;
	}

	public Date getAdvantageTagLastDate() {
		return advantageTagLastDate;
	}

	public void setAdvantageTagLastDate(Date advantageTagLastDate) {
		this.advantageTagLastDate = advantageTagLastDate;
	}

	public Date getInferiorityTagLastDate() {
		return inferiorityTagLastDate;
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

	public void setInferiorityTagLastDate(Date inferiorityTagLastDate) {
		this.inferiorityTagLastDate = inferiorityTagLastDate;
	}

	public Date getLogLastDate() {
		return logLastDate;
	}

	public void setLogLastDate(Date logLastDate) {
		this.logLastDate = logLastDate;
	}

	public Date getEncourageLastDate() {
		return encourageLastDate;
	}

	public void setEncourageLastDate(Date encourageLastDate) {
		this.encourageLastDate = encourageLastDate;
	}

	public String getAdvantageTagContent() {
		return advantageTagContent;
	}

	public void setAdvantageTagContent(String advantageTagContent) {
		this.advantageTagContent = advantageTagContent;
	}

	public String getInferiorityTagContent() {
		return inferiorityTagContent;
	}

	public void setInferiorityTagContent(String inferiorityTagContent) {
		this.inferiorityTagContent = inferiorityTagContent;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

    
}
