package net.chinahrd.etl.impl.laborEfficiency;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpOtherDayEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpOtherDayETL extends SimpleAbstractEtl<SourceEmpOtherDayEntity>{

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
		return SourceEmpOtherDayEntity.getEntityAuxiliary()
			.asEmpOtherDayId("t.`emp_other_day_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpKey("t.`emp_key`")
			.asEmpId("t.`emp_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asHourCount("t.`hour_count`")
			.asOrganizationId("t.`organization_id`")
			.asCheckworkTypeId("t.`checkwork_type_id`")
			.asDays("t.`days`")
			.asYearMonths("t.`year_months`")
			.setSqlBody(" emp_other_day t where 1=1");
	}

}
