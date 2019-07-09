package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_satfac_genre")
public class SourceDimSatfacGenreEntity implements Entity{

	private String _satfac_genre_id;
	private String _customer_id;
	private String _satfac_name;
	private String _satfac_genre_parent_id;
	private int _is_children;
	private String _create_date;
	private int _show_index;

	public SourceDimSatfacGenreEntity(){
		super();
	}

	public SourceDimSatfacGenreEntity(String _satfac_genre_id,String _customer_id,String _satfac_name,String _satfac_genre_parent_id,int _is_children,String _create_date,int _show_index){
		this._satfac_genre_id = _satfac_genre_id;
		this._customer_id = _customer_id;
		this._satfac_name = _satfac_name;
		this._satfac_genre_parent_id = _satfac_genre_parent_id;
		this._is_children = _is_children;
		this._create_date = _create_date;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimSatfacGenreEntity ["+
			"	satfac_genre_id="+_satfac_genre_id+
			"	customer_id="+_customer_id+
			"	satfac_name="+_satfac_name+
			"	satfac_genre_parent_id="+_satfac_genre_parent_id+
			"	is_children="+_is_children+
			"	create_date="+_create_date+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "satfac_genre_id",type=ColumnType.VARCHAR)
	public String getSatfacGenreId(){
		return this._satfac_genre_id; 
	}

	public void setSatfacGenreId(String _satfac_genre_id){
		this._satfac_genre_id = _satfac_genre_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "satfac_name",type=ColumnType.VARCHAR)
	public String getSatfacName(){
		return this._satfac_name; 
	}

	public void setSatfacName(String _satfac_name){
		this._satfac_name = _satfac_name;
	}

	@Column(name = "satfac_genre_parent_id",type=ColumnType.VARCHAR)
	public String getSatfacGenreParentId(){
		return this._satfac_genre_parent_id; 
	}

	public void setSatfacGenreParentId(String _satfac_genre_parent_id){
		this._satfac_genre_parent_id = _satfac_genre_parent_id;
	}

	@Column(name = "is_children",type=ColumnType.TINYINT)
	public int getIsChildren(){
		return this._is_children; 
	}

	public void setIsChildren(int _is_children){
		this._is_children = _is_children;
	}

	@Column(name = "create_date",type=ColumnType.DATE)
	public String getCreateDate(){
		return this._create_date; 
	}

	public void setCreateDate(String _create_date){
		this._create_date = _create_date;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimSatfacGenreEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimSatfacGenreEntityAuxiliary();
	}

	public static class SourceDimSatfacGenreEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimSatfacGenreEntityAuxiliary asSatfacGenreId(String colName, CustomColumn<?, ?>... cols){
			setColName("satfac_genre_id","`satfac_genre_id`", colName, "setSatfacGenreId", "getString", cols);
			return this;
		}
		public SourceDimSatfacGenreEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimSatfacGenreEntityAuxiliary asSatfacName(String colName, CustomColumn<?, ?>... cols){
			setColName("satfac_name","`satfac_name`", colName, "setSatfacName", "getString", cols);
			return this;
		}
		public SourceDimSatfacGenreEntityAuxiliary asSatfacGenreParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("satfac_genre_parent_id","`satfac_genre_parent_id`", colName, "setSatfacGenreParentId", "getString", cols);
			return this;
		}
		public SourceDimSatfacGenreEntityAuxiliary asIsChildren(String colName, CustomColumn<?, ?>... cols){
			setColName("is_children","`is_children`", colName, "setIsChildren", "getInt", cols);
			return this;
		}
		public SourceDimSatfacGenreEntityAuxiliary asCreateDate(String colName, CustomColumn<?, ?>... cols){
			setColName("create_date","`create_date`", colName, "setCreateDate", "getString", cols);
			return this;
		}
		public SourceDimSatfacGenreEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
