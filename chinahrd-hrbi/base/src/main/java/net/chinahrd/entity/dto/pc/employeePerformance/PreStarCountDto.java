package net.chinahrd.entity.dto.pc.employeePerformance;

import java.io.Serializable;

public class PreStarCountDto implements Serializable {
	/**  */
	private static final long serialVersionUID = 7792989350925075060L;
	private String performanceKey;
	private String performanceName;
	private Integer mgrCount;//管理者
	private Integer notMgrCount;//员工数
	private Integer joinCount;//参加测评的人数
	
	
	public String getPerformanceKey() {
		return performanceKey;
	}
	public void setPerformanceKey(String performanceKey) {
		this.performanceKey = performanceKey;
	}
	public String getPerformanceName() {
		return performanceName;
	}
	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}
	public Integer getMgrCount() {
		return mgrCount;
	}
	public void setMgrCount(Integer mgrCount) {
		this.mgrCount = mgrCount;
	}
	public Integer getNotMgrCount() {
		return notMgrCount;
	}
	public void setNotMgrCount(Integer notMgrCount) {
		this.notMgrCount = notMgrCount;
	}
	public Integer getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
