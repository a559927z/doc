package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_personality")
public class SourceEmpPersonalityEntity implements Entity{

	private String _emp_personality_id;
	private String _customer_id;
	private String _emp_id;
	private String _personality_id;
	private int _type;
	private String _refresh;

	public SourceEmpPersonalityEntity(){
		super();
	}

	public SourceEmpPersonalityEntity(String _emp_personality_id,String _customer_id,String _emp_id,String _personality_id,int _type,String _refresh){
		this._emp_personality_id = _emp_personality_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._personality_id = _personality_id;
		this._type = _type;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceEmpPersonalityEntity ["+
			"	emp_personality_id="+_emp_personality_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	personality_id="+_personality_id+
			"	type="+_type+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "emp_personality_id",type=ColumnType.VARCHAR)
	public String getEmpPersonalityId(){
		return this._emp_personality_id; 
	}

	public void setEmpPersonalityId(String _emp_personality_id){
		this._emp_personality_id = _emp_personality_id;
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

	@Column(name = "personality_id",type=ColumnType.VARCHAR)
	public String getPersonalityId(){
		return this._personality_id; 
	}

	public void setPersonalityId(String _personality_id){
		this._personality_id = _personality_id;
	}

	@Column(name = "type",type=ColumnType.INT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceEmpPersonalityEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpPersonalityEntityAuxiliary();
	}

	public static class SourceEmpPersonalityEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpPersonalityEntityAuxiliary asEmpPersonalityId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_personality_id","`emp_personality_id`", colName, "setEmpPersonalityId", "getString", cols);
			return this;
		}
		public SourceEmpPersonalityEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpPersonalityEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpPersonalityEntityAuxiliary asPersonalityId(String colName, CustomColumn<?, ?>... cols){
			setColName("personality_id","`personality_id`", colName, "setPersonalityId", "getString", cols);
			return this;
		}
		public SourceEmpPersonalityEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceEmpPersonalityEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
