package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimRunOffEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimRunOffETL extends SimpleAbstractEtl<SourceDimRunOffEntity> {

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
		return SourceDimRunOffEntity.getEntityAuxiliary()
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asRunOffKey("t.`run_off_key`")
			.asRunOffName("t.`run_off_name`")
			.asType("t.`type`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_run_off t where 1=1");
	}

}
