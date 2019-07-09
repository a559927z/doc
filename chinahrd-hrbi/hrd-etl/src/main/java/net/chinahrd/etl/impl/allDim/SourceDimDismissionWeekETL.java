package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimDismissionWeekEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimDismissionWeekETL extends SimpleAbstractEtl<SourceDimDismissionWeekEntity> {


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
		return SourceDimDismissionWeekEntity.getEntityAuxiliary()
			.asDismissionWeekId("t.`dismission_week_id`")
			.asCustomerId("t.`customer_id`")
			.asName("t.`name`")
			.asDays("t.`days`")
			.asType("t.`type`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_dismission_week t where 1=1");
	}

}
