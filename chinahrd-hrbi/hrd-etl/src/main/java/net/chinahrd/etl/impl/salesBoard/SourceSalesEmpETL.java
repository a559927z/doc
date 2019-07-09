package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesEmpEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesEmpETL extends SimpleAbstractEtl<SourceSalesEmpEntity> {

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
		return SourceSalesEmpEntity.getEntityAuxiliary()
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asOrganizationId("t.`organization_id`")
			.asAge("t.`age`")
			.asSex("t.`sex`")
			.asPerformanceId("t.`performance_id`")
			.asDegreeId("t.`degree_id`")
			.asTeamId("t.`team_id`")
			.setSqlBody(" sales_emp t where 1=1");
	}
	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
