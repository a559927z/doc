package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceLecturerCourseDesignEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceLecturerCourseDesignETL extends SimpleAbstractEtl<SourceLecturerCourseDesignEntity> {

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
		return SourceLecturerCourseDesignEntity.getEntityAuxiliary()
			.asLecturerCourseDesignId("t.`lecturer_course_design_id`")
			.asCustomerId("t.`customer_id`")
			.asLecturerId("t.`lecturer_id`")
			.asCourseId("t.`course_id`")
			.setSqlBody(" lecturer_course_design t where 1=1");
	}

}
