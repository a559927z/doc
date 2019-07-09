package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimSalesProductEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimSalesProductETL extends SimpleAbstractEtl<SourceDimSalesProductEntity> {


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
		return SourceDimSalesProductEntity.getEntityAuxiliary()
			.asProductId("t.`product_id`")
			.asCustomerId("t.`customer_id`")
			.asProductKey("t.`product_key`")
			.asProductName("t.`product_name`")
			.asProductCost("t.`product_cost`")
			.asProductPrice("t.`product_price`")
			.asProductSupplierId("t.`product_supplier_id`")
			.asProductModulId("t.`product_modul_id`")
			.asProductCategoryId("t.`product_category_id`")
			.setSqlBody(" dim_sales_product t where 1=1");
	}

}
