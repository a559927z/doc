package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePromotionResultsEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePromotionResultsETL extends SimpleAbstractEtl<SourcePromotionResultsEntity> {

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
		return SourcePromotionResultsEntity.getEntityAuxiliary()
			.asPromotionResultsId("t.`promotion_results_id`")
			.asEmpId("t.`emp_id`")
			.asCompanyAge("t.`company_age`")
			.asOranizationParentId("t.`oranization_parent_id`")
			.asOrganizationId("t.`organization_id`")
			.asFullPath("t.`full_path`")
			.asPerformanceId("t.`performance_id`")
			.asSequenceId("t.`sequence_id`")
			.asIsKeyTalent("t.`is_key_talent`")
			.asCustomerId("t.`customer_id`")
			.asEntryDate("t.`entry_date`")
			.asShowIndex("t.`show_index`")
			.asRankName("t.`rank_name`")
			.asShowIndexAf("t.`show_index_af`")
			.asRankNameAf("t.`rank_name_af`")
			.asStatus("t.`status`")
			.asStatusDate("t.`status_date`")
			.asPromotionDate("t.`promotion_date`")
			.setSqlBody(" promotion_results t where 1=1");
	}

}
