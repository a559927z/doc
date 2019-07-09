package net.chinahrd.etl.impl.recruitBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceOutTalentEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceOutTalentETL extends SimpleAbstractEtl<SourceOutTalentEntity> {

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
		return SourceOutTalentEntity.getEntityAuxiliary()
			.asOutTalentId("t.`out_talent_id`")
			.asCustomerId("t.`customer_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asUserName("t.`user_name`")
			.asEmail("t.`email`")
			.asAge("t.`age`")
			.asSex("t.`sex`")
			.asDegreeId("t.`degree_id`")
			.asUrl("t.`url`")
			.asSchool("t.`school`")
			.asRefresh("t.`refresh`")
			.asImgPath("t.`img_path`")
			.asTag("t.`tag`")
			.asCId("t.`c_id`")
			.setSqlBody(" out_talent t where 1=1");
	}
	public static void main(String... arg){
		new SourceOutTalentETL().execute();
	}

}
