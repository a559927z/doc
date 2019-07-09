package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimOrganizationTypeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimOrganizationTypeETL extends SimpleAbstractEtl<SourceDimOrganizationTypeEntity> {

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
		return SourceDimOrganizationTypeEntity.getEntityAuxiliary()
			.asOrganizationTypeId("t.`organization_type_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationTypeKey("t.`organization_type_key`")
			.asOrganizationTypeLevel("t.`organization_type_level`")
			.asOrganizationTypeName("t.`organization_type_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_organization_type t where 1=1");
	}

}
