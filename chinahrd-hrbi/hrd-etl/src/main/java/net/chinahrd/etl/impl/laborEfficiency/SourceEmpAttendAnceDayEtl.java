package net.chinahrd.etl.impl.laborEfficiency;

import net.chinahrd.etl.Etl;
import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpAttendanceDayEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

/**
 * 考勤表-日
 * @author jxzhang on 2017年5月9日
 * @Verdion 1.0 版本
 */
public class SourceEmpAttendAnceDayEtl extends SimpleAbstractEtl<SourceEmpAttendanceDayEntity> {

	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public void setBatchQuery(BatchSqlConfig config) {
		config.setBatchSql(URL, USER, PASSWORD, MY_SQL_DRIVER, "select * from emp_attendance_day t where t.days > '2015-11-31'");
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
		return Etl.MY_SQL_DRIVER;
	}

	@Override
	protected SqlAuxiliary getSqlAuxiliary() {
		return SourceEmpAttendanceDayEntity.getEntityAuxiliary()
				.asEmpAttendanceDayId("t.`emp_attendance_day_id`")
				.asCustomerId("t.`customer_id`")
				.asEmpKey("t.`emp_key`")
				.asEmpId("t.`emp_id`")
				.asUserNameCh("t.`user_name_ch`")
				.asHourCount("t.`hour_count`")
				.asTheoryHourCount("t.`theory_hour_count`")
				.asOrganizationId("t.`organization_id`")
				.asCheckworkTypeId("t.`checkwork_type_id`")
				.asDays("t.`days`")
				.asYearMonths("t.`year_months`")
				.setSqlBody(" emp_attendance_day t where t.days > '2015-11-31'")
				;
	}
}
