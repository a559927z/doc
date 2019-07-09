package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpProfTitleEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpProfTitleETL extends SimpleAbstractEtl<SourceEmpProfTitleEntity> {

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
		return SourceEmpProfTitleEntity.getEntityAuxiliary()
			.asEmpProfTitleId("t.`emp_prof_title_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asGainDate("t.`gain_date`")
			.asEmpProfTitleName("t.`emp_prof_title_name`")
			.asProfLv("t.`prof_lv`")
			.asAwardUnit("t.`award_unit`")
			.asEffectDate("t.`effect_date`")
			.setSqlBody(" emp_prof_title t where 1=1");
	}

}
