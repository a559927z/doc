package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePromotionElementSchemeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePromotionElementSchemeETL extends SimpleAbstractEtl<SourcePromotionElementSchemeEntity> {

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
		return SourcePromotionElementSchemeEntity.getEntityAuxiliary()
			.asCustomerId("t.`customer_id`")
			.asSchemeId("t.`scheme_id`")
			.asSchemeName("t.`scheme_name`")
			.asCompanyAge("t.`company_age`")
			.asCurtNamePer("t.`curt_name_per`")
			.asCurtNameEval("t.`curt_name_eval`")
			.asCertificateId("t.`certificate_id`")
			.asCertificateTypeId("t.`certificate_type_id`")
			.asCreateUserId("t.`create_user_id`")
			.asModifyUserId("t.`modify_user_id`")
			.asCreateTime("t.`create_time`")
			.asModifyTime("t.`modify_time`")
			.asStartDate("t.`start_date`")
			.asInvalidDate("t.`invalid_date`")
			.setSqlBody(" promotion_element_scheme t where 1=1");
	}

}
