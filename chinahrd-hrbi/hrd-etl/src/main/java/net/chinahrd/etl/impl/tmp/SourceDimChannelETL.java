package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimChannelEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimChannelETL extends SimpleAbstractEtl<SourceDimChannelEntity> {

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
		return SourceDimChannelEntity.getEntityAuxiliary()
			.asChannelId("t.`channel_id`")
			.asCustomerId("t.`customer_id`")
			.asChannelKey("t.`channel_key`")
			.asChannelName("t.`channel_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_channel t where 1=1");
	}

}
