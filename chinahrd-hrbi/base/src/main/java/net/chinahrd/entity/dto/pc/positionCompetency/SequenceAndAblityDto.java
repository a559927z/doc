/**
*net.chinahrd.biz.paper.dto.competency.positionCompetency
*/
package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;

/**维度表信息
 * @author htpeng
 *2016年9月7日下午5:18:33
 */
public class SequenceAndAblityDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Double rate;
//	private int count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
//	/**
//	 * @return the count
//	 */
//	public int getCount() {
//		return count;
//	}
//	/**
//	 * @param count the count to set
//	 */
//	public void setCount(int count) {
//		this.count = count;
//	}
	
	
}
