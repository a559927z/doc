package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimSequenceEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimSequenceETL extends SimpleAbstractEtl<SourceDimSequenceEntity> {


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
		return SourceDimSequenceEntity.getEntityAuxiliary()
			.asSequenceId("t.`sequence_id`")
			.asCustomerId("t.`customer_id`")
			.asSequenceKey("t.`sequence_key`")
			.asSequenceName("t.`sequence_name`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_sequence t where 1=1");
	}

}
