package net.chinahrd.entity.dto.app.manpowerCost;

/**
 * 人力成本结构
 * @author htpeng
 *2015年12月30日上午11:19:09
 */
public class ManpowerItemDto implements Comparable<ManpowerItemDto>{
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
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ManpowerItemDto o) {

		return this.getCost()>o.getCost()?1:this.getCost()==o.getCost()?0:-1;
	}


}
