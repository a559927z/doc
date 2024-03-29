package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceBudgetEmpNumbeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceBudgetEmpNumbeETL extends SimpleAbstractEtl<SourceBudgetEmpNumbeEntity> {

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
		return SourceBudgetEmpNumbeEntity.getEntityAuxiliary()
			.asBudgetEmpNumberId("t.`budget_emp_number_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asNumber("t.`number`")
			.asYear("t.`year`")
			.setSqlBody(" budget_emp_numbe t where 1=1");
	}

}
