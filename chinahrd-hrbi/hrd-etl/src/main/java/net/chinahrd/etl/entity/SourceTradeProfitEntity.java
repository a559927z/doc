package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_trade_profit")
public class SourceTradeProfitEntity implements Entity{

	private String _trade_profit_id;
	private String _customer_id;
	private String _organization_id;
	private Double _sales_amount;
	private Double _expend_amount;
	private Double _gain_amount;
	private Double _target_value;
	private int _year_month;

	public SourceTradeProfitEntity(){
		super();
	}

	public SourceTradeProfitEntity(String _trade_profit_id,String _customer_id,String _organization_id,Double _sales_amount,Double _expend_amount,Double _gain_amount,Double _target_value,int _year_month){
		this._trade_profit_id = _trade_profit_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._sales_amount = _sales_amount;
		this._expend_amount = _expend_amount;
		this._gain_amount = _gain_amount;
		this._target_value = _target_value;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceTradeProfitEntity ["+
			"	trade_profit_id="+_trade_profit_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	sales_amount="+_sales_amount+
			"	expend_amount="+_expend_amount+
			"	gain_amount="+_gain_amount+
			"	target_value="+_target_value+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "trade_profit_id",type=ColumnType.CHAR)
	public String getTradeProfitId(){
		return this._trade_profit_id; 
	}

	public void setTradeProfitId(String _trade_profit_id){
		this._trade_profit_id = _trade_profit_id;
	}

	@Column(name = "customer_id",type=ColumnType.CHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "sales_amount",type=ColumnType.DECIMAL)
	public Double getSalesAmount(){
		return this._sales_amount; 
	}

	public void setSalesAmount(Double _sales_amount){
		this._sales_amount = _sales_amount;
	}

	@Column(name = "expend_amount",type=ColumnType.DOUBLE)
	public Double getExpendAmount(){
		return this._expend_amount; 
	}

	public void setExpendAmount(Double _expend_amount){
		this._expend_amount = _expend_amount;
	}

	@Column(name = "gain_amount",type=ColumnType.DECIMAL)
	public Double getGainAmount(){
		return this._gain_amount; 
	}

	public void setGainAmount(Double _gain_amount){
		this._gain_amount = _gain_amount;
	}

	@Column(name = "target_value",type=ColumnType.DOUBLE)
	public Double getTargetValue(){
		return this._target_value; 
	}

	public void setTargetValue(Double _target_value){
		this._target_value = _target_value;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceTradeProfitEntityAuxiliary  getEntityAuxiliary(){
		return new SourceTradeProfitEntityAuxiliary();
	}

	public static class SourceTradeProfitEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceTradeProfitEntityAuxiliary asTradeProfitId(String colName, CustomColumn<?, ?>... cols){
			setColName("trade_profit_id","`trade_profit_id`", colName, "setTradeProfitId", "getString", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asSalesAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_amount","`sales_amount`", colName, "setSalesAmount", "getDouble", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asExpendAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("expend_amount","`expend_amount`", colName, "setExpendAmount", "getDouble", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asGainAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("gain_amount","`gain_amount`", colName, "setGainAmount", "getDouble", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asTargetValue(String colName, CustomColumn<?, ?>... cols){
			setColName("target_value","`target_value`", colName, "setTargetValue", "getDouble", cols);
			return this;
		}
		public SourceTradeProfitEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
