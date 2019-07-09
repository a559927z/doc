package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapTalentInfoEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapTalentInfoETL extends SimpleAbstractEtl<SourceMapTalentInfoEntity> {

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
		return SourceMapTalentInfoEntity.getEntityAuxiliary()
			.asEmpId("t.`emp_id`")
			.asUserNameCh("t.`user_name_ch`")
			.asCustomerId("t.`customer_id`")
			.asFullPath("t.`full_path`")
			.asOrganizationParentId("t.`organization_parent_id`")
			.asOrganizationId("t.`organization_id`")
			.asSequenceId("t.`sequence_id`")
			.asSequenceSubId("t.`sequence_sub_id`")
			.asAbilityId("t.`ability_id`")
			.asDegreeId("t.`degree_id`")
			.asPerformanceId("t.`performance_id`")
			.asPositionId("t.`position_id`")
			.asAge("t.`age`")
			.asSex("t.`sex`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" map_talent_info t where 1=1");
	}

}
