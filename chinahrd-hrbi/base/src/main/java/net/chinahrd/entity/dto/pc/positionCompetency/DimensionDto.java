/**
*net.chinahrd.biz.paper.dto.competency.positionCompetency
*/
package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;

/**维度表信息
 * @author htpeng
 *2016年9月7日下午5:18:33
 */
public class DimensionDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String empId;
	private String name;
	private String positionId;
	private String reallyScoreName;
	private String expectScoreName;
	private Double reallyScore;
	private Double expectScore;
	private Double rate;
	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}
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
	public String getReallyScoreName() {
		return reallyScoreName;
	}
	public void setReallyScoreName(String reallyScoreName) {
		this.reallyScoreName = reallyScoreName;
	}
	public String getExpectScoreName() {
		return expectScoreName;
	}
	public void setExpectScoreName(String expectScoreName) {
		this.expectScoreName = expectScoreName;
	}
	public Double getReallyScore() {
		return reallyScore;
	}
	public void setReallyScore(Double reallyScore) {
		this.reallyScore = reallyScore;
	}
	public Double getExpectScore() {
		return expectScore;
	}
	public void setExpectScore(Double expectScore) {
		this.expectScore = expectScore;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	
}
