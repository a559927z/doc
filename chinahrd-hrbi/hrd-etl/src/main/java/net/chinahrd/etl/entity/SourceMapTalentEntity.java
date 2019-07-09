package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_map_talent")
public class SourceMapTalentEntity implements Entity{

	private String _map_talent_id;
	private String _customer_id;
	private String _emp_id;
	private String _x_axis_id;
	private String _x_axis_id_af;
	private String _x_axis_id_af_r;
	private String _y_axis_id;
	private String _y_axis_id_af;
	private String _y_axis_id_af_r;
	private String _update_time;
	private int _is_update;
	private String _modify_id;
	private String _release_id;
	private int _year_months;
	private String _note;
	private String _note1;
	private String _date_time;

	public SourceMapTalentEntity(){
		super();
	}

	public SourceMapTalentEntity(String _map_talent_id,String _customer_id,String _emp_id,String _x_axis_id,String _x_axis_id_af,String _x_axis_id_af_r,String _y_axis_id,String _y_axis_id_af,String _y_axis_id_af_r,String _update_time,int _is_update,String _modify_id,String _release_id,int _year_months,String _note,String _note1,String _date_time){
		this._map_talent_id = _map_talent_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._x_axis_id = _x_axis_id;
		this._x_axis_id_af = _x_axis_id_af;
		this._x_axis_id_af_r = _x_axis_id_af_r;
		this._y_axis_id = _y_axis_id;
		this._y_axis_id_af = _y_axis_id_af;
		this._y_axis_id_af_r = _y_axis_id_af_r;
		this._update_time = _update_time;
		this._is_update = _is_update;
		this._modify_id = _modify_id;
		this._release_id = _release_id;
		this._year_months = _year_months;
		this._note = _note;
		this._note1 = _note1;
		this._date_time = _date_time;
	}

	@Override
	public String toString() {
		return "SourceMapTalentEntity ["+
			"	map_talent_id="+_map_talent_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	x_axis_id="+_x_axis_id+
			"	x_axis_id_af="+_x_axis_id_af+
			"	x_axis_id_af_r="+_x_axis_id_af_r+
			"	y_axis_id="+_y_axis_id+
			"	y_axis_id_af="+_y_axis_id_af+
			"	y_axis_id_af_r="+_y_axis_id_af_r+
			"	update_time="+_update_time+
			"	is_update="+_is_update+
			"	modify_id="+_modify_id+
			"	release_id="+_release_id+
			"	year_months="+_year_months+
			"	note="+_note+
			"	note1="+_note1+
			"	date_time="+_date_time+
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

	@Column(name = "x_axis_id_af",type=ColumnType.VARCHAR)
	public String getXAxisIdAf(){
		return this._x_axis_id_af; 
	}

	public void setXAxisIdAf(String _x_axis_id_af){
		this._x_axis_id_af = _x_axis_id_af;
	}

	@Column(name = "x_axis_id_af_r",type=ColumnType.VARCHAR)
	public String getXAxisIdAfR(){
		return this._x_axis_id_af_r; 
	}

	public void setXAxisIdAfR(String _x_axis_id_af_r){
		this._x_axis_id_af_r = _x_axis_id_af_r;
	}

	@Column(name = "y_axis_id",type=ColumnType.VARCHAR)
	public String getYAxisId(){
		return this._y_axis_id; 
	}

	public void setYAxisId(String _y_axis_id){
		this._y_axis_id = _y_axis_id;
	}

	@Column(name = "y_axis_id_af",type=ColumnType.VARCHAR)
	public String getYAxisIdAf(){
		return this._y_axis_id_af; 
	}

	public void setYAxisIdAf(String _y_axis_id_af){
		this._y_axis_id_af = _y_axis_id_af;
	}

	@Column(name = "y_axis_id_af_r",type=ColumnType.VARCHAR)
	public String getYAxisIdAfR(){
		return this._y_axis_id_af_r; 
	}

	public void setYAxisIdAfR(String _y_axis_id_af_r){
		this._y_axis_id_af_r = _y_axis_id_af_r;
	}

	@Column(name = "update_time",type=ColumnType.DATETIME)
	public String getUpdateTime(){
		return this._update_time; 
	}

	public void setUpdateTime(String _update_time){
		this._update_time = _update_time;
	}

	@Column(name = "is_update",type=ColumnType.INT)
	public int getIsUpdate(){
		return this._is_update; 
	}

	public void setIsUpdate(int _is_update){
		this._is_update = _is_update;
	}

	@Column(name = "modify_id",type=ColumnType.VARCHAR)
	public String getModifyId(){
		return this._modify_id; 
	}

	public void setModifyId(String _modify_id){
		this._modify_id = _modify_id;
	}

	@Column(name = "release_id",type=ColumnType.VARCHAR)
	public String getReleaseId(){
		return this._release_id; 
	}

	public void setReleaseId(String _release_id){
		this._release_id = _release_id;
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

	@Column(name = "note1",type=ColumnType.VARCHAR)
	public String getNote1(){
		return this._note1; 
	}

	public void setNote1(String _note1){
		this._note1 = _note1;
	}

	@Column(name = "date_time",type=ColumnType.DATE)
	public String getDateTime(){
		return this._date_time; 
	}

	public void setDateTime(String _date_time){
		this._date_time = _date_time;
	}

	// 实例化内部类
	public static SourceMapTalentEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMapTalentEntityAuxiliary();
	}

	public static class SourceMapTalentEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMapTalentEntityAuxiliary asMapTalentId(String colName, CustomColumn<?, ?>... cols){
			setColName("map_talent_id","`map_talent_id`", colName, "setMapTalentId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asXAxisId(String colName, CustomColumn<?, ?>... cols){
			setColName("x_axis_id","`x_axis_id`", colName, "setXAxisId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asXAxisIdAf(String colName, CustomColumn<?, ?>... cols){
			setColName("x_axis_id_af","`x_axis_id_af`", colName, "setXAxisIdAf", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asXAxisIdAfR(String colName, CustomColumn<?, ?>... cols){
			setColName("x_axis_id_af_r","`x_axis_id_af_r`", colName, "setXAxisIdAfR", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asYAxisId(String colName, CustomColumn<?, ?>... cols){
			setColName("y_axis_id","`y_axis_id`", colName, "setYAxisId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asYAxisIdAf(String colName, CustomColumn<?, ?>... cols){
			setColName("y_axis_id_af","`y_axis_id_af`", colName, "setYAxisIdAf", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asYAxisIdAfR(String colName, CustomColumn<?, ?>... cols){
			setColName("y_axis_id_af_r","`y_axis_id_af_r`", colName, "setYAxisIdAfR", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asUpdateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("update_time","`update_time`", colName, "setUpdateTime", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asIsUpdate(String colName, CustomColumn<?, ?>... cols){
			setColName("is_update","`is_update`", colName, "setIsUpdate", "getInt", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asModifyId(String colName, CustomColumn<?, ?>... cols){
			setColName("modify_id","`modify_id`", colName, "setModifyId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asReleaseId(String colName, CustomColumn<?, ?>... cols){
			setColName("release_id","`release_id`", colName, "setReleaseId", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asYearMonths(String colName, CustomColumn<?, ?>... cols){
			setColName("year_months","`year_months`", colName, "setYearMonths", "getInt", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asNote1(String colName, CustomColumn<?, ?>... cols){
			setColName("note1","`note1`", colName, "setNote1", "getString", cols);
			return this;
		}
		public SourceMapTalentEntityAuxiliary asDateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("date_time","`date_time`", colName, "setDateTime", "getString", cols);
			return this;
		}
	}
}
