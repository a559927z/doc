package net.chinahrd.etl.impl.recruitBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceRecruitChannelEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceRecruitChannelETL extends SimpleAbstractEtl<SourceRecruitChannelEntity> {

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
		return SourceRecruitChannelEntity.getEntityAuxiliary()
			.asRecruitChannelId("t.`recruit_channel_id`")
			.asCustomerId("t.`customer_id`")
			.asChannelId("t.`channel_id`")
			.asPositionId("t.`position_id`")
			.asEmployNum("t.`employ_num`")
			.asOutlay("t.`outlay`")
			.asStartDate("t.`start_date`")
			.asEndDate("t.`end_date`")
			.asDays("t.`days`")
			.asRecruitPublicId("t.`recruit_public_id`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.asCId("t.`c_id`")
			.setSqlBody(" recruit_channel t where 1=1");
	}

	public static void main(String...strings){
		new SourceRecruitChannelETL().execute();
	}
}
