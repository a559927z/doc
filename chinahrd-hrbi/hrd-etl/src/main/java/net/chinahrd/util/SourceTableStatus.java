package net.chinahrd.util;

import java.util.Map;

import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.db.DatabaseUtil;
import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.sql.AlloteSQL;

/**
 * 创建数据库实体类
 * 
 * @author htpeng 2017年4月12日下午3:22:49
 */
public class SourceTableStatus {

	// 读数据连接
	public static final String URL="jdbc:mysql://172.16.9.50:3369/mup-source?characterEncoding=utf-8";
	public static final String USER="mup";
	public static final String PASSWORD="1q2w3e123";
	public static final String DRIVER="com.mysql.jdbc.Driver";

	// 写数据连接
	public static String url = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String user = "mup";
	public static String password = "1q2w3e123";
	public static String driverClassName = "com.mysql.jdbc.Driver";

	private static String deleteSql = "DELETE FROM sys_table_status";

	
	public static void init() {
		createEntity(AlloteSQL.allotSQL(new DBConfig(USER, PASSWORD, URL, DRIVER)).init().getEntityModel());
	}

	private static void createEntity(Map<String, EntityModel> map) {
		DatabaseUtil db = new DatabaseUtil(url, user, password, driverClassName);
		try {
			db.saveOrUpdate(deleteSql);

			StringBuffer sql = new StringBuffer(
					"INSERT INTO sys_table_status (table_name,table_name_ch,status) VALUES");
			StringBuffer valuesSql = new StringBuffer();
			for (String str : map.keySet()) {
				if (valuesSql.length() > 0) {
					valuesSql.append(",");
				}
				EntityModel entityModel = map.get(str);
				StringBuffer sb = new StringBuffer("(");
				sb.append("'" + entityModel.getTabName() + "',");
				sb.append("'" + entityModel.getComment() + "',");
				sb.append("1");
				sb.append(")");
				valuesSql.append(sb);
			}
			sql.append(valuesSql);
			System.out.println(sql);
			db.saveOrUpdate(sql.toString());
		} catch (Exception e1) {

		} finally {
			db.close();
		}
	}

}
