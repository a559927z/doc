package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_manpower_cost_value")
public class SourceManpowerCostValueEntity implements Entity{

	private String _manpower_cost_value_id;
	private String _customer_id;
	private String _organization_id;
	private Double _budget_value;
	private int _year;

	public SourceManpowerCostValueEntity(){
		super();
	}

	public SourceManpowerCostValueEntity(String _manpower_cost_value_id,String _customer_id,String _organization_id,Double _budget_value,int _year){
		this._manpower_cost_value_id = _manpower_cost_value_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._budget_value = _budget_value;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceManpowerCostValueEntity ["+
			"	manpower_cost_value_id="+_manpower_cost_value_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	budget_value="+_budget_value+
			"	year="+_year+
			"]";
	}

	@Column(name = "manpower_cost_value_id",type=ColumnType.VARCHAR)
	public String getManpowerCostValueId(){
		return this._manpower_cost_value_id; 
	}

	public void setManpowerCostValueId(String _manpower_cost_value_id){
		this._manpower_cost_value_id = _manpower_cost_value_id;
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

	@Column(name = "budget_value",type=ColumnType.DOUBLE)
	public Double getBudgetValue(){
		return this._budget_value; 
	}

	public void setBudgetValue(Double _budget_value){
		this._budget_value = _budget_value;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceManpowerCostValueEntityAuxiliary  getEntityAuxiliary(){
		return new SourceManpowerCostValueEntityAuxiliary();
	}

	public static class SourceManpowerCostValueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceManpowerCostValueEntityAuxiliary asManpowerCostValueId(String colName, CustomColumn<?, ?>... cols){
			setColName("manpower_cost_value_id","`manpower_cost_value_id`", colName, "setManpowerCostValueId", "getString", cols);
			return this;
		}
		public SourceManpowerCostValueEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceManpowerCostValueEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceManpowerCostValueEntityAuxiliary asBudgetValue(String colName, CustomColumn<?, ?>... cols){
			setColName("budget_value","`budget_value`", colName, "setBudgetValue", "getDouble", cols);
			return this;
		}
		public SourceManpowerCostValueEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
