package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-员工能力素质标签dto
 * Created by wqcai on 16/5/18.
 */
public class RecruitEmpQualityTagDto implements Serializable {

    private static final long serialVersionUID = 3290269714667259005L;
    private String empId;           //员工ID
    private String qualityId;       //能力素质标签ID
    private String vocabulary;      //能力素质词条
    private Double showIndex;       //能力素质排名(排序用)
    private Double score;           //得分(合格之后)

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getQualityId() {
        return qualityId;
    }

    public void setQualityId(String qualityId) {
        this.qualityId = qualityId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public Double getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Double showIndex) {
        this.showIndex = showIndex;
    }
}
