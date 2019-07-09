package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_lecturer")
public class SourceLecturerEntity implements Entity{

	private String _lecturer_id;
	private String _customer_id;
	private String _emp_id;
	private String _lecturer_name;
	private String _level_id;
	private int _type;

	public SourceLecturerEntity(){
		super();
	}

	public SourceLecturerEntity(String _lecturer_id,String _customer_id,String _emp_id,String _lecturer_name,String _level_id,int _type){
		this._lecturer_id = _lecturer_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._lecturer_name = _lecturer_name;
		this._level_id = _level_id;
		this._type = _type;
	}

	@Override
	public String toString() {
		return "SourceLecturerEntity ["+
			"	lecturer_id="+_lecturer_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	lecturer_name="+_lecturer_name+
			"	level_id="+_level_id+
			"	type="+_type+
			"]";
	}

	@Column(name = "lecturer_id",type=ColumnType.VARCHAR)
	public String getLecturerId(){
		return this._lecturer_id; 
	}

	public void setLecturerId(String _lecturer_id){
		this._lecturer_id = _lecturer_id;
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

	@Column(name = "lecturer_name",type=ColumnType.VARCHAR)
	public String getLecturerName(){
		return this._lecturer_name; 
	}

	public void setLecturerName(String _lecturer_name){
		this._lecturer_name = _lecturer_name;
	}

	@Column(name = "level_id",type=ColumnType.VARCHAR)
	public String getLevelId(){
		return this._level_id; 
	}

	public void setLevelId(String _level_id){
		this._level_id = _level_id;
	}

	@Column(name = "type",type=ColumnType.TINYINT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	// 实例化内部类
	public static SourceLecturerEntityAuxiliary  getEntityAuxiliary(){
		return new SourceLecturerEntityAuxiliary();
	}

	public static class SourceLecturerEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceLecturerEntityAuxiliary asLecturerId(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_id","`lecturer_id`", colName, "setLecturerId", "getString", cols);
			return this;
		}
		public SourceLecturerEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceLecturerEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceLecturerEntityAuxiliary asLecturerName(String colName, CustomColumn<?, ?>... cols){
			setColName("lecturer_name","`lecturer_name`", colName, "setLecturerName", "getString", cols);
			return this;
		}
		public SourceLecturerEntityAuxiliary asLevelId(String colName, CustomColumn<?, ?>... cols){
			setColName("level_id","`level_id`", colName, "setLevelId", "getString", cols);
			return this;
		}
		public SourceLecturerEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
	}
}
