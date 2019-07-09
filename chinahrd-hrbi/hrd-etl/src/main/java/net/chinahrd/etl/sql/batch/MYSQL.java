/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql.batch;

import net.chinahrd.etl.sql.BatchSql;

/**
 * MYSQL 分页sql
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public class MYSQL extends BatchSql {

	
	@Override
	public String getBachSql(String sql,int start,int end,int batchNum,int count) {
		return sql + " LIMIT " + start + "," + batchNum;
	}

	
	@Override
	protected boolean vaild() throws Exception {
		return true;
	}

	


}
