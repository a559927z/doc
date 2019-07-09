package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimDedicatGenreEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimDedicatGenreETL extends SimpleAbstractEtl<SourceDimDedicatGenreEntity> {

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
		return SourceDimDedicatGenreEntity.getEntityAuxiliary()
			.asDedicatGenreId("t.`dedicat_genre_id`")
			.asCustomerId("t.`customer_id`")
			.asDedicatName("t.`dedicat_name`")
			.asDedicatGenreParentId("t.`dedicat_genre_parent_id`")
			.asIsChildren("t.`is_children`")
			.asCreateDate("t.`create_date`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_dedicat_genre t where 1=1");
	}

}
