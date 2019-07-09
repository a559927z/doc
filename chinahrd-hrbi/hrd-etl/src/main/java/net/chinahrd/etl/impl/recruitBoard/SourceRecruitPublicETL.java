package net.chinahrd.etl.impl.recruitBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceRecruitPublicEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceRecruitPublicETL extends SimpleAbstractEtl<SourceRecruitPublicEntity> {

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
		return SourceRecruitPublicEntity.getEntityAuxiliary()
			.asRecruitPublicId("t.`recruit_public_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asPositionId("t.`position_id`")
			.asEmployNum("t.`employ_num`")
			.asPlanNum("t.`plan_num`")
			.asStartDate("t.`start_date`")
			.asEndDate("t.`end_date`")
			.asDays("t.`days`")
			.asResumeNum("t.`resume_num`")
			.asInterviewNum("t.`interview_num`")
			.asOfferNum("t.`offer_num`")
			.asEntryNum("t.`entry_num`")
			.asIsPublic("t.`is_public`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" recruit_public t where 1=1");
	}

	public static void main(String...strings){
		new SourceRecruitPublicETL().execute();
	}
}

