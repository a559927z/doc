package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimOrganizationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimOrganizationETL extends SimpleAbstractEtl<SourceDimOrganizationEntity> {

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
		return SourceDimOrganizationEntity.getEntityAuxiliary()
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationCompanyId("t.`organization_company_id`")
			.asOrganizationTypeId("t.`organization_type_id`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationParentId("t.`organization_parent_id`")
			.asOrganizationName("t.`organization_name`")
			.asOrganizationNameFull("t.`organization_name_full`")
			.asIsSingle("t.`is_single`")
			.asNote("t.`note`")
			.asProfessionId("t.`profession_id`")
			.setSqlBody(" dim_organization t where 1=1");
	}

}
