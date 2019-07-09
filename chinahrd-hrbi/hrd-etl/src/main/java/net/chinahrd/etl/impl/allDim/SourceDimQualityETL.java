package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimQualityEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimQualityETL extends SimpleAbstractEtl<SourceDimQualityEntity> {


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
		return SourceDimQualityEntity.getEntityAuxiliary()
			.asQualityId("t.`quality_id`")
			.asCustomerId("t.`customer_id`")
			.asVocabulary("t.`vocabulary`")
			.asNote("t.`note`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_quality t where 1=1");
	}

}
