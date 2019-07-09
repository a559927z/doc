package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapTalentEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapTalentETL extends SimpleAbstractEtl<SourceMapTalentEntity> {

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
		return SourceMapTalentEntity.getEntityAuxiliary()
			.asMapTalentId("t.`map_talent_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asXAxisId("t.`x_axis_id`")
			.asXAxisIdAf("t.`x_axis_id_af`")
			.asXAxisIdAfR("t.`x_axis_id_af_r`")
			.asYAxisId("t.`y_axis_id`")
			.asYAxisIdAf("t.`y_axis_id_af`")
			.asYAxisIdAfR("t.`y_axis_id_af_r`")
			.asUpdateTime("t.`update_time`")
			.asIsUpdate("t.`is_update`")
			.asModifyId("t.`modify_id`")
			.asReleaseId("t.`release_id`")
			.asYearMonths("t.`year_months`")
			.asNote("t.`note`")
			.asNote1("t.`note1`")
			.asDateTime("t.`date_time`")
			.setSqlBody(" map_talent t where 1=1");
	}

}
