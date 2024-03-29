package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpPqRelationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpPqRelationETL extends SimpleAbstractEtl<SourceEmpPqRelationEntity> {

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
		return SourceEmpPqRelationEntity.getEntityAuxiliary()
			.asEmpPqRelationId("t.`emp_pq_relation_id`")
			.asQualityId("t.`quality_id`")
			.asMatchingSourceId("t.`matching_source_id`")
			.asDemandsMatchingSourceId("t.`demands_matching_source_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asDate("t.`date`")
			.asRefresh("t.`refresh`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" emp_pq_relation t where 1=1");
	}

}
