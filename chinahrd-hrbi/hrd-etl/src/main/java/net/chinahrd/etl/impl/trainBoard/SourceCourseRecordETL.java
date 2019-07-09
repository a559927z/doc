package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceCourseRecordEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceCourseRecordETL extends SimpleAbstractEtl<SourceCourseRecordEntity> {

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
		return SourceCourseRecordEntity.getEntityAuxiliary()
			.asCourseRecordId("t.`course_record_id`")
			.asCustomerId("t.`customer_id`")
			.asCourseId("t.`course_id`")
			.asStartDate("t.`start_date`")
			.asEndDate("t.`end_date`")
			.asStatus("t.`status`")
			.asTrainUnit("t.`train_unit`")
			.asTrainPlanId("t.`train_plan_id`")
			.asYear("t.`year`")
			.setSqlBody(" course_record t where 1=1");
	}

}
