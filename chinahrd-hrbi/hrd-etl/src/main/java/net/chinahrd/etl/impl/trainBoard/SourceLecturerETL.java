package net.chinahrd.etl.impl.trainBoard;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceLecturerEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceLecturerETL extends SimpleAbstractEtl<SourceLecturerEntity> {

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
		return SourceLecturerEntity.getEntityAuxiliary()
			.asLecturerId("t.`lecturer_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asLecturerName("t.`lecturer_name`")
			.asLevelId("t.`level_id`")
			.asType("t.`type`")
			.setSqlBody(" lecturer t where 1=1");
	}

}
