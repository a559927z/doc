package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_detail")
public class SourceSalesDetailEntity implements Entity{

	private String _sales_detail_id;
	private String _customer_id;
	private String _emp_id;
	private String _product_id;
	private int _product_number;
	private Double _sales_money;
	private Double _sales_profit;
	private String _sales_province_id;
	private String _sales_city_id;
	private String _sales_date;

	public SourceSalesDetailEntity(){
		super();
	}

	public SourceSalesDetailEntity(String _sales_detail_id,String _customer_id,String _emp_id,String _product_id,int _product_number,Double _sales_money,Double _sales_profit,String _sales_province_id,String _sales_city_id,String _sales_date){
		this._sales_detail_id = _sales_detail_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._product_id = _product_id;
		this._product_number = _product_number;
		this._sales_money = _sales_money;
		this._sales_profit = _sales_profit;
		this._sales_province_id = _sales_province_id;
		this._sales_city_id = _sales_city_id;
		this._sales_date = _sales_date;
	}

	@Override
	public String toString() {
		return "SourceSalesDetailEntity ["+
			"	sales_detail_id="+_sales_detail_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	product_id="+_product_id+
			"	product_number="+_product_number+
			"	sales_money="+_sales_money+
			"	sales_profit="+_sales_profit+
			"	sales_province_id="+_sales_province_id+
			"	sales_city_id="+_sales_city_id+
			"	sales_date="+_sales_date+
			"]";
	}

	@Column(name = "sales_detail_id",type=ColumnType.VARCHAR)
	public String getSalesDetailId(){
		return this._sales_detail_id; 
	}

	public void setSalesDetailId(String _sales_detail_id){
		this._sales_detail_id = _sales_detail_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "product_id",type=ColumnType.VARCHAR)
	public String getProductId(){
		return this._product_id; 
	}

	public void setProductId(String _product_id){
		this._product_id = _product_id;
	}

	@Column(name = "product_number",type=ColumnType.INT)
	public int getProductNumber(){
		return this._product_number; 
	}

	public void setProductNumber(int _product_number){
		this._product_number = _product_number;
	}

	@Column(name = "sales_money",type=ColumnType.DOUBLE)
	public Double getSalesMoney(){
		return this._sales_money; 
	}

	public void setSalesMoney(Double _sales_money){
		this._sales_money = _sales_money;
	}

	@Column(name = "sales_profit",type=ColumnType.DOUBLE)
	public Double getSalesProfit(){
		return this._sales_profit; 
	}

	public void setSalesProfit(Double _sales_profit){
		this._sales_profit = _sales_profit;
	}

	@Column(name = "sales_province_id",type=ColumnType.VARCHAR)
	public String getSalesProvinceId(){
		return this._sales_province_id; 
	}

	public void setSalesProvinceId(String _sales_province_id){
		this._sales_province_id = _sales_province_id;
	}

	@Column(name = "sales_city_id",type=ColumnType.VARCHAR)
	public String getSalesCityId(){
		return this._sales_city_id; 
	}

	public void setSalesCityId(String _sales_city_id){
		this._sales_city_id = _sales_city_id;
	}

	@Column(name = "sales_date",type=ColumnType.DATE)
	public String getSalesDate(){
		return this._sales_date; 
	}

	public void setSalesDate(String _sales_date){
		this._sales_date = _sales_date;
	}

	// 实例化内部类
	public static SourceSalesDetailEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesDetailEntityAuxiliary();
	}

	public static class SourceSalesDetailEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesDetailEntityAuxiliary asSalesDetailId(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_detail_id","`sales_detail_id`", colName, "setSalesDetailId", "getString", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asProductId(String colName, CustomColumn<?, ?>... cols){
			setColName("product_id","`product_id`", colName, "setProductId", "getString", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asProductNumber(String colName, CustomColumn<?, ?>... cols){
			setColName("product_number","`product_number`", colName, "setProductNumber", "getInt", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asSalesMoney(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_money","`sales_money`", colName, "setSalesMoney", "getDouble", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asSalesProfit(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_profit","`sales_profit`", colName, "setSalesProfit", "getDouble", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asSalesProvinceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_province_id","`sales_province_id`", colName, "setSalesProvinceId", "getString", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asSalesCityId(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_city_id","`sales_city_id`", colName, "setSalesCityId", "getString", cols);
			return this;
		}
		public SourceSalesDetailEntityAuxiliary asSalesDate(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_date","`sales_date`", colName, "setSalesDate", "getString", cols);
			return this;
		}
	}
}
