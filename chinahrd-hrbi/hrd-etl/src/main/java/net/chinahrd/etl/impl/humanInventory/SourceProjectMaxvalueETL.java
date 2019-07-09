package net.chinahrd.etl.impl.humanInventory;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProjectMaxvalueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;
import net.chinahrd.utils.PropertiesUtil;

public class SourceProjectMaxvalueETL extends SimpleAbstractEtl<SourceProjectMaxvalueEntity> {

	private String propsPath = "conf/config.properties";
	public final String CUSTOMER_ID=PropertiesUtil.getProperty(propsPath, "customer.id");
	
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
			.asCustomerId(CUSTOMER_ID)
			.asOrganizationId("t.`organization_id`")
			.asMaxval("t.`maxval`")
			.asTotalWorkHours("t.`total_work_hours`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" project_maxvalue t where 1=1");
	}

}
