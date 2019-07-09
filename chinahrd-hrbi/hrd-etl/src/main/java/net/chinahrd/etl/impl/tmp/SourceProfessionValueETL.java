package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProfessionValueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProfessionValueETL extends SimpleAbstractEtl<SourceProfessionValueEntity> {

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
		return SourceProfessionValueEntity.getEntityAuxiliary()
			.asProfessionValue("t.`profession_value`")
			.asProfessionName("t.`profession_name`")
			.asProfessionValueKey("t.`profession_value_key`")
			.asValue("t.`value`")
			.asProfessionId("t.`profession_id`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" profession_value t where 1=1");
	}

}
