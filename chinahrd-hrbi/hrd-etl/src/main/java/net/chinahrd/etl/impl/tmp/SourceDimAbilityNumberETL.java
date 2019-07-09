package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimAbilityNumberEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimAbilityNumberETL extends SimpleAbstractEtl<SourceDimAbilityNumberEntity> {

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
		return SourceDimAbilityNumberEntity.getEntityAuxiliary()
			.asAbilityNumberId("t.`ability_number_id`")
			.asAbilityNumberKey("t.`ability_number_key`")
			.asAbilityNumberName("t.`ability_number_name`")
			.asCustomerId("t.`customer_id`")
			.asShowIndex("t.`show_index`")
			.asCreateDate("t.`create_date`")
			.setSqlBody(" dim_ability_number t where 1=1");
	}

}
