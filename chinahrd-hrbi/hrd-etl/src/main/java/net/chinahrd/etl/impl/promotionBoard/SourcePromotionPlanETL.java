package net.chinahrd.etl.impl.promotionBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePromotionPlanEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePromotionPlanETL extends SimpleAbstractEtl<SourcePromotionPlanEntity> {

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
		return SourcePromotionPlanEntity.getEntityAuxiliary()
			.asPromotionPlanId("t.`promotion_plan_id`")
			.asCustomerId("t.`customer_id`")
			.asRankNameAf("t.`rank_name_af`")
			.asSchemeId("t.`scheme_id`")
			.asCreateUserId("t.`create_user_id`")
			.asModifyUserId("t.`modify_user_id`")
			.asCreateTime("t.`create_time`")
			.asModifyTime("t.`modify_time`")
			.setSqlBody(" promotion_plan t where 1=1");
	}

}
