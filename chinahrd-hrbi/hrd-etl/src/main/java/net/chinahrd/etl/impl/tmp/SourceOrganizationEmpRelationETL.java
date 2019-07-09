package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceOrganizationEmpRelationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceOrganizationEmpRelationETL extends SimpleAbstractEtl<SourceOrganizationEmpRelationEntity> {

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
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asEmpId("t.`emp_id`")
			.asSysCodeItemId("t.`sys_code_item_id`")
			.asRefreshDate("t.`refresh_date`")
			.setSqlBody(" organization_emp_relation t where 1=1");
	}

}
