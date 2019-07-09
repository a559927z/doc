package net.chinahrd.entity.dto.pc.manpowerCost;

import java.io.Serializable;

/**
 * 人力成本结构
 * @author htpeng
 *2015年12月30日上午11:19:09
 */
public class ManpowerItemDto implements Serializable{
	private static final long serialVersionUID = 4244919316636333275L;
	private double cost;
	private String itemName;
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


}
