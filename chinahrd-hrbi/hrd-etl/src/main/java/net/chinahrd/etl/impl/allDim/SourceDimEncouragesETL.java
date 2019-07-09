package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimEncouragesEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimEncouragesETL extends SimpleAbstractEtl<SourceDimEncouragesEntity> {


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
		return SourceDimEncouragesEntity.getEntityAuxiliary()
			.asEncouragesId("t.`encourages_id`")
			.asCustomerId("t.`customer_id`")
			.asEncouragesKey("t.`encourages_key`")
			.asContent("t.`content`")
			.asShowIndex("t.`show_index`")
//			.asCId("t.`c_id`")
			.setSqlBody(" dim_encourages t where 1=1");
	}

}
