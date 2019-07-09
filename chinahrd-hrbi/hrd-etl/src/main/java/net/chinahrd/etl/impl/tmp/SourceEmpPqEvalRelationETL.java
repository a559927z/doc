package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpPqEvalRelationEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpPqEvalRelationETL extends SimpleAbstractEtl<SourceEmpPqEvalRelationEntity> {

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
		return SourceEmpPqEvalRelationEntity.getEntityAuxiliary()
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asExaminationResultId("t.`examination_result_id`")
			.asExaminationResult("t.`examination_result`")
			.asDate("t.`date`")
			.asRefresh("t.`refresh`")
			.asCurtName("t.`curt_name`")
			.setSqlBody(" emp_pq_eval_relation t where 1=1");
	}

}
