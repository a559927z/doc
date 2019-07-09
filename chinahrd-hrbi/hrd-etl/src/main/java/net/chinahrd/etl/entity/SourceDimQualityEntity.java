package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_quality")
public class SourceDimQualityEntity implements Entity{

	private String _quality_id;
	private String _customer_id;
	private String _vocabulary;
	private String _note;
	private int _show_index;
	private String _refresh;

	public SourceDimQualityEntity(){
		super();
	}

	public SourceDimQualityEntity(String _quality_id,String _customer_id,String _vocabulary,String _note,int _show_index,String _refresh){
		this._quality_id = _quality_id;
		this._customer_id = _customer_id;
		this._vocabulary = _vocabulary;
		this._note = _note;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimQualityEntity ["+
			"	quality_id="+_quality_id+
			"	customer_id="+_customer_id+
			"	vocabulary="+_vocabulary+
			"	note="+_note+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "quality_id",type=ColumnType.VARCHAR)
	public String getQualityId(){
		return this._quality_id; 
	}

	public void setQualityId(String _quality_id){
		this._quality_id = _quality_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "vocabulary",type=ColumnType.VARCHAR)
	public String getVocabulary(){
		return this._vocabulary; 
	}

	public void setVocabulary(String _vocabulary){
		this._vocabulary = _vocabulary;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
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
	public static SourceDimQualityEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimQualityEntityAuxiliary();
	}

	public static class SourceDimQualityEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimQualityEntityAuxiliary asQualityId(String colName, CustomColumn<?, ?>... cols){
			setColName("quality_id","`quality_id`", colName, "setQualityId", "getString", cols);
			return this;
		}
		public SourceDimQualityEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimQualityEntityAuxiliary asVocabulary(String colName, CustomColumn<?, ?>... cols){
			setColName("vocabulary","`vocabulary`", colName, "setVocabulary", "getString", cols);
			return this;
		}
		public SourceDimQualityEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceDimQualityEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimQualityEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
