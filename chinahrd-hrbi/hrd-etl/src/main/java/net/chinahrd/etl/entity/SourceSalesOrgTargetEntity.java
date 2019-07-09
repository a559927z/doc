package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_org_target")
public class SourceSalesOrgTargetEntity implements Entity{

	private String _organization_id;
	private String _customer_id;
	private Double _sales_target;
	private int _sales_number;
	private Double _sales_profit;
	private Double _return_amount;
	private Double _payment;
	private int _year_month;

	public SourceSalesOrgTargetEntity(){
		super();
	}

	public SourceSalesOrgTargetEntity(String _organization_id,String _customer_id,Double _sales_target,int _sales_number,Double _sales_profit,Double _return_amount,Double _payment,int _year_month){
		this._organization_id = _organization_id;
		this._customer_id = _customer_id;
		this._sales_target = _sales_target;
		this._sales_number = _sales_number;
		this._sales_profit = _sales_profit;
		this._return_amount = _return_amount;
		this._payment = _payment;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceSalesOrgTargetEntity ["+
			"	organization_id="+_organization_id+
			"	customer_id="+_customer_id+
			"	sales_target="+_sales_target+
			"	sales_number="+_sales_number+
			"	sales_profit="+_sales_profit+
			"	return_amount="+_return_amount+
			"	payment="+_payment+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
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

	@Column(name = "sales_number",type=ColumnType.INT)
	public int getSalesNumber(){
		return this._sales_number; 
	}

	public void setSalesNumber(int _sales_number){
		this._sales_number = _sales_number;
	}

	@Column(name = "sales_profit",type=ColumnType.DOUBLE)
	public Double getSalesProfit(){
		return this._sales_profit; 
	}

	public void setSalesProfit(Double _sales_profit){
		this._sales_profit = _sales_profit;
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
	public static SourceSalesOrgTargetEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesOrgTargetEntityAuxiliary();
	}

	public static class SourceSalesOrgTargetEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesOrgTargetEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asSalesTarget(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_target","`sales_target`", colName, "setSalesTarget", "getDouble", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asSalesNumber(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_number","`sales_number`", colName, "setSalesNumber", "getInt", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asSalesProfit(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_profit","`sales_profit`", colName, "setSalesProfit", "getDouble", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asReturnAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("return_amount","`return_amount`", colName, "setReturnAmount", "getDouble", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asPayment(String colName, CustomColumn<?, ?>... cols){
			setColName("payment","`payment`", colName, "setPayment", "getDouble", cols);
			return this;
		}
		public SourceSalesOrgTargetEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
