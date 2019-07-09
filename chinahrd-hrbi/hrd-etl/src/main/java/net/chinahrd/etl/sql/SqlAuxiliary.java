/**
*net.chinahrd.etl
*/
package net.chinahrd.etl.sql;

import java.util.List;

/**sql 辅助接口
 * @author htpeng
 *2017年4月24日下午5:52:28
 */
public interface SqlAuxiliary {
	/**
	 * 获取查询Sql
	 * @return
	 */
	String getSql();
	
	/**
	 * 获取查询SQL 的所有列
	 * @return
	 */
	List<SqlColModel> getSqlCol();
}
