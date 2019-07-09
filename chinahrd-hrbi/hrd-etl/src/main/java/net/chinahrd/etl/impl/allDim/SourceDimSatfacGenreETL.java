package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimSatfacGenreEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimSatfacGenreETL extends SimpleAbstractEtl<SourceDimSatfacGenreEntity> {


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
		return SourceDimSatfacGenreEntity.getEntityAuxiliary()
			.asSatfacGenreId("t.`satfac_genre_id`")
			.asCustomerId("t.`customer_id`")
			.asSatfacName("t.`satfac_name`")
			.asSatfacGenreParentId("t.`satfac_genre_parent_id`")
			.asIsChildren("t.`is_children`")
			.asCreateDate("t.`create_date`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_satfac_genre t where 1=1");
	}

}
