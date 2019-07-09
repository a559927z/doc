package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProjectMaxvalueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProjectMaxvalueETL extends SimpleAbstractEtl<SourceProjectMaxvalueEntity> {

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
		return SourceProjectMaxvalueEntity.getEntityAuxiliary()
			.asProjectMaxvalueId("t.`project_maxvalue_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asMaxval("t.`maxval`")
			.asTotalWorkHours("t.`total_work_hours`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" project_maxvalue t where 1=1");
	}

}
