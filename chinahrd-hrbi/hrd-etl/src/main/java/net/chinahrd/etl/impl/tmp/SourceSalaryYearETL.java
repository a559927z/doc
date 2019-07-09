package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalaryYearEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalaryYearETL extends SimpleAbstractEtl<SourceSalaryYearEntity> {

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
		return SourceSalaryYearEntity.getEntityAuxiliary()
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asStructureId("t.`structure_id`")
			.asSalaryValueYear("t.`salary_value_year`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" salary_year t where 1=1");
	}

}
