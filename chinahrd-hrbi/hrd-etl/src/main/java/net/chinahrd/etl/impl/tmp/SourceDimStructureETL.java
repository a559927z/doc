package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimStructureEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimStructureETL extends SimpleAbstractEtl<SourceDimStructureEntity> {

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
		return SourceDimStructureEntity.getEntityAuxiliary()
			.asStructureId("t.`structure_id`")
			.asCustomerId("t.`customer_id`")
			.asStructureName("t.`structure_name`")
			.asIsFixed("t.`is_fixed`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_structure t where 1=1");
	}

}
