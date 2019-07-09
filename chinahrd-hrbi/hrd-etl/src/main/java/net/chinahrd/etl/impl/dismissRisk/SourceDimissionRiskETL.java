package net.chinahrd.etl.impl.dismissRisk;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimissionRiskEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimissionRiskETL  extends SimpleAbstractEtl<SourceDimissionRiskEntity>  {

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
		return SourceDimissionRiskEntity.getEntityAuxiliary()
			.asDimissionRiskId("t.dimission_risk_id")
			.asCustomerId("t.customer_id")
			.asEmpId("t.emp_id")
			.asNote("t.note")
			.asRiskDate("t.risk_date")
			.asRiskFlag("t.risk_flag")
			.asIsLast("t.is_last")
			.setSqlBody(" dimission_risk t where 1=1");
	}

}
