package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimPerformanceEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimPerformanceETL extends SimpleAbstractEtl<SourceDimPerformanceEntity> {


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
		return SourceDimPerformanceEntity.getEntityAuxiliary()
			.asPerformanceId("t.`performance_id`")
			.asCustomerId("t.`customer_id`")
			.asPerformanceKey("t.`performance_key`")
			.asPerformanceName("t.`performance_name`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_performance t where 1=1");
	}

}
