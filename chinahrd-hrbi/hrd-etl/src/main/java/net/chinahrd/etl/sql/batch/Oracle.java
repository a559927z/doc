/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql.batch;



import net.chinahrd.etl.sql.BatchSql;

/**
 * Oracle 分页sql
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public class Oracle extends BatchSql {
	
	/**
	 * @param sql
	 * @return
	 */
	public String getBachSql(String sql,int start,int end,int batchNum,int count) {
		return 
		"SELECT * FROM   "+
		"(  "+
		"SELECT A.*, ROWNUM RN   "+
		"FROM ("+sql+") A   "+
		")  "+
		"WHERE RN BETWEEN"+(start+1)+" AND "+ end;
	}	


	
	@Override
	protected boolean vaild() throws Exception {
		return true;
	}


}
