package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesTeamRankEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesTeamRankETL extends SimpleAbstractEtl<SourceSalesTeamRankEntity> {

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
		return SourceSalesTeamRankEntity.getEntityAuxiliary()
			.asTeamId("t.`team_id`")
			.asOrganizationId("t.`organization_id`")
			.asCustomerId("t.`customer_id`")
			.asTeamRank("t.`team_rank`")
			.asRankDate("t.`rank_date`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" sales_team_rank t where 1=1");
	}


	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
