package net.chinahrd.entity.dto.pc.humanInventory;

import java.io.Serializable;

/**
 * 人力盘点dto controller传递参数用dto
 * 
 * @author malong 2017-01-12
 * 
 */
public class HumanInventoryParamDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2899740245151479164L;
	private String customerId;// 客户id
	private String organId; // 组织机构id
	private String startDate; // 项目开始时间
	private String endDate; // 项目结束时间
	private Double startManpowerInYear; // 本年人力投入-开始值
	private Double endManpowerInYear; // 本年人力投入-结束值
	private Double startManpowerInMonth; // 本月人力投入-开始值
	private Double endManpowerInMonth; // 本月人力投入-结束值
	private Double startManpowerLastMonth; // 上月人力投入-开始值
	private Double endManpowerLastMonth; // 上月人力投入-结束值
	private Double startFeeYearInput; // 本年费用投入-开始值
	private Double endFeeYearInput; // 本年费用投入-结束值
	private Double startFeeMonthInput; // 本月费用投入-开始值
	private Double endFeeMonthInput; // 本月费用投入-结束值
	private Double startFeeLastMonthInput; // 上月费用投入-开始值
	private Double endFeeLastMonthInput; // 上月费用投入-结束值
	private Double startGainInYear; // 本年项目利润-开始值
	private Double endGainInYear; // 本年项目利润-结束值
	private Double startGainInMonth; // 本月项目利润-开始值
	private Double endGainInMonth; // 本月项目利润-结束值
	private Double startGainLastMonth; // 上月项目利润-开始值
	private Double endGainLastMonth; // 上月项目利润-结束值
	private String projectProgress; // 项目进度
	private String manpowerInputSelect;// 人力投入选择值
	private String startManpowerInput;// 人力投入-开始值
	private String endManpowerInput;// 人力投入-结束值
	private String feeInputSelect;// 费用投入选择值
	private String startFeeInput;// 费用投入-开始值
	private String endFeeInput;// 费用投入-结束值
	private String projectGainSelect;// 项目利润选择值
	private String startProjectGain;// 项目利润-开始值
	private String endProjectGain;// 项目利润-结束值
	private String type;// 标识字段
	private String keyName;
	private String principal;// 负责人名称
	private String participateManpowerInputSelect;// 参与项目-人力投入-选中值
	private String participateStartInput;// 参与项目-人力投入-开始值
	private String participateEndInput;// 参与项目-人力投入-结束值
	private Double startProjectNumInYear;// 累计参与项目数-开始值
	private Double endProjectNumInYear;// 累计参与项目数-结束值
	private Double startProjectNum;// 当前参与项目数-开始值
	private Double endProjectNum;// 当前参与项目数-结束值

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
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

	public Double getStartManpowerInYear() {
		return startManpowerInYear;
	}

	public void setStartManpowerInYear(Double startManpowerInYear) {
		this.startManpowerInYear = startManpowerInYear;
	}

	public Double getEndManpowerInYear() {
		return endManpowerInYear;
	}

	public void setEndManpowerInYear(Double endManpowerInYear) {
		this.endManpowerInYear = endManpowerInYear;
	}

	public Double getStartManpowerInMonth() {
		return startManpowerInMonth;
	}

	public void setStartManpowerInMonth(Double startManpowerInMonth) {
		this.startManpowerInMonth = startManpowerInMonth;
	}

	public Double getEndManpowerInMonth() {
		return endManpowerInMonth;
	}

	public void setEndManpowerInMonth(Double endManpowerInMonth) {
		this.endManpowerInMonth = endManpowerInMonth;
	}

	public Double getStartManpowerLastMonth() {
		return startManpowerLastMonth;
	}

	public void setStartManpowerLastMonth(Double startManpowerLastMonth) {
		this.startManpowerLastMonth = startManpowerLastMonth;
	}

	public Double getEndManpowerLastMonth() {
		return endManpowerLastMonth;
	}

	public void setEndManpowerLastMonth(Double endManpowerLastMonth) {
		this.endManpowerLastMonth = endManpowerLastMonth;
	}

	public Double getStartFeeYearInput() {
		return startFeeYearInput;
	}

	public void setStartFeeYearInput(Double startFeeYearInput) {
		this.startFeeYearInput = startFeeYearInput;
	}

	public Double getEndFeeYearInput() {
		return endFeeYearInput;
	}

	public void setEndFeeYearInput(Double endFeeYearInput) {
		this.endFeeYearInput = endFeeYearInput;
	}

	public Double getStartFeeMonthInput() {
		return startFeeMonthInput;
	}

	public void setStartFeeMonthInput(Double startFeeMonthInput) {
		this.startFeeMonthInput = startFeeMonthInput;
	}

	public Double getEndFeeMonthInput() {
		return endFeeMonthInput;
	}

	public void setEndFeeMonthInput(Double endFeeMonthInput) {
		this.endFeeMonthInput = endFeeMonthInput;
	}

	public Double getStartFeeLastMonthInput() {
		return startFeeLastMonthInput;
	}

	public void setStartFeeLastMonthInput(Double startFeeLastMonthInput) {
		this.startFeeLastMonthInput = startFeeLastMonthInput;
	}

	public Double getEndFeeLastMonthInput() {
		return endFeeLastMonthInput;
	}

	public void setEndFeeLastMonthInput(Double endFeeLastMonthInput) {
		this.endFeeLastMonthInput = endFeeLastMonthInput;
	}

	public Double getStartGainInYear() {
		return startGainInYear;
	}

	public void setStartGainInYear(Double startGainInYear) {
		this.startGainInYear = startGainInYear;
	}

	public Double getEndGainInYear() {
		return endGainInYear;
	}

	public void setEndGainInYear(Double endGainInYear) {
		this.endGainInYear = endGainInYear;
	}

	public Double getStartGainInMonth() {
		return startGainInMonth;
	}

	public void setStartGainInMonth(Double startGainInMonth) {
		this.startGainInMonth = startGainInMonth;
	}

	public Double getEndGainInMonth() {
		return endGainInMonth;
	}

	public void setEndGainInMonth(Double endGainInMonth) {
		this.endGainInMonth = endGainInMonth;
	}

	public Double getStartGainLastMonth() {
		return startGainLastMonth;
	}

	public void setStartGainLastMonth(Double startGainLastMonth) {
		this.startGainLastMonth = startGainLastMonth;
	}

	public Double getEndGainLastMonth() {
		return endGainLastMonth;
	}

	public void setEndGainLastMonth(Double endGainLastMonth) {
		this.endGainLastMonth = endGainLastMonth;
	}

	public String getProjectProgress() {
		return projectProgress;
	}

	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}

	public String getManpowerInputSelect() {
		return manpowerInputSelect;
	}

	public void setManpowerInputSelect(String manpowerInputSelect) {
		this.manpowerInputSelect = manpowerInputSelect;
	}

	public String getStartManpowerInput() {
		return startManpowerInput;
	}

	public void setStartManpowerInput(String startManpowerInput) {
		this.startManpowerInput = startManpowerInput;
	}

	public String getEndManpowerInput() {
		return endManpowerInput;
	}

	public void setEndManpowerInput(String endManpowerInput) {
		this.endManpowerInput = endManpowerInput;
	}

	public String getFeeInputSelect() {
		return feeInputSelect;
	}

	public void setFeeInputSelect(String feeInputSelect) {
		this.feeInputSelect = feeInputSelect;
	}

	public String getStartFeeInput() {
		return startFeeInput;
	}

	public void setStartFeeInput(String startFeeInput) {
		this.startFeeInput = startFeeInput;
	}

	public String getEndFeeInput() {
		return endFeeInput;
	}

	public void setEndFeeInput(String endFeeInput) {
		this.endFeeInput = endFeeInput;
	}

	public String getProjectGainSelect() {
		return projectGainSelect;
	}

	public void setProjectGainSelect(String projectGainSelect) {
		this.projectGainSelect = projectGainSelect;
	}

	public String getStartProjectGain() {
		return startProjectGain;
	}

	public void setStartProjectGain(String startProjectGain) {
		this.startProjectGain = startProjectGain;
	}

	public String getEndProjectGain() {
		return endProjectGain;
	}

	public void setEndProjectGain(String endProjectGain) {
		this.endProjectGain = endProjectGain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getParticipateManpowerInputSelect() {
		return participateManpowerInputSelect;
	}

	public void setParticipateManpowerInputSelect(String participateManpowerInputSelect) {
		this.participateManpowerInputSelect = participateManpowerInputSelect;
	}

	public String getParticipateStartInput() {
		return participateStartInput;
	}

	public void setParticipateStartInput(String participateStartInput) {
		this.participateStartInput = participateStartInput;
	}

	public String getParticipateEndInput() {
		return participateEndInput;
	}

	public void setParticipateEndInput(String participateEndInput) {
		this.participateEndInput = participateEndInput;
	}

	public Double getStartProjectNumInYear() {
		return startProjectNumInYear;
	}

	public void setStartProjectNumInYear(Double startProjectNumInYear) {
		this.startProjectNumInYear = startProjectNumInYear;
	}

	public Double getEndProjectNumInYear() {
		return endProjectNumInYear;
	}

	public void setEndProjectNumInYear(Double endProjectNumInYear) {
		this.endProjectNumInYear = endProjectNumInYear;
	}

	public Double getStartProjectNum() {
		return startProjectNum;
	}

	public void setStartProjectNum(Double startProjectNum) {
		this.startProjectNum = startProjectNum;
	}

	public Double getEndProjectNum() {
		return endProjectNum;
	}

	public void setEndProjectNum(Double endProjectNum) {
		this.endProjectNum = endProjectNum;
	}

}
