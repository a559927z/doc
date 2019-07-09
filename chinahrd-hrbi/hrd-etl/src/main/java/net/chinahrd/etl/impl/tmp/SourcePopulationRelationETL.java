package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePopulationRelationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePopulationRelationETL extends SimpleAbstractEtl<SourcePopulationRelationEntity> {

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
		return SourcePopulationRelationEntity.getEntityAuxiliary()
			.asCustomerId("t.`customer_id`")
			.asPopulationId("t.`population_id`")
			.asEmpId("t.`emp_id`")
			.asOrganizationId("t.`organization_id`")
			.asDays("t.`days`")
			.setSqlBody(" population_relation t where 1=1");
	}


}
