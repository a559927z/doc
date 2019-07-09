package net.chinahrd.etl.impl.allBase;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceEmpCertificateInfoEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceEmpCertificateInfoETL extends SimpleAbstractEtl<SourceEmpCertificateInfoEntity> {

	public static String URL = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String USER = "mup";
	public static String PASSWORD = "1q2w3e123";

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
			.asEmpCertificateInfoId("t.emp_certificate_info_id")
			.asEmpId("t.emp_id")
			.asCustomerId("t.customer_id")
			.asCertificateId("t.certificate_id")
			.asObtainDate("t.obtain_date")
			.setSqlBody(" emp_certificate_info t where 1=1");
	}
}
