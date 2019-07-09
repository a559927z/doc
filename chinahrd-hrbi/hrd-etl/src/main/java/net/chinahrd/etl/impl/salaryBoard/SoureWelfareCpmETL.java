package net.chinahrd.etl.impl.salaryBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceWelfareCpmEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SoureWelfareCpmETL extends SimpleAbstractEtl<SourceWelfareCpmEntity> {

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
		return SourceWelfareCpmEntity.getEntityAuxiliary()
			.asId("t.`welfare_cpm_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asCpmId("t.`cpm_id`")
			.asWelfareValue("t.`welfare_value`")
			.asDate("t.`date`")
			.asYearMonth("t.`year_month`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" welfare_cpm t where 1=1");
	}

	public static void main(String...strings){
		new SoureWelfareCpmETL().execute();
	}
}
