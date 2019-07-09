package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimProjectTypeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimProjectTypeETL extends SimpleAbstractEtl<SourceDimProjectTypeEntity> {

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
		return SourceDimProjectTypeEntity.getEntityAuxiliary()
			.asProjectTypeId("t.`project_type_id`")
			.asCustomerId("t.`customer_id`")
			.asProjectTypeName("t.`project_type_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_project_type t where 1=1");
	}

}
