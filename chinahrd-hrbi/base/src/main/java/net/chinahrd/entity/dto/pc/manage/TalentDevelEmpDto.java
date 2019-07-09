package net.chinahrd.entity.dto.pc.manage;

import java.util.Date;

import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;

public class TalentDevelEmpDto extends EmpBaseInfoDto {

	private static final long serialVersionUID = 5616404282284576629L;

	/** 模块 */
	private Integer moduleType;
	/** 360测评项目名称 */
	private String abilityName;
	/** 标准分 */
	private Double standardScore;
	/** 实际得分 */
	private Double gainScore;
	/** 成绩（实际得分/标准分） */
	private Double score;

	/** 平均实际得分 */
	private Double gainScoreAvg;
	/** 测评总人数 */
	private Integer total;
	/** 测评项目总人数 */
	private Integer abTotal;

	/** 入职时职级 */
	private String entryRankName;
	private Date entryDate;
	/** 当前时职级 */
	private String rankName;
	private Date promoteDate;
	/** 晋升所花的时间 */
	private Double stayTime;
	/** 晋升次数 */
	private Integer pomoteNum;
	/** 上次职级 */
	private String rankNameEd;
	/** 上次到现在所花的时间 */
	private Double stayTimeEd;
	
	/** 季度 */
	private String yearQuarter;
	/** 年 */
	private Integer year;

	/** 培训总时间 */
	private Double trainTime;
	/** 培训总次数 */
	private Integer trainNum;
	
	/** 匹配度 */
	private Double abilityMatch;
	/** 测评时间 */
	private Date examDate;
	
	

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public Double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}

	public Double getGainScore() {
		return gainScore;
	}

	public void setGainScore(Double gainScore) {
		this.gainScore = gainScore;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getGainScoreAvg() {
		return gainScoreAvg;
	}

	public void setGainScoreAvg(Double gainScoreAvg) {
		this.gainScoreAvg = gainScoreAvg;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAbTotal() {
		return abTotal;
	}

	public void setAbTotal(Integer abTotal) {
		this.abTotal = abTotal;
	}

	public String getEntryRankName() {
		return entryRankName;
	}

	public void setEntryRankName(String entryRankName) {
		this.entryRankName = entryRankName;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public Date getPromoteDate() {
		return promoteDate;
	}

	public void setPromoteDate(Date promoteDate) {
		this.promoteDate = promoteDate;
	}

	public Double getStayTime() {
		return stayTime;
	}

	public void setStayTime(Double stayTime) {
		this.stayTime = stayTime;
	}

	public Integer getPomoteNum() {
		return pomoteNum;
	}

	public void setPomoteNum(Integer pomoteNum) {
		this.pomoteNum = pomoteNum;
	}

	public String getYearQuarter() {
		return yearQuarter;
	}

	public void setYearQuarter(String yearQuarter) {
		this.yearQuarter = yearQuarter;
	}

	public Double getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Double trainTime) {
		this.trainTime = trainTime;
	}

	public Integer getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(Integer trainNum) {
		this.trainNum = trainNum;
	}

	public Double getAbilityMatch() {
		return abilityMatch;
	}

	public void setAbilityMatch(Double abilityMatch) {
		this.abilityMatch = abilityMatch;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getRankNameEd() {
		return rankNameEd;
	}

	public void setRankNameEd(String rankNameEd) {
		this.rankNameEd = rankNameEd;
	}

	public Double getStayTimeEd() {
		return stayTimeEd;
	}

	public void setStayTimeEd(Double stayTimeEd) {
		this.stayTimeEd = stayTimeEd;
	}
	
}
