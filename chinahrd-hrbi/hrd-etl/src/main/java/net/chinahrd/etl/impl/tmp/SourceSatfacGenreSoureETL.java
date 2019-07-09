package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSatfacGenreSoureEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSatfacGenreSoureETL extends SimpleAbstractEtl<SourceSatfacGenreSoureEntity> {

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
		return SourceSatfacGenreSoureEntity.getEntityAuxiliary()
			.asSatfacSourceId("t.`satfac_source_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asSatfacGenreId("t.`satfac_genre_id`")
			.asSoure("t.`soure`")
			.asDate("t.`date`")
			.setSqlBody(" satfac_genre_soure t where 1=1");
	}

}
