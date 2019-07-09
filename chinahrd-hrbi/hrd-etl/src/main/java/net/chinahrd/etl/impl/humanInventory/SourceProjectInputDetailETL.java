package net.chinahrd.etl.impl.humanInventory;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProjectInputDetailEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProjectInputDetailETL extends SimpleAbstractEtl<SourceProjectInputDetailEntity> implements Runnable {

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
		return SourceProjectInputDetailEntity.getEntityAuxiliary()
			.asProjectInputDetailId("t.`project_input_detail_id`")
			.asProjectId("t.`project_id`")
			.asProjectInputTypeId("t.`project_input_type_id`")
			.asOutlay("t.`outlay`")
			.asDate("t.`date`")
			.asType("t.`type`")
			.setSqlBody(" project_input_detail t where 1=1");
	}

	@Override
	public void run() {
		this.execute();
	}
}
