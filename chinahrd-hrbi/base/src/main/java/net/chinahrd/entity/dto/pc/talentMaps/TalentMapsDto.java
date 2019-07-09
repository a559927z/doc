package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;

/**
 * 人才地图
 * 
 * @author xwLi 2016-07-18
 */
public class TalentMapsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5425385440094257100L;
	private String id;
	private String name;
	private String text;
	private String organId;
	private String organName;
	private String fullPath;
	private String updateTime;
	private String customerId;
	private int flag;
	private String adjustmentId;
	private String releaseId;
	private String empId;
	private String startTime;
	private String modifyTime;
	private String releaseTime;
	private String xaxis;
	private String xaxisAf;
	private String yaxis;
	private String yaxisAf;
	private String xaxisName;
	private String xaxisAfName;
	private String yaxisName;
	private String yaxisAfName;
	private String xData;
	private String yData;
	private String xDataAf;
	private String yDataAf;

	private String imgPath; // 图片路径
	private String userName; // 姓名
	private String ability;
	private String zText;
	private String zData;
	private String xText;

	private String date; // 时间
	private String cnDate; // 带汉字时间
	private String abilityNumberId; // 能力id
	private String abilityNumberName; // 能力名称
	private String performanceId; // 绩效id
	private String performanceName; // 绩效名称
	private Integer conNum; // 数量
	private String rankName; //
	private String positionName; // 岗位
	private Integer riskFlag; // 风险标识
	private String note;// 风险备注
	private String advantageTag;// 优势标签
	private String inferiorityTag;// 劣势标签
	private String encourage;// 激励要素
	private String curtName;
	private String abilityLvName;// 职级
	private String zName; // z轴名称
	private String empIds;// 多id
	private String userNames;// 多姓名
	
	private Integer yearMonth;
	private String topId;
	private Integer isKeyTalent;
	private String runOffDate;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(String adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getXaxis() {
		return xaxis;
	}

	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}

	public String getYaxis() {
		return yaxis;
	}

	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}

	public String getXaxisAf() {
		return xaxisAf;
	}

	public void setXaxisAf(String xaxisAf) {
		this.xaxisAf = xaxisAf;
	}

	public String getYaxisAf() {
		return yaxisAf;
	}

	public void setYaxisAf(String yaxisAf) {
		this.yaxisAf = yaxisAf;
	}

	public String getXaxisName() {
		return xaxisName;
	}

	public void setXaxisName(String xaxisName) {
		this.xaxisName = xaxisName;
	}

	public String getYaxisName() {
		return yaxisName;
	}

	public void setYaxisName(String yaxisName) {
		this.yaxisName = yaxisName;
	}

	public String getXaxisAfName() {
		return xaxisAfName;
	}

	public void setXaxisAfName(String xaxisAfName) {
		this.xaxisAfName = xaxisAfName;
	}

	public String getYaxisAfName() {
		return yaxisAfName;
	}

	public void setYaxisAfName(String yaxisAfName) {
		this.yaxisAfName = yaxisAfName;
	}

	public String getxDataAf() {
		return xDataAf;
	}

	public void setxDataAf(String xDataAf) {
		this.xDataAf = xDataAf;
	}

	public String getyDataAf() {
		return yDataAf;
	}

	public void setyDataAf(String yDataAf) {
		this.yDataAf = yDataAf;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public String getzText() {
		return zText;
	}

	public void setzText(String zText) {
		this.zText = zText;
	}

	public String getzData() {
		return zData;
	}

	public void setzData(String zData) {
		this.zData = zData;
	}

	public String getxText() {
		return xText;
	}

	public void setxText(String xText) {
		this.xText = xText;
	}

	public String getxData() {
		return xData;
	}

	public void setxData(String xData) {
		this.xData = xData;
	}

	public String getyData() {
		return yData;
	}

	public void setyData(String yData) {
		this.yData = yData;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCnDate() {
		return cnDate;
	}

	public void setCnDate(String cnDate) {
		this.cnDate = cnDate;
	}

	public String getAbilityNumberId() {
		return abilityNumberId;
	}

	public void setAbilityNumberId(String abilityNumberId) {
		this.abilityNumberId = abilityNumberId;
	}

	public String getAbilityNumberName() {
		return abilityNumberName;
	}

	public void setAbilityNumberName(String abilityNumberName) {
		this.abilityNumberName = abilityNumberName;
	}

	public String getPerformanceId() {
		return performanceId;
	}

	public void setPerformanceId(String performanceId) {
		this.performanceId = performanceId;
	}

	public String getPerformanceName() {
		return performanceName;
	}

	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}

	public Integer getConNum() {
		return conNum == null ? 0 : conNum;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getRiskFlag() {
		return riskFlag;
	}

	public void setRiskFlag(Integer riskFlag) {
		this.riskFlag = riskFlag;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getEncourage() {
		return encourage;
	}

	public void setEncourage(String encourage) {
		this.encourage = encourage;
	}

	public String getCurtName() {
		return curtName;
	}

	public void setCurtName(String curtName) {
		this.curtName = curtName;
	}

	public String getAbilityLvName() {
		return abilityLvName;
	}

	public void setAbilityLvName(String abilityLvName) {
		this.abilityLvName = abilityLvName;
	}

	public String getzName() {
		return zName;
	}

	public void setzName(String zName) {
		this.zName = zName;
	}

	public String getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getTopId() {
		return topId;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public Integer getIsKeyTalent() {
		return isKeyTalent;
	}

	public void setIsKeyTalent(Integer isKeyTalent) {
		this.isKeyTalent = isKeyTalent;
	}

	public String getRunOffDate() {
		return runOffDate;
	}

	public void setRunOffDate(String runOffDate) {
		this.runOffDate = runOffDate;
	}

}
