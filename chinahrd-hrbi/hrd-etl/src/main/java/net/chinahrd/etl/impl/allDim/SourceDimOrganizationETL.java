package net.chinahrd.etl.impl.allDim;

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
			.asId("t.`organization_id`")
			.asOrganizationKey("t.`organization_key`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationParentId("t.`organization_parent_id`")
			.asOrganizationParentKey("t.`organization_parent_key`")
			.asOrganizationCompanyId("t.`organization_company_id`")
			.asOrganizationTypeId("t.`organization_type_id`")
			.asOrganizationName("t.`organization_name`")
			.asOrganizationNameFull("t.`organization_name_full`")
			.asIsSingle("t.`is_single`")
			.asNote("t.`note`")
			.asProfessionId("t.`profession_id`")
			.setSqlBody(" dim_organization t where 1=1");
	}
	public static void main(String...strings){
		new SourceDimOrganizationETL().execute();
	}
}
