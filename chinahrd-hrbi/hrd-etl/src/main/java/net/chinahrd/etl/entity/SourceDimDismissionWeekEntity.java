package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_dismission_week")
public class SourceDimDismissionWeekEntity implements Entity{

	private String _dismission_week_id;
	private String _customer_id;
	private String _name;
	private int _days;
	private int _type;
	private int _show_index;
	private String _refresh;

	public SourceDimDismissionWeekEntity(){
		super();
	}

	public SourceDimDismissionWeekEntity(String _dismission_week_id,String _customer_id,String _name,int _days,int _type,int _show_index,String _refresh){
		this._dismission_week_id = _dismission_week_id;
		this._customer_id = _customer_id;
		this._name = _name;
		this._days = _days;
		this._type = _type;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimDismissionWeekEntity ["+
			"	dismission_week_id="+_dismission_week_id+
			"	customer_id="+_customer_id+
			"	name="+_name+
			"	days="+_days+
			"	type="+_type+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "dismission_week_id",type=ColumnType.VARCHAR)
	public String getDismissionWeekId(){
		return this._dismission_week_id; 
	}

	public void setDismissionWeekId(String _dismission_week_id){
		this._dismission_week_id = _dismission_week_id;
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

	@Column(name = "days",type=ColumnType.TINYINT)
	public int getDays(){
		return this._days; 
	}

	public void setDays(int _days){
		this._days = _days;
	}

	@Column(name = "type",type=ColumnType.TINYINT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceDimDismissionWeekEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimDismissionWeekEntityAuxiliary();
	}

	public static class SourceDimDismissionWeekEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimDismissionWeekEntityAuxiliary asDismissionWeekId(String colName, CustomColumn<?, ?>... cols){
			setColName("dismission_week_id","`dismission_week_id`", colName, "setDismissionWeekId", "getString", cols);
			return this;
		}
		public SourceDimDismissionWeekEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimDismissionWeekEntityAuxiliary asName(String colName, CustomColumn<?, ?>... cols){
			setColName("name","`name`", colName, "setName", "getString", cols);
			return this;
		}
		public SourceDimDismissionWeekEntityAuxiliary asDays(String colName, CustomColumn<?, ?>... cols){
			setColName("days","`days`", colName, "setDays", "getInt", cols);
			return this;
		}
		public SourceDimDismissionWeekEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceDimDismissionWeekEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimDismissionWeekEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
