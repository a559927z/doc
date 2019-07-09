/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql;

import org.springframework.util.StringUtils;

import net.chinahrd.utils.db.DatabaseUtil;

/**
 * sql分页操作类
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public  class BatchSqlHandle implements BatchSqlConfig {
	private String sql ;
	private int start = 0;
	private int end = 0;
	
	/**
	 * 总条数
	 */
	private int count = -1;
	
	/**
	 * 是否允许查询
	 */
	private boolean isQuery = false;
	
	/**
	 * 分页数量
	 */
	private int batchNum=BatchSqlConfig.BATCHNUM;
	/**
	 * 排序字段   （sql server 使用）
	 */
	private String orderColumn;
	
	/**
	 * 分页Sql 执行器
	 */
	private BatchSql batchSql;
	
	/**
	 * 数据库操作类
	 */
	private DatabaseUtil databaseUtil;
	/**
	 * 是否分页
	 */
	private boolean isBatch = false;


	public void init() throws Exception {
		if (isBatch) {
			batchSql=BatchSqlFactory.getBatchSql(databaseUtil.getDriver());
			batchSql.setSql(this.sql);
			batchSql.setOrderColumn(this.orderColumn);
			batchSql.setDatabaseUtil(this.databaseUtil);
			if(batchSql.vaild()){
				count = batchSql.quertCount();
			}
		}
	}

	/**
	 * 获取查询sql
	 * @param sql 查询sql
	 * @return
	 */
	public String getQuerySql(String sql) {
		if (isBatch) {
			return batchSql.getBachSql(sql,start,end,batchNum,count);
		}
		return sql;
	}

	/**
	 * 是否继续查询
	 * @return
	 */
	public boolean next() {
		if (count == -1) {
			isQuery = false;
			count = 0;
			return true;
		} else if (count > 0) {
			start = end;
			isQuery = end < count;
			end = count - end > batchNum ? batchNum + end : count;
		}
		return isQuery;
	}

	
	@Override
	public void setBatchSql(String url, String user, String password, String driver, String sql) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(driver) || StringUtils.isEmpty(sql)) {
			throw new RuntimeException("");
		}
		setBatchSql(new DatabaseUtil(url, user, password, driver),sql);
	}

	
	@Override
	public void setBatchSql(DatabaseUtil databaseUtil, String sql) {
		if(StringUtils.isEmpty(sql) || null==databaseUtil){
			throw new RuntimeException("");
		}
		this.databaseUtil=databaseUtil;
		this.sql=sql;
		this.isBatch = true;
	}

	@Override
	public void setBatchNum(int batchNum) {
		this.batchNum=batchNum;
	}
	

	@Override
	public void setOrderColumn(String column) {
		this.orderColumn=column;
	}


	
	/**
	 * 
	 * 关闭连接
	 */
	public void close() {
		if(null!=this.databaseUtil){
			this.databaseUtil.close();
			this.databaseUtil=null;
		}
	}
}
