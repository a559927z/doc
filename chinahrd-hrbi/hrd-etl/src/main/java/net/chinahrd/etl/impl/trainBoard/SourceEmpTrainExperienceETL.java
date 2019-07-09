package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpTrainExperienceEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpTrainExperienceETL extends SimpleAbstractEtl<SourceEmpTrainExperienceEntity> {

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
		return SourceEmpTrainExperienceEntity.getEntityAuxiliary()
			.asTrainExperienceId("t.`train_experience_id`")
			.asCourseRecordId("t.`course_record_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asCourseName("t.`course_name`")
			.asStartDate("t.`start_date`")
			.asFinishDate("t.`finish_date`")
			.asTrainTime("t.`train_time`")
			.asStatus("t.`status`")
			.asResult("t.`result`")
			.asTrainUnit("t.`train_unit`")
			.asGainCertificate("t.`gain_certificate`")
			.asLecturerName("t.`lecturer_name`")
			.asNote("t.`note`")
			.asYear("t.`year`")
			.asMark("t.`mark`")
			.setSqlBody(" emp_train_experience t where 1=1");
	}

}
