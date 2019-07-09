package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimProvinceEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimProvinceETL extends SimpleAbstractEtl<SourceDimProvinceEntity> {

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
		return SourceDimProvinceEntity.getEntityAuxiliary()
			.asProvinceId("t.`province_id`")
			.asCustomerId("t.`customer_id`")
			.asProvinceKey("t.`province_key`")
			.asProvinceName("t.`province_name`")
			.asShowIndex("t.`show_index`")
			.asCurtName("t.`curt_name`")
			.setSqlBody(" dim_province t where 1=1");
	}

}
