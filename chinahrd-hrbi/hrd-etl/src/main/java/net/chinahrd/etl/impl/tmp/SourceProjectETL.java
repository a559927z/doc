package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceProjectEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceProjectETL extends SimpleAbstractEtl<SourceProjectEntity> {

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
		return SourceProjectEntity.getEntityAuxiliary()
			.asProjectId("t.`project_id`")
			.asCustomerId("t.`customer_id`")
			.asProjectKey("t.`project_key`")
			.asProjectName("t.`project_name`")
			.asEmpId("t.`emp_id`")
			.asOrganizationId("t.`organization_id`")
			.asProjectTypeId("t.`project_type_id`")
			.asProjectProgressId("t.`project_progress_id`")
			.asProjectParentId("t.`project_parent_id`")
			.asFullPath("t.`full_path`")
			.asHasChilren("t.`has_chilren`")
			.asStartDate("t.`start_date`")
			.asEndDate("t.`end_date`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" project t where 1=1");
	}

}
