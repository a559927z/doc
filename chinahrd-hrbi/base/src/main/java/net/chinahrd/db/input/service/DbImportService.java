//package net.chinahrd.db.input.service;
//
//import java.util.Date;
//
//public interface DbImportService {
//
//
//	/**
//	 * 第一次导数据true，每天晚上导数据false
//	 * @param isInit
//	 * @return
//	 */
//	boolean replaceVDimEmp(boolean isInit);
//
//	boolean callUnderling();
//
//	boolean callHistoryEmpCount();
//
//	/**
//	 * 日志记录
//	 * 
//	 * @param tableName
//	 *            操作表名
//	 * @param optText
//	 *            操作结果
//	 * @param userTime
//	 *            用时
//	 * @param startDate
//	 *            开始时间
//	 * @param endDate
//	 *            结束时间
//	 * @param resultType结果返回提示
//	 */
//	void insertLog(String tableName, String optText, String userTime,
//			Date startDate, Date endDate, String resultType, int show_index);
//
//	boolean callEmpOvertimeDay();
//
//	boolean historyEmpCountMonth();
//
//	boolean replaceJobChange();
//
//
//}
