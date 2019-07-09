package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProfessionQuantileRelationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProfessionQuantileRelationETL extends SimpleAbstractEtl<SourceProfessionQuantileRelationEntity> {

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
		return SourceProfessionQuantileRelationEntity.getEntityAuxiliary()
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asProfessionId("t.`profession_id`")
			.asQuantileId("t.`quantile_id`")
			.asQuantileValue("t.`quantile_value`")
			.asType("t.`type`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" profession_quantile_relation t where 1=1");
	}

}
