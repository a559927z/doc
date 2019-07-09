package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceMatchingSchoolEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceMatchingSchoolETL extends SimpleAbstractEtl<SourceMatchingSchoolEntity> {


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
		return SourceMatchingSchoolEntity.getEntityAuxiliary()
			.asMatchingSchoolId("t.`matching_school_id`")
			.asCustomerId("t.`customer_id`")
			.asName("t.`name`")
			.asSchoolType("t.`school_type`")
			.asLevel("t.`level`")
			.setSqlBody(" matching_school t where 1=1");
	}

}
