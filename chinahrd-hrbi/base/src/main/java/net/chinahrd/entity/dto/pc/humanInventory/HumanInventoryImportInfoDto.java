package net.chinahrd.entity.dto.pc.humanInventory;

import java.io.Serializable;

public class HumanInventoryImportInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6384296750265516205L;

	private String projectName;
	private String action;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
