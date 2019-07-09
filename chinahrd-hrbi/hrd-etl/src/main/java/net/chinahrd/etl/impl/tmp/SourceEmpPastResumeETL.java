package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpPastResumeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpPastResumeETL extends SimpleAbstractEtl<SourceEmpPastResumeEntity> {

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
		return SourceEmpPastResumeEntity.getEntityAuxiliary()
			.asEmpPastResumeId("t.`emp_past_resume_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asWorkUnit("t.`work_unit`")
			.asDepartmentName("t.`department_name`")
			.asPositionName("t.`position_name`")
			.asBonusPenaltyName("t.`bonus_penalty_name`")
			.asWitnessName("t.`witness_name`")
			.asWitnessContactInfo("t.`witness_contact_info`")
			.asChangeReason("t.`change_reason`")
			.asEntryDate("t.`entry_date`")
			.asRunOffDate("t.`run_off_date`")
			.asMark("t.`mark`")
			.setSqlBody(" emp_past_resume t where 1=1");
	}

}
