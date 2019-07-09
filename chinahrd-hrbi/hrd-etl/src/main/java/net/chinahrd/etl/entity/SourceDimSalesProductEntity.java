package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_sales_product")
public class SourceDimSalesProductEntity implements Entity{

	private String _product_id;
	private String _customer_id;
	private String _product_key;
	private String _product_name;
	private Double _product_cost;
	private Double _product_price;
	private String _product_supplier_id;
	private String _product_modul_id;
	private String _product_category_id;

	public SourceDimSalesProductEntity(){
		super();
	}

	public SourceDimSalesProductEntity(String _product_id,String _customer_id,String _product_key,String _product_name,Double _product_cost,Double _product_price,String _product_supplier_id,String _product_modul_id,String _product_category_id){
		this._product_id = _product_id;
		this._customer_id = _customer_id;
		this._product_key = _product_key;
		this._product_name = _product_name;
		this._product_cost = _product_cost;
		this._product_price = _product_price;
		this._product_supplier_id = _product_supplier_id;
		this._product_modul_id = _product_modul_id;
		this._product_category_id = _product_category_id;
	}

	@Override
	public String toString() {
		return "SourceDimSalesProductEntity ["+
			"	product_id="+_product_id+
			"	customer_id="+_customer_id+
			"	product_key="+_product_key+
			"	product_name="+_product_name+
			"	product_cost="+_product_cost+
			"	product_price="+_product_price+
			"	product_supplier_id="+_product_supplier_id+
			"	product_modul_id="+_product_modul_id+
			"	product_category_id="+_product_category_id+
			"]";
	}

	@Column(name = "product_id",type=ColumnType.VARCHAR)
	public String getProductId(){
		return this._product_id; 
	}

	public void setProductId(String _product_id){
		this._product_id = _product_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "product_key",type=ColumnType.VARCHAR)
	public String getProductKey(){
		return this._product_key; 
	}

	public void setProductKey(String _product_key){
		this._product_key = _product_key;
	}

	@Column(name = "product_name",type=ColumnType.VARCHAR)
	public String getProductName(){
		return this._product_name; 
	}

	public void setProductName(String _product_name){
		this._product_name = _product_name;
	}

	@Column(name = "product_cost",type=ColumnType.DOUBLE)
	public Double getProductCost(){
		return this._product_cost; 
	}

	public void setProductCost(Double _product_cost){
		this._product_cost = _product_cost;
	}

	@Column(name = "product_price",type=ColumnType.DOUBLE)
	public Double getProductPrice(){
		return this._product_price; 
	}

	public void setProductPrice(Double _product_price){
		this._product_price = _product_price;
	}

	@Column(name = "product_supplier_id",type=ColumnType.VARCHAR)
	public String getProductSupplierId(){
		return this._product_supplier_id; 
	}

	public void setProductSupplierId(String _product_supplier_id){
		this._product_supplier_id = _product_supplier_id;
	}

	@Column(name = "product_modul_id",type=ColumnType.VARCHAR)
	public String getProductModulId(){
		return this._product_modul_id; 
	}

	public void setProductModulId(String _product_modul_id){
		this._product_modul_id = _product_modul_id;
	}

	@Column(name = "product_category_id",type=ColumnType.VARCHAR)
	public String getProductCategoryId(){
		return this._product_category_id; 
	}

	public void setProductCategoryId(String _product_category_id){
		this._product_category_id = _product_category_id;
	}

	// 实例化内部类
	public static SourceDimSalesProductEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimSalesProductEntityAuxiliary();
	}

	public static class SourceDimSalesProductEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimSalesProductEntityAuxiliary asProductId(String colName, CustomColumn<?, ?>... cols){
			setColName("product_id","`product_id`", colName, "setProductId", "getString", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductKey(String colName, CustomColumn<?, ?>... cols){
			setColName("product_key","`product_key`", colName, "setProductKey", "getString", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductName(String colName, CustomColumn<?, ?>... cols){
			setColName("product_name","`product_name`", colName, "setProductName", "getString", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductCost(String colName, CustomColumn<?, ?>... cols){
			setColName("product_cost","`product_cost`", colName, "setProductCost", "getDouble", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductPrice(String colName, CustomColumn<?, ?>... cols){
			setColName("product_price","`product_price`", colName, "setProductPrice", "getDouble", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductSupplierId(String colName, CustomColumn<?, ?>... cols){
			setColName("product_supplier_id","`product_supplier_id`", colName, "setProductSupplierId", "getString", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductModulId(String colName, CustomColumn<?, ?>... cols){
			setColName("product_modul_id","`product_modul_id`", colName, "setProductModulId", "getString", cols);
			return this;
		}
		public SourceDimSalesProductEntityAuxiliary asProductCategoryId(String colName, CustomColumn<?, ?>... cols){
			setColName("product_category_id","`product_category_id`", colName, "setProductCategoryId", "getString", cols);
			return this;
		}
	}
}
