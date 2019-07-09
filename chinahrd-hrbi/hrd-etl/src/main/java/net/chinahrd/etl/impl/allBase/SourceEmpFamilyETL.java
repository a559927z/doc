package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpFamilyEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpFamilyETL extends SimpleAbstractEtl<SourceEmpFamilyEntity> {

	public static String URL = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String USER = "mup";
	public static String PASSWORD = "1q2w3e123";

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
		return SourceEmpFamilyEntity.getEntityAuxiliary()
			.asEmpFamilyId("t.emp_family_id")
			.asCustomerId("t.customer_id")
			.asEmpId("t.emp_id")
			.asEmpFamilyName("t.emp_family_name")
			.asCall("t.call")
			.asIsChild("t.is_child")
			.asWorkUnit("t.work_unit")
			.asDepartmentName("t.department_name")
			.asPositionName("t.position_name")
			.asTelPhone("t.tel_phone")
			.asBornDate("t.born_date")
			.asNote("t.note")
			.setSqlBody(" emp_family t where 1=1");
	}
}
