package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceVDimEmpEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceVDimEmpETL extends SimpleAbstractEtl<SourceVDimEmpEntity> {

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
		return SourceVDimEmpEntity.getEntityAuxiliary()
			.asVDimEmpId("t.`v_dim_emp_id`")
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpKey("t.`emp_key`")
			.asUserName("t.`user_name`")
			.asUserNameCh("t.`user_name_ch`")
			.asEmpHfId("t.`emp_hf_id`")
			.asEmpHfKey("t.`emp_hf_key`")
			.asEmail("t.`email`")
			.asImgPath("t.`img_path`")
			.asPasstimeOrFulltime("t.`passtime_or_fulltime`")
			.asContract("t.`contract`")
			.asBlood("t.`blood`")
			.asAge("t.`age`")
			.asSex("t.`sex`")
			.asNation("t.`nation`")
			.asDegreeId("t.`degree_id`")
			.asDegree("t.`degree`")
			.asNativePlace("t.`native_place`")
			.asCountry("t.`country`")
			.asBirthPlace("t.`birth_place`")
			.asBirthDate("t.`birth_date`")
			.asNationalId("t.`national_id`")
			.asNationalType("t.`national_type`")
			.asMarryStatus("t.`marry_status`")
			.asPattyName("t.`patty_name`")
			.asCompanyAge("t.`company_age`")
			.asIsKeyTalent("t.`is_key_talent`")
			.asOrganizationParentId("t.`organization_parent_id`")
			.asOrganizationParentName("t.`organization_parent_name`")
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
			.asJobTitleId("t.`job_title_id`")
			.asJobTitleName("t.`job_title_name`")
			.asAbilityLvId("t.`ability_lv_id`")
			.asAbilityLvName("t.`ability_lv_name`")
			.asRankName("t.`rank_name`")
			.asPerformanceId("t.`performance_id`")
			.asPerformanceName("t.`performance_name`")
			.asPopulationId("t.`population_id`")
			.asPopulationName("t.`population_name`")
			.asTelPhone("t.`tel_phone`")
			.asQq("t.`qq`")
			.asWxCode("t.`wx_code`")
			.asAddress("t.`address`")
			.asContractUnit("t.`contract_unit`")
			.asWorkPlaceId("t.`work_place_id`")
			.asWorkPlace("t.`work_place`")
			.asRegularDate("t.`regular_date`")
			.asChannelId("t.`channel_id`")
			.asSpeciality("t.`speciality`")
			.asIsRegular("t.`is_regular`")
			.asAreaId("t.`area_id`")
			.asEntryDate("t.`entry_date`")
			.asRunOffDate("t.`run_off_date`")
			.asRefreshDate("t.`refresh_date`")
			.asMark("t.`mark`")
			.setSqlBody(" v_dim_emp t where 1=1");
	}

}
