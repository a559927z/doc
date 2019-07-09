package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapTeamEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapTeamETL extends SimpleAbstractEtl<SourceMapTeamEntity> {

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
		return SourceMapTeamEntity.getEntityAuxiliary()
			.asMapTeamId("t.`map_team_id`")
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asTeamName("t.`team_name`")
			.asRequirement("t.`requirement`")
			.asPkView("t.`pk_view`")
			.asCreateTime("t.`create_time`")
			.setSqlBody(" map_team t where 1=1");
	}

}
