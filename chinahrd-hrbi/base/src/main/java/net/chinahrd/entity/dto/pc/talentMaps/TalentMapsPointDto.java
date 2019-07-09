package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;
import java.util.List;

/**
 * 人才地图点信息
 * Created by wqcai on 16/08/12 012.
 */
public class TalentMapsPointDto implements Serializable {
    private static final long serialVersionUID = -1943582001683646480L;

    private String empId;       //用户ID
    private String text;        //用户名
    private String xLabelId;    //x轴ID
    private String xLabel;      //x轴坐标
    private String yLabelId;    //y轴ID
    private String yLabel;      //y轴坐标
    private String team;        //所属团队
    private String yearMonth;       //时间
    private String yearMonthText;   //时间

    private List<TalentMapsPointDto> children;  //所有子对象


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getYearMonthText() {
        return yearMonthText;
    }

    public void setYearMonthText(String yearMonthText) {
        this.yearMonthText = yearMonthText;
    }

    public List<TalentMapsPointDto> getChildren() {
        return children;
    }

    public void setChildren(List<TalentMapsPointDto> children) {
        this.children = children;
    }

    public String getId(){
        return empId;
    }

    public String getyLabelId() {
        return yLabelId;
    }

    public void setyLabelId(String yLabelId) {
        this.yLabelId = yLabelId;
    }

    public String getxLabelId() {
        return xLabelId;
    }

    public void setxLabelId(String xLabelId) {
        this.xLabelId = xLabelId;
    }
}
