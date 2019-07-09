package net.chinahrd.entity.dto.pc.manpowerCost;

import java.io.Serializable;

/**
 * 各架构人力成本
 * @author htpeng
 *2015年12月30日上午11:19:09
 */
public class ManpowerOrganDto implements Serializable{
	private static final long serialVersionUID = -1638303592207245748L;
	private double cost;
	private String organ;
	private String organId;
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}

}
