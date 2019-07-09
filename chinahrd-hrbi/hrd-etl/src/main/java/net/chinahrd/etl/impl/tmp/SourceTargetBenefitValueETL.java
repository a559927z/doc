package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceTargetBenefitValueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceTargetBenefitValueETL extends SimpleAbstractEtl<SourceTargetBenefitValueEntity> {

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
		return SourceTargetBenefitValueEntity.getEntityAuxiliary()
			.asTargetBenefitValueId("t.`target_benefit_value_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asTargetValue("t.`target_value`")
			.asYear("t.`year`")
			.setSqlBody(" target_benefit_value t where 1=1");
	}

}
