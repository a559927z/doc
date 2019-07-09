package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesOrgTargetEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesOrgTargetETL extends SimpleAbstractEtl<SourceSalesOrgTargetEntity> {

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
		return SourceSalesOrgTargetEntity.getEntityAuxiliary()
			.asOrganizationId("t.`organization_id`")
			.asCustomerId("t.`customer_id`")
			.asSalesTarget("t.`sales_target`")
			.asSalesNumber("t.`sales_number`")
			.asSalesProfit("t.`sales_profit`")
			.asReturnAmount("t.`return_amount`")
			.asPayment("t.`payment`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" sales_org_target t where 1=1");
	}

	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
