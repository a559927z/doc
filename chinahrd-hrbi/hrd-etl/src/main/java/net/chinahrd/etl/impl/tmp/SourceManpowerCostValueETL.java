package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceManpowerCostValueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceManpowerCostValueETL extends SimpleAbstractEtl<SourceManpowerCostValueEntity> {

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
		return SourceManpowerCostValueEntity.getEntityAuxiliary()
			.asManpowerCostValueId("t.`manpower_cost_value_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asBudgetValue("t.`budget_value`")
			.asYear("t.`year`")
			.setSqlBody(" manpower_cost_value t where 1=1");
	}

}
