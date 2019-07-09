package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimCourseTypeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimCourseTypeETL extends SimpleAbstractEtl<SourceDimCourseTypeEntity> {


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
		return SourceDimCourseTypeEntity.getEntityAuxiliary()
			.asCourseTypeId("t.`course_type_id`")
			.asCustomerId("t.`customer_id`")
			.asCourseTypeKey("t.`course_type_key`")
			.asCourseTypeName("t.`course_type_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_course_type t where 1=1");
	}

}
