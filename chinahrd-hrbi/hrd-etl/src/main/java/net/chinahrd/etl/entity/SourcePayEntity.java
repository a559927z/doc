package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_pay")
public class SourcePayEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _emp_id;
	private String _usre_name_ch;
	private String _organization_id;
	private String _full_path;
	private String _postion_id;
	private String _pay_contract;
	private String _pay_should;
	private String _pay_actual;
	private String _salary_actual;
	private String _welfare_actual;
	private Double _cr_value;
	private int _year_month;
	private String _refresh;
	private int _year;
	private String _bonus;

	public SourcePayEntity(){
		super();
	}

	public SourcePayEntity(String _id,String _customer_id,String _emp_id,String _usre_name_ch,String _organization_id,String _full_path,String _postion_id,String _pay_contract,String _pay_should,String _pay_actual,String _salary_actual,String _welfare_actual,Double _cr_value,int _year_month,String _refresh,int _year,String _bonus){
		this._id = _id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._usre_name_ch = _usre_name_ch;
		this._organization_id = _organization_id;
		this._full_path = _full_path;
		this._postion_id = _postion_id;
		this._pay_contract = _pay_contract;
		this._pay_should = _pay_should;
		this._pay_actual = _pay_actual;
		this._salary_actual = _salary_actual;
		this._welfare_actual = _welfare_actual;
		this._cr_value = _cr_value;
		this._year_month = _year_month;
		this._refresh = _refresh;
		this._year = _year;
		this._bonus = _bonus;
	}

	@Override
	public String toString() {
		return "SourcePayEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	usre_name_ch="+_usre_name_ch+
			"	organization_id="+_organization_id+
			"	full_path="+_full_path+
			"	postion_id="+_postion_id+
			"	pay_contract="+_pay_contract+
			"	pay_should="+_pay_should+
			"	pay_actual="+_pay_actual+
			"	salary_actual="+_salary_actual+
			"	welfare_actual="+_welfare_actual+
			"	cr_value="+_cr_value+
			"	year_month="+_year_month+
			"	refresh="+_refresh+
			"	year="+_year+
			"	bonus="+_bonus+
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

	@Column(name = "usre_name_ch",type=ColumnType.VARCHAR)
	public String getUsreNameCh(){
		return this._usre_name_ch; 
	}

	public void setUsreNameCh(String _usre_name_ch){
		this._usre_name_ch = _usre_name_ch;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "full_path",type=ColumnType.VARCHAR)
	public String getFullPath(){
		return this._full_path; 
	}

	public void setFullPath(String _full_path){
		this._full_path = _full_path;
	}

	@Column(name = "postion_id",type=ColumnType.VARCHAR)
	public String getPostionId(){
		return this._postion_id; 
	}

	public void setPostionId(String _postion_id){
		this._postion_id = _postion_id;
	}

	@Column(name = "pay_contract",type=ColumnType.VARCHAR)
	public String getPayContract(){
		return this._pay_contract; 
	}

	public void setPayContract(String _pay_contract){
		this._pay_contract = _pay_contract;
	}

	@Column(name = "pay_should",type=ColumnType.VARCHAR)
	public String getPayShould(){
		return this._pay_should; 
	}

	public void setPayShould(String _pay_should){
		this._pay_should = _pay_should;
	}

	@Column(name = "pay_actual",type=ColumnType.VARCHAR)
	public String getPayActual(){
		return this._pay_actual; 
	}

	public void setPayActual(String _pay_actual){
		this._pay_actual = _pay_actual;
	}

	@Column(name = "salary_actual",type=ColumnType.VARCHAR)
	public String getSalaryActual(){
		return this._salary_actual; 
	}

	public void setSalaryActual(String _salary_actual){
		this._salary_actual = _salary_actual;
	}

	@Column(name = "welfare_actual",type=ColumnType.VARCHAR)
	public String getWelfareActual(){
		return this._welfare_actual; 
	}

	public void setWelfareActual(String _welfare_actual){
		this._welfare_actual = _welfare_actual;
	}

	@Column(name = "cr_value",type=ColumnType.DOUBLE)
	public Double getCrValue(){
		return this._cr_value; 
	}

	public void setCrValue(Double _cr_value){
		this._cr_value = _cr_value;
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

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "bonus",type=ColumnType.VARCHAR)
	public String getBonus(){
		return this._bonus; 
	}

	public void setBonus(String _bonus){
		this._bonus = _bonus;
	}

	// 实例化内部类
	public static SourcePayEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePayEntityAuxiliary();
	}

	public static class SourcePayEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePayEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asUsreNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("usre_name_ch","`usre_name_ch`", colName, "setUsreNameCh", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asPostionId(String colName, CustomColumn<?, ?>... cols){
			setColName("postion_id","`postion_id`", colName, "setPostionId", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asPayContract(String colName, CustomColumn<?, ?>... cols){
			setColName("pay_contract","`pay_contract`", colName, "setPayContract", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asPayShould(String colName, CustomColumn<?, ?>... cols){
			setColName("pay_should","`pay_should`", colName, "setPayShould", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asPayActual(String colName, CustomColumn<?, ?>... cols){
			setColName("pay_actual","`pay_actual`", colName, "setPayActual", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asSalaryActual(String colName, CustomColumn<?, ?>... cols){
			setColName("salary_actual","`salary_actual`", colName, "setSalaryActual", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asWelfareActual(String colName, CustomColumn<?, ?>... cols){
			setColName("welfare_actual","`welfare_actual`", colName, "setWelfareActual", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asCrValue(String colName, CustomColumn<?, ?>... cols){
			setColName("cr_value","`cr_value`", colName, "setCrValue", "getDouble", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourcePayEntityAuxiliary asBonus(String colName, CustomColumn<?, ?>... cols){
			setColName("bonus","`bonus`", colName, "setBonus", "getString", cols);
			return this;
		}
	}
}
