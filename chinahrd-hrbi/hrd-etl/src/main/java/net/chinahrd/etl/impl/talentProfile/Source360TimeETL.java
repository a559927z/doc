package net.chinahrd.etl.impl.talentProfile;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.Source360TimeEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class Source360TimeETL extends SimpleAbstractEtl<Source360TimeEntity> implements Runnable {

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
		return Source360TimeEntity.getEntityAuxiliary()
			.as360TimeId("t.`360_time_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asReportDate("t.`report_date`")
			.asReportName("t.`report_name`")
			.asPath("t.`path`")
			.asType("t.`type`")
			.asYear("t.`year`")
			.setSqlBody(" 360_time t where 1=1");
	}

	@Override
	public void run() {
		this.execute();
	}
	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
