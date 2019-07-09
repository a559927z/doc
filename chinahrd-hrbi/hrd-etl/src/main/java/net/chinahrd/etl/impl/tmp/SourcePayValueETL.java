package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePayValueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePayValueETL extends SimpleAbstractEtl<SourcePayValueEntity> {

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
		return SourcePayValueEntity.getEntityAuxiliary()
			.asPayValueId("t.`pay_value_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asPayValue("t.`pay_value`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" pay_value t where 1=1");
	}

}
