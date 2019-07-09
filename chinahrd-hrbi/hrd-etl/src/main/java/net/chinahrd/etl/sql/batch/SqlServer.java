/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql.batch;



import org.springframework.util.StringUtils;

import net.chinahrd.etl.sql.BatchSql;

/**
 * SqlServer 分页sql
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public class SqlServer extends BatchSql {
	
	/**
	 * @param sql
	 * @return
	 */
	public String getBachSql(String sql,int start,int end,int batchNum,int count) {
//		return "SELECT * FROM"+
//		"(SELECT ROW_NUMBER() OVER(ORDER BY "+getOrderColumn()+" ASC) "
//				+ "AS 'ROWNUMBER', * FROM ("+sql+") a) AS temp "+
//		"WHERE ROWNUMBER BETWEEN "+(start+1)+" AND "+ end +" ORDER BY ROWNUMBER desc";
		return "SELECT * FROM"+
		"(SELECT ROW_NUMBER() OVER(ORDER BY "+getOrderColumn()+" ASC) "
				+ "AS 'ROWNUMBER', * FROM ("+sql+") a) AS temp "+
		"WHERE ROWNUMBER BETWEEN "+(count-end+1)+" AND "+ (count-start) +" ORDER BY ROWNUMBER desc";
	}	


	
	@Override
	protected boolean vaild() throws Exception {
		System.out.println("排序字段："+getOrderColumn());
		if(StringUtils.isEmpty(getOrderColumn())){
			throw new RuntimeException("排序字段不能为空");
		}
		return true;
	}


}
