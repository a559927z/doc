package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceOrganizationChangeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceOrganizationChangeETL extends SimpleAbstractEtl<SourceOrganizationChangeEntity> {

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
		return SourceOrganizationChangeEntity.getEntityAuxiliary()
			.asOrganizationChangeId("t.`organization_change_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asEmpKey("t.`emp_key`")
			.asUserNameCh("t.`user_name_ch`")
			.asChangeDate("t.`change_date`")
			.asChangeTypeId("t.`change_type_id`")
			.asOrganizationId("t.`organization_id`")
			.asOrganizationName("t.`organization_name`")
			.asOrganizationIdEd("t.`organization_id_ed`")
			.asOrganizationNameEd("t.`organization_name_ed`")
			.asPositionId("t.`position_id`")
			.asPositionName("t.`position_name`")
			.asSequenceId("t.`sequence_id`")
			.asSequenceName("t.`sequence_name`")
			.asAbilityId("t.`ability_id`")
			.asAbilityName("t.`ability_name`")
			.asNote("t.`note`")
			.asRefresh("t.`refresh`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" organization_change t where 1=1");
	}

}
