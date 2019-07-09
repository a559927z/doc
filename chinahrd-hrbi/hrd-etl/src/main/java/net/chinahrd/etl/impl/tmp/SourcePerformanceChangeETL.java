package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePerformanceChangeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePerformanceChangeETL extends SimpleAbstractEtl<SourcePerformanceChangeEntity> {

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
		return SourcePerformanceChangeEntity.getEntityAuxiliary()
			.asPerformanceChangeId("t.`performance_change_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asEmpKey("t.`emp_key`")
			.asOrganizationParentId("t.`organization_parent_id`")
			.asOrganzationParentName("t.`organzation_parent_name`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationName("t.`organization_name`")
			.asPositionId("t.`position_id`")
			.asPositionName("t.`position_name`")
			.asPerformanceId("t.`performance_id`")
			.asPerformanceName("t.`performance_name`")
			.asRankName("t.`rank_name`")
			.asRankNamePast("t.`rank_name_past`")
			.asYearMonth("t.`year_month`")
			.asType("t.`type`")
			.setSqlBody(" performance_change t where 1=1");
	}

}
