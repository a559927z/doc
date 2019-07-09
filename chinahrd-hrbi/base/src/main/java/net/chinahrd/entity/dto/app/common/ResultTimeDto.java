/**
*net.chinahrd.biz.paper.mobile.dto.common
*/
package net.chinahrd.entity.dto.app.common;

/**
 * @author htpeng
 *2016年3月25日下午1:52:02
 */
public class ResultTimeDto {
	private String startDate;
	private String endDate;
	private String timeRange;
	private Object obj;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
