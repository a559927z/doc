package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;

/**
 * 岗位胜任度  DTO
 * @author htpeng
 *2016年9月5日下午5:02:13
 */
public class PositionCompetencyDto implements Serializable,Comparable<PositionCompetencyDto> {
	private static final long serialVersionUID = 5887912303933446422L;
	private String id;
	private String organId;
	private String parentId;
	private String customerId;
	private String organName;
	private String empName;
	private int hasChildren;
	private String positionId;
	private String positionName;
	private Double rate;
	private String yearMonth;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void concat(PositionCompetencyDto pcd){
		if(null==pcd){
			return;
		}
		if(this.organId==null){
			this.organId=pcd.getOrganId();
		}
		if(this.parentId==null){
			this.parentId=pcd.getParentId();
		}
		if(this.customerId==null){
			this.customerId=pcd.getCustomerId();
		}
		if(this.organName==null){
			this.organName=pcd.getOrganName();
		}
		if(this.empName==null){
			this.empName=pcd.getEmpName();
		}
		if(this.positionId==null){
			this.positionId=pcd.getPositionId();
		}
		if(this.positionName==null){
			this.positionName=pcd.getPositionName();
		}
		if(this.rate==null){
			this.rate=pcd.getRate();
		}
		if(this.yearMonth==null){
			this.yearMonth=pcd.getYearMonth();
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PositionCompetencyDto o) {
		double thisV=this.getRate();
		double thatV=o.getRate();
		return thisV>thatV?1:thisV==thatV?0:-1;
	}
}
