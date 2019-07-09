package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 招聘看板-满足率
 * Created by wqcai on 16/5/5.
 */
public class RecruitPositionMeetRateDto implements Serializable {

    private static final long serialVersionUID = 3252556132357106660L;

    private String id;              //主键ID
    private String organId;         //所属机构ID
    private String organName;       //所属机构名称
    private String positionId;      //岗位ID
    private String positionName;    //岗位名称
    private Timestamp startDate;    //招聘开始时间
    private Timestamp endDate;      //招聘结束时间
    private int planNum;            //计划人数
    private int employNum;          //已招人数
    private int waitRecruitNum;     //待招聘人数
    private boolean isWarn;         //是否预警
    private double meetRate;        //满足率
    private int isPublic;           //状态  1：未发布  2：已发布  3：已结束
    private int resumeNum;          //简历份数
    private int interviewNum;       //面试人数
    private int offerNum;           //通过人数
    private int entryNum;           //入职人数

    private boolean isView;         //是否显示
    private String functionConfigId;    //手动配置项ID


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlanNum() {
        return planNum;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }

    public int getWaitRecruitNum() {
        return waitRecruitNum == 0 && planNum != 0 ? planNum - employNum : 0;
    }

    public void setWaitRecruitNum(int waitRecruitNum) {
        this.waitRecruitNum = waitRecruitNum;
    }

    public double getMeetRate() {
        return meetRate;
    }

    public void setMeetRate(double meetRate) {
        this.meetRate = meetRate;
    }

    public int getResumeNum() {
        return resumeNum;
    }

    public void setResumeNum(int resumeNum) {
        this.resumeNum = resumeNum;
    }

    public int getInterviewNum() {
        return interviewNum;
    }

    public void setInterviewNum(int interviewNum) {
        this.interviewNum = interviewNum;
    }

    public int getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(int offerNum) {
        this.offerNum = offerNum;
    }

    public int getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    public int getEmployNum() {
        return employNum;
    }

    public void setEmployNum(int employNum) {
        this.employNum = employNum;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public boolean isWarn() {
        return isWarn;
    }

    public void setWarn(boolean warn) {
        isWarn = warn;
    }

    public String getFunctionConfigId() {
        return functionConfigId;
    }

    public void setFunctionConfigId(String functionConfigId) {
        this.functionConfigId = functionConfigId;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
