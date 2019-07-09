package net.chinahrd.etl.impl.salaryBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceShareHoldingEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceShareHoldingETL extends SimpleAbstractEtl<SourceShareHoldingEntity> {

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
		return SourceShareHoldingEntity.getEntityAuxiliary()
			.asId("t.`share_holding_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asUsreNameCh("t.`usre_name_ch`")
			.asOrganizationId("t.`organization_id`")
			.asFullPath("t.`full_path`")
			.asNowShare("t.`now_share`")
			.asConferShare("t.`confer_share`")
			.asPrice("t.`price`")
			.asHoldPeriod("t.`hold_period`")
			.asSubNum("t.`sub_num`")
			.asSubDate("t.`sub_date`")
			.asYear("t.`year`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" share_holding t where 1=1");
	}
	
	public static void main(String...strings){
		new SourceShareHoldingETL().execute();
	}
}
