package net.chinahrd.etl.impl.benefit;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceTradeProfitEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceTradeProfitETL extends SimpleAbstractEtl<SourceTradeProfitEntity> implements Runnable {

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
		return SourceTradeProfitEntity.getEntityAuxiliary()
			.asTradeProfitId("t.`trade_profit_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asSalesAmount("t.`sales_amount`")
			.asExpendAmount("t.`expend_amount`")
			.asGainAmount("t.`gain_amount`")
			.asTargetValue("t.`target_value`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" trade_profit t where 1=1");
	}

	@Override
	public void run() {
		this.execute();
	}
}
