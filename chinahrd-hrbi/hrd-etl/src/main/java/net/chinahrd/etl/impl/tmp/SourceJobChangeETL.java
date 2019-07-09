package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceJobChangeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceJobChangeETL extends SimpleAbstractEtl<SourceJobChangeEntity> {

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
		return SourceJobChangeEntity.getEntityAuxiliary()
			.asEmpJobChangeId("t.`emp_job_change_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asEmpKey("t.`emp_key`")
			.asUserNameCh("t.`user_name_ch`")
			.asChangeDate("t.`change_date`")
			.asChangeTypeId("t.`change_type_id`")
			.asChangeType("t.`change_type`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationName("t.`organization_name`")
			.asPositionId("t.`position_id`")
			.asPositionName("t.`position_name`")
			.asSequenceId("t.`sequence_id`")
			.asSequenceName("t.`sequence_name`")
			.asSequenceSubId("t.`sequence_sub_id`")
			.asSequenceSubName("t.`sequence_sub_name`")
			.asAbilityId("t.`ability_id`")
			.asAbilityName("t.`ability_name`")
			.asAbilityLvId("t.`ability_lv_id`")
			.asAbilityLvName("t.`ability_lv_name`")
			.asJobTitleId("t.`job_title_id`")
			.asJobTitleName("t.`job_title_name`")
			.asRankName("t.`rank_name`")
			.asRankNameEd("t.`rank_name_ed`")
			.asPositionIdEd("t.`position_id_ed`")
			.asPositionNameEd("t.`position_name_ed`")
			.asNote("t.`note`")
			.asRefresh("t.`refresh`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" job_change t where 1=1");
	}

}
