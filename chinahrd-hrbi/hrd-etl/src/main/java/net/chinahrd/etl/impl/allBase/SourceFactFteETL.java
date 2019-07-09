package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceFactFteEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceFactFteETL extends SimpleAbstractEtl<SourceFactFteEntity> {

	public static String URL = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String USER = "mup";
	public static String PASSWORD = "1q2w3e123";

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
		return SourceFactFteEntity.getEntityAuxiliary()
			.asFteId("t.fte_id")
			.asCustomerId("t.customer_id")
			.asOrganizationId("t.organization_id")
			.asOrganizationName("t.organization_name")
			.asFulltimeSum("t.fulltime_sum")
			.asPasstimeSum("t.passtime_sum")
			.asOvertimeSum("t.overtime_sum")
			.asFteValue("t.fte_value")
			.asYearMonth("t.year_month")
			.asGainAmount("t.gain_amount")
			.asSalesAmount("t.sales_amount")
			.asTargetValue("t.target_value")
			.asBenefitValue("t.benefit_value")
			.asRangePer("t.range_per")
			.setSqlBody(" fact_fte t where 1=1");
	}
}
