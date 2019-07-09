package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpPersonalityEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpPersonalityETL extends SimpleAbstractEtl<SourceEmpPersonalityEntity> {

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
		return SourceEmpPersonalityEntity.getEntityAuxiliary()
			.asEmpPersonalityId("t.`emp_personality_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asPersonalityId("t.`personality_id`")
			.asType("t.`type`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" emp_personality t where 1=1");
	}

}
