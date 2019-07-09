package net.chinahrd.etl.impl;

import java.sql.ResultSet;
import java.util.Map;

import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 数据处理
 * 
 * @author jxzhang on 2017年5月10日
 * @Verdion 1.0 版本
 */
public class DataHandling {

	// 读数据连接
	private static String propsPath = "conf/default_database.properties";
	public final static String URL = PropertiesUtil.getProperty(propsPath, "r-url");
	public final static String USER = PropertiesUtil.getProperty(propsPath, "r-user");
	public final static String PASSWORD = PropertiesUtil.getProperty(propsPath, "r-password");
	public final static String DRIVER = PropertiesUtil.getProperty(propsPath, "r-driver");

	/**
	 * 获取主子序列层级职级（短名）
	 * 
	 * @param empId
	 * @return
	 * @throws Throwable
	 */
	public static String getRankByEmpId(String empId) throws Throwable {

		String sql = "SELECT CONCAT( t2.curt_name, t6.curt_name, t3.curt_name, '.', t4.curt_name ) as rank FROM v_dim_emp tt LEFT JOIN dim_sequence t2 ON t2.sequence_id = tt.sequence_id AND t2.customer_id = tt.customer_id LEFT JOIN dim_sequence_sub t6 ON t6.sequence_sub_id = tt.sequence_sub_id AND t6.customer_id = tt.customer_id LEFT JOIN dim_ability t3 ON t3.ability_id = tt.ability_id AND t3.customer_id = tt.customer_id LEFT JOIN dim_ability_lv t4 ON t4.ability_lv_id = tt.ability_lv_id AND t4.customer_id = tt.customer_id WHERE tt.emp_id = "
				+ empId;

		DatabaseUtil databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, DRIVER);
		ResultSet rs = databaseUtil.query(sql);
		String result = "";
		while (rs.next()) {
			result = rs.getString("rank");
		}
		databaseUtil.close();
		return result;
	}

	/**
	 * 获取主子序列层级职级（短名）
	 * 
	 * @return
	 * @throws Throwable
	 */
	public static Map<String, String> getRankAll() throws Throwable {

		String sql = "SELECT emp_id, CONCAT( t2.curt_name, t6.curt_name, t3.curt_name, '.', t4.curt_name ) as rank FROM v_dim_emp tt LEFT JOIN dim_sequence t2 ON t2.sequence_id = tt.sequence_id AND t2.customer_id = tt.customer_id LEFT JOIN dim_sequence_sub t6 ON t6.sequence_sub_id = tt.sequence_sub_id AND t6.customer_id = tt.customer_id LEFT JOIN dim_ability t3 ON t3.ability_id = tt.ability_id AND t3.customer_id = tt.customer_id LEFT JOIN dim_ability_lv t4 ON t4.ability_lv_id = tt.ability_lv_id AND t4.customer_id = tt.customer_id ";
		DatabaseUtil databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, DRIVER);
		ResultSet rs = databaseUtil.query(sql);
		Map<String, String> mResult = CollectionKit.newMap();
		while (rs.next()) {
			mResult.put(rs.getString("emp_id"), rs.getString("rank"));
		}
		databaseUtil.close();
		return mResult;
	}

}