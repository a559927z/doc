package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimCourseEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimCourseETL extends SimpleAbstractEtl<SourceDimCourseEntity>{


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
		return SourceDimCourseEntity.getEntityAuxiliary()
			.asCourseId("t.`course_id`")
			.asCustomerId("t.`customer_id`")
			.asCourseKey("t.`course_key`")
			.asCourseName("t.`course_name`")
			.asCourseTypeId("t.`course_type_id`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_course t where 1=1");
	}

}
