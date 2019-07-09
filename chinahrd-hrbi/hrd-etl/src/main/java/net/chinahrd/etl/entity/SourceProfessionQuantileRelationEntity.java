package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_profession_quantile_relation")
public class SourceProfessionQuantileRelationEntity implements Entity{

	private String _id;
	private String _customer_id;
	private String _profession_id;
	private String _quantile_id;
	private Double _quantile_value;
	private int _type;
	private int _year;
	private String _refresh;

	public SourceProfessionQuantileRelationEntity(){
		super();
	}

	public SourceProfessionQuantileRelationEntity(String _id,String _customer_id,String _profession_id,String _quantile_id,Double _quantile_value,int _type,int _year,String _refresh){
		this._id = _id;
		this._customer_id = _customer_id;
		this._profession_id = _profession_id;
		this._quantile_id = _quantile_id;
		this._quantile_value = _quantile_value;
		this._type = _type;
		this._year = _year;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceProfessionQuantileRelationEntity ["+
			"	id="+_id+
			"	customer_id="+_customer_id+
			"	profession_id="+_profession_id+
			"	quantile_id="+_quantile_id+
			"	quantile_value="+_quantile_value+
			"	type="+_type+
			"	year="+_year+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "id",type=ColumnType.VARCHAR)
	public String getId(){
		return this._id; 
	}

	public void setId(String _id){
		this._id = _id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "profession_id",type=ColumnType.VARCHAR)
	public String getProfessionId(){
		return this._profession_id; 
	}

	public void setProfessionId(String _profession_id){
		this._profession_id = _profession_id;
	}

	@Column(name = "quantile_id",type=ColumnType.VARCHAR)
	public String getQuantileId(){
		return this._quantile_id; 
	}

	public void setQuantileId(String _quantile_id){
		this._quantile_id = _quantile_id;
	}

	@Column(name = "quantile_value",type=ColumnType.DOUBLE)
	public Double getQuantileValue(){
		return this._quantile_value; 
	}

	public void setQuantileValue(Double _quantile_value){
		this._quantile_value = _quantile_value;
	}

	@Column(name = "type",type=ColumnType.TINYINT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
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
	public static SourceProfessionQuantileRelationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceProfessionQuantileRelationEntityAuxiliary();
	}

	public static class SourceProfessionQuantileRelationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceProfessionQuantileRelationEntityAuxiliary asId(String colName, CustomColumn<?, ?>... cols){
			setColName("id","`id`", colName, "setId", "getString", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asProfessionId(String colName, CustomColumn<?, ?>... cols){
			setColName("profession_id","`profession_id`", colName, "setProfessionId", "getString", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asQuantileId(String colName, CustomColumn<?, ?>... cols){
			setColName("quantile_id","`quantile_id`", colName, "setQuantileId", "getString", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asQuantileValue(String colName, CustomColumn<?, ?>... cols){
			setColName("quantile_value","`quantile_value`", colName, "setQuantileValue", "getDouble", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceProfessionQuantileRelationEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
