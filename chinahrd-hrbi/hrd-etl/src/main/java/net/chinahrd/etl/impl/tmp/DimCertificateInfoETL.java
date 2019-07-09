package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.DimCertificateInfoEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class DimCertificateInfoETL extends SimpleAbstractEtl<DimCertificateInfoEntity> {

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
		return DimCertificateInfoEntity.getEntityAuxiliary()
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
