package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceWelfareUncpmEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceWelfareUncpmETL extends SimpleAbstractEtl<SourceWelfareUncpmEntity> {

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
		return SourceWelfareUncpmEntity.getEntityAuxiliary()
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asUncpmId("t.`uncpm_id`")
			.asNote("t.`note`")
			.asDate("t.`date`")
			.asYearMonth("t.`year_month`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" welfare_uncpm t where 1=1");
	}

}
