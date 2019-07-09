package net.chinahrd.entity.dto.pc.humanInventory;

import java.io.Serializable;

public class HumanInventoryChartDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2645944766734737544L;
	private String date; // 日期时间
	private Double sumInput;// 投入金额
	private Double sumOutput;// 产出金额
	private Double sumMoreCon;// 盈利项目数量
	private Double sumLessCon;// 亏损项目数量
	private Double sumMoreGain;// 盈利金额
	private Double sumLessGain;// 亏损金额
	private int conNum;// 数量
	private String projectId;// 项目id
	private String projectName;// 项目名称
	private Double sumOutlay;// 项目投入花费
	private String projectTypeName;// 项目类型名称
	private String flag;// 标识字段
	private Double sumGain;// 总利润
	private Double input;// 投入金额
	private Double output;// 产出金额
	private Double gain;// 利润金额
	private Double manPower;// 人力
	private Double avgNum;// 人均项目数
	private Double perNum;// 

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getSumInput() {
		return sumInput;
	}

	public void setSumInput(Double sumInput) {
		this.sumInput = sumInput;
	}

	public Double getSumOutput() {
		return sumOutput;
	}

	public void setSumOutput(Double sumOutput) {
		this.sumOutput = sumOutput;
	}

	public Double getSumMoreCon() {
		return sumMoreCon;
	}

	public void setSumMoreCon(Double sumMoreCon) {
		this.sumMoreCon = sumMoreCon;
	}

	public Double getSumLessCon() {
		return sumLessCon;
	}

	public void setSumLessCon(Double sumLessCon) {
		this.sumLessCon = sumLessCon;
	}

	public Double getSumMoreGain() {
		return sumMoreGain;
	}

	public void setSumMoreGain(Double sumMoreGain) {
		this.sumMoreGain = sumMoreGain;
	}

	public Double getSumLessGain() {
		return sumLessGain;
	}

	public void setSumLessGain(Double sumLessGain) {
		this.sumLessGain = sumLessGain;
	}

	public int getConNum() {
		return conNum;
	}

	public void setConNum(int conNum) {
		this.conNum = conNum;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getSumOutlay() {
		return sumOutlay;
	}

	public void setSumOutlay(Double sumOutlay) {
		this.sumOutlay = sumOutlay;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getSumGain() {
		return sumGain;
	}

	public void setSumGain(Double sumGain) {
		this.sumGain = sumGain;
	}

	public Double getInput() {
		return input;
	}

	public void setInput(Double input) {
		this.input = input;
	}

	public Double getOutput() {
		return output;
	}

	public void setOutput(Double output) {
		this.output = output;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	public Double getManPower() {
		return manPower;
	}

	public void setManPower(Double manPower) {
		this.manPower = manPower;
	}

	public Double getAvgNum() {
		return avgNum;
	}

	public void setAvgNum(Double avgNum) {
		this.avgNum = avgNum;
	}

	public Double getPerNum() {
		return perNum;
	}

	public void setPerNum(Double perNum) {
		this.perNum = perNum;
	}

}
