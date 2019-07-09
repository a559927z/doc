package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceLecturerCourseSpeakEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceLecturerCourseSpeakETL extends SimpleAbstractEtl<SourceLecturerCourseSpeakEntity> {

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
		return SourceLecturerCourseSpeakEntity.getEntityAuxiliary()
			.asLecturerCourseSpeakId("t.`lecturer_course_speak_id`")
			.asCustomerId("t.`customer_id`")
			.asLecturerId("t.`lecturer_id`")
			.asCourseId("t.`course_id`")
			.asStartDate("t.`start_date`")
			.asEndDate("t.`end_date`")
			.asYear("t.`year`")
			.setSqlBody(" lecturer_course_speak t where 1=1");
	}

}
