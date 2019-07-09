package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_map_talent_result")
public class SourceMapTalentResultEntity implements Entity{

	private String _map_talent_id;
	private String _customer_id;
	private String _emp_id;
	private String _x_axis_id;
	private String _y_axis_id;
	private String _update_time;
	private int _year_months;
	private String _note;

	public SourceMapTalentResultEntity(){
		super();
	}

	public SourceMapTalentResultEntity(String _map_talent_id,String _customer_id,String _emp_id,String _x_axis_id,String _y_axis_id,String _update_time,int _year_months,String _note){
		this._map_talent_id = _map_talent_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._x_axis_id = _x_axis_id;
		this._y_axis_id = _y_axis_id;
		this._update_time = _update_time;
		this._year_months = _year_months;
		this._note = _note;
	}

	@Override
	public String toString() {
		return "SourceMapTalentResultEntity ["+
			"	map_talent_id="+_map_talent_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	x_axis_id="+_x_axis_id+
			"	y_axis_id="+_y_axis_id+
			"	update_time="+_update_time+
			"	year_months="+_year_months+
			"	note="+_note+
			"]";
	}

	@Column(name = "map_talent_id",type=ColumnType.VARCHAR)
	public String getMapTalentId(){
		return this._map_talent_id; 
	}

	public void setMapTalentId(String _map_talent_id){
		this._map_talent_id = _map_talent_id;
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

	@Column(name = "x_axis_id",type=ColumnType.VARCHAR)
	public String getXAxisId(){
		return this._x_axis_id; 
	}

	public void setXAxisId(String _x_axis_id){
		this._x_axis_id = _x_axis_id;
	}

	@Column(name = "y_axis_id",type=ColumnType.VARCHAR)
	public String getYAxisId(){
		return this._y_axis_id; 
	}

	public void setYAxisId(String _y_axis_id){
		this._y_axis_id = _y_axis_id;
	}

	@Column(name = "update_time",type=ColumnType.DATETIME)
	public String getUpdateTime(){
		return this._update_time; 
	}

	public void setUpdateTime(String _update_time){
		this._update_time = _update_time;
	}

	@Column(name = "year_months",type=ColumnType.INT)
	public int getYearMonths(){
		return this._year_months; 
	}

	public void setYearMonths(int _year_months){
		this._year_months = _year_months;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	// 实例化内部类
	public static SourceMapTalentResultEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMapTalentResultEntityAuxiliary();
	}

	public static class SourceMapTalentResultEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMapTalentResultEntityAuxiliary asMapTalentId(String colName, CustomColumn<?, ?>... cols){
			setColName("map_talent_id","`map_talent_id`", colName, "setMapTalentId", "getString", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asXAxisId(String colName, CustomColumn<?, ?>... cols){
			setColName("x_axis_id","`x_axis_id`", colName, "setXAxisId", "getString", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asYAxisId(String colName, CustomColumn<?, ?>... cols){
			setColName("y_axis_id","`y_axis_id`", colName, "setYAxisId", "getString", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asUpdateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("update_time","`update_time`", colName, "setUpdateTime", "getString", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asYearMonths(String colName, CustomColumn<?, ?>... cols){
			setColName("year_months","`year_months`", colName, "setYearMonths", "getInt", cols);
			return this;
		}
		public SourceMapTalentResultEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
	}
}
