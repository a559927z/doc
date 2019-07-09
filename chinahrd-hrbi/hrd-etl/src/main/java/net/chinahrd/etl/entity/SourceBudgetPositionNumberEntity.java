package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_budget_position_number")
public class SourceBudgetPositionNumberEntity implements Entity{

	private String _budget_position_number_id;
	private String _customer_id;
	private String _organization_id;
	private String _position_id;
	private int _number;
	private int _year;
	private String _refresh;

	public SourceBudgetPositionNumberEntity(){
		super();
	}

	public SourceBudgetPositionNumberEntity(String _budget_position_number_id,String _customer_id,String _organization_id,String _position_id,int _number,int _year,String _refresh){
		this._budget_position_number_id = _budget_position_number_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._position_id = _position_id;
		this._number = _number;
		this._year = _year;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceBudgetPositionNumberEntity ["+
			"	budget_position_number_id="+_budget_position_number_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	position_id="+_position_id+
			"	number="+_number+
			"	year="+_year+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "budget_position_number_id",type=ColumnType.VARCHAR)
	public String getBudgetPositionNumberId(){
		return this._budget_position_number_id; 
	}

	public void setBudgetPositionNumberId(String _budget_position_number_id){
		this._budget_position_number_id = _budget_position_number_id;
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

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
	}

	@Column(name = "number",type=ColumnType.INT)
	public int getNumber(){
		return this._number; 
	}

	public void setNumber(int _number){
		this._number = _number;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceBudgetPositionNumberEntityAuxiliary  getEntityAuxiliary(){
		return new SourceBudgetPositionNumberEntityAuxiliary();
	}

	public static class SourceBudgetPositionNumberEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceBudgetPositionNumberEntityAuxiliary asBudgetPositionNumberId(String colName, CustomColumn<?, ?>... cols){
			setColName("budget_position_number_id","`budget_position_number_id`", colName, "setBudgetPositionNumberId", "getString", cols);
			return this;
		}
		public SourceBudgetPositionNumberEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceBudgetPositionNumberEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceBudgetPositionNumberEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceBudgetPositionNumberEntityAuxiliary asNumber(String colName, CustomColumn<?, ?>... cols){
			setColName("number","`number`", colName, "setNumber", "getInt", cols);
			return this;
		}
		public SourceBudgetPositionNumberEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceBudgetPositionNumberEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
