package net.chinahrd.etl.impl.accordDismiss;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceRunOffRecordEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceRunOffRecordETL extends SimpleAbstractEtl<SourceRunOffRecordEntity> implements Runnable {

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
		return SourceRunOffRecordEntity.getEntityAuxiliary()
			.asRunOffRecordId("t.`run_off_record_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asRunOffId("t.`run_off_id`")
			.asWhereAbouts("t.`where_abouts`")
			.asRunOffDate("t.`run_off_date`")
			.asIsWarn("t.`is_warn`")
			.asReHire("t.`re_hire`")
			.setSqlBody(" run_off_record t where 1=1");
	}

	@Override
	public void run() {
		this.execute();
	}
}
