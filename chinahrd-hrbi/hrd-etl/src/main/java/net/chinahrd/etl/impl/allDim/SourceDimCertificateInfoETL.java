package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimCertificateInfoEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimCertificateInfoETL extends SimpleAbstractEtl<SourceDimCertificateInfoEntity>{


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
		return SourceDimCertificateInfoEntity.getEntityAuxiliary()
			.asCertificateInfoId("t.`certificate_info_id`")
			.asCertificateName("t.`certificate_name`")
			.asCustomerId("t.`customer_id`")
			.asCertificateTypeId("t.`certificate_type_id`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" dim_certificate_info t where 1=1");
	}


}
