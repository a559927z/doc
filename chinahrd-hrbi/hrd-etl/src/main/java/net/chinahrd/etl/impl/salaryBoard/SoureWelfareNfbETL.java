package net.chinahrd.etl.impl.salaryBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceWelfareNfbEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SoureWelfareNfbETL extends SimpleAbstractEtl<SourceWelfareNfbEntity> {

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
		return SourceWelfareNfbEntity.getEntityAuxiliary()
			.asId("t.`welfare_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asNfbId("t.`nfb_id`")
			.asWelfareValue("t.`welfare_value`")
			.asDate("t.`date`")
			.asYearMonth("t.`year_month`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" welfare_nfb t where 1=1");
	}
	
	public static void main(String...strings){
		new SoureWelfareNfbETL().execute();
	}
}
