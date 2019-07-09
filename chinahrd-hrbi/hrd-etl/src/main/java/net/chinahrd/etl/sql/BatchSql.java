/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql;

import java.sql.ResultSet;


import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 分页sql执行器
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public abstract class BatchSql {
	private static final String asName="num";
	
	private String sql;
	
	/**
	 * 排序字段
	 */
	protected String orderColumn;
	/**
	 * 数据库操作类
	 */
	private DatabaseUtil databaseUtil;


	/**
	 * 获取分页查询Sql语句
	 * @param sql  sql语句
	 * @param start   查询开始位置
	 * @param end     查询结束位置
	 * @param batchNum  查询数量
	 * @return
	 */
	protected abstract String getBachSql(String sql,int start,int end,int batchNum,int count) ;
	
	/**
	 * 检验分页配置是否有效
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean vaild() throws Exception ;
	

	/**
	 * 查询总条数
	 * @return
	 * @throws Exception
	 */
	public int quertCount() throws Exception {
		ResultSet rs =this.databaseUtil.query(sql);
		while (rs.next()) {
			return rs.getInt(asName);
		}
		return 0;
	}


	
	/**
	 * 设置数据库连接
	 * @param databaseUtil  数据库连接
	 */
	public void setDatabaseUtil(DatabaseUtil databaseUtil) {
		this.databaseUtil=databaseUtil;
	}

	/**
	 * 设置sql
	 * @param sql  查询sql
	 */
	public void setSql(String sql) {
		StringBuffer sqlBuff=new StringBuffer("SELECT COUNT(1) ");
		sqlBuff.append(asName);
		sqlBuff.append(" FROM (");
		sqlBuff.append(sql);
		sqlBuff.append(") AS aa");
		this.sql=sqlBuff.toString();
	}
	
	
	/**
	 * 设置排序字段 （目前SQLserver需要使用）
	 * @param order  排序字段
	 */
	public void setOrderColumn(String orderColumn) {
		this.orderColumn=orderColumn;
	}
	

	/**
	 * @return
	 */
	protected String getOrderColumn() {
		return this.orderColumn;
	}

}
