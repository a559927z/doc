package net.chinahrd.etl.impl.allDim;

import java.sql.ResultSet;
import java.util.List;

import net.chinahrd.etl.AbstractEtl;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.entity.SourceDimOrganizationTypeEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 机构类型
 * 
 * @author jxzhang on 2017年4月24日
 * @Verdion 1.0 版本
 */
public class SourceDimOrganizationType extends AbstractEtl<SourceDimOrganizationTypeEntity> {

	public static String url = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String user = "mup";
	public static String password = "1q2w3e123";
	public static String driverClassName = MY_SQL_DRIVER;

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public List<SourceDimOrganizationTypeEntity> query() throws Throwable {
		StringBuffer sql = new StringBuffer();
		sql.delete(0, sql.length());
		sql.append("SELECT * FROM dim_organization_type");

		List<SourceDimOrganizationTypeEntity> rsList = CollectionKit.newList();
		DatabaseUtil databaseUtil = new DatabaseUtil(url, user, password, driverClassName);
		ResultSet rs = databaseUtil.query(sql.toString());
		String rsScriptSQL = "";
		while (rs.next()) {
			String v1 = rs.getString("organization_type_id");
			String v11 = Entity.CUSTOMER_ID;
			String v2 = rs.getString("organization_type_key");
			int v3 = rs.getInt("organization_type_level");
			String v4 = rs.getString("organization_type_name");
			int v5 = rs.getInt("show_index");

			rsScriptSQL += v1 + " " + v2 + " " + v3 + " " + v4 + " " + v5;
			rsScriptSQL += "\r\n";
			rsList.add(new SourceDimOrganizationTypeEntity(v1, v11, v2, v3, v4, v5));
		}
		System.out.println(rsScriptSQL);
		databaseUtil.close();
		return rsList;
	}

	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
	}

}
