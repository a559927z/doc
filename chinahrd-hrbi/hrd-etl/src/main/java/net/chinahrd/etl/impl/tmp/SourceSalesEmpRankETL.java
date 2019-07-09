package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSalesEmpRankEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSalesEmpRankETL extends SimpleAbstractEtl<SourceSalesEmpRankEntity> {

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
		return SourceSalesEmpRankEntity.getEntityAuxiliary()
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpRank("t.`emp_rank`")
			.asOrganizationId("t.`organization_id`")
			.asRankDate("t.`rank_date`")
			.asYearMonth("t.`year_month`")
			.asProportionId("t.`proportion_id`")
			.setSqlBody(" sales_emp_rank t where 1=1");
	}

}
