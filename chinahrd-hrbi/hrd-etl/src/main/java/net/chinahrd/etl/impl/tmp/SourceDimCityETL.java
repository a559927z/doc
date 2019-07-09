package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimCityEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimCityETL extends SimpleAbstractEtl<SourceDimCityEntity> {

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
		return SourceDimCityEntity.getEntityAuxiliary()
			.asCityId("t.`city_id`")
			.asCustomerId("t.`customer_id`")
			.asCityKey("t.`city_key`")
			.asCityName("t.`city_name`")
			.asProvinceId("t.`province_id`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_city t where 1=1");
	}

}
