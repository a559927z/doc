package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.pc.admin.TreeDto;

/**
 * ztree
 */
public class TreeDto implements Serializable {

	private static final long serialVersionUID = -9213925226786641925L;

	private String id;

	private String parentId;

	private String name;

	private Boolean isParent = false;

	private Boolean checked; // true，选中

	private String fullPath;

	private Boolean open = false;
	private List<TreeDto> children;
	private int halfCheck = 0; // 1，半选
	private String pId;
	private int type;
	public int getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(int halfCheck) {
		this.halfCheck = halfCheck;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<TreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<TreeDto> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsParent() {
		return isParent == null ? false : isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
