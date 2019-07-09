package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceTrainValueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceTrainValueETL extends SimpleAbstractEtl<SourceTrainValueEntity> {

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
		return SourceTrainValueEntity.getEntityAuxiliary()
			.asTrainValueId("t.`train_value_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asBudgetValue("t.`budget_value`")
			.asYear("t.`year`")
			.setSqlBody(" train_value t where 1=1");
	}

}
