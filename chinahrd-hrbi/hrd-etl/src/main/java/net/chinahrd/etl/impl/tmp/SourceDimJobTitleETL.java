package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimJobTitleEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimJobTitleETL extends SimpleAbstractEtl<SourceDimJobTitleEntity> {

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
		return SourceDimJobTitleEntity.getEntityAuxiliary()
			.asJobTitleId("t.`job_title_id`")
			.asCustomerId("t.`customer_id`")
			.asJobTitleKey("t.`job_title_key`")
			.asJobTitleName("t.`job_title_name`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_job_title t where 1=1");
	}

}
