package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_matching_score")
public class SourceMatchingScoreEntity implements Entity{

	private String _matching_score_id;
	private String _customer_id;
	private String _v1;
	private int _show_index;
	private String _refresh;

	public SourceMatchingScoreEntity(){
		super();
	}

	public SourceMatchingScoreEntity(String _matching_score_id,String _customer_id,String _v1,int _show_index,String _refresh){
		this._matching_score_id = _matching_score_id;
		this._customer_id = _customer_id;
		this._v1 = _v1;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceMatchingScoreEntity ["+
			"	matching_score_id="+_matching_score_id+
			"	customer_id="+_customer_id+
			"	v1="+_v1+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "matching_score_id",type=ColumnType.VARCHAR)
	public String getMatchingScoreId(){
		return this._matching_score_id; 
	}

	public void setMatchingScoreId(String _matching_score_id){
		this._matching_score_id = _matching_score_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "v1",type=ColumnType.VARCHAR)
	public String getV1(){
		return this._v1; 
	}

	public void setV1(String _v1){
		this._v1 = _v1;
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
	public static SourceMatchingScoreEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMatchingScoreEntityAuxiliary();
	}

	public static class SourceMatchingScoreEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMatchingScoreEntityAuxiliary asMatchingScoreId(String colName, CustomColumn<?, ?>... cols){
			setColName("matching_score_id","`matching_score_id`", colName, "setMatchingScoreId", "getString", cols);
			return this;
		}
		public SourceMatchingScoreEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMatchingScoreEntityAuxiliary asV1(String colName, CustomColumn<?, ?>... cols){
			setColName("v1","`v1`", colName, "setV1", "getString", cols);
			return this;
		}
		public SourceMatchingScoreEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceMatchingScoreEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
