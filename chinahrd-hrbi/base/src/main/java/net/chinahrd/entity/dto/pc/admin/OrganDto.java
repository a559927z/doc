package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;

public class OrganDto implements Serializable {

	private static final long serialVersionUID = -7715350923278809559L;

	private String organizationId;
	private String customerId;
	private String organizationTypeId;
	private String organizationKey;
	private String organizationParentId;
	private String organizationName;
	private String note;
	private int isSingle;
	private String fullPath;
	private int hasChildren;
	private int level;
	private int depth;
	private int halfCheck;

	private boolean queryChildren=true;  //有子节点时  默认点击查询子节点
	
	private String organizationParentName;
	private String organizationTypeLevel;
	public boolean getQueryChildren() {
		return queryChildren;
	}

	public void setQueryChildren(boolean queryChildren) {
		this.queryChildren = queryChildren;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(int halfCheck) {
		this.halfCheck = halfCheck;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(String organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public String getOrganizationKey() {
		return organizationKey;
	}

	public void setOrganizationKey(String organizationKey) {
		this.organizationKey = organizationKey;
	}

	public String getOrganizationParentId() {
		return organizationParentId;
	}

	public void setOrganizationParentId(String organizationParentId) {
		this.organizationParentId = organizationParentId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(int isSingle) {
		this.isSingle = isSingle;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public int getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getOrganizationParentName() {
		return organizationParentName;
	}

	public void setOrganizationParentName(String organizationParentName) {
		this.organizationParentName = organizationParentName;
	}

	public String getOrganizationTypeLevel() {
		return organizationTypeLevel;
	}

	public void setOrganizationTypeLevel(String organizationTypeLevel) {
		this.organizationTypeLevel = organizationTypeLevel;
	}

}
