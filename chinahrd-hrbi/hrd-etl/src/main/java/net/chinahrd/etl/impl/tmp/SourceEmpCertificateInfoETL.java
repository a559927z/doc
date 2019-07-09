package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpCertificateInfoEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpCertificateInfoETL extends SimpleAbstractEtl<SourceEmpCertificateInfoEntity> {

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
		return SourceEmpCertificateInfoEntity.getEntityAuxiliary()
			.asEmpCertificateInfoId("t.`emp_certificate_info_id`")
			.asEmpId("t.`emp_id`")
			.asCustomerId("t.`customer_id`")
			.asCertificateId("t.`certificate_id`")
			.asObtainDate("t.`obtain_date`")
			.setSqlBody(" emp_certificate_info t where 1=1");
	}

}
