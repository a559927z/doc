package net.chinahrd.etl.impl.empSatisfaction;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceSatfacGenreScoreEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceSatfacGenreScoreETL extends SimpleAbstractEtl<SourceSatfacGenreScoreEntity> {

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
		return SourceSatfacGenreScoreEntity.getEntityAuxiliary()
			.asSatfacScoreId("t.`satfac_soure_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asSatfacGenreId("t.`satfac_genre_id`")
			.asScore("t.`soure`")
			.asDate("t.`date`")
			.setSqlBody(" satfac_genre_soure t where 1=1");
	}

}
