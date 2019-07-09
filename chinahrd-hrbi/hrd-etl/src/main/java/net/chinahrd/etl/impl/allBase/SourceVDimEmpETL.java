package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceVDimEmpEntity;
import net.chinahrd.etl.sql.FormatColumn;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceVDimEmpETL extends SimpleAbstractEtl<SourceVDimEmpEntity> {

	public static String URL = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String USER = "mup";
	public static String PASSWORD = "1q2w3e123";

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
			.asCustomerId("t.customer_id")
			.asEmpId("t.emp_id")
			.asEmpKey("t.emp_key")
			.asUserName("t.user_name")
			.asUserNameCh("t.user_name_ch")
			.asEmpHfId("t.emp_hf_id")
			.asEmpHfKey("t.emp_hf_key")
			.asEmail("t.email")
			.asImgPath("t.img_path")
			.asPasstimeOrFulltime("t.passtime_or_fulltime")
			.asContract("t.contract")
			.asBlood("t.blood")
			.asIsKeyTalent("t.is_key_talent")
			.asSex("t.sex")
			.asNation("t.nation")
			.asDegreeId("t.degree_id")
			.asDegree("t.degree")
			.asNativePlace("t.native_place")
			.asCountry("t.country")
			.asBirthPlace("t.birth_place")
			.asBirthDate("t.birth_date")
			.asNationalId("t.national_id")
			.asNationalType("t.national_type")
			.asMarryStatus("t.marry_status")
			.asPattyName("t.patty_name")
			.asPositionId("t.position_id")
			.asPositionName("t.position_name")
			.asOrganizationParentId("t.organization_parent_id")
			.asOrganizationParentName("t.organization_parent_name")
			.asOrganizationId("t.organization_id")
			.asOrganizationName("t.organization_name")
			.asSequenceId("t.sequence_id")
			.asSequenceName("t.sequence_name")
			.asSequenceSubId("t.sequence_sub_id")
			.asSequenceSubName("t.sequence_sub_name")
			.asPerformanceId("t.performance_id")
			.asPerformanceName("t.performance_name")
			.asAbilityId("t.ability_id")
			.asJobTitleId("t.job_title_id")
			.asAbilityLvId("t.ability_lv_id")
			.asAbilityName("t.ability_name")
			.asJobTitleName("t.job_title_name")
			.asAbilityLvName("t.ability_lv_name")
			.asRunOffDate("t.run_off_date")
			.asEntryDate("t.entry_date")
			.asQq("t.qq",new FormatColumn<String, Integer>() {

				@Override
				public Integer formatData(Object obj) {
					Integer qq=0;
					try {
						qq=Integer.valueOf(obj.toString());
					} catch (NumberFormatException e) {
					}
					return qq;
				}
			})
			.asWxCode("t.wx_code")
			.asAddress("t.address")
			.asContractUnit("t.contract_unit")
			.asWorkPlace("t.work_place")
			.asWorkPlaceId("t.work_place_id")
			.asRegularDate("t.regular_date")
			.asChannelId("t.channel_id")
			.asSpeciality("t.speciality")
			.asIsRegular("t.is_regular")
			.setSqlBody(" v_dim_emp t where 1=1");
	}
}
