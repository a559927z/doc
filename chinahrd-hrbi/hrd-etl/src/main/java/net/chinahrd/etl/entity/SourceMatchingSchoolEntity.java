package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_matching_school")
public class SourceMatchingSchoolEntity implements Entity{

	private String _matching_school_id;
	private String _customer_id;
	private String _name;
	private String _school_type;
	private int _level;

	public SourceMatchingSchoolEntity(){
		super();
	}

	public SourceMatchingSchoolEntity(String _matching_school_id,String _customer_id,String _name,String _school_type,int _level){
		this._matching_school_id = _matching_school_id;
		this._customer_id = _customer_id;
		this._name = _name;
		this._school_type = _school_type;
		this._level = _level;
	}

	@Override
	public String toString() {
		return "SourceMatchingSchoolEntity ["+
			"	matching_school_id="+_matching_school_id+
			"	customer_id="+_customer_id+
			"	name="+_name+
			"	school_type="+_school_type+
			"	level="+_level+
			"]";
	}

	@Column(name = "matching_school_id",type=ColumnType.VARCHAR)
	public String getMatchingSchoolId(){
		return this._matching_school_id; 
	}

	public void setMatchingSchoolId(String _matching_school_id){
		this._matching_school_id = _matching_school_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "name",type=ColumnType.VARCHAR)
	public String getName(){
		return this._name; 
	}

	public void setName(String _name){
		this._name = _name;
	}

	@Column(name = "school_type",type=ColumnType.VARCHAR)
	public String getSchoolType(){
		return this._school_type; 
	}

	public void setSchoolType(String _school_type){
		this._school_type = _school_type;
	}

	@Column(name = "level",type=ColumnType.INT)
	public int getLevel(){
		return this._level; 
	}

	public void setLevel(int _level){
		this._level = _level;
	}

	// 实例化内部类
	public static SourceMatchingSchoolEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMatchingSchoolEntityAuxiliary();
	}

	public static class SourceMatchingSchoolEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMatchingSchoolEntityAuxiliary asMatchingSchoolId(String colName, CustomColumn<?, ?>... cols){
			setColName("matching_school_id","`matching_school_id`", colName, "setMatchingSchoolId", "getString", cols);
			return this;
		}
		public SourceMatchingSchoolEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMatchingSchoolEntityAuxiliary asName(String colName, CustomColumn<?, ?>... cols){
			setColName("name","`name`", colName, "setName", "getString", cols);
			return this;
		}
		public SourceMatchingSchoolEntityAuxiliary asSchoolType(String colName, CustomColumn<?, ?>... cols){
			setColName("school_type","`school_type`", colName, "setSchoolType", "getString", cols);
			return this;
		}
		public SourceMatchingSchoolEntityAuxiliary asLevel(String colName, CustomColumn<?, ?>... cols){
			setColName("level","`level`", colName, "setLevel", "getInt", cols);
			return this;
		}
	}
}
