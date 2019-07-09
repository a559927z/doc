package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-离职周期
 * Created by wqcai on 16/5/16.
 */
public class RecruitDismissionWeekDto implements Serializable {

    private static final long serialVersionUID = -546335059166338535L;

    private String weekId;       //周期ID
    private String weekName;                    //周期描述
    private Integer days;                   //周期天数
    private Integer prevDays;               //上个周期的天数
    private Double dismissionRate;          //试用流失占比
    private int type;                       //周期排序类型


    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getPrevDays() {
        return prevDays;
    }

    public void setPrevDays(Integer prevDays) {
        this.prevDays = prevDays;
    }

    public Double getDismissionRate() {
        return dismissionRate;
    }

    public void setDismissionRate(Double dismissionRate) {
        this.dismissionRate = dismissionRate;
    }

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }
}