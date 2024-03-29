package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_other_day")
public class SourceEmpOtherDayEntity implements Entity{

	private String _emp_other_day_id;
	private String _customer_id;
	private String _emp_key;
	private String _emp_id;
	private String _user_name_ch;
	private Double _hour_count;
	private String _organization_id;
	private String _checkwork_type_id;
	private String _days;
	private int _year_months;

	public SourceEmpOtherDayEntity(){
		super();
	}

	public SourceEmpOtherDayEntity(String _emp_other_day_id,String _customer_id,String _emp_key,String _emp_id,String _user_name_ch,Double _hour_count,String _organization_id,String _checkwork_type_id,String _days,int _year_months){
		this._emp_other_day_id = _emp_other_day_id;
		this._customer_id = _customer_id;
		this._emp_key = _emp_key;
		this._emp_id = _emp_id;
		this._user_name_ch = _user_name_ch;
		this._hour_count = _hour_count;
		this._organization_id = _organization_id;
		this._checkwork_type_id = _checkwork_type_id;
		this._days = _days;
		this._year_months = _year_months;
	}

	@Override
	public String toString() {
		return "SourceEmpOtherDayEntity ["+
			"	emp_other_day_id="+_emp_other_day_id+
			"	customer_id="+_customer_id+
			"	emp_key="+_emp_key+
			"	emp_id="+_emp_id+
			"	user_name_ch="+_user_name_ch+
			"	hour_count="+_hour_count+
			"	organization_id="+_organization_id+
			"	checkwork_type_id="+_checkwork_type_id+
			"	days="+_days+
			"	year_months="+_year_months+
			"]";
	}

	@Column(name = "emp_other_day_id",type=ColumnType.VARCHAR)
	public String getEmpOtherDayId(){
		return this._emp_other_day_id; 
	}

	public void setEmpOtherDayId(String _emp_other_day_id){
		this._emp_other_day_id = _emp_other_day_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_key",type=ColumnType.VARCHAR)
	public String getEmpKey(){
		return this._emp_key; 
	}

	public void setEmpKey(String _emp_key){
		this._emp_key = _emp_key;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "hour_count",type=ColumnType.DOUBLE)
	public Double getHourCount(){
		return this._hour_count; 
	}

	public void setHourCount(Double _hour_count){
		this._hour_count = _hour_count;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "checkwork_type_id",type=ColumnType.VARCHAR)
	public String getCheckworkTypeId(){
		return this._checkwork_type_id; 
	}

	public void setCheckworkTypeId(String _checkwork_type_id){
		this._checkwork_type_id = _checkwork_type_id;
	}

	@Column(name = "days",type=ColumnType.DATE)
	public String getDays(){
		return this._days; 
	}

	public void setDays(String _days){
		this._days = _days;
	}

	@Column(name = "year_months",type=ColumnType.INT)
	public int getYearMonths(){
		return this._year_months; 
	}

	public void setYearMonths(int _year_months){
		this._year_months = _year_months;
	}

	// 实例化内部类
	public static SourceEmpOtherDayEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpOtherDayEntityAuxiliary();
	}

	public static class SourceEmpOtherDayEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpOtherDayEntityAuxiliary asEmpOtherDayId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_other_day_id","`emp_other_day_id`", colName, "setEmpOtherDayId", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asEmpKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_key","`emp_key`", colName, "setEmpKey", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asHourCount(String colName, CustomColumn<?, ?>... cols){
			setColName("hour_count","`hour_count`", colName, "setHourCount", "getDouble", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asCheckworkTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("checkwork_type_id","`checkwork_type_id`", colName, "setCheckworkTypeId", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asDays(String colName, CustomColumn<?, ?>... cols){
			setColName("days","`days`", colName, "setDays", "getString", cols);
			return this;
		}
		public SourceEmpOtherDayEntityAuxiliary asYearMonths(String colName, CustomColumn<?, ?>... cols){
			setColName("year_months","`year_months`", colName, "setYearMonths", "getInt", cols);
			return this;
		}
	}
}
