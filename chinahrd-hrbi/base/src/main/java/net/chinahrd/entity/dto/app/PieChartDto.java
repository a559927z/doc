/**
*net.chinahrd.biz.paper.mobile.dto
*/
package net.chinahrd.entity.dto.app;

import java.io.Serializable;

/**默认饼图dto
 * @author htpeng
 *2016年8月24日下午2:26:55
 */
public class PieChartDto implements Serializable,Comparable<PieChartDto>{
	private static final long serialVersionUID = -898731697229391206L;
	private String name;
	private Double value;
	private Double rate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PieChartDto o) {
		Double thisV=this.getValue();
		Double thatV=o.getValue();
		return thisV>thatV?1:thisV==thatV?0:-1;
	}
	
}
