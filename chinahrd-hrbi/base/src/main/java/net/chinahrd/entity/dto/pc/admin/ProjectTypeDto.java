package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;

public class ProjectTypeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private String typeName;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	

}
