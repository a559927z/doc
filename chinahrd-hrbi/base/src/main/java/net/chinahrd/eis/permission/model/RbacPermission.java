package net.chinahrd.eis.permission.model;

import java.io.Serializable;

public class RbacPermission implements Serializable {

	private static final long serialVersionUID = 8739783846731071660L;

	private String fullPath;
	private String itemCodes;

	private String roleKey;

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

}
