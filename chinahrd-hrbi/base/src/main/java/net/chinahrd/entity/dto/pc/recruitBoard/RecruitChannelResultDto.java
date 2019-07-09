package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-招聘渠道
 * Created by wqcai on 16/5/5.
 */
public class RecruitChannelResultDto implements Serializable {

    private static final long serialVersionUID = 1774296131095507511L;
    private String channelId;       //渠道ID/岗位ID
    private String channelName;     //渠道名称/岗位名称
    private String parent;          //父级Id
    private double dimissionRate;   //试用期离职率
    private int days;               //招聘周期
    private Double outlay;          //人均招聘费用
    private int employNum;          //已招人数

    private String level;              //级别
    private String isLeaf;         //是否为叶节点
    private String expanded;       //是否展开节点
    private String id;              //树ID



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

    public double getDimissionRate() {
        return dimissionRate;
    }

    public void setDimissionRate(double dimissionRate) {
        this.dimissionRate = dimissionRate;
    }

    public Double getOutlay() {
        return outlay;
    }

    public void setOutlay(Double outlay) {
        this.outlay = outlay;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getEmployNum() {
        return employNum;
    }

    public void setEmployNum(int employNum) {
        this.employNum = employNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
