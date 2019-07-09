package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDeptKpiEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDeptKpiETL extends SimpleAbstractEtl<SourceDeptKpiEntity> {

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
		return SourceDeptKpiEntity.getEntityAuxiliary()
			.asDeptPerExamRelationId("t.dept_per_exam_relation_id")
			.asCustomerId("t.customer_id")
			.asOrganizationId("t.organization_id")
			.asKpiValue("t.kpi_value")
			.asYear("t.year")
			.asRefresh("t.refresh")
			.setSqlBody(" dept_kpi t where 1=1");
	}
}
