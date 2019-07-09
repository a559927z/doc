package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_overtime_day")
public class SourceEmpOvertimeDayEntity implements Entity{

	private String _emp_overtime_day_id;
	private String _customer_id;
	private String _emp_key;
	private String _emp_id;
	private String _user_name_ch;
	private Double _hour_count;
	private String _organization_id;
	private String _checkwork_type_id;
	private String _days;
	private int _year_month;

	public SourceEmpOvertimeDayEntity(){
		super();
	}

	public SourceEmpOvertimeDayEntity(String _emp_overtime_day_id,String _customer_id,String _emp_key,String _emp_id,String _user_name_ch,Double _hour_count,String _organization_id,String _checkwork_type_id,String _days,int _year_month){
		this._emp_overtime_day_id = _emp_overtime_day_id;
		this._customer_id = _customer_id;
		this._emp_key = _emp_key;
		this._emp_id = _emp_id;
		this._user_name_ch = _user_name_ch;
		this._hour_count = _hour_count;
		this._organization_id = _organization_id;
		this._checkwork_type_id = _checkwork_type_id;
		this._days = _days;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceEmpOvertimeDayEntity ["+
			"	emp_overtime_day_id="+_emp_overtime_day_id+
			"	customer_id="+_customer_id+
			"	emp_key="+_emp_key+
			"	emp_id="+_emp_id+
			"	user_name_ch="+_user_name_ch+
			"	hour_count="+_hour_count+
			"	organization_id="+_organization_id+
			"	checkwork_type_id="+_checkwork_type_id+
			"	days="+_days+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "emp_overtime_day_id",type=ColumnType.VARCHAR)
	public String getEmpOvertimeDayId(){
		return this._emp_overtime_day_id; 
	}

	public void setEmpOvertimeDayId(String _emp_overtime_day_id){
		this._emp_overtime_day_id = _emp_overtime_day_id;
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

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceEmpOvertimeDayEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpOvertimeDayEntityAuxiliary();
	}

	public static class SourceEmpOvertimeDayEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpOvertimeDayEntityAuxiliary asEmpOvertimeDayId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_overtime_day_id","`emp_overtime_day_id`", colName, "setEmpOvertimeDayId", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asEmpKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_key","`emp_key`", colName, "setEmpKey", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asHourCount(String colName, CustomColumn<?, ?>... cols){
			setColName("hour_count","`hour_count`", colName, "setHourCount", "getDouble", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asCheckworkTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("checkwork_type_id","`checkwork_type_id`", colName, "setCheckworkTypeId", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asDays(String colName, CustomColumn<?, ?>... cols){
			setColName("days","`days`", colName, "setDays", "getString", cols);
			return this;
		}
		public SourceEmpOvertimeDayEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
