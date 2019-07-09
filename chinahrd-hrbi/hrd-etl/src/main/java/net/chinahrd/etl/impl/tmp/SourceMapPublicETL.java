package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapPublicEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapPublicETL extends SimpleAbstractEtl<SourceMapPublicEntity> {

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
		return SourceMapPublicEntity.getEntityAuxiliary()
			.asOrganizationId("t.`organization_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asFullPath("t.`full_path`")
			.asUpdateTime("t.`update_time`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" map_public t where 1=1");
	}

}
