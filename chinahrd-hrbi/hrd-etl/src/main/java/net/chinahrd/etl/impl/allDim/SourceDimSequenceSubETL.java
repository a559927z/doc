package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimSequenceSubEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimSequenceSubETL extends SimpleAbstractEtl<SourceDimSequenceSubEntity>  {


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
		return SourceDimSequenceSubEntity.getEntityAuxiliary()
			.asSequenceSubId("t.`sequence_sub_id`")
			.asCustomerId("t.`customer_id`")
			.asSequenceId("t.`sequence_id`")
			.asSequenceSubKey("t.`sequence_sub_key`")
			.asSequenceSubName("t.`sequence_sub_name`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_sequence_sub t where 1=1");
	}


}
