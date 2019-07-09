package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceBudgetEmpNumberEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceBudgetEmpNumberETL extends SimpleAbstractEtl<SourceBudgetEmpNumberEntity> {

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
		return SourceBudgetEmpNumberEntity.getEntityAuxiliary()
			.asBudgetEmpNumberId("t.`budget_emp_number_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asNumber("t.`number`")
			.asYear("t.`year`")
			.setSqlBody(" budget_emp_number t where 1=1");
	}

}
