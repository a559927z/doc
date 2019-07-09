package net.chinahrd.entity.dto.pc.manage;

import java.io.Serializable;

/**
 * 预警人员详细数据
 * Created by htpeng on 15/12/08 0012.
 */
public class WarnInfoDto implements Serializable {
    private static final long serialVersionUID = 5021154907941311305L;

    private String empId;                           
    private String userNameCh;                     
    private String imgPath;
    private String positionName;
    private String sequenceName;
    private String sequenceSubName;
    private String jobTitleName;
	private String rankName;  //职级
    private String keyTalentTypeName;    //
    
    private String performanceName;  //绩效
    private double avHour;         //平均时长
    private int totalHour;         //加班总时长
    private int week;          //加班周期
    
    public double getAvHour() {
		return avHour;
	}
	public void setAvHour(double avHour) {
		this.avHour = avHour;
	}
	public int getTotalHour() {
		return totalHour;
	}
	public void setTotalHour(int totalHour) {
		this.totalHour = totalHour;
	}
	

	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getUserNameCh() {
		return userNameCh;
	}
	public void setUserNameCh(String userNameCh) {
		this.userNameCh = userNameCh;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public String getSequenceSubName() {
		return sequenceSubName;
	}
	public void setSequenceSubName(String sequenceSubName) {
		this.sequenceSubName = sequenceSubName;
	}
	public String getJobTitleName() {
		return jobTitleName;
	}
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}
	public String getPerformanceName() {
		return performanceName;
	}
	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}
	public String getKeyTalentTypeName() {
		return keyTalentTypeName;
	}
	public void setKeyTalentTypeName(String keyTalentTypeName) {
		this.keyTalentTypeName = keyTalentTypeName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
}
