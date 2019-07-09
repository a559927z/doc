package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_manpower_cost")
public class SourceManpowerCostEntity implements Entity{

	private String _manpower_cost_id;
	private String _customer_id;
	private String _organization_id;
	private Double _cost;
	private Double _cost_budget;
	private Double _cost_company;
	private int _year_month;

	public SourceManpowerCostEntity(){
		super();
	}

	public SourceManpowerCostEntity(String _manpower_cost_id,String _customer_id,String _organization_id,Double _cost,Double _cost_budget,Double _cost_company,int _year_month){
		this._manpower_cost_id = _manpower_cost_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._cost = _cost;
		this._cost_budget = _cost_budget;
		this._cost_company = _cost_company;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceManpowerCostEntity ["+
			"	manpower_cost_id="+_manpower_cost_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	cost="+_cost+
			"	cost_budget="+_cost_budget+
			"	cost_company="+_cost_company+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "manpower_cost_id",type=ColumnType.VARCHAR)
	public String getManpowerCostId(){
		return this._manpower_cost_id; 
	}

	public void setManpowerCostId(String _manpower_cost_id){
		this._manpower_cost_id = _manpower_cost_id;
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

	@Column(name = "cost",type=ColumnType.DOUBLE)
	public Double getCost(){
		return this._cost; 
	}

	public void setCost(Double _cost){
		this._cost = _cost;
	}

	@Column(name = "cost_budget",type=ColumnType.DOUBLE)
	public Double getCostBudget(){
		return this._cost_budget; 
	}

	public void setCostBudget(Double _cost_budget){
		this._cost_budget = _cost_budget;
	}

	@Column(name = "cost_company",type=ColumnType.DOUBLE)
	public Double getCostCompany(){
		return this._cost_company; 
	}

	public void setCostCompany(Double _cost_company){
		this._cost_company = _cost_company;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceManpowerCostEntityAuxiliary  getEntityAuxiliary(){
		return new SourceManpowerCostEntityAuxiliary();
	}

	public static class SourceManpowerCostEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceManpowerCostEntityAuxiliary asManpowerCostId(String colName, CustomColumn<?, ?>... cols){
			setColName("manpower_cost_id","`manpower_cost_id`", colName, "setManpowerCostId", "getString", cols);
			return this;
		}
		public SourceManpowerCostEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceManpowerCostEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceManpowerCostEntityAuxiliary asCost(String colName, CustomColumn<?, ?>... cols){
			setColName("cost","`cost`", colName, "setCost", "getDouble", cols);
			return this;
		}
		public SourceManpowerCostEntityAuxiliary asCostBudget(String colName, CustomColumn<?, ?>... cols){
			setColName("cost_budget","`cost_budget`", colName, "setCostBudget", "getDouble", cols);
			return this;
		}
		public SourceManpowerCostEntityAuxiliary asCostCompany(String colName, CustomColumn<?, ?>... cols){
			setColName("cost_company","`cost_company`", colName, "setCostCompany", "getDouble", cols);
			return this;
		}
		public SourceManpowerCostEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
