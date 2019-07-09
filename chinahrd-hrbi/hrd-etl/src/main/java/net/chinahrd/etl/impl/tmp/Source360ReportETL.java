package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.Source360ReportEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class Source360ReportETL extends SimpleAbstractEtl<Source360ReportEntity> {

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
		return Source360ReportEntity.getEntityAuxiliary()
			.as360ReportId("t.`360_report_id`")
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.as360AbilityId("t.`360_ability_id`")
			.as360AbilityName("t.`360_ability_name`")
			.as360AbilityLvId("t.`360_ability_lv_id`")
			.as360AbilityLvName("t.`360_ability_lv_name`")
			.asStandardScore("t.`standard_score`")
			.asGainScore("t.`gain_score`")
			.asScore("t.`score`")
			.asModuleType("t.`module_type`")
			.as360TimeId("t.`360_time_id`")
			.setSqlBody(" 360_report t where 1=1");
	}

}
