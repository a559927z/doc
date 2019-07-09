package net.chinahrd.eis.permission.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import net.chinahrd.entity.dto.pc.admin.RoleFunctionDto;

/**
 * 功能权限
 * 
 * @author jxzhang on 2016年12月9日
 * @Verdion 1.0 版本
 */
public class RbacFunction extends RbacPermission implements Serializable {

	private static final long serialVersionUID = 4318186817263561119L;
	private String functionId;
	private String customerId;
	private String functionName;
	private String functionParentId;
	private String url;
	private int isMenu;// 0 false,1 true
	private int showIndex;
	private String note;
	private String createUserId;
	private String modifyUserId;
	private Timestamp createTime;
	private Timestamp modifyTime;

	private List<RoleFunctionDto> childs;

	public List<RoleFunctionDto> getChilds() {
		return childs == null ? Collections.<RoleFunctionDto>emptyList() : childs;
	}

	public void setChilds(List<RoleFunctionDto> childs) {
		this.childs = childs;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionParentId() {
		return functionParentId;
	}

	public void setFunctionParentId(String functionParentId) {
		this.functionParentId = functionParentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}

	public int getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(int showIndex) {
		this.showIndex = showIndex;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

}
