package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpContactPersonEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpContactPersonETL extends SimpleAbstractEtl<SourceEmpContactPersonEntity> {

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
		return SourceEmpContactPersonEntity.getEntityAuxiliary()
			.asEmpContactPersonId("t.`emp_contact_person_id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asEmpContactPersonName("t.`emp_contact_person_name`")
			.asTelPhone("t.`tel_phone`")
			.asCall("t.`call`")
			.asIsUrgent("t.`is_urgent`")
			.asCreateTime("t.`create_time`")
			.setSqlBody(" emp_contact_person t where 1=1");
	}

}
