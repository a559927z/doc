package net.chinahrd.etl.impl.empSatisfaction;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDedicatOrganEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDedicatOrganETL extends SimpleAbstractEtl<SourceDedicatOrganEntity> implements Runnable {

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
		return SourceDedicatOrganEntity.getEntityAuxiliary()
			.asDedicatOrganId("t.`dedicat_organ_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationName("t.`organization_name`")
			.asScore("t.`soure`")
			.asDate("t.`date`")
			.setSqlBody(" dedicat_organ t where 1=1");
	}

	@Override
	public void run() {
		this.execute();
	}
}
