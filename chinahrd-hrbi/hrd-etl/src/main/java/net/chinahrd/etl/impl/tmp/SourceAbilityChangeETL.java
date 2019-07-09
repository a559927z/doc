package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceAbilityChangeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceAbilityChangeETL extends SimpleAbstractEtl<SourceAbilityChangeEntity> {

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
		return SourceAbilityChangeEntity.getEntityAuxiliary()
			.asAbilityChangeId("t.`ability_change_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asFullPath("t.`full_path`")
			.asOrganizationParentId("t.`organization_parent_id`")
			.asOrganizationId("t.`organization_id`")
			.asSequenceId("t.`sequence_id`")
			.asSequenceSubId("t.`sequence_sub_id`")
			.asAbilityId("t.`ability_id`")
			.asAge("t.`age`")
			.asSex("t.`sex`")
			.asDegreeId("t.`degree_id`")
			.asAbilityNumberId("t.`ability_number_id`")
			.asUpdateTime("t.`update_time`")
			.asYearMonths("t.`year_months`")
			.setSqlBody(" ability_change t where 1=1");
	}

}
