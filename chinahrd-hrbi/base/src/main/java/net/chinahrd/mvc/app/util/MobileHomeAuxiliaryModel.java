/**
*net.chinahrd.biz.paper.mobile.util
*/
package net.chinahrd.mvc.app.util;

import java.util.List;

/**
 * 移动端辅助类需要的参数
 * @author htpeng
 *2016年7月14日下午5:46:11
 */
public class MobileHomeAuxiliaryModel {
	private String customerId;
	private String organId;
	private String empId;
	private List<String> organList;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public List<String> getOrganList() {
		return organList;
	}
	public void setOrganList(List<String> organList) {
		this.organList = organList;
	}
	
	
}
