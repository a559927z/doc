package net.chinahrd.etl.impl.positionCompetency;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePositionQualityEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePositionQualityETL extends SimpleAbstractEtl<SourcePositionQualityEntity> {

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
		return SourcePositionQualityEntity.getEntityAuxiliary()
			.asPositionQualityId("t.`position_quality_id`")
			.asCustomerId("t.`customer_id`")
			.asPositionId("t.`position_id`")
			.asQualityId("t.`quality_id`")
			.asMatchingScoreId("t.`matching_soure_id`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" position_quality t where 1=1");
	}
	
	public static void main(String...strings){
		new SourcePositionQualityETL().execute();

	}
}
