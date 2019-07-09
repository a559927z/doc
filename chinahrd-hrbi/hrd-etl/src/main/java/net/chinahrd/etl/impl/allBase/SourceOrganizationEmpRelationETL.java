package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceOrganizationEmpRelationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceOrganizationEmpRelationETL extends SimpleAbstractEtl<SourceOrganizationEmpRelationEntity> {

	public static String URL = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String USER = "mup";
	public static String PASSWORD = "1q2w3e123";

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	protected String getUrl() {
		return URL;
	}

	@Override
	protected String getUser() {
		return USER;
	}

	@Override
	protected String getPassword() {
		return PASSWORD;
	}

	@Override
	protected String getDriver() {
		return MY_SQL_DRIVER;
	}

	@Override
	protected SqlAuxiliary getSqlAuxiliary() {
		return SourceOrganizationEmpRelationEntity.getEntityAuxiliary()
			.asCustomerId("t.customer_id")
			.asOrganizationId("t.organization_id")
			.asEmpId("t.emp_id")
			.asSysCodeItemId("t.sys_code_item_id")
			.asRefreshDate("t.refresh")
			.setSqlBody(" organization_emp_relation t where 1=1");
	}
}
