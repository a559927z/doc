package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimSeparationRiskEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimSeparationRiskETL extends SimpleAbstractEtl<SourceDimSeparationRiskEntity> {

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
		return SourceDimSeparationRiskEntity.getEntityAuxiliary()
			.asSeparationRiskId("t.`separation_risk_id`")
			.asCustomerId("t.`customer_id`")
			.asSeparationRiskKey("t.`separation_risk_key`")
			.asSeparationRiskParentId("t.`separation_risk_parent_id`")
			.asSeparationRiskParentKey("t.`separation_risk_parent_key`")
			.asSeparationRiskName("t.`separation_risk_name`")
			.asRefer("t.`refer`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_separation_risk t where 1=1");
	}

}
