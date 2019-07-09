package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_recruit_value")
public class SourceRecruitValueEntity implements Entity{

	private String _recruit_value_id;
	private String _customer_id;
	private String _organization_id;
	private Double _budget_value;
	private Double _outlay;
	private int _year;
	private String _refresh;
	private String _c_id;

	public SourceRecruitValueEntity(){
		super();
	}

	public SourceRecruitValueEntity(String _recruit_value_id,String _customer_id,String _organization_id,Double _budget_value,Double _outlay,int _year,String _refresh,String _c_id){
		this._recruit_value_id = _recruit_value_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._budget_value = _budget_value;
		this._outlay = _outlay;
		this._year = _year;
		this._refresh = _refresh;
		this._c_id = _c_id;
	}

	@Override
	public String toString() {
		return "SourceRecruitValueEntity ["+
			"	recruit_value_id="+_recruit_value_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	budget_value="+_budget_value+
			"	outlay="+_outlay+
			"	year="+_year+
			"	refresh="+_refresh+
			"	c_id="+_c_id+
			"]";
	}

	@Column(name = "recruit_value_id",type=ColumnType.VARCHAR)
	public String getRecruitValueId(){
		return this._recruit_value_id; 
	}

	public void setRecruitValueId(String _recruit_value_id){
		this._recruit_value_id = _recruit_value_id;
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

	@Column(name = "budget_value",type=ColumnType.DOUBLE)
	public Double getBudgetValue(){
		return this._budget_value; 
	}

	public void setBudgetValue(Double _budget_value){
		this._budget_value = _budget_value;
	}

	@Column(name = "outlay",type=ColumnType.DOUBLE)
	public Double getOutlay(){
		return this._outlay; 
	}

	public void setOutlay(Double _outlay){
		this._outlay = _outlay;
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

	@Column(name = "c_id",type=ColumnType.VARCHAR)
	public String getCId(){
		return this._c_id; 
	}

	public void setCId(String _c_id){
		this._c_id = _c_id;
	}

	// 实例化内部类
	public static SourceRecruitValueEntityAuxiliary  getEntityAuxiliary(){
		return new SourceRecruitValueEntityAuxiliary();
	}

	public static class SourceRecruitValueEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceRecruitValueEntityAuxiliary asRecruitValueId(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_value_id","`recruit_value_id`", colName, "setRecruitValueId", "getString", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asBudgetValue(String colName, CustomColumn<?, ?>... cols){
			setColName("budget_value","`budget_value`", colName, "setBudgetValue", "getDouble", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asOutlay(String colName, CustomColumn<?, ?>... cols){
			setColName("outlay","`outlay`", colName, "setOutlay", "getDouble", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceRecruitValueEntityAuxiliary asCId(String colName, CustomColumn<?, ?>... cols){
			setColName("c_id","`c_id`", colName, "setCId", "getString", cols);
			return this;
		}
	}
}
