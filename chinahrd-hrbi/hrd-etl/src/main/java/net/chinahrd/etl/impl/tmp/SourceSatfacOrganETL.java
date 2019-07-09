package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSatfacOrganEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSatfacOrganETL extends SimpleAbstractEtl<SourceSatfacOrganEntity> {

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
		return SourceSatfacOrganEntity.getEntityAuxiliary()
			.asSatfacOrganId("t.`satfac_organ_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationName("t.`organization_name`")
			.asSoure("t.`soure`")
			.asDate("t.`date`")
			.setSqlBody(" satfac_organ t where 1=1");
	}

}
