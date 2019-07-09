package net.chinahrd.entity.dto.pc.humanInventory;

import java.io.Serializable;

public class HumanInventoryImportErrorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1007978497066179070L;
	
	private String locationError;
	private String errorMsg;
	
	public String getLocationError() {
		return locationError;
	}
	public void setLocationError(String locationError) {
		this.locationError = locationError;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
