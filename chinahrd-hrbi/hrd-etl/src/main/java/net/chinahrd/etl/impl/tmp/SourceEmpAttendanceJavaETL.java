package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpAttendanceJavaEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpAttendanceJavaETL extends SimpleAbstractEtl<SourceEmpAttendanceJavaEntity> {

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
		return SourceEmpAttendanceJavaEntity.getEntityAuxiliary()
			.asEmpAttendanceId("t.`emp_attendance_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpKey("t.`emp_key`")
			.asEmpId("t.`emp_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asClockInAm("t.`clock_in_am`")
			.asClockOutAm("t.`clock_out_am`")
			.asClockInPm("t.`clock_in_pm`")
			.asClockOutPm("t.`clock_out_pm`")
			.asOptIn("t.`opt_in`")
			.asOptOut("t.`opt_out`")
			.asOptReason("t.`opt_reason`")
			.asCalHour("t.`cal_hour`")
			.asCheckworkTypeId("t.`checkwork_type_id`")
			.asNote("t.`note`")
			.asDays("t.`days`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" emp_attendance_java t where 1=1");
	}

}
