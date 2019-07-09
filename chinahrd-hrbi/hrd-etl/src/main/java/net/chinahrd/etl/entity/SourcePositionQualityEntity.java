package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_position_quality")
public class SourcePositionQualityEntity implements Entity{

	private String _position_quality_id;
	private String _customer_id;
	private String _position_id;
	private String _quality_id;
	private String _matching_score_id;
	private int _show_index;
	private String _refresh;

	public SourcePositionQualityEntity(){
		super();
	}

	public SourcePositionQualityEntity(String _position_quality_id,String _customer_id,String _position_id,String _quality_id,String _matching_score_id,int _show_index,String _refresh){
		this._position_quality_id = _position_quality_id;
		this._customer_id = _customer_id;
		this._position_id = _position_id;
		this._quality_id = _quality_id;
		this._matching_score_id = _matching_score_id;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourcePositionQualityEntity ["+
			"	position_quality_id="+_position_quality_id+
			"	customer_id="+_customer_id+
			"	position_id="+_position_id+
			"	quality_id="+_quality_id+
			"	matching_score_id="+_matching_score_id+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "position_quality_id",type=ColumnType.VARCHAR)
	public String getPositionQualityId(){
		return this._position_quality_id; 
	}

	public void setPositionQualityId(String _position_quality_id){
		this._position_quality_id = _position_quality_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
	}

	@Column(name = "quality_id",type=ColumnType.VARCHAR)
	public String getQualityId(){
		return this._quality_id; 
	}

	public void setQualityId(String _quality_id){
		this._quality_id = _quality_id;
	}

	@Column(name = "matching_score_id",type=ColumnType.VARCHAR)
	public String getMatchingScoreId(){
		return this._matching_score_id; 
	}

	public void setMatchingScoreId(String _matching_score_id){
		this._matching_score_id = _matching_score_id;
	}

	@Column(name = "show_index",type=ColumnType.TINYINT)
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
	public static SourcePositionQualityEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePositionQualityEntityAuxiliary();
	}

	public static class SourcePositionQualityEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePositionQualityEntityAuxiliary asPositionQualityId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_quality_id","`position_quality_id`", colName, "setPositionQualityId", "getString", cols);
			return this;
		}
		public SourcePositionQualityEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePositionQualityEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourcePositionQualityEntityAuxiliary asQualityId(String colName, CustomColumn<?, ?>... cols){
			setColName("quality_id","`quality_id`", colName, "setQualityId", "getString", cols);
			return this;
		}
		public SourcePositionQualityEntityAuxiliary asMatchingScoreId(String colName, CustomColumn<?, ?>... cols){
			setColName("matching_score_id","`matching_score_id`", colName, "setMatchingScoreId", "getString", cols);
			return this;
		}
		public SourcePositionQualityEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourcePositionQualityEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
