/**
*net.chinahrd.biz.paper.mobile.dto
*/
package net.chinahrd.entity.dto.app.common;

/**
 * 人员搜索
 * @author htpeng
 *2016年5月16日下午6:00:57
 */
public class EmpDto {
	private String keyTalentId; //用于关键人才搜索
	private String empId;
	private String userName;
	private String imgPath;
	private String phone;
	private String email;
	private String organizationName;
	private String positionName;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	public String getKeyTalentId() {
		return keyTalentId;
	}
	public void setKeyTalentId(String keyTalentId) {
		this.keyTalentId = keyTalentId;
	}
	
}
