package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimAbilityEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimAbilityETL extends SimpleAbstractEtl<SourceDimAbilityEntity> {

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
		return SourceDimAbilityEntity.getEntityAuxiliary()
			.asAbilityId("t.`ability_id`")
			.asCustomerId("t.`customer_id`")
			.asAbilityKey("t.`ability_key`")
			.asAbilityName("t.`ability_name`")
			.asCurtName("t.`curt_name`")
			.asType("t.`type`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_ability t where 1=1");
	}

}
