package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dimission_risk_item")
public class SourceDimissionRiskItemEntity implements Entity{

	private String _dimission_risk_item_id;
	private String _customer_id;
	private String _dimission_risk_id;
	private String _separation_risk_id;
	private int _risk_flag;

	public SourceDimissionRiskItemEntity(){
		super();
	}

	public SourceDimissionRiskItemEntity(String _dimission_risk_item_id,String _customer_id,String _dimission_risk_id,String _separation_risk_id,int _risk_flag){
		this._dimission_risk_item_id = _dimission_risk_item_id;
		this._customer_id = _customer_id;
		this._dimission_risk_id = _dimission_risk_id;
		this._separation_risk_id = _separation_risk_id;
		this._risk_flag = _risk_flag;
	}

	@Override
	public String toString() {
		return "SourceDimissionRiskItemEntity ["+
			"	dimission_risk_item_id="+_dimission_risk_item_id+
			"	customer_id="+_customer_id+
			"	dimission_risk_id="+_dimission_risk_id+
			"	separation_risk_id="+_separation_risk_id+
			"	risk_flag="+_risk_flag+
			"]";
	}

	@Column(name = "dimission_risk_item_id",type=ColumnType.VARCHAR)
	public String getDimissionRiskItemId(){
		return this._dimission_risk_item_id; 
	}

	public void setDimissionRiskItemId(String _dimission_risk_item_id){
		this._dimission_risk_item_id = _dimission_risk_item_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "dimission_risk_id",type=ColumnType.VARCHAR)
	public String getDimissionRiskId(){
		return this._dimission_risk_id; 
	}

	public void setDimissionRiskId(String _dimission_risk_id){
		this._dimission_risk_id = _dimission_risk_id;
	}

	@Column(name = "separation_risk_id",type=ColumnType.VARCHAR)
	public String getSeparationRiskId(){
		return this._separation_risk_id; 
	}

	public void setSeparationRiskId(String _separation_risk_id){
		this._separation_risk_id = _separation_risk_id;
	}

	@Column(name = "risk_flag",type=ColumnType.INT)
	public int getRiskFlag(){
		return this._risk_flag; 
	}

	public void setRiskFlag(int _risk_flag){
		this._risk_flag = _risk_flag;
	}

	// 实例化内部类
	public static SourceDimissionRiskItemEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimissionRiskItemEntityAuxiliary();
	}

	public static class SourceDimissionRiskItemEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimissionRiskItemEntityAuxiliary asDimissionRiskItemId(String colName, CustomColumn<?, ?>... cols){
			setColName("dimission_risk_item_id","`dimission_risk_item_id`", colName, "setDimissionRiskItemId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskItemEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskItemEntityAuxiliary asDimissionRiskId(String colName, CustomColumn<?, ?>... cols){
			setColName("dimission_risk_id","`dimission_risk_id`", colName, "setDimissionRiskId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskItemEntityAuxiliary asSeparationRiskId(String colName, CustomColumn<?, ?>... cols){
			setColName("separation_risk_id","`separation_risk_id`", colName, "setSeparationRiskId", "getString", cols);
			return this;
		}
		public SourceDimissionRiskItemEntityAuxiliary asRiskFlag(String colName, CustomColumn<?, ?>... cols){
			setColName("risk_flag","`risk_flag`", colName, "setRiskFlag", "getInt", cols);
			return this;
		}
	}
}
