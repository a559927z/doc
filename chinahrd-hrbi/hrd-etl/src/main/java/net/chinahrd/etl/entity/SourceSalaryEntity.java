package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_salary")
public class SourceSalaryEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _emp_id;
	private String _structure_id;
	private Double _salary_value;
	private int _year_month;
	private String _refresh;

	public SourceSalaryEntity(){
		super();
	}

	public SourceSalaryEntity(String _id,String _customer_id,String _emp_id,String _structure_id,Double _salary_value,int _year_month,String _refresh){
		this._id = _id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._structure_id = _structure_id;
		this._salary_value = _salary_value;
		this._year_month = _year_month;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceSalaryEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	structure_id="+_structure_id+
			"	salary_value="+_salary_value+
			"	year_month="+_year_month+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "id",type=ColumnType.VARCHAR)
	public String getId(){
		return this._id; 
	}

	public void setId(String _id){
		this._id = _id;
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

	@Column(name = "structure_id",type=ColumnType.VARCHAR)
	public String getStructureId(){
		return this._structure_id; 
	}

	public void setStructureId(String _structure_id){
		this._structure_id = _structure_id;
	}

	@Column(name = "salary_value",type=ColumnType.DOUBLE)
	public Double getSalaryValue(){
		return this._salary_value; 
	}

	public void setSalaryValue(Double _salary_value){
		this._salary_value = _salary_value;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceSalaryEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalaryEntityAuxiliary();
	}

	public static class SourceSalaryEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalaryEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceSalaryEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalaryEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceSalaryEntityAuxiliary asStructureId(String colName, CustomColumn<?, ?>... cols){
			setColName("structure_id","`structure_id`", colName, "setStructureId", "getString", cols);
			return this;
		}
		public SourceSalaryEntityAuxiliary asSalaryValue(String colName, CustomColumn<?, ?>... cols){
			setColName("salary_value","`salary_value`", colName, "setSalaryValue", "getDouble", cols);
			return this;
		}
		public SourceSalaryEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceSalaryEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
