package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.TrainPlanEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class TrainPlanETL extends SimpleAbstractEtl<TrainPlanEntity> {

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
		return TrainPlanEntity.getEntityAuxiliary()
			.asTrainPlanId("t.`train_plan_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
			.asTrainName("t.`train_name`")
			.asTrainNum("t.`train_num`")
			.asDate("t.`date`")
			.asStartDate("t.`start_date`")
			.asEndDate("t.`end_date`")
			.asYear("t.`year`")
			.asStatus("t.`status`")
			.setSqlBody(" train_plan t where 1=1");
	}

}
