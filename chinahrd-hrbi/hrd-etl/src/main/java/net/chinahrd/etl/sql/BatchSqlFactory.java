/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql;

import net.chinahrd.etl.Etl;
import net.chinahrd.etl.sql.batch.MYSQL;
import net.chinahrd.etl.sql.batch.Oracle;
import net.chinahrd.etl.sql.batch.SqlServer;

/**
 * 分页sql执行器工厂
 * 
 * @author htpeng 2017年5月2日下午6:49:03
 */
public class BatchSqlFactory {
	/**
	 * 根据数据库驱动获取分页sql执行器
	 * @param driver
	 * @return
	 */
	public static BatchSql getBatchSql(String driver) {
		return Sql.getBatchSqlByDriver(driver);
	}

	private enum Sql {
		NULL(""),
		MYSQL(Etl.MY_SQL_DRIVER) {
			public BatchSql getBatchSql(String driver) {
				return new MYSQL();
			}
		},
		SQLSERVER(Etl.SQL_SERVER_DRIVER) {
			public BatchSql getBatchSql(String driver) {
				return new SqlServer();
			}
		},
		ORACLE(Etl.ORACLE_DRIVER) {
			public BatchSql getBatchSql(String driver) {
				return new Oracle();
			}
		};

		private String driver;

		
		Sql(String driver) {
			this.driver = driver;
		}

		/**
		 * @return
		 */
		public BatchSql getBatchSql(String driver) {
			throw new RuntimeException("驱动类型错误,没用对应驱动:"+driver);
		}

		private static BatchSql getBatchSqlByDriver(String driver) {
			for (Sql sql : Sql.values()) {
				if (sql.driver.equals(driver)) {
					return sql.getBatchSql(driver);
				}
			}
			return NULL.getBatchSql(driver);
		}

	}
}
