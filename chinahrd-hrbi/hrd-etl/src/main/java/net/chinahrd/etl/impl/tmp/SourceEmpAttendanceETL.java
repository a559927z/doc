package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpAttendanceEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpAttendanceETL extends SimpleAbstractEtl<SourceEmpAttendanceEntity> {

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
		return SourceEmpAttendanceEntity.getEntityAuxiliary()
			.asCardID("t.`CardID`")
			.asEmpIName("t.`EmpIName`")
			.asRecDate("t.`RecDate`")
			.asRecTime("t.`RecTime`")
			.asRefreshDate("t.`refresh_date`")
			.setSqlBody(" emp_attendance t where 1=1");
	}

}
