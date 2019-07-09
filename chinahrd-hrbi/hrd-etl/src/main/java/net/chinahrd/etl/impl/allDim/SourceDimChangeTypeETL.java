package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimChangeTypeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimChangeTypeETL extends SimpleAbstractEtl<SourceDimChangeTypeEntity> {


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
		return SourceDimChangeTypeEntity.getEntityAuxiliary()
			.asChangeTypeId("t.`change_type_id`")
			.asCustomerId("t.`customer_id`")
			.asChangeTypeName("t.`change_type_name`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_change_type t where 1=1");
	}

}
