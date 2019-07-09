package net.chinahrd.util;

import java.util.Map;

import net.chinahrd.utils.version.core.entity.ColumnModel;
import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.sql.AlloteSQL;

/**
 * 
 * @author jxzhang on 2017年5月27日
 * @Verdion 1.0 版本
 */
public class ConstuctorADDColumtSQL {

	public static final String URL = "jdbc:mysql://172.16.9.50:3369/mup-proceduct?characterEncoding=utf-8";
	public static final String USER = "mup";
	public static final String PASSWORD = "1q2w3e123";
	public static final String DRIVER = "com.mysql.jdbc.Driver";

	public static void init() {
		DBConfig dbConfig = new DBConfig(USER, PASSWORD, URL, DRIVER);
		createSQL(AlloteSQL.allotSQL(dbConfig).init().getEntityModel());
	}

	private static void createSQL(Map<String, EntityModel> map) {
		StringBuffer buff = new StringBuffer();
		for (String str : map.keySet()) {
			EntityModel entityModel = map.get(str);
			String tableName = entityModel.getTabName();
//			buff.append(tableName);
//			buff.append("\t");
			
//			for (ColumnModel columnModel : entityModel.getColList()) {
//				String colName = columnModel.getColname();
////				buff.append(colName);
//				if(colName.equals("create_time")){
//					buff.append("ALTER TABLE `"+tableName+"` DROP COLUMN `create_time`;\n");
//				}
////				buff.append(",");
//			}
			buff.append("ALTER TABLE `"+tableName+"` ADD COLUMN `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ;\n");
			buff.append("ALTER TABLE `"+tableName+"` ADD COLUMN `refresh` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `create_time`;\n");
		}
		System.out.print(buff);
	}

	public static void main(String[] args) {
		new ConstuctorADDColumtSQL();
		ConstuctorADDColumtSQL.init();
	}

}
