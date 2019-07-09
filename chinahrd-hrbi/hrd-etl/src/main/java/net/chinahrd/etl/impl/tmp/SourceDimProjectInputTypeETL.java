package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimProjectInputTypeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimProjectInputTypeETL extends SimpleAbstractEtl<SourceDimProjectInputTypeEntity> {

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
		return SourceDimProjectInputTypeEntity.getEntityAuxiliary()
			.asProjectInputTypeId("t.`project_input_type_id`")
			.asProjectInputTypeName("t.`project_input_type_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_project_input_type t where 1=1");
	}

}
