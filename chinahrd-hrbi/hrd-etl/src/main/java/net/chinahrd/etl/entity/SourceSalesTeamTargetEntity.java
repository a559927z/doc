package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_team_target")
public class SourceSalesTeamTargetEntity implements Entity{

	private String _team_id;
	private String _customer_id;
	private int _sales_target;
	private int _payment;
	private int _return_amount;
	private int _year_month;

	public SourceSalesTeamTargetEntity(){
		super();
	}

	public SourceSalesTeamTargetEntity(String _team_id,String _customer_id,int _sales_target,int _payment,int _return_amount,int _year_month){
		this._team_id = _team_id;
		this._customer_id = _customer_id;
		this._sales_target = _sales_target;
		this._payment = _payment;
		this._return_amount = _return_amount;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceSalesTeamTargetEntity ["+
			"	team_id="+_team_id+
			"	customer_id="+_customer_id+
			"	sales_target="+_sales_target+
			"	payment="+_payment+
			"	return_amount="+_return_amount+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "team_id",type=ColumnType.VARCHAR)
	public String getTeamId(){
		return this._team_id; 
	}

	public void setTeamId(String _team_id){
		this._team_id = _team_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "sales_target",type=ColumnType.INT)
	public int getSalesTarget(){
		return this._sales_target; 
	}

	public void setSalesTarget(int _sales_target){
		this._sales_target = _sales_target;
	}

	@Column(name = "payment",type=ColumnType.INT)
	public int getPayment(){
		return this._payment; 
	}

	public void setPayment(int _payment){
		this._payment = _payment;
	}

	@Column(name = "return_amount",type=ColumnType.INT)
	public int getReturnAmount(){
		return this._return_amount; 
	}

	public void setReturnAmount(int _return_amount){
		this._return_amount = _return_amount;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceSalesTeamTargetEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesTeamTargetEntityAuxiliary();
	}

	public static class SourceSalesTeamTargetEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesTeamTargetEntityAuxiliary asTeamId(String colName, CustomColumn<?, ?>... cols){
			setColName("team_id","`team_id`", colName, "setTeamId", "getString", cols);
			return this;
		}
		public SourceSalesTeamTargetEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesTeamTargetEntityAuxiliary asSalesTarget(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_target","`sales_target`", colName, "setSalesTarget", "getInt", cols);
			return this;
		}
		public SourceSalesTeamTargetEntityAuxiliary asPayment(String colName, CustomColumn<?, ?>... cols){
			setColName("payment","`payment`", colName, "setPayment", "getInt", cols);
			return this;
		}
		public SourceSalesTeamTargetEntityAuxiliary asReturnAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("return_amount","`return_amount`", colName, "setReturnAmount", "getInt", cols);
			return this;
		}
		public SourceSalesTeamTargetEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
