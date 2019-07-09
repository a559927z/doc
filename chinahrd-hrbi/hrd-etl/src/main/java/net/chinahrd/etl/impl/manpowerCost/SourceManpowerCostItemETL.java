package net.chinahrd.etl.impl.manpowerCost;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceManpowerCostItemEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceManpowerCostItemETL extends SimpleAbstractEtl<SourceManpowerCostItemEntity> {

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
		return SourceManpowerCostItemEntity.getEntityAuxiliary()
			.asManpowerCostItemId("t.`manpower_cost_item_id`")
			.asCustomerId("t.`customer_id`")
			.asOrganizationId("t.`organization_id`")
		    .asItemCost("t.`item_cost`")
		    .asItemId("t.`item_id`")
		    .asItemName("t.`item_name`")
		    .asShowIndex("t.`show_index`")
			.asYearMonth("t.`year_month`")
			.setSqlBody(" manpower_cost_item t where 1=1");
	}
	
	public static void main(String...strings){
		new SourceManpowerCostItemETL().execute();
	}
}
