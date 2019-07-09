package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimSalesTeamEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimSalesTeamETL extends SimpleAbstractEtl<SourceDimSalesTeamEntity> {

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
		return SourceDimSalesTeamEntity.getEntityAuxiliary()
			.asTeamId("t.`team_id`")
			.asCustomerId("t.`customer_id`")
			.asTeamName("t.`team_name`")
			.asEmpId("t.`emp_id`")
			.asEmpNames("t.`emp_names`")
			.asOrganizationId("t.`organization_id`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_sales_team t where 1=1");
	}

}
