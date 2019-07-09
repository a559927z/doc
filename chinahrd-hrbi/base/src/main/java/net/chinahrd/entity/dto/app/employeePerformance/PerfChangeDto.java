package net.chinahrd.entity.dto.app.employeePerformance;

import java.io.Serializable;

/**
 * 员工绩效dto
 * Created by wqcai on 15/10/15 0015.
 */
public class PerfChangeDto implements Serializable {
    private static final long serialVersionUID = -5326854791334761128L;

    private String perfChangeId;            //主键ID
    private String empId;                   //员工ID
    private String organParentName;         //单位/（分）公司
    private String organName;               //部门
    private String positionName;            //岗位
    private String perfKey;                 //绩效key
    private String perfName;                //绩效
    private String gradeName;               //能力等级
    private String score;                   //得分
    private Integer yearMonth;              //年月
    private String rankingYear;             //排名年度
    private String rankingQuarter;          //排名季度

    public String getPerfChangeId() {
        return perfChangeId;
    }

    public void setPerfChangeId(String perfChangeId) {
        this.perfChangeId = perfChangeId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getOrganParentName() {
        return organParentName;
    }

    public void setOrganParentName(String organParentName) {
        this.organParentName = organParentName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getPerfKey() {
        return perfKey;
    }

    public void setPerfKey(String perfKey) {
        this.perfKey = perfKey;
    }

    public String getPerfName() {
        return perfName;
    }

    public void setPerfName(String perfName) {
        this.perfName = perfName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getRankingYear() {
        return rankingYear;
    }

    public void setRankingYear(String rankingYear) {
        this.rankingYear = rankingYear;
    }

    public String getRankingQuarter() {
        return rankingQuarter;
    }

    public void setRankingQuarter(String rankingQuarter) {
        this.rankingQuarter = rankingQuarter;
    }
}
