package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_family")
public class SourceEmpFamilyEntity implements Entity{

	private String _emp_family_id;
	private String _customer_id;
	private String _emp_id;
	private String _emp_family_name;
	private String _call;
	private int _is_child;
	private String _work_unit;
	private String _department_name;
	private String _position_name;
	private String _tel_phone;
	private String _born_date;
	private String _note;

	public SourceEmpFamilyEntity(){
		super();
	}

	public SourceEmpFamilyEntity(String _emp_family_id,String _customer_id,String _emp_id,String _emp_family_name,String _call,int _is_child,String _work_unit,String _department_name,String _position_name,String _tel_phone,String _born_date,String _note){
		this._emp_family_id = _emp_family_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._emp_family_name = _emp_family_name;
		this._call = _call;
		this._is_child = _is_child;
		this._work_unit = _work_unit;
		this._department_name = _department_name;
		this._position_name = _position_name;
		this._tel_phone = _tel_phone;
		this._born_date = _born_date;
		this._note = _note;
	}

	@Override
	public String toString() {
		return "SourceEmpFamilyEntity ["+
			"	emp_family_id="+_emp_family_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	emp_family_name="+_emp_family_name+
			"	call="+_call+
			"	is_child="+_is_child+
			"	work_unit="+_work_unit+
			"	department_name="+_department_name+
			"	position_name="+_position_name+
			"	tel_phone="+_tel_phone+
			"	born_date="+_born_date+
			"	note="+_note+
			"]";
	}

	@Column(name = "emp_family_id",type=ColumnType.VARCHAR)
	public String getEmpFamilyId(){
		return this._emp_family_id; 
	}

	public void setEmpFamilyId(String _emp_family_id){
		this._emp_family_id = _emp_family_id;
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

	@Column(name = "emp_family_name",type=ColumnType.VARCHAR)
	public String getEmpFamilyName(){
		return this._emp_family_name; 
	}

	public void setEmpFamilyName(String _emp_family_name){
		this._emp_family_name = _emp_family_name;
	}

	@Column(name = "call",type=ColumnType.VARCHAR)
	public String getCall(){
		return this._call; 
	}

	public void setCall(String _call){
		this._call = _call;
	}

	@Column(name = "is_child",type=ColumnType.TINYINT)
	public int getIsChild(){
		return this._is_child; 
	}

	public void setIsChild(int _is_child){
		this._is_child = _is_child;
	}

	@Column(name = "work_unit",type=ColumnType.VARCHAR)
	public String getWorkUnit(){
		return this._work_unit; 
	}

	public void setWorkUnit(String _work_unit){
		this._work_unit = _work_unit;
	}

	@Column(name = "department_name",type=ColumnType.VARCHAR)
	public String getDepartmentName(){
		return this._department_name; 
	}

	public void setDepartmentName(String _department_name){
		this._department_name = _department_name;
	}

	@Column(name = "position_name",type=ColumnType.VARCHAR)
	public String getPositionName(){
		return this._position_name; 
	}

	public void setPositionName(String _position_name){
		this._position_name = _position_name;
	}

	@Column(name = "tel_phone",type=ColumnType.VARCHAR)
	public String getTelPhone(){
		return this._tel_phone; 
	}

	public void setTelPhone(String _tel_phone){
		this._tel_phone = _tel_phone;
	}

	@Column(name = "born_date",type=ColumnType.DATETIME)
	public String getBornDate(){
		return this._born_date; 
	}

	public void setBornDate(String _born_date){
		this._born_date = _born_date;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	// 实例化内部类
	public static SourceEmpFamilyEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpFamilyEntityAuxiliary();
	}

	public static class SourceEmpFamilyEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpFamilyEntityAuxiliary asEmpFamilyId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_family_id","`emp_family_id`", colName, "setEmpFamilyId", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asEmpFamilyName(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_family_name","`emp_family_name`", colName, "setEmpFamilyName", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asCall(String colName, CustomColumn<?, ?>... cols){
			setColName("call","`call`", colName, "setCall", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asIsChild(String colName, CustomColumn<?, ?>... cols){
			setColName("is_child","`is_child`", colName, "setIsChild", "getInt", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asWorkUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("work_unit","`work_unit`", colName, "setWorkUnit", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asDepartmentName(String colName, CustomColumn<?, ?>... cols){
			setColName("department_name","`department_name`", colName, "setDepartmentName", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asTelPhone(String colName, CustomColumn<?, ?>... cols){
			setColName("tel_phone","`tel_phone`", colName, "setTelPhone", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asBornDate(String colName, CustomColumn<?, ?>... cols){
			setColName("born_date","`born_date`", colName, "setBornDate", "getString", cols);
			return this;
		}
		public SourceEmpFamilyEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
	}
}
