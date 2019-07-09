package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-岗位推荐人群
 * Created by wqcai on 16/5/21.
 */
public class RecruitRecommendEmpDto implements Serializable {

    private static final long serialVersionUID = 4574102724482965401L;

    private String empId;           //员工ID
    private String imgPath;         //员工头像
    private String userNameCh;      //员工名称
    private Double score;           //匹配度
    private String organName;       //组织机构
    private String qualitysStr;        //标签名称
    private int type;           //类型  0:内部人员    1:外部人员
    private String url;             //简历


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getQualitysStr() {
        return qualitysStr;
    }

    public void setQualitysStr(String qualitysStr) {
        this.qualitysStr = qualitysStr;
    }

}
