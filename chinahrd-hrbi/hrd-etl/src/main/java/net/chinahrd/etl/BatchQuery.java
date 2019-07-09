/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;

import net.chinahrd.etl.sql.BatchSqlConfig;

/**分页查询接口
 * @author htpeng
 *2017年5月3日上午10:58:48
 */
public interface BatchQuery {
	 /**
	  * 设置分页查询
	  * @param config 分页配置
	  */
	 void setBatchQuery(BatchSqlConfig config);
}
