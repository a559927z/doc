package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesProTargetEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesProTargetETL extends SimpleAbstractEtl<SourceSalesProTargetEntity> {

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
		return SourceSalesProTargetEntity.getEntityAuxiliary()
			.asProductId("t.`product_id`")
			.asCustomerId("t.`customer_id`")
			.asSalesTarget("t.`sales_target`")
			.asReturnAmount("t.`return_amount`")
			.asPayment("t.`payment`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" sales_pro_target t where 1=1");
	}

	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
