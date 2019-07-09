package net.chinahrd.etl.impl.manpowerCost;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceManpowerCostEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceManpowerCostETL extends SimpleAbstractEtl<SourceManpowerCostEntity> {

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
		return SourceManpowerCostEntity.getEntityAuxiliary()
			.asManpowerCostId("t.`manpower_cost_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asCost("t.`cost`")
			.asCostBudget("t.`cost_budget`")
			.asCostCompany("t.`cost_company`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" manpower_cost t where 1=1");
	}
	
	public static void main(String...strings){
		new SourceManpowerCostETL().execute();
	}
}
