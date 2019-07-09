package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimPositionEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimPositionETL extends SimpleAbstractEtl<SourceDimPositionEntity> {


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
		return SourceDimPositionEntity.getEntityAuxiliary()
			.asPositionId("t.`position_id`")
			.asCustomerId("t.`customer_id`")
			.asPositionKey("t.`position_key`")
			.asPositionName("t.`position_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_position t where 1=1");
	}

}
