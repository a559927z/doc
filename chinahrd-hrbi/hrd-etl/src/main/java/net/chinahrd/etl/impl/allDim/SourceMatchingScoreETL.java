package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMatchingScoreEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMatchingScoreETL extends SimpleAbstractEtl<SourceMatchingScoreEntity> {


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
		return SourceMatchingScoreEntity.getEntityAuxiliary()
			.asMatchingScoreId("t.`matching_soure_id`")
			.asCustomerId("t.`customer_id`")
			.asV1("t.`v1`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" matching_soure t where 1=1");
	}

	public static void main(String...strings){
		new SourceMatchingScoreETL().execute();
	}
}
