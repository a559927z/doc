package net.chinahrd.etl.impl.humanInventory;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProjectManpowerEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProjectManpowerETL extends SimpleAbstractEtl<SourceProjectManpowerEntity> {

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
		return SourceProjectManpowerEntity.getEntityAuxiliary()
			.asProjectManpowerId("t.`project_manpower_id`")
			.asEmpId("t.`emp_id`")
			.asInput("t.`input`")
			.asNote("t.`note`")
			.asProjectId("t.`project_id`")
			.asProjectSubId("t.`project_sub_id`")
			.asDate("t.`date`")
			.asType("t.`type`")
			.setSqlBody(" project_manpower t where 1=1");
	}

}
