package net.chinahrd.mvc.pc.service.admin;

import java.util.List;
import java.util.Map;

public interface ImportDataService {

	/**
	 * 分析客户提供过来的数据结构
	 * 
	 * @param excelDataList
	 * @param customerId TODO
	 * @param createUserId TODO
	 */
	boolean addOrgans(List<Map<String, Object>> excelDataList, String customerId, String createUserId);
}
