package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-标签dto
 * Created by wqcai on 16/5/18.
 */
public class RecruitTagDto implements Serializable {

    private static final long serialVersionUID = -5081994975552466421L;
    private String tagId;     //键
    private String tagName;    //名称
    private Double tagVal;   //值(人数/占比)
    private Double tagScore;    //标签得分
    /**
     * 返回类型
     * 数据标签     0:基本信息 1:能力素质  2:关健人才优势
     * 查询标签     1:学校类型  2:学历    3:能力素质分值  4:能力素质
     */
    private int tagType = 0;


    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public Double getTagVal() {
        return tagVal;
    }

    public void setTagVal(Double tagVal) {
        this.tagVal = tagVal;
    }

    public Double getTagScore() {
        return tagScore;
    }

    public void setTagScore(Double tagScore) {
        this.tagScore = tagScore;
    }
}
