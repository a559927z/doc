package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimProfessionEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimProfessionETL extends SimpleAbstractEtl<SourceDimProfessionEntity> {


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
		return SourceDimProfessionEntity.getEntityAuxiliary()
			.asProfessionId("t.`profession_id`")
			.asProfessionName("t.`profession_name`")
			.asProfessionKey("t.`profession_key`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_profession t where 1=1");
	}

}
