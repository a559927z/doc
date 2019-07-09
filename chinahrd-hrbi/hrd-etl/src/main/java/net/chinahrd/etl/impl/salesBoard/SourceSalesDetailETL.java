package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesDetailEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesDetailETL extends SimpleAbstractEtl<SourceSalesDetailEntity> {

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
		return SourceSalesDetailEntity.getEntityAuxiliary()
			.asSalesDetailId("t.`sales_detail_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("o.emp_id")
			.asProductId("t.`product_id`")
			.asProductNumber("t.`product_number`")
			.asSalesMoney("t.`sales_money`")
			.asSalesProfit("t.`sales_profit`")
			.asSalesProvinceId("o.`province_id`")
			.asSalesCityId("o.`city_id`")
			.asSalesDate("o.`sales_date`")
			.setSqlBody(" sales_detail t inner join sales_order o on t.sales_order_id = o.sales_order_id where 1=1");
	}

	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}

}
