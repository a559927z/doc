package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_contact_person")
public class SourceEmpContactPersonEntity implements Entity{

	private String _emp_contact_person_id;
	private String _customer_id;
	private String _emp_id;
	private String _emp_contact_person_name;
	private String _tel_phone;
	private String _call;
	private int _is_urgent;
	private String _create_time;

	public SourceEmpContactPersonEntity(){
		super();
	}

	public SourceEmpContactPersonEntity(String _emp_contact_person_id,String _customer_id,String _emp_id,String _emp_contact_person_name,String _tel_phone,String _call,int _is_urgent,String _create_time){
		this._emp_contact_person_id = _emp_contact_person_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._emp_contact_person_name = _emp_contact_person_name;
		this._tel_phone = _tel_phone;
		this._call = _call;
		this._is_urgent = _is_urgent;
		this._create_time = _create_time;
	}

	@Override
	public String toString() {
		return "SourceEmpContactPersonEntity ["+
			"	emp_contact_person_id="+_emp_contact_person_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	emp_contact_person_name="+_emp_contact_person_name+
			"	tel_phone="+_tel_phone+
			"	call="+_call+
			"	is_urgent="+_is_urgent+
			"	create_time="+_create_time+
			"]";
	}

	@Column(name = "emp_contact_person_id",type=ColumnType.VARCHAR)
	public String getEmpContactPersonId(){
		return this._emp_contact_person_id; 
	}

	public void setEmpContactPersonId(String _emp_contact_person_id){
		this._emp_contact_person_id = _emp_contact_person_id;
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

	@Column(name = "emp_contact_person_name",type=ColumnType.VARCHAR)
	public String getEmpContactPersonName(){
		return this._emp_contact_person_name; 
	}

	public void setEmpContactPersonName(String _emp_contact_person_name){
		this._emp_contact_person_name = _emp_contact_person_name;
	}

	@Column(name = "tel_phone",type=ColumnType.VARCHAR)
	public String getTelPhone(){
		return this._tel_phone; 
	}

	public void setTelPhone(String _tel_phone){
		this._tel_phone = _tel_phone;
	}

	@Column(name = "call",type=ColumnType.VARCHAR)
	public String getCall(){
		return this._call; 
	}

	public void setCall(String _call){
		this._call = _call;
	}

	@Column(name = "is_urgent",type=ColumnType.INT)
	public int getIsUrgent(){
		return this._is_urgent; 
	}

	public void setIsUrgent(int _is_urgent){
		this._is_urgent = _is_urgent;
	}

	@Column(name = "create_time",type=ColumnType.DATETIME)
	public String getCreateTime(){
		return this._create_time; 
	}

	public void setCreateTime(String _create_time){
		this._create_time = _create_time;
	}

	// 实例化内部类
	public static SourceEmpContactPersonEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpContactPersonEntityAuxiliary();
	}

	public static class SourceEmpContactPersonEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpContactPersonEntityAuxiliary asEmpContactPersonId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_contact_person_id","`emp_contact_person_id`", colName, "setEmpContactPersonId", "getString", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asEmpContactPersonName(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_contact_person_name","`emp_contact_person_name`", colName, "setEmpContactPersonName", "getString", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asTelPhone(String colName, CustomColumn<?, ?>... cols){
			setColName("tel_phone","`tel_phone`", colName, "setTelPhone", "getString", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asCall(String colName, CustomColumn<?, ?>... cols){
			setColName("call","`call`", colName, "setCall", "getString", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asIsUrgent(String colName, CustomColumn<?, ?>... cols){
			setColName("is_urgent","`is_urgent`", colName, "setIsUrgent", "getInt", cols);
			return this;
		}
		public SourceEmpContactPersonEntityAuxiliary asCreateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("create_time","`create_time`", colName, "setCreateTime", "getString", cols);
			return this;
		}
	}
}
