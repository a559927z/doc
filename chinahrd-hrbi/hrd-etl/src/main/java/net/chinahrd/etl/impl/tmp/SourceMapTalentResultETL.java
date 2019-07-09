package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapTalentResultEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapTalentResultETL extends SimpleAbstractEtl<SourceMapTalentResultEntity> {

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
		return SourceMapTalentResultEntity.getEntityAuxiliary()
			.asMapTalentId("t.`map_talent_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asXAxisId("t.`x_axis_id`")
			.asYAxisId("t.`y_axis_id`")
			.asUpdateTime("t.`update_time`")
			.asYearMonths("t.`year_months`")
			.asNote("t.`note`")
			.setSqlBody(" map_talent_result t where 1=1");
	}

}
