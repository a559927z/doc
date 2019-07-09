package net.chinahrd.etl.impl.salaryBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalaryEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalaryETL extends SimpleAbstractEtl<SourceSalaryEntity> {

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
		return SourceSalaryEntity.getEntityAuxiliary()
			.asId("t.`salary_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asStructureId("t.`structure_id`")
			.asSalaryValue("t.`salary_value`")
			.asYearMonth("t.`year_month`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" salary t where 1=1");
	}

	public static void main(String...strings){
		new SourceSalaryETL().execute();
	}
}
