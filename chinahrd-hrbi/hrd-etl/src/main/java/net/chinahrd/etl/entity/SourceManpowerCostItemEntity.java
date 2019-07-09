package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_manpower_cost_item")
public class SourceManpowerCostItemEntity implements Entity{

	private String _manpower_cost_item_id;
	private String _customer_id;
	private String _organization_id;
	private String _item_id;
	private String _item_name;
	private Double _item_cost;
	private int _year_month;
	private int _show_index;

	public SourceManpowerCostItemEntity(){
		super();
	}

	public SourceManpowerCostItemEntity(String _manpower_cost_item_id,String _customer_id,String _organization_id,String _item_id,String _item_name,Double _item_cost,int _year_month,int _show_index){
		this._manpower_cost_item_id = _manpower_cost_item_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._item_id = _item_id;
		this._item_name = _item_name;
		this._item_cost = _item_cost;
		this._year_month = _year_month;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceManpowerCostItemEntity ["+
			"	manpower_cost_item_id="+_manpower_cost_item_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	item_id="+_item_id+
			"	item_name="+_item_name+
			"	item_cost="+_item_cost+
			"	year_month="+_year_month+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "manpower_cost_item_id",type=ColumnType.VARCHAR)
	public String getManpowerCostItemId(){
		return this._manpower_cost_item_id; 
	}

	public void setManpowerCostItemId(String _manpower_cost_item_id){
		this._manpower_cost_item_id = _manpower_cost_item_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "item_id",type=ColumnType.VARCHAR)
	public String getItemId(){
		return this._item_id; 
	}

	public void setItemId(String _item_id){
		this._item_id = _item_id;
	}

	@Column(name = "item_name",type=ColumnType.VARCHAR)
	public String getItemName(){
		return this._item_name; 
	}

	public void setItemName(String _item_name){
		this._item_name = _item_name;
	}

	@Column(name = "item_cost",type=ColumnType.DOUBLE)
	public Double getItemCost(){
		return this._item_cost; 
	}

	public void setItemCost(Double _item_cost){
		this._item_cost = _item_cost;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceManpowerCostItemEntityAuxiliary  getEntityAuxiliary(){
		return new SourceManpowerCostItemEntityAuxiliary();
	}

	public static class SourceManpowerCostItemEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceManpowerCostItemEntityAuxiliary asManpowerCostItemId(String colName, CustomColumn<?, ?>... cols){
			setColName("manpower_cost_item_id","`manpower_cost_item_id`", colName, "setManpowerCostItemId", "getString", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asItemId(String colName, CustomColumn<?, ?>... cols){
			setColName("item_id","`item_id`", colName, "setItemId", "getString", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asItemName(String colName, CustomColumn<?, ?>... cols){
			setColName("item_name","`item_name`", colName, "setItemName", "getString", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asItemCost(String colName, CustomColumn<?, ?>... cols){
			setColName("item_cost","`item_cost`", colName, "setItemCost", "getDouble", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceManpowerCostItemEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
