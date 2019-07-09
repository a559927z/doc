package net.chinahrd.entity.dto.pc.humanInventory;

import java.io.Serializable;

/**
 * 人力盘点dto
 * 
 */
public class HumanInventoryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2127823926123270816L;

	private String id;
	private String organId; // 组织机构ID
	private String organName; // 组织名称
	private String customerId; // 客户ID
	private String projectId; // 项目ID
	private String projectName; // 项目名称
	private String empId;
	private String empName; // 负责人
	private double manpowerInYear; // 本年人力
	private double manpowerInMonth; // 本月人力
	private double manpowerLastMonth; // 上月人力
	private double manpowerChangeRate; // 人力变化率
	private double feeYearInput; // 本年投入费用
	private double feeMonthInput; // 本月投入费用
	private double feeLastMonthInput; // 上月投入费用
	private double feeChangeRate; // 费用变化率
	private double gainInYear; // 累计利润
	private double gainInMonth; // 本月利润
	private double gainLastMonth; // 上月利润
	private double gainChangeRate; // 利润变化率
	private double manageSeries; // 管理序列
	private double functionalSeries; // 职能序列
	private double professionalSeries; // 专业序列
	private double BusinessSeries; // 业务序列
	private String startDate; // 项目开始时间
	private String endDate; // 项目结束时间
	private String projectProgress; // 项目进度
	private Integer projectNumInYear; // 本年参与项目数
	private Integer projectNum; // 现参与项目数
	private Integer isPrincipal; // 负责项目数
	private boolean isLeaf; //是否有子节点
	private int hasChildren; // 是否有子节点

	private double laborCost; // 人工费用
	private double lodgingCost; // 住宿费用
	private double travelCost; // 差旅费用
	private double socialSecurityFee; // 社保费
	private double otherCost; // 其他费用
	private double socialSecurityPurchaseFee; // 社保购置费用
	private double specialMaterialCost; // 专用材料费用
	private double officeCost; // 办公费用
	private double fixedAssetCost; // 固定资产费用
	private double total; // 合计
	private double inputOutputRatio; // 投入产出比
	private double inputFee; // 投入费用
	private double outputFee; // 产出费用
	private double inputFeeInYear; // 投入费用
	private double outputFeeInYear; // 产出费用
	private int yearMonth; // 年月
	private String subProjectName; // 子项目
	private String subProjectId; // 子项目
	private double manpowerInput; // 人力投入
	private String workContent; // 工作内容
	private int type;// 日期类型
	private String projectInputTypeId;// 项目投入费用类型Id
	private String projectInputTypeName;// 项目投入费用类型名称
	private String userName;// 负责人名称
	private String organFullName;// 组织全称
	private String projectKey;// 项目编码
	private String projectTypeId;// 项目类型id
	private String projectProgressId;// 项目进度id
	private String projectParentId;// 父项目id
	private String fullPath;// 全路径
	private String refresh;// 更新日期
	private String projectTypeName;// 项目类型名称
	private int showIndex; // 排序
	private String codeItemId; // 字典表id
	private String codeItemName; // 字典表名称
	private String userKey; // 用户编码
	private String projectInputDetailId; // 项目投入明细id
	private double outlay;// 花费
	private String date;// 盘点日期
	private double input;// 投入
	private double output;// 产出
	private double gain;// 利润
	private String projectCostId;// 项目费用明细id
	private String projectPartakeId;// 主导项目参与项目Id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectPartakeId() {
		return projectPartakeId;
	}

	public void setProjectPartakeId(String projectPartakeId) {
		this.projectPartakeId = projectPartakeId;
	}

	public String getProjectCostId() {
		return projectCostId;
	}

	public void setProjectCostId(String projectCostId) {
		this.projectCostId = projectCostId;
	}

	public double getInput() {
		return input;
	}

	public void setInput(double input) {
		this.input = input;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public String getProjectInputDetailId() {
		return projectInputDetailId;
	}

	public void setProjectInputDetailId(String projectInputDetailId) {
		this.projectInputDetailId = projectInputDetailId;
	}

	public double getOutlay() {
		return outlay;
	}

	public void setOutlay(double outlay) {
		this.outlay = outlay;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getProjectInputTypeId() {
		return projectInputTypeId;
	}

	public void setProjectInputTypeId(String projectInputTypeId) {
		this.projectInputTypeId = projectInputTypeId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getCodeItemId() {
		return codeItemId;
	}

	public void setCodeItemId(String codeItemId) {
		this.codeItemId = codeItemId;
	}

	public String getCodeItemName() {
		return codeItemName;
	}

	public void setCodeItemName(String codeItemName) {
		this.codeItemName = codeItemName;
	}

	public int getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(int showIndex) {
		this.showIndex = showIndex;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public String getProjectProgressId() {
		return projectProgressId;
	}

	public void setProjectProgressId(String projectProgressId) {
		this.projectProgressId = projectProgressId;
	}

	public String getProjectParentId() {
		return projectParentId;
	}

	public void setProjectParentId(String projectParentId) {
		this.projectParentId = projectParentId;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getOrganFullName() {
		return organFullName;
	}

	public void setOrganFullName(String organFullName) {
		this.organFullName = organFullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectInputTypeName() {
		return projectInputTypeName;
	}

	public void setProjectInputTypeName(String projectInputTypeName) {
		this.projectInputTypeName = projectInputTypeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public double getManpowerInYear() {
		return manpowerInYear;
	}

	public void setManpowerInYear(double manpowerInYear) {
		this.manpowerInYear = manpowerInYear;
	}

	public double getManpowerInMonth() {
		return manpowerInMonth;
	}

	public void setManpowerInMonth(double manpowerInMonth) {
		this.manpowerInMonth = manpowerInMonth;
	}

	public double getManpowerLastMonth() {
		return manpowerLastMonth;
	}

	public void setManpowerLastMonth(double manpowerLastMonth) {
		this.manpowerLastMonth = manpowerLastMonth;
	}

	public double getManpowerChangeRate() {
		return manpowerChangeRate;
	}

	public void setManpowerChangeRate(double manpowerChangeRate) {
		this.manpowerChangeRate = manpowerChangeRate;
	}

	public double getFeeYearInput() {
		return feeYearInput;
	}

	public void setFeeYearInput(double feeYearInput) {
		this.feeYearInput = feeYearInput;
	}

	public double getFeeMonthInput() {
		return feeMonthInput;
	}

	public void setFeeMonthInput(double feeMonthInput) {
		this.feeMonthInput = feeMonthInput;
	}

	public double getFeeLastMonthInput() {
		return feeLastMonthInput;
	}

	public void setFeeLastMonthInput(double feeLastMonthInput) {
		this.feeLastMonthInput = feeLastMonthInput;
	}

	public double getFeeChangeRate() {
		return feeChangeRate;
	}

	public void setFeeChangeRate(double feeChangeRate) {
		this.feeChangeRate = feeChangeRate;
	}

	public double getGainInYear() {
		return gainInYear;
	}

	public void setGainInYear(double gainInYear) {
		this.gainInYear = gainInYear;
	}

	public double getGainInMonth() {
		return gainInMonth;
	}

	public void setGainInMonth(double gainInMonth) {
		this.gainInMonth = gainInMonth;
	}

	public double getGainLastMonth() {
		return gainLastMonth;
	}

	public void setGainLastMonth(double gainLastMonth) {
		this.gainLastMonth = gainLastMonth;
	}

	public double getGainChangeRate() {
		return gainChangeRate;
	}

	public void setGainChangeRate(double gainChangeRate) {
		this.gainChangeRate = gainChangeRate;
	}

	public double getManageSeries() {
		return manageSeries;
	}

	public void setManageSeries(double manageSeries) {
		this.manageSeries = manageSeries;
	}

	public double getFunctionalSeries() {
		return functionalSeries;
	}

	public void setFunctionalSeries(double functionalSeries) {
		this.functionalSeries = functionalSeries;
	}

	public double getProfessionalSeries() {
		return professionalSeries;
	}

	public void setProfessionalSeries(double professionalSeries) {
		this.professionalSeries = professionalSeries;
	}

	public double getBusinessSeries() {
		return BusinessSeries;
	}

	public void setBusinessSeries(double businessSeries) {
		BusinessSeries = businessSeries;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProjectProgress() {
		return projectProgress;
	}

	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}

	public Integer getProjectNumInYear() {
		return projectNumInYear;
	}

	public void setProjectNumInYear(Integer projectNumInYear) {
		this.projectNumInYear = projectNumInYear;
	}

	public Integer getProjectNun() {
		return projectNum;
	}

	public void setProjectNun(Integer projectNum) {
		this.projectNum = projectNum;
	}

	public Integer getIsPrincipal() {
		return isPrincipal;
	}

	public void setIsPrincipal(Integer isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(Integer projectNum) {
		this.projectNum = projectNum;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public int getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	public boolean getIsLeaf() {
		return getHasChildren()==1?false:true;
	}
	
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	 
	public double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(double laborCost) {
		this.laborCost = laborCost;
	}

	public double getLodgingCost() {
		return lodgingCost;
	}

	public void setLodgingCost(double lodgingCost) {
		this.lodgingCost = lodgingCost;
	}

	public double getTravelCost() {
		return travelCost;
	}

	public void setTravelCost(double travelCost) {
		this.travelCost = travelCost;
	}

	public double getSocialSecurityFee() {
		return socialSecurityFee;
	}

	public void setSocialSecurityFee(double socialSecurityFee) {
		this.socialSecurityFee = socialSecurityFee;
	}

	public double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(double otherCost) {
		this.otherCost = otherCost;
	}

	public double getSocialSecurityPurchaseFee() {
		return socialSecurityPurchaseFee;
	}

	public void setSocialSecurityPurchaseFee(double socialSecurityPurchaseFee) {
		this.socialSecurityPurchaseFee = socialSecurityPurchaseFee;
	}

	public double getSpecialMaterialCost() {
		return specialMaterialCost;
	}

	public void setSpecialMaterialCost(double specialMaterialCost) {
		this.specialMaterialCost = specialMaterialCost;
	}

	public double getOfficeCost() {
		return officeCost;
	}

	public void setOfficeCost(double officeCost) {
		this.officeCost = officeCost;
	}

	public double getFixedAssetCost() {
		return fixedAssetCost;
	}

	public void setFixedAssetCost(double fixedAssetCost) {
		this.fixedAssetCost = fixedAssetCost;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getInputOutputRatio() {
		return inputOutputRatio;
	}

	public void setInputOutputRatio(double inputOutputRatio) {
		this.inputOutputRatio = inputOutputRatio;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public double getInputFee() {
		return inputFee;
	}

	public void setInputFee(double inputFee) {
		this.inputFee = inputFee;
	}

	public double getOutputFee() {
		return outputFee;
	}

	public void setOutputFee(double outputFee) {
		this.outputFee = outputFee;
	}

	public String getSubProjectName() {
		return subProjectName;
	}

	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}

	public double getManpowerInput() {
		return manpowerInput;
	}

	public void setManpowerInput(double manpowerInput) {
		this.manpowerInput = manpowerInput;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public double getInputFeeInYear() {
		return inputFeeInYear;
	}

	public void setInputFeeInYear(double inputFeeInYear) {
		this.inputFeeInYear = inputFeeInYear;
	}

	public double getOutputFeeInYear() {
		return outputFeeInYear;
	}

	public void setOutputFeeInYear(double outputFeeInYear) {
		this.outputFeeInYear = outputFeeInYear;
	}

	public String getSubProjectId() {
		return subProjectId;
	}

	public void setSubProjectId(String subProjectId) {
		this.subProjectId = subProjectId;
	}

}
