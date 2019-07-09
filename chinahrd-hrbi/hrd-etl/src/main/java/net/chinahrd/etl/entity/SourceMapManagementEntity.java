package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_map_management")
public class SourceMapManagementEntity implements Entity{

	private String _map_management_id;
	private String _customer_id;
	private String _top_id;
	private int _status;
	private String _adjustment_id;
	private String _emp_id;
	private String _starte_date;
	private String _motify_date;
	private String _release_date;
	private int _year_months;

	public SourceMapManagementEntity(){
		super();
	}

	public SourceMapManagementEntity(String _map_management_id,String _customer_id,String _top_id,int _status,String _adjustment_id,String _emp_id,String _starte_date,String _motify_date,String _release_date,int _year_months){
		this._map_management_id = _map_management_id;
		this._customer_id = _customer_id;
		this._top_id = _top_id;
		this._status = _status;
		this._adjustment_id = _adjustment_id;
		this._emp_id = _emp_id;
		this._starte_date = _starte_date;
		this._motify_date = _motify_date;
		this._release_date = _release_date;
		this._year_months = _year_months;
	}

	@Override
	public String toString() {
		return "SourceMapManagementEntity ["+
			"	map_management_id="+_map_management_id+
			"	customer_id="+_customer_id+
			"	top_id="+_top_id+
			"	status="+_status+
			"	adjustment_id="+_adjustment_id+
			"	emp_id="+_emp_id+
			"	starte_date="+_starte_date+
			"	motify_date="+_motify_date+
			"	release_date="+_release_date+
			"	year_months="+_year_months+
			"]";
	}

	@Column(name = "map_management_id",type=ColumnType.VARCHAR)
	public String getMapManagementId(){
		return this._map_management_id; 
	}

	public void setMapManagementId(String _map_management_id){
		this._map_management_id = _map_management_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "top_id",type=ColumnType.VARCHAR)
	public String getTopId(){
		return this._top_id; 
	}

	public void setTopId(String _top_id){
		this._top_id = _top_id;
	}

	@Column(name = "status",type=ColumnType.INT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	@Column(name = "adjustment_id",type=ColumnType.VARCHAR)
	public String getAdjustmentId(){
		return this._adjustment_id; 
	}

	public void setAdjustmentId(String _adjustment_id){
		this._adjustment_id = _adjustment_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "starte_date",type=ColumnType.DATETIME)
	public String getStarteDate(){
		return this._starte_date; 
	}

	public void setStarteDate(String _starte_date){
		this._starte_date = _starte_date;
	}

	@Column(name = "motify_date",type=ColumnType.DATETIME)
	public String getMotifyDate(){
		return this._motify_date; 
	}

	public void setMotifyDate(String _motify_date){
		this._motify_date = _motify_date;
	}

	@Column(name = "release_date",type=ColumnType.DATETIME)
	public String getReleaseDate(){
		return this._release_date; 
	}

	public void setReleaseDate(String _release_date){
		this._release_date = _release_date;
	}

	@Column(name = "year_months",type=ColumnType.INT)
	public int getYearMonths(){
		return this._year_months; 
	}

	public void setYearMonths(int _year_months){
		this._year_months = _year_months;
	}

	// 实例化内部类
	public static SourceMapManagementEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMapManagementEntityAuxiliary();
	}

	public static class SourceMapManagementEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMapManagementEntityAuxiliary asMapManagementId(String colName, CustomColumn<?, ?>... cols){
			setColName("map_management_id","`map_management_id`", colName, "setMapManagementId", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asTopId(String colName, CustomColumn<?, ?>... cols){
			setColName("top_id","`top_id`", colName, "setTopId", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asAdjustmentId(String colName, CustomColumn<?, ?>... cols){
			setColName("adjustment_id","`adjustment_id`", colName, "setAdjustmentId", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asStarteDate(String colName, CustomColumn<?, ?>... cols){
			setColName("starte_date","`starte_date`", colName, "setStarteDate", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asMotifyDate(String colName, CustomColumn<?, ?>... cols){
			setColName("motify_date","`motify_date`", colName, "setMotifyDate", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asReleaseDate(String colName, CustomColumn<?, ?>... cols){
			setColName("release_date","`release_date`", colName, "setReleaseDate", "getString", cols);
			return this;
		}
		public SourceMapManagementEntityAuxiliary asYearMonths(String colName, CustomColumn<?, ?>... cols){
			setColName("year_months","`year_months`", colName, "setYearMonths", "getInt", cols);
			return this;
		}
	}
}
