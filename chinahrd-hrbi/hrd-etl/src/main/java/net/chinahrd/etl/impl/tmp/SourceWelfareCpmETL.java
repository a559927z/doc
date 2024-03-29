package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceWelfareCpmEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceWelfareCpmETL extends SimpleAbstractEtl<SourceWelfareCpmEntity> {

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
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asCpmId("t.`cpm_id`")
			.asWelfareValue("t.`welfare_value`")
			.asDate("t.`date`")
			.asYearMonth("t.`year_month`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" welfare_cpm t where 1=1");
	}

}
