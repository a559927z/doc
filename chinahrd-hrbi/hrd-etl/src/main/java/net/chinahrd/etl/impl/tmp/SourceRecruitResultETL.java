package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceRecruitResultEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceRecruitResultETL extends SimpleAbstractEtl<SourceRecruitResultEntity> {

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
		return SourceRecruitResultEntity.getEntityAuxiliary()
			.asRecruitResultId("t.`recruit_result_id`")
			.asCustomerId("t.`customer_id`")
			.asRecruitResultKey("t.`recruit_result_key`")
			.asUsername("t.`username`")
			.asSex("t.`sex`")
			.asAge("t.`age`")
			.asDegreeId("t.`degree_id`")
			.asMajor("t.`major`")
			.asSchool("t.`school`")
			.asIsResume("t.`is_resume`")
			.asIsInterview("t.`is_interview`")
			.asIsOffer("t.`is_offer`")
			.asIsEntry("t.`is_entry`")
			.asUrl("t.`url`")
			.asRecruitPublicId("t.`recruit_public_id`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.asCId("t.`c_id`")
			.setSqlBody(" recruit_result t where 1=1");
	}

}
