package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-招聘渠道
 * Created by wqcai on 16/5/5.
 */
public class RecruitChannelQueryDto implements Serializable {

    private static final long serialVersionUID = 6557352007937626495L;
    private String positionId;          //岗位ID
    private String positionName;        //岗位名称
    private int positionDays;           //岗位招聘周期
    private int postionEmployNum;       //岗位已招人数
    private String channelId;           //渠道ID
    private String channelName;         //渠道名称
    private int days;                   //渠道招聘周期
    private int employNum;              //已招人数
    private Double outlay;              //招聘花费
    private int dimissionNum;           //流失人数

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getDimissionNum() {
        return dimissionNum;
    }

    public void setDimissionNum(int dimissionNum) {
        this.dimissionNum = dimissionNum;
    }

    public int getEmployNum() {
        return employNum;
    }

    public void setEmployNum(int employNum) {
        this.employNum = employNum;
    }

    public Double getOutlay() {
        return outlay;
    }

    public void setOutlay(Double outlay) {
        this.outlay = outlay;
    }

    public int getPositionDays() {
        return positionDays;
    }

    public void setPositionDays(int positionDays) {
        this.positionDays = positionDays;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getPostionEmployNum() {
        return postionEmployNum;
    }

    public void setPostionEmployNum(int postionEmployNum) {
        this.postionEmployNum = postionEmployNum;
    }
}
