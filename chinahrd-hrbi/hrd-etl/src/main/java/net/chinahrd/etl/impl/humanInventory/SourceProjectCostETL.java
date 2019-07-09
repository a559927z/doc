package net.chinahrd.etl.impl.humanInventory;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProjectCostEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProjectCostETL extends SimpleAbstractEtl<SourceProjectCostEntity> {

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
		return SourceProjectCostEntity.getEntityAuxiliary()
			.asProjectCostId("t.`project_cost_id`")
			.asCustomerId("t.`customer_id`")
			.asInput("t.`input`")
			.asOutput("t.`output`")
			.asGain("t.`gain`")
			.asProjectId("t.`project_id`")
			.asDate("t.`date`")
			.asType("t.`type`")
			.setSqlBody(" project_cost t where 1=1");
	}
}
