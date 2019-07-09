package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMatchingSourceEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMatchingSourceETL extends SimpleAbstractEtl<SourceMatchingSourceEntity> {

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
		return SourceMatchingSourceEntity.getEntityAuxiliary()
			.asMatchingSourceId("t.`matching_source_id`")
			.asCustomerId("t.`customer_id`")
			.asV1("t.`v1`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" matching_source t where 1=1");
	}

}
