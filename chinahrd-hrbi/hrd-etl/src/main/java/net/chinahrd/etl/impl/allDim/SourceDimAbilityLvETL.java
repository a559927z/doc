package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimAbilityLvEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimAbilityLvETL extends SimpleAbstractEtl<SourceDimAbilityLvEntity> {


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
		return SourceDimAbilityLvEntity.getEntityAuxiliary()
			.asAbilityLvId("t.`ability_lv_id`")
			.asCustomerId("t.`customer_id`")
			.asAbilityLvKey("t.`ability_lv_key`")
			.asAbilityLvName("t.`ability_lv_name`")
			.asCurtName("t.`curt_name`")
			.asShowIndex("t.`show_index`")
			.setSqlBody(" dim_ability_lv t where 1=1");
	}

}
