package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_pro_target")
public class SourceSalesProTargetEntity implements Entity{

	private String _product_id;
	private String _customer_id;
	private Double _sales_target;
	private Double _return_amount;
	private Double _payment;
	private int _year_month;

	public SourceSalesProTargetEntity(){
		super();
	}

	public SourceSalesProTargetEntity(String _product_id,String _customer_id,Double _sales_target,Double _return_amount,Double _payment,int _year_month){
		this._product_id = _product_id;
		this._customer_id = _customer_id;
		this._sales_target = _sales_target;
		this._return_amount = _return_amount;
		this._payment = _payment;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceSalesProTargetEntity ["+
			"	product_id="+_product_id+
			"	customer_id="+_customer_id+
			"	sales_target="+_sales_target+
			"	return_amount="+_return_amount+
			"	payment="+_payment+
			"	year_month="+_year_month+
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

	@Column(name = "sales_target",type=ColumnType.DOUBLE)
	public Double getSalesTarget(){
		return this._sales_target; 
	}

	public void setSalesTarget(Double _sales_target){
		this._sales_target = _sales_target;
	}

	@Column(name = "return_amount",type=ColumnType.DOUBLE)
	public Double getReturnAmount(){
		return this._return_amount; 
	}

	public void setReturnAmount(Double _return_amount){
		this._return_amount = _return_amount;
	}

	@Column(name = "payment",type=ColumnType.DOUBLE)
	public Double getPayment(){
		return this._payment; 
	}

	public void setPayment(Double _payment){
		this._payment = _payment;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceSalesProTargetEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesProTargetEntityAuxiliary();
	}

	public static class SourceSalesProTargetEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesProTargetEntityAuxiliary asProductId(String colName, CustomColumn<?, ?>... cols){
			setColName("product_id","`product_id`", colName, "setProductId", "getString", cols);
			return this;
		}
		public SourceSalesProTargetEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesProTargetEntityAuxiliary asSalesTarget(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_target","`sales_target`", colName, "setSalesTarget", "getDouble", cols);
			return this;
		}
		public SourceSalesProTargetEntityAuxiliary asReturnAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("return_amount","`return_amount`", colName, "setReturnAmount", "getDouble", cols);
			return this;
		}
		public SourceSalesProTargetEntityAuxiliary asPayment(String colName, CustomColumn<?, ?>... cols){
			setColName("payment","`payment`", colName, "setPayment", "getDouble", cols);
			return this;
		}
		public SourceSalesProTargetEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
