package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpBonusPenaltyEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpBonusPenaltyETL extends SimpleAbstractEtl<SourceEmpBonusPenaltyEntity> {

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
		return SourceEmpBonusPenaltyEntity.getEntityAuxiliary()
			.asEmpBonusPenaltyId("t.`emp_bonus_penalty_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asEmpBonusPenaltyName("t.`emp_bonus_penalty_name`")
			.asType("t.`type`")
			.asGrantUnit("t.`grant_unit`")
			.asWitnessName("t.`witness_name`")
			.asBonusPenaltyDate("t.`bonus_penalty_date`")
			.asCId("t.`c_id`")
			.setSqlBody(" emp_bonus_penalty t where 1=1");
	}

}
