package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDedicatGenreSoureEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDedicatGenreSoureETL extends SimpleAbstractEtl<SourceDedicatGenreSoureEntity> {

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
		return SourceDedicatGenreSoureEntity.getEntityAuxiliary()
			.asDedicatSourceId("t.`dedicat_source_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asDedicatGenreId("t.`dedicat_genre_id`")
			.asSoure("t.`soure`")
			.asDate("t.`date`")
			.setSqlBody(" dedicat_genre_soure t where 1=1");
	}

}
