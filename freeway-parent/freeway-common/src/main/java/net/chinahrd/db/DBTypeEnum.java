package net.chinahrd.db;

/**
 * 数据库类型常量定义
 * 
 * @author bright
 *
 */
public enum DBTypeEnum {
	Oracle("oracle", "oracle.jdbc.driver.OracleDriver"),
	SQLServer("sqlserver", "net.sourceforge.jtds.jdbc.Driver"),
	MySQL("mysql", "com.mysql.jdbc.Driver"),
	PostgresSQL("postgressql", "org.postgresql.Driver");

	private String code;
	private String driverClass;

	private DBTypeEnum(String code, String driverClass) {
		this.code = code;
		this.driverClass = driverClass;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	// 是否包含枚举项
	public static boolean contains(String name) {
		for (DBTypeEnum item : values()) {
			if (item.name().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public static DBTypeEnum byCode(String code) {
		for (DBTypeEnum item : values()) {
			if (item.getCode().equalsIgnoreCase(code)) {
				return item;
			}
		}
		return null;
	}
}
