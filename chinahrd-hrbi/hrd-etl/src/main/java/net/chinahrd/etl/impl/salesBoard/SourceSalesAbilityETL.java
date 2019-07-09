package net.chinahrd.etl.impl.salesBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesAbilityEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesAbilityETL extends SimpleAbstractEtl<SourceSalesAbilityEntity> {

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
		return SourceSalesAbilityEntity.getEntityAuxiliary()
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asStatus("t.`status`")
			.asCheckDate("t.`check_date`")
			.setSqlBody(" sales_ability t where 1=1");
	}


	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
