package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_separation_risk")
public class SourceDimSeparationRiskEntity implements Entity{

	private String _separation_risk_id;
	private String _customer_id;
	private String _separation_risk_key;
	private String _separation_risk_parent_id;
	private String _separation_risk_parent_key;
	private String _separation_risk_name;
	private String _refer;
	private int _show_index;

	public SourceDimSeparationRiskEntity(){
		super();
	}

	public SourceDimSeparationRiskEntity(String _separation_risk_id,String _customer_id,String _separation_risk_key,String _separation_risk_parent_id,String _separation_risk_parent_key,String _separation_risk_name,String _refer,int _show_index){
		this._separation_risk_id = _separation_risk_id;
		this._customer_id = _customer_id;
		this._separation_risk_key = _separation_risk_key;
		this._separation_risk_parent_id = _separation_risk_parent_id;
		this._separation_risk_parent_key = _separation_risk_parent_key;
		this._separation_risk_name = _separation_risk_name;
		this._refer = _refer;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimSeparationRiskEntity ["+
			"	separation_risk_id="+_separation_risk_id+
			"	customer_id="+_customer_id+
			"	separation_risk_key="+_separation_risk_key+
			"	separation_risk_parent_id="+_separation_risk_parent_id+
			"	separation_risk_parent_key="+_separation_risk_parent_key+
			"	separation_risk_name="+_separation_risk_name+
			"	refer="+_refer+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "separation_risk_id",type=ColumnType.VARCHAR)
	public String getSeparationRiskId(){
		return this._separation_risk_id; 
	}

	public void setSeparationRiskId(String _separation_risk_id){
		this._separation_risk_id = _separation_risk_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "separation_risk_key",type=ColumnType.VARCHAR)
	public String getSeparationRiskKey(){
		return this._separation_risk_key; 
	}

	public void setSeparationRiskKey(String _separation_risk_key){
		this._separation_risk_key = _separation_risk_key;
	}

	@Column(name = "separation_risk_parent_id",type=ColumnType.VARCHAR)
	public String getSeparationRiskParentId(){
		return this._separation_risk_parent_id; 
	}

	public void setSeparationRiskParentId(String _separation_risk_parent_id){
		this._separation_risk_parent_id = _separation_risk_parent_id;
	}

	@Column(name = "separation_risk_parent_key",type=ColumnType.VARCHAR)
	public String getSeparationRiskParentKey(){
		return this._separation_risk_parent_key; 
	}

	public void setSeparationRiskParentKey(String _separation_risk_parent_key){
		this._separation_risk_parent_key = _separation_risk_parent_key;
	}

	@Column(name = "separation_risk_name",type=ColumnType.VARCHAR)
	public String getSeparationRiskName(){
		return this._separation_risk_name; 
	}

	public void setSeparationRiskName(String _separation_risk_name){
		this._separation_risk_name = _separation_risk_name;
	}

	@Column(name = "refer",type=ColumnType.TEXT)
	public String getRefer(){
		return this._refer; 
	}

	public void setRefer(String _refer){
		this._refer = _refer;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimSeparationRiskEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimSeparationRiskEntityAuxiliary();
	}

	public static class SourceDimSeparationRiskEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimSeparationRiskEntityAuxiliary asSeparationRiskId(String colName, CustomColumn<?, ?>... cols){
			setColName("separation_risk_id","`separation_risk_id`", colName, "setSeparationRiskId", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asSeparationRiskKey(String colName, CustomColumn<?, ?>... cols){
			setColName("separation_risk_key","`separation_risk_key`", colName, "setSeparationRiskKey", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asSeparationRiskParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("separation_risk_parent_id","`separation_risk_parent_id`", colName, "setSeparationRiskParentId", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asSeparationRiskParentKey(String colName, CustomColumn<?, ?>... cols){
			setColName("separation_risk_parent_key","`separation_risk_parent_key`", colName, "setSeparationRiskParentKey", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asSeparationRiskName(String colName, CustomColumn<?, ?>... cols){
			setColName("separation_risk_name","`separation_risk_name`", colName, "setSeparationRiskName", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asRefer(String colName, CustomColumn<?, ?>... cols){
			setColName("refer","`refer`", colName, "setRefer", "getString", cols);
			return this;
		}
		public SourceDimSeparationRiskEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
