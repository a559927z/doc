package net.chinahrd.entity.dto.pc.clientImg;

import java.io.Serializable;

/**
 * 联系人信息
 * @author malong 2017-02-22
 * 
 * */
public class ClientContactsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7043320921058694691L;
	private String contactsId;// 联系人id
	private String contactsName;// 联系人名称
	private String sex;// 联系人性别
	private String dept;// 部门
	private String parentName;// 直属上司
	private String post;// 职务
	private Double age;// 年龄
	private String nativePlace;// 籍贯
	private String marriage;// 婚姻状况
	private Integer children;// 子女数量
	private String interest;// 兴趣
	private String disposition;// 性格
	private String contactsTel;// 联系人手机
	private String contactsEmail;// 联系人email

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getContactsEmail() {
		return contactsEmail;
	}

	public void setContactsEmail(String contactsEmail) {
		this.contactsEmail = contactsEmail;
	}

}
