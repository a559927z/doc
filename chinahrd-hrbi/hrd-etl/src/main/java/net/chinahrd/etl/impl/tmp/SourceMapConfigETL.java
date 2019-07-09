package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMapConfigEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMapConfigETL extends SimpleAbstractEtl<SourceMapConfigEntity> {

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
		return SourceMapConfigEntity.getEntityAuxiliary()
			.asMapConfigId("t.`map_config_id`")
			.asCustomerId("t.`customer_id`")
			.asXNumber("t.`x_number`")
			.asYNumber("t.`y_number`")
			.asColor("t.`color`")
			.asUpdateTime("t.`update_time`")
			.asZName("t.`z_name`")
			.setSqlBody(" map_config t where 1=1");
	}

}
