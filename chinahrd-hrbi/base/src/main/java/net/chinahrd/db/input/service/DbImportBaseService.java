package net.chinahrd.db.input.service;

import net.chinahrd.db.input.Entity.CheckInputdataStatusEntity;

public interface DbImportBaseService {

	CheckInputdataStatusEntity findCheckInputdataStatus();
	void updateCheckInputdataStatus(String id, int code, String note);

	
	boolean callDimTables();
	boolean replaceDimOrganization(boolean isInit);
	boolean callDimPosition();
	
	
	boolean callDimTables2();
	
}
