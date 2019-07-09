package net.chinahrd.etl.impl.promotionBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePromotionTotalEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePromotionTotalETL extends SimpleAbstractEtl<SourcePromotionTotalEntity> {

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
		return SourcePromotionTotalEntity.getEntityAuxiliary()
			.asPromotionTotalId("t.`promotion_total_id`")
			.asSchemeId("t.`scheme_id`")
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asRankName("t.`rank_name`")
			.asRankNameAf("t.`rank_name_af`")
			.asOrganizationId("t.`organization_id`")
			.asStatus("t.`status`")
			.asConditionProp("t.`condition_prop`")
			.asTotalDate("t.`total_date`")
			.setSqlBody(" promotion_total t where 1=1");
	}

}
