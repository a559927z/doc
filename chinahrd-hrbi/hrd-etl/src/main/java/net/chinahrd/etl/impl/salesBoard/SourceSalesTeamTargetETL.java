package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesTeamTargetEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesTeamTargetETL extends SimpleAbstractEtl<SourceSalesTeamTargetEntity> {

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
		return SourceSalesTeamTargetEntity.getEntityAuxiliary()
			.asTeamId("t.`team_id`")
			.asCustomerId("t.`customer_id`")
			.asSalesTarget("t.`sales_target`")
			.asPayment("t.`payment`")
			.asReturnAmount("t.`return_amount`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" sales_team_target t where 1=1");
	}



	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
