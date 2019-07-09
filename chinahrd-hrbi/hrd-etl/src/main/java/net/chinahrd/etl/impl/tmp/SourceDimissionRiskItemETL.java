package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimissionRiskItemEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimissionRiskItemETL extends SimpleAbstractEtl<SourceDimissionRiskItemEntity> {

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
		return SourceDimissionRiskItemEntity.getEntityAuxiliary()
			.asDimissionRiskItemId("t.`dimission_risk_item_id`")
			.asCustomerId("t.`customer_id`")
			.asDimissionRiskId("t.`dimission_risk_id`")
			.asSeparationRiskId("t.`separation_risk_id`")
			.asRiskFlag("t.`risk_flag`")
			.setSqlBody(" dimission_risk_item t where 1=1");
	}

}
