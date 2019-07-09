package net.chinahrd.etl.impl.talentMaps;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapManagementEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapManagementETL extends SimpleAbstractEtl<SourceMapManagementEntity> implements Runnable {

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
		return SourceMapManagementEntity.getEntityAuxiliary()
			.asMapManagementId("t.`map_management_id`")
			.asCustomerId("t.`customer_id`")
			.asTopId("t.`top_id`")
			.asStatus("t.`status`")
			.asAdjustmentId("t.`adjustment_id`")
			.asEmpId("t.`emp_id`")
			.asStarteDate("t.`starte_date`")
			.asMotifyDate("t.`motify_date`")
			.asReleaseDate("t.`release_date`")
			.asYearMonths("t.`year_months`")
			.setSqlBody(" map_management t where 1=1");
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
