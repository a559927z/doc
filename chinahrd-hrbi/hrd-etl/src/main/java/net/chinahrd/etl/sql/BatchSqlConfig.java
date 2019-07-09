/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql;

import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 分页sql 配置接口
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public interface BatchSqlConfig {
	/**
	 * 默认分页数量
	 */
	public static final int BATCHNUM = 200000;

	/**
	 * 设置分页器
	 * @param url         
	 * @param user
	 * @param password
	 * @param driver
	 * @param sql		查询的sql(不需要查询数量)
	 */
	public void setBatchSql(String url, String user, String password, String driver, String sql);

	/**
	 * 设置分页器
	 * @param databaseUtil  数据库连接
	 * @param sql		查询的sql(不需要查询数量)
	 */
	public void setBatchSql(DatabaseUtil databaseUtil, String sql);

	/**
	 * 设置分页数量
	 * @param batchNum
	 */
	public void setBatchNum(int batchNum);
	
	/**
	 * 设置排序字段（sql server 分页需要）
	 * @param batchNum
	 */
	public void setOrderColumn(String column);
}
