package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceTrainSatfacEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceTrainSatfacETL extends SimpleAbstractEtl<SourceTrainSatfacEntity> {

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
		return SourceTrainSatfacEntity.getEntityAuxiliary()
			.asTrainSatfacId("t.`train_satfac_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asSoure("t.`soure`")
			.asYear("t.`year`")
			.setSqlBody(" train_satfac t where 1=1");
	}

}
