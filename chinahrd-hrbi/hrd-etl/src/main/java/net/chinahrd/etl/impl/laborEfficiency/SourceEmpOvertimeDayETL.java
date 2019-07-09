package net.chinahrd.etl.impl.laborEfficiency;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpOvertimeDayEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpOvertimeDayETL extends SimpleAbstractEtl<SourceEmpOvertimeDayEntity> {

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
		return SourceEmpOvertimeDayEntity.getEntityAuxiliary()
			.asEmpOvertimeDayId("t.`emp_overtime_day_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpKey("t.`emp_key`")
			.asEmpId("t.`emp_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asHourCount("t.`hour_count`")
			.asOrganizationId("t.`organization_id`")
			.asCheckworkTypeId("t.`checkwork_type_id`")
			.asDays("t.`days`")
			.asYearMonth("t.`year_months`")
			.setSqlBody(" emp_overtime_day t where 1=1");
	}
}