package net.chinahrd.etl.impl.recruitBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceRecruitValueEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceRecruitValueETL extends SimpleAbstractEtl<SourceRecruitValueEntity> {

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
		return SourceRecruitValueEntity.getEntityAuxiliary()
			.asRecruitValueId("t.`recruit_value_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asBudgetValue("t.`budget_value`")
			.asOutlay("t.`outlay`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.asCId("t.`c_id`")
			.setSqlBody(" recruit_value t where 1=1");
	}
	
	public static void main(String...strings){
		new SourceRecruitValueETL().execute();
	}
}
