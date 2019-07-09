package net.chinahrd.db.input.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("dbImportQuotaDao")
public interface DbImportQuotaDao {
	
	int findLogInfo(Map<String, Object> mp);
	
	/**
	 * 人均效益
	 * 
	 * @param mp
	 */
	void callQuotaRenJunXiaoYi(Map<String, Object> mp);
}
