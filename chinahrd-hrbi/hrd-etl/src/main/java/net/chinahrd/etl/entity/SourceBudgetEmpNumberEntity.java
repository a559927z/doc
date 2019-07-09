package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_budget_emp_number")
public class SourceBudgetEmpNumberEntity implements Entity{

	private String _budget_emp_number_id;
	private String _customer_id;
	private String _organization_id;
	private int _number;
	private int _year;

	public SourceBudgetEmpNumberEntity(){
		super();
	}

	public SourceBudgetEmpNumberEntity(String _budget_emp_number_id,String _customer_id,String _organization_id,int _number,int _year){
		this._budget_emp_number_id = _budget_emp_number_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._number = _number;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceBudgetEmpNumberEntity ["+
			"	budget_emp_number_id="+_budget_emp_number_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	number="+_number+
			"	year="+_year+
			"]";
	}

	@Column(name = "budget_emp_number_id",type=ColumnType.VARCHAR)
	public String getBudgetEmpNumberId(){
		return this._budget_emp_number_id; 
	}

	public void setBudgetEmpNumberId(String _budget_emp_number_id){
		this._budget_emp_number_id = _budget_emp_number_id;
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

	@Column(name = "number",type=ColumnType.INT)
	public int getNumber(){
		return this._number; 
	}

	public void setNumber(int _number){
		this._number = _number;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceBudgetEmpNumberEntityAuxiliary  getEntityAuxiliary(){
		return new SourceBudgetEmpNumberEntityAuxiliary();
	}

	public static class SourceBudgetEmpNumberEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceBudgetEmpNumberEntityAuxiliary asBudgetEmpNumberId(String colName, CustomColumn<?, ?>... cols){
			setColName("budget_emp_number_id","`budget_emp_number_id`", colName, "setBudgetEmpNumberId", "getString", cols);
			return this;
		}
		public SourceBudgetEmpNumberEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceBudgetEmpNumberEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceBudgetEmpNumberEntityAuxiliary asNumber(String colName, CustomColumn<?, ?>... cols){
			setColName("number","`number`", colName, "setNumber", "getInt", cols);
			return this;
		}
		public SourceBudgetEmpNumberEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
