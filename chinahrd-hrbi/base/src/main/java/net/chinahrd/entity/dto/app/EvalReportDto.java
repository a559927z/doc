package net.chinahrd.entity.dto.app;

import java.io.Serializable;

/**
 * （人才剖像）360测评报告Dto
 * Created by wqcai on 15/10/20 0020.
 */
public class EvalReportDto implements Serializable {
    private static final long serialVersionUID = 8977296900176605835L;

    private String evalId;        //测评报告主键ID
    private String empId;               //员工ID
    private String reportYear;          //测评年度
    private String path;                //附件路径
    private String abilityId;           //能力维度ID
    private String abilityName;         //能力维度
    private String abilityLvName;       //能力层级
    private Double standardScore;		//标准分
    private Double gainScore;			//取得分
    private Double score;               //综合评分


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getAbilityLvName() {
        return abilityLvName;
    }

    public void setAbilityLvName(String abilityLvName) {
        this.abilityLvName = abilityLvName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getEvalId() {
        return evalId;
    }

    public void setEvalId(String evalId) {
        this.evalId = evalId;
    }

    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
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
    
}
