package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_fact_fte")
public class SourceFactFteEntity implements Entity{

	private String _fte_id;
	private String _customer_id;
	private String _organization_id;
	private String _organization_name;
	private Double _fulltime_sum;
	private Double _passtime_sum;
	private Double _overtime_sum;
	private Double _fte_value;
	private int _year_month;
	private Double _gain_amount;
	private Double _sales_amount;
	private Double _target_value;
	private Double _benefit_value;
	private Double _range_per;

	public SourceFactFteEntity(){
		super();
	}

	public SourceFactFteEntity(String _fte_id,String _customer_id,String _organization_id,String _organization_name,Double _fulltime_sum,Double _passtime_sum,Double _overtime_sum,Double _fte_value,int _year_month,Double _gain_amount,Double _sales_amount,Double _target_value,Double _benefit_value,Double _range_per){
		this._fte_id = _fte_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._fulltime_sum = _fulltime_sum;
		this._passtime_sum = _passtime_sum;
		this._overtime_sum = _overtime_sum;
		this._fte_value = _fte_value;
		this._year_month = _year_month;
		this._gain_amount = _gain_amount;
		this._sales_amount = _sales_amount;
		this._target_value = _target_value;
		this._benefit_value = _benefit_value;
		this._range_per = _range_per;
	}

	@Override
	public String toString() {
		return "SourceFactFteEntity ["+
			"	fte_id="+_fte_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	fulltime_sum="+_fulltime_sum+
			"	passtime_sum="+_passtime_sum+
			"	overtime_sum="+_overtime_sum+
			"	fte_value="+_fte_value+
			"	year_month="+_year_month+
			"	gain_amount="+_gain_amount+
			"	sales_amount="+_sales_amount+
			"	target_value="+_target_value+
			"	benefit_value="+_benefit_value+
			"	range_per="+_range_per+
			"]";
	}

	@Column(name = "fte_id",type=ColumnType.VARCHAR)
	public String getFteId(){
		return this._fte_id; 
	}

	public void setFteId(String _fte_id){
		this._fte_id = _fte_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
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

	@Column(name = "organization_name",type=ColumnType.VARCHAR)
	public String getOrganizationName(){
		return this._organization_name; 
	}

	public void setOrganizationName(String _organization_name){
		this._organization_name = _organization_name;
	}

	@Column(name = "fulltime_sum",type=ColumnType.DOUBLE)
	public Double getFulltimeSum(){
		return this._fulltime_sum; 
	}

	public void setFulltimeSum(Double _fulltime_sum){
		this._fulltime_sum = _fulltime_sum;
	}

	@Column(name = "passtime_sum",type=ColumnType.DOUBLE)
	public Double getPasstimeSum(){
		return this._passtime_sum; 
	}

	public void setPasstimeSum(Double _passtime_sum){
		this._passtime_sum = _passtime_sum;
	}

	@Column(name = "overtime_sum",type=ColumnType.DOUBLE)
	public Double getOvertimeSum(){
		return this._overtime_sum; 
	}

	public void setOvertimeSum(Double _overtime_sum){
		this._overtime_sum = _overtime_sum;
	}

	@Column(name = "fte_value",type=ColumnType.DOUBLE)
	public Double getFteValue(){
		return this._fte_value; 
	}

	public void setFteValue(Double _fte_value){
		this._fte_value = _fte_value;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	@Column(name = "gain_amount",type=ColumnType.DECIMAL)
	public Double getGainAmount(){
		return this._gain_amount; 
	}

	public void setGainAmount(Double _gain_amount){
		this._gain_amount = _gain_amount;
	}

	@Column(name = "sales_amount",type=ColumnType.DECIMAL)
	public Double getSalesAmount(){
		return this._sales_amount; 
	}

	public void setSalesAmount(Double _sales_amount){
		this._sales_amount = _sales_amount;
	}

	@Column(name = "target_value",type=ColumnType.DOUBLE)
	public Double getTargetValue(){
		return this._target_value; 
	}

	public void setTargetValue(Double _target_value){
		this._target_value = _target_value;
	}

	@Column(name = "benefit_value",type=ColumnType.DOUBLE)
	public Double getBenefitValue(){
		return this._benefit_value; 
	}

	public void setBenefitValue(Double _benefit_value){
		this._benefit_value = _benefit_value;
	}

	@Column(name = "range_per",type=ColumnType.DOUBLE)
	public Double getRangePer(){
		return this._range_per; 
	}

	public void setRangePer(Double _range_per){
		this._range_per = _range_per;
	}

	// 实例化内部类
	public static SourceFactFteEntityAuxiliary  getEntityAuxiliary(){
		return new SourceFactFteEntityAuxiliary();
	}

	public static class SourceFactFteEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceFactFteEntityAuxiliary asFteId(String colName, CustomColumn<?, ?>... cols){
			setColName("fte_id","`fte_id`", colName, "setFteId", "getString", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asFulltimeSum(String colName, CustomColumn<?, ?>... cols){
			setColName("fulltime_sum","`fulltime_sum`", colName, "setFulltimeSum", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asPasstimeSum(String colName, CustomColumn<?, ?>... cols){
			setColName("passtime_sum","`passtime_sum`", colName, "setPasstimeSum", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asOvertimeSum(String colName, CustomColumn<?, ?>... cols){
			setColName("overtime_sum","`overtime_sum`", colName, "setOvertimeSum", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asFteValue(String colName, CustomColumn<?, ?>... cols){
			setColName("fte_value","`fte_value`", colName, "setFteValue", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asGainAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("gain_amount","`gain_amount`", colName, "setGainAmount", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asSalesAmount(String colName, CustomColumn<?, ?>... cols){
			setColName("sales_amount","`sales_amount`", colName, "setSalesAmount", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asTargetValue(String colName, CustomColumn<?, ?>... cols){
			setColName("target_value","`target_value`", colName, "setTargetValue", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asBenefitValue(String colName, CustomColumn<?, ?>... cols){
			setColName("benefit_value","`benefit_value`", colName, "setBenefitValue", "getDouble", cols);
			return this;
		}
		public SourceFactFteEntityAuxiliary asRangePer(String colName, CustomColumn<?, ?>... cols){
			setColName("range_per","`range_per`", colName, "setRangePer", "getDouble", cols);
			return this;
		}
	}
}
