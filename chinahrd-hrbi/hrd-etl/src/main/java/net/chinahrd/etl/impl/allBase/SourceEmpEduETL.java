package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpEduEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpEduETL extends SimpleAbstractEtl<SourceEmpEduEntity> {

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
		return SourceEmpEduEntity.getEntityAuxiliary()
			.asEmpEduId("t.emp_edu_id")
			.asCustomerId("t.customer_id")
			.asEmpId("t.emp_id")
			.asSchoolName("t.school_name")
			.asDegreeId("t.degree_id")
			.asDegree("t.degree")
			.asMajor("t.major")
			.asStartDate("t.start_date")
			.asEndDate("t.end_date")
			.asIsLastMajor("t.is_last_major")
			.asAcademicDegree("t.academic_degree")
			.asDevelopMode("t.develop_mode")
			.asBonus("t.bonus")
			.asNote("t.note")
			.setSqlBody(" emp_edu t where 1=1");
	}
}
