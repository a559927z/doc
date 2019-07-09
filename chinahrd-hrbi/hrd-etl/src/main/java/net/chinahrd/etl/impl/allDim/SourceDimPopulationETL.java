package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimPopulationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimPopulationETL extends SimpleAbstractEtl<SourceDimPopulationEntity> {


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
		return SourceDimPopulationEntity.getEntityAuxiliary()
			.asPopulationId("t.`population_id`")
			.asCustomerId("t.`customer_id`")
			.asPopulationKey("t.`population_key`")
			.asPopulationName("t.`population_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_population t where 1=1");
	}

}
