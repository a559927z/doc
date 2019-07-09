package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimKeyTalentTypeEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimKeyTalentTypeETL extends SimpleAbstractEtl<SourceDimKeyTalentTypeEntity> {

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
		return SourceDimKeyTalentTypeEntity.getEntityAuxiliary()
			.asKeyTalentTypeId("t.`key_talent_type_id`")
			.asCustomerId("t.`customer_id`")
			.asKeyTalentTypeKey("t.`key_talent_type_key`")
			.asKeyTalentTypeName("t.`key_talent_type_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_key_talent_type t where 1=1");
	}

}
